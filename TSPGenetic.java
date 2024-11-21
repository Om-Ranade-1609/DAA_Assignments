import java.util.*;

public class TSPGeneticAlgorithm {
    static final int NUM_CITIES = 4;
    static final int POPULATION_SIZE = 6;
    static final int MAX_GENERATIONS = 1000;
    static final double MUTATION_RATE = 0.05;
    static final double CROSSOVER_RATE = 0.9;
    
    static int[][] cost = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };
    
    // Generate a random path (permutation of cities)
    static List<Integer> generateRandomPath() {
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < NUM_CITIES; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        return path;
    }
    
    // Calculate the total cost of a path
    static int calculateCost(List<Integer> path) {
        int costSum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            costSum += cost[path.get(i)][path.get(i + 1)];
        }
        costSum += cost[path.get(path.size() - 1)][path.get(0)]; // Return to the start city
        return costSum;
    }
    
    // Perform crossover between two paths
    static List<Integer> crossover(List<Integer> parent1, List<Integer> parent2) {
        Random rand = new Random();
        List<Integer> offspring = new ArrayList<>(Collections.nCopies(NUM_CITIES, -1));

        int start = rand.nextInt(NUM_CITIES);
        int end = rand.nextInt(NUM_CITIES);

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Copy the segment from parent1
        for (int i = start; i <= end; i++) {
            offspring.set(i, parent1.get(i));
        }

        // Fill in the remaining cities from parent2
        for (int i = 0; i < NUM_CITIES; i++) {
            if (!offspring.contains(parent2.get(i))) {
                for (int j = 0; j < NUM_CITIES; j++) {
                    if (offspring.get(j) == -1) {
                        offspring.set(j, parent2.get(i));
                        break;
                    }
                }
            }
        }

        return offspring;
    }
    
    // Perform mutation by swapping two cities in the path
    static void mutate(List<Integer> path) {
        Random rand = new Random();
        if (rand.nextDouble() < MUTATION_RATE) {
            int i = rand.nextInt(NUM_CITIES);
            int j = rand.nextInt(NUM_CITIES);
            Collections.swap(path, i, j);
        }
    }

    // Select parents using tournament selection
    static List<List<Integer>> selectParents(List<List<Integer>> population) {
        Random rand = new Random();
        List<List<Integer>> selectedParents = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            List<Integer> parent1 = tournamentSelection(population);
            List<Integer> parent2 = tournamentSelection(population);
            selectedParents.add(parent1);
            selectedParents.add(parent2);
        }
        
        return selectedParents;
    }

    // Tournament selection for parent selection
    static List<Integer> tournamentSelection(List<List<Integer>> population) {
        Random rand = new Random();
        List<List<Integer>> tournament = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tournament.add(population.get(rand.nextInt(population.size())));
        }

        // Find the best path (minimum cost) in the tournament
        return tournament.stream()
                         .min(Comparator.comparingInt(TSPGeneticAlgorithm::calculateCost))
                         .get();
    }

    // Main genetic algorithm function
    public static void main(String[] args) {
        // Generate initial population
        List<List<Integer>> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(generateRandomPath());
        }

        int generation = 0;
        List<Integer> bestPath = null;
        int bestCost = Integer.MAX_VALUE;

        while (generation < MAX_GENERATIONS) {
            List<List<Integer>> newPopulation = new ArrayList<>();

            // Crossover and mutation to generate the new population
            while (newPopulation.size() < POPULATION_SIZE) {
                List<List<Integer>> parents = selectParents(population);

                List<Integer> parent1 = parents.get(0);
                List<Integer> parent2 = parents.get(1);

                List<Integer> offspring;
                if (Math.random() < CROSSOVER_RATE) {
                    offspring = crossover(parent1, parent2);
                } else {
                    offspring = new ArrayList<>(parent1); // No crossover, just copy
                }

                mutate(offspring);
                newPopulation.add(offspring);
            }

            // Replace old population with new one
            population = newPopulation;

            // Find the best path in the current generation
            for (List<Integer> path : population) {
                int cost = calculateCost(path);
                if (cost < bestCost) {
                    bestCost = cost;
                    bestPath = path;
                }
            }

            generation++;
        }

        // Print the result
        System.out.println("Best path: " + bestPath);
        System.out.println("Minimum cost: " + bestCost);
    }
}
