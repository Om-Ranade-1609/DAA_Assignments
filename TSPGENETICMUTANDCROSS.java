import java.util.*;

public class TSPGenetic {
    static int[][] cost = {{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}};
    static int N = 4, MAX_GEN = 1000, MUT_RATE = 5, CROSS_RATE = 90;

    static List<Integer> randomPath() {
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < N; i++) path.add(i);
        Collections.shuffle(path);
        return path;
    }

    static int calculateCost(List<Integer> path) {
        int costSum = 0;
        for (int i = 0; i < N - 1; i++) costSum += cost[path.get(i)][path.get(i + 1)];
        return costSum + cost[path.get(N - 1)][path.get(0)];
    }

    static List<Integer> crossover(List<Integer> p1, List<Integer> p2) {
        Random rand = new Random();
        int start = rand.nextInt(N), end = rand.nextInt(N);
        if (start > end) { int temp = start; start = end; end = temp; }
        List<Integer> child = new ArrayList<>(Collections.nCopies(N, -1));
        for (int i = start; i <= end; i++) child.set(i, p1.get(i));
        for (int i = 0; i < N; i++) if (!child.contains(p2.get(i))) {
            for (int j = 0; j < N; j++) if (child.get(j) == -1) {
                child.set(j, p2.get(i)); break;
            }
        }
        return child;
    }

    static void mutate(List<Integer> path) {
        Random rand = new Random();
        if (rand.nextDouble() < 0.05) Collections.swap(path, rand.nextInt(N), rand.nextInt(N));
    }

    public static void main(String[] args) {
        List<Integer> p1 = randomPath(), p2 = randomPath(), bestPath = p1;
        int bestCost = calculateCost(p1);
        for (int gen = 0; gen < MAX_GEN; gen++) {
            List<Integer> offspring = (new Random().nextInt(100) < CROSS_RATE) ? crossover(p1, p2) : new ArrayList<>(p1);
            mutate(offspring);
            int cost = calculateCost(offspring);
            if (cost < bestCost) {
                bestCost = cost;
                bestPath = offspring;
            }
        }
        System.out.println("Best Path: " + bestPath);
        System.out.println("Min Cost: " + bestCost);
    }
}
