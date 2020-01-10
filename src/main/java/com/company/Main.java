package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static long globalMinWeight;

    public static void main(String[] args) {

        try {
            DataContainer.getInstance().fetchWithData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        globalMinWeight = findGlobalMinimumWeight();
        List<List<Integer>> cycles = convertToSingleCycles();
        System.out.println(findOptimum(cycles));
    }

    private static long findOptimum(List<List<Integer>> cycles) {

        long result = 0;
        for (List<Integer> cycle : cycles) {

            result += Math.min(calculateByDividingCycles(cycle), calculateByMergingCycles(cycle));
        }

        return result;
    }

    private static List<List<Integer>> convertToSingleCycles() {

        List<List<Integer>> simpleCycles = new ArrayList<>();

        int[] permutation = new int[DataContainer.getInstance().getNumberOfElephants() + 1];

        for (int i = 0; i < DataContainer.getInstance().getNumberOfElephants(); i++) {

            permutation[DataContainer.getInstance().getTargetOrder().get(i)]
                    = DataContainer.getInstance().getActualOrder().get(i);
        }

        boolean[] visited = new boolean[DataContainer.getInstance().getNumberOfElephants() + 1];

        List<Integer> singleSimpleCycle = new ArrayList<>();
        for (int i = 1; i < DataContainer.getInstance().getNumberOfElephants(); i++) {
            singleSimpleCycle.clear();

            if (!visited[i]) {
                int x = i;
                while (!visited[x]) {
                    visited[x] = true;
                    singleSimpleCycle.add(x);
                    x = permutation[x];
                }
            }

            if (singleSimpleCycle.size() > 1) {
                simpleCycles.add(new ArrayList<>(singleSimpleCycle));
            }
        }

        return simpleCycles;
    }

    private static long calculateByDividingCycles(List<Integer> cycle) {

        long result = 0;
        result += sumElephantsWeightsIn(cycle);
        result += findLocalMinimumWeightFrom(cycle) * (cycle.size() - 2);
        return result;
    }

    private static long sumElephantsWeightsIn(List<Integer> cycle) {

        long result = 0;

        for (Integer integer : cycle) {
            result += DataContainer.getInstance().getElephantsWeights().get(integer - 1);
        }

        return result;
    }

    private static long calculateByMergingCycles(List<Integer> cycle) {

        long result = 0;

        result += sumElephantsWeightsIn(cycle);
        result += findLocalMinimumWeightFrom(cycle);
        result += globalMinWeight * (cycle.size() + 1);

        return result;
    }

    private static long findGlobalMinimumWeight() {

        return Collections.min(DataContainer.getInstance().getElephantsWeights());
    }

    private static long findLocalMinimumWeightFrom(List<Integer> singleCycle) {

        int elephantIndex = Collections.min(singleCycle, (first, second) -> {

            int firstWeight = DataContainer.getInstance().getElephantsWeights().get(first - 1);
            int secondWeight = DataContainer.getInstance().getElephantsWeights().get(second - 1);

            return firstWeight - secondWeight;
        });

        return DataContainer.getInstance().getElephantsWeights().get(elephantIndex - 1);
    }
}