package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        try {
            Collections.sort(inputNumbers);
        } catch (Throwable thr) {
            throw new CannotBuildPyramidException();
        }

        int length = inputNumbers.size();
        int i = 1;
        while (true) {
            if (length < 0) {
                throw new CannotBuildPyramidException();
            }
            if (length == 0) {
                break;
            }
            length -= i;
            i += 1;
        }
        int[][] ans = new int[i - 1][2 * (i - 1) - 1];
        int k = 0;
        int len = 1;
        for (int h = 0; h < ans.length; h++) {
            for (int j = 0; j < len; j++) {
                ans[h][i - 2 + 2 * j] = inputNumbers.get(k);
                k++;
            }
            i--;
            len++;
        }
        return ans;
    }


}
