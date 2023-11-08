package com.lgf.junit5examples.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListShrinker {

    private static final Logger log = LoggerFactory.getLogger(ListShrinker.class);

    private final String rangeSeparator;
    private final String listSeparator;

    private ListShrinker(String listSeparator, String rangeSeparator) {
        this.rangeSeparator = rangeSeparator;
        this.listSeparator = listSeparator;
    }

    public static ListShrinker build(String listSeparator, String rangeSeparator) {
        return new ListShrinker(listSeparator, rangeSeparator);
    }

    public static List<String> split(String list, String separator, int maxLenght) {

        if (StringUtils.isBlank(list)) {
            return new ArrayList<String>();
        } else if (list.length() < maxLenght) {
            return new ArrayList<String>(Collections.singletonList(list));
        } else {
            String leftList = list.substring(0, maxLenght);
            int lastSeparator = leftList.lastIndexOf(separator);
            leftList = list.substring(0, lastSeparator);
            List<String> reducedList = (new ArrayList<String>(Collections.singletonList(leftList)));
            reducedList.addAll(split(list.substring(lastSeparator + 1), separator, maxLenght));
            return reducedList;
        }
    }

    public String shrink(List<Integer> numbers) {
        String res = "";

        log.debug("Input: " + numbers.stream().map(o -> o.toString()).collect(Collectors.joining(",")));

        numbers.sort(Comparator.naturalOrder());

        int i = 0, j = 1;

        // loop through all items in array.
        while (i < numbers.size()) {
            // increase j while array[j] - array[j - 1] equals 1
            while (j < numbers.size() && (numbers.get(j) - numbers.get(j - 1)) == 1) {
                j++;
            }
            // we came out of that loop, no longer in a sequence.
            // write to the output.
            res += toTuple(i, j - 1, numbers);
            // i now points to j.
            // j is now i + 1;
            i = j;
            j = i + 1;
        }

        if (res.startsWith(listSeparator))
            res = res.substring(1);

        log.debug("Output: " + res);

        return res;
    }

    private String toTuple(int low, int high, List<Integer> numbers) {
        if (low == high)
            return listSeparator + numbers.get(low);

        return listSeparator + numbers.get(low) + rangeSeparator + numbers.get(high);
    }

}
