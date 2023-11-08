package com.lgf.junit5examples.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListShrinkerTest {

    private final String VALUE_SEPARATOR = ",";
    private final String RANGE_SEPARATOR = "-";

    @Test
    @DisplayName("An empty input list should result in an empty shrinked list")
    public void noValues() {
        List<Integer> values = new ArrayList<Integer>();
        assertEquals("", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(values));
    }

    @Test
    @DisplayName("An one value input list should result in an one value shrinked list")
    public void onlyOneValue() {
        String inputValues = "1";
        assertEquals("1", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A two consecutive values input list should result in a two value shrinked list")
    public void twoConsecutiveValues() {
        String inputValues = "1,2";
        assertEquals("1-2", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A two non consecutive values input list should result in the same non consecutive two value shrinked list")
    public void twoNonConsecutiveValues() {
        String inputValues = "1,3";
        assertEquals(inputValues, ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A three non consecutive values input list should result in the same non consecutive three values shrinked list")
    public void threeNonConsecutiveValues() {
        String inputValues = "1,3,5";
        assertEquals(inputValues, ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A three consecutive values input list should result in a two value (min,max) shrinked list separated by a minus")
    public void threeConsecutiveValues() {
        String inputValues = "1,2,3";
        assertEquals("1-3", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A mixed consecutive and non consecutive values input list should result in shinkred list for consecutive ones and same list for the rest")
    public void consecutiveValuesPlusOneNonConsecutive() {
        String inputValues = "1,2,3,5";
        assertEquals("1-3,5", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A mixed consecutive and non consecutive values input list should result in shinkred list for consecutive ones and same list for the rest")
    public void consecutiveValuesPlusTwoNonConsecutive() {
        String inputValues = "1,2,3,5,7";
        assertEquals("1-3,5,7", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    @DisplayName("A two separated consecutive group values list should result in shinkred list for the consecutive groups separating each other by a coma")
    public void twoSeparatedConsecutivesGroups() {
        String inputValues = "1,2,3,5,6,7";
        assertEquals("1-3,5-7", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    public void unsortedList() {
        String inputValues = "2,1,6,4,8,5,10";
        assertEquals("1-2,4-6,8,10", ListShrinker.build(VALUE_SEPARATOR, RANGE_SEPARATOR).shrink(toIntegerList(inputValues)));
    }

    @Test
    public void consecutiveValuesPlusTwoNonConsecutiveDifSeparators() {
        String inputValues = "1,2,3,5,7";
        assertEquals(ListShrinker.build(".", "/").shrink(toIntegerList(inputValues)), "1/3.5.7");
    }

    @Test
    public void splitNoValue() {
        String inputValues = "";
        List<String> list = ListShrinker.split(inputValues, VALUE_SEPARATOR, 5);
        assertEquals(0, list.size(), "List must be empty");
    }

    @Test
    public void splitOneValueOneList() {
        String inputValues = "1";
        List<String> list = ListShrinker.split(inputValues, VALUE_SEPARATOR, 5);
        assertEquals(1, list.size(), "Number of lists should be equal to ");
        assertEquals(inputValues, list.get(0), "First list should be equal to ");
    }

    @Test
    public void splitMultipleValuesOneList() {
        String inputValues = "1,2,3";
        List<String> list = ListShrinker.split(inputValues, VALUE_SEPARATOR, 10);
        assertEquals(1, list.size(), "Number of lists should be equal to ");
        assertEquals(inputValues, list.get(0), "First list should be equal to ");
    }

    @Test
    public void splitMultipleValuesMultipleLists() {
        String inputValues = "1,2,3,5,7";
        List<String> list = ListShrinker.split(inputValues, VALUE_SEPARATOR, 5);
        assertEquals(3, list.size(), "Number of lists should be equal to ");
        assertEquals("1,2", list.get(0), "First list should be equal to ");
        assertEquals("3,5", list.get(1), "Second list should be equal to ");
        assertEquals("7", list.get(2), "Second list should be equal to ");
    }

    @Test
    public void splitMultipleValuesMultipleListsWithRanges() {
        String inputValues = "1-3,5,7-11,15,21";
        List<String> list = ListShrinker.split(inputValues, VALUE_SEPARATOR, 8);
        assertEquals(3, list.size(), "Number of lists should be equal to ");
        assertEquals("1-3,5", list.get(0), "First list should be equal to ");
        assertEquals("7-11,15", list.get(1), "Second list should be equal to ");
        assertEquals("21", list.get(2), "Second list should be equal to ");
    }

    private List<Integer> toIntegerList(String inputValues) {
        return Arrays.asList(inputValues.split(",")).stream().map(o -> Integer.parseInt(o)).collect(Collectors.toList());
    }

}
