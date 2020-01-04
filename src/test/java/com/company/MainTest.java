package com.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }


    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @DisplayName("Testing input files:")
    @MethodSource("argumentFactory")
    void MainTest_parametrized(String fileName, int expected, String description) {

        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
        FileInputStream testFile = new FileInputStream(fileName);
        //System.setIn(testFile);
        //when

        //then
    }

    private static Stream<Arguments> argumentFactory(){

        return Stream.of(
                Arguments.of("qqq QQQ a AAA aaaaaaacvvv", 5, "(Correctly calculated mixed set of words)"),
                Arguments.of(null, 0, "(Correctly calculated \"0\" words when String is null)"),
                Arguments.of("",0,"(Correctly calculated words when String is empty)"),
                Arguments.of("aaaQQQ",1,"(Correctly calculated \"1\" when one word, few chars long)"),
                Arguments.of("q",1,"(Correctly calculated \"1\" when one word, one char long (other than whitespace))"),
                Arguments.of("\n",0,"(Correctly calculated \"0\" when one word, one char long (whitespace))"),
                Arguments.of("qqq   qqq    \n\n  QQQ",3,"(Correctly calculated words when separated with more than one whitespace)"),
                Arguments.of("                   \n\n", 0,"(Correctly calculated \"0\" when only whitespaces)"),
                Arguments.of("q w e r t y",6,"(Correctly calculating set of single char words)")
        );
    }
}