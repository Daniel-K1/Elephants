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
                Arguments.of("slo1ocen.in", "slo1ocen.out"),
                Arguments.of("slo2.in", "slo2.out"),
                Arguments.of("slo2ocen.in", "slo2ocen.out"),
                Arguments.of("slo3.in", "slo3.out"),
                Arguments.of("slo3ocen.in", "slo3ocen.out"),
                Arguments.of("slo4.in", "slo4.out"),
                Arguments.of("slo4ocen.in", "slo4ocen.out"),
                Arguments.of("slo5.in", "slo5.out"),
                Arguments.of("slo6.in", "slo6.out"),
                Arguments.of("slo7.in", "slo7.out"),
                Arguments.of("slo8a.in", "slo8a.out"),
                Arguments.of("slo8b.in", "slo8b.out"),
                Arguments.of("slo9a.in", "slo9a.out"),
                Arguments.of("slo9b.in", "slo9b.out"),
                Arguments.of("slo10a.in", "slo10a.out"),
                Arguments.of("slo10b.in", "slo10b.out")
        );
    }
}