package 回溯o;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CombineSolution3 {
  List<List<Integer>> result = new ArrayList<>();
  LinkedList<Integer> path = new LinkedList<>();
  Integer sum = 0;

  public List<List<Integer>> combinationSum3(int k, int n) {
    combine(k, n, 1);
    return result;
  }

  void combine(int k, int n, int startIndex) {
    if (sum > n || path.size() > k) {
      return;
    }
    if (sum == n && path.size() == k) {
      result.add(new ArrayList<>(path));
      return;
    }

    for (int i = startIndex; i <= 9 - (k - path.size()) + 1; i++) {
      path.add(i);
      sum += i;
      combine(k, n, i + 1);
      sum -= i;
      path.removeLast();
    }

  }

  public static void main(String[] args) {
    CombineSolution3 solution3 = new CombineSolution3();
    System.out.println("example 1:combinationSum3(3, 9) ");
    List<List<Integer>> result1 = solution3.combinationSum3(3, 9);
    System.out.println(result1);
  }
}
