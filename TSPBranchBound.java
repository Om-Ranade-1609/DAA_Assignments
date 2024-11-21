import java.util.*;

public class TSPBranchBound {
    static int N = 4; // Number of cities
    static int[][] cost = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    static class Node {
        int level, bound, pathCost;
        int[] path = new int[N + 1];

        Node(int level, int bound, int pathCost, int[] path) {
            this.level = level;
            this.bound = bound;
            this.pathCost = pathCost;
            this.path = path.clone();
        }
    }

    // Calculate the lower bound of the node
    static int calculateBound(Node node) {
        int bound = node.pathCost;

        // Compute the minimum outgoing edges for each unvisited city
        boolean[] visited = new boolean[N];
        for (int i = 0; i < node.level; i++) {
            visited[node.path[i]] = true;
        }

        for (int i = 0; i < node.level; i++) {
            int u = node.path[i];
            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
            for (int v = 0; v < N; v++) {
                if (!visited[v] && cost[u][v] < min1) {
                    min2 = min1;
                    min1 = cost[u][v];
                } else if (!visited[v] && cost[u][v] < min2) {
                    min2 = cost[u][v];
                }
            }
            if (min1 != Integer.MAX_VALUE) {
                bound += min1 + min2;
            }
        }
        return bound;
    }

    // Branch and Bound to find the minimum cost
    static int branchBound() {
        int[] bestPath = new int[N + 1];
        int minCost = Integer.MAX_VALUE;
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.bound));

        // Start with the first city in the path
        int[] startPath = new int[N + 1];
        startPath[0] = 0; // Start at city 0
        Node startNode = new Node(1, 0, 0, startPath);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            // If all cities are visited, check the cost and update the best path
            if (currentNode.level == N) {
                int totalCost = currentNode.pathCost + cost[currentNode.path[N - 1]][0]; // Add the return to start city
                if (totalCost < minCost) {
                    minCost = totalCost;
                    System.arraycopy(currentNode.path, 0, bestPath, 0, N);
                }
            } else {
                for (int i = 0; i < N; i++) {
                    if (currentNode.path[i] == 0) { // Not visited city i
                        int[] newPath = Arrays.copyOf(currentNode.path, N + 1);
                        newPath[currentNode.level] = i;
                        int newCost = currentNode.pathCost + cost[currentNode.path[currentNode.level - 1]][i];

                        // Create the new node after calculating the path and cost
                        Node newNode = new Node(currentNode.level + 1, 0, newCost, newPath);

                        // Calculate the bound for the new node
                        newNode.bound = calculateBound(newNode);

                        // If the bound is less than the current minimum cost, add to the queue
                        if (newNode.bound < minCost) {
                            queue.add(newNode);
                        }
                    }
                }
            }
        }
        System.out.println("Best path: " + Arrays.toString(bestPath));
        return minCost;
    }

    public static void main(String[] args) {
        int minCost = branchBound();
        System.out.println("Minimum Cost: " + minCost);
    }
}
