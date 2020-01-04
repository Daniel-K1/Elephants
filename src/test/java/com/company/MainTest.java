package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @AfterEach
    void tearDown() {
        DataContainer.getInstance().getElephantsWeights().clear();
        DataContainer.getInstance().getActualOrder().clear();
        DataContainer.getInstance().getTargetOrder().clear();
    }

    @ParameterizedTest
    @DisplayName("Testing input files:")
    @MethodSource("argumentFactory")
    void MainTest_parametrized(String inputFile, String resultFile) throws IOException {

        //given
        InputStream testFile = getClass().getClassLoader().getResourceAsStream(inputFile);
        System.setIn(testFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream(resultFile))));

        //when
        Main.main(null);

        //then
        String expectedValue = br.readLine();
        String actualValue = outputStream.toString().trim();
        assertEquals(expectedValue, actualValue);
    }

    private static Stream<Arguments> argumentFactory() {

        return Stream.of(
                Arguments.of("slo1.in", "slo1.out"),
                Arguments.of("slo2.in", "slo2.out"),
                Arguments.of("slo3.in", "slo3.out")
        );
    }
}