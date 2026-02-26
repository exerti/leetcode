package 回溯o;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CombineSolution {
    public List<List<Integer>> combine(int n, int k) {
        Bfs(k, n, 1);
        return result;
    }

    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();

    void Bfs(int k, int n, int startIndex) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++) {
            path.add(i);
            Bfs(k, n, i + 1);
            path.removeLast();

        }
    }

    public static void main(String[] args) {
        CombineSolution solution = new CombineSolution();

        // Example 1: n = 4, k = 2
        System.out.println("Example 1: combine(4, 2)");
        List<List<Integer>> result1 = solution.combine(4, 2);
        System.out.println(result1);
        // Output: [[1,2], [1,3], [1,4], [2,3], [2,4], [3,4]]

        // Example 2: n = 1, k = 1
        System.out.println("\nExample 2: combine(1, 1)");
        List<List<Integer>> result2 = solution.combine(1, 1);
        System.out.println(result2);
        // Output: [[1]]
    }
};
