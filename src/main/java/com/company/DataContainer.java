package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DataContainer {

    private static DataContainer instance = new DataContainer();

    private int numberOfElephants;
    private List<Integer> elephantsWeights;
    private List<Integer> actualOrder;
    private List<Integer> targetOrder;


    private DataContainer() {

        numberOfElephants = 0;
        elephantsWeights = new ArrayList<>();
        actualOrder = new ArrayList<>();
        targetOrder = new ArrayList<>();
    }

    static DataContainer getInstance() {
        return instance;
    }

    void fetchWithData() throws IOException {

        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextLine()) {
            numberOfElephants = Integer.parseInt(scanner.nextLine());
        } else {
            throw new IOException("Empty file");
        }

        elephantsWeights.addAll(fillInList(scanner));
        actualOrder.addAll((fillInList(scanner)));
        targetOrder.addAll(fillInList(scanner));
    }

    private List<Integer> fillInList(Scanner scanner) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < numberOfElephants; i++) {
            list.add(scanner.nextInt());
        }

        return list;
 }

    List<Integer> getElephantsWeights() {
        return elephantsWeights;
    }

    List<Integer> getActualOrder() {
        return actualOrder;
    }

    List<Integer> getTargetOrder() {
        return targetOrder;
    }

    int getNumberOfElephants() {
        return numberOfElephants;
    }
}