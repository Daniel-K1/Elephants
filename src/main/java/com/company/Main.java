package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            DataContainer.getInstance().fetchWithData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Integer>> cycles = convertToSingleCycles();

        System.out.println(findOptimum(cycles));
    }

    private static int findOptimum(List<List<Integer>> cycles) {

        int result = 0;
        for (List<Integer> cycle : cycles) {

            if (cycle.size() > 1) {
                result += Math.min(calculateByDividingCycles(cycle), calculateByMergingCycles(cycle));
            }
        }

        return result;
    }

    private static List<List<Integer>> convertToSingleCycles() {

        List<List<Integer>> simpleCycles = new ArrayList<>();
        List<Integer> singleSimpleCycle = new ArrayList<>();

        for (int i = 0; i < DataContainer.getInstance().getActualOrder().size(); i++) {
            if (DataContainer.getInstance().getActualOrder().get(i).equals(DataContainer.getInstance().getTargetOrder().get(i))) {
                singleSimpleCycle.add(DataContainer.getInstance().getActualOrder().get(i));
                simpleCycles.add(new ArrayList<>(singleSimpleCycle));
                singleSimpleCycle.clear();
                DataContainer.getInstance().getTargetOrder().remove(i);
                DataContainer.getInstance().getActualOrder().remove(i);
            }
        }

        while (DataContainer.getInstance().getActualOrder().size() != 0) {
            int buffer;
            int position = 0;
            singleSimpleCycle.add(DataContainer.getInstance().getActualOrder().get(0));
            buffer = DataContainer.getInstance().getTargetOrder().get(0);

            while (!singleSimpleCycle.contains(buffer)) {

                singleSimpleCycle.add(buffer);
                DataContainer.getInstance().getActualOrder().remove(position);
                DataContainer.getInstance().getTargetOrder().remove(position);
                position = DataContainer.getInstance().getActualOrder().indexOf(buffer);
                buffer = DataContainer.getInstance().getTargetOrder().get(position);
            }

            simpleCycles.add(new ArrayList<>(singleSimpleCycle));
            singleSimpleCycle.clear();
            DataContainer.getInstance().getActualOrder().remove(position);
            DataContainer.getInstance().getTargetOrder().remove(position);
        }

        return simpleCycles;
    }

    private static int calculateByDividingCycles(List<Integer> cycle) {

        int result = 0;

        result += sumElephantsWeightsIn(cycle);

        result += findLocalMinimumWeightFrom(cycle) * (cycle.size() - 2);

        return result;
    }

    private static int sumElephantsWeightsIn(List<Integer> cycle) {

        int result = 0;

        for (Integer integer : cycle) {
            result += DataContainer.getInstance().getElephantsWeights().get(integer - 1);
        }

        return result;
    }

    private static int calculateByMergingCycles(List<Integer> cycle) {

        int result = 0;

        int globalMinWeight = findGlobalMinimumWeight();

        result += sumElephantsWeightsIn(cycle);

        result += findLocalMinimumWeightFrom(cycle);
        result += globalMinWeight * (cycle.size() + 1);

        return result;
    }

    private static int findGlobalMinimumWeight() {

        return Collections.min(DataContainer.getInstance().getElephantsWeights());
    }

    private static int findLocalMinimumWeightFrom(List<Integer> singleCycle) {

        int elephantIndex = Collections.min(singleCycle, (first, second) -> {

            int firstWeight = DataContainer.getInstance().getElephantsWeights().get(first - 1);
            int secondWeight = DataContainer.getInstance().getElephantsWeights().get(second - 1);

            return firstWeight - secondWeight;
        });

        return DataContainer.getInstance().getElephantsWeights().get(elephantIndex - 1);
    }
}