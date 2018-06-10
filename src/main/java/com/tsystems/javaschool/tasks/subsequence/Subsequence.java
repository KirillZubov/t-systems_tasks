package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */

    boolean find(List x, List y) {
        int index = 0;

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        if (index == x.size()) return true;

        for (Object i : y) {
            if (index == x.size()) return true;
            if (i.equals(x.get(index))) index++;
        }
        return false;
    }
}
