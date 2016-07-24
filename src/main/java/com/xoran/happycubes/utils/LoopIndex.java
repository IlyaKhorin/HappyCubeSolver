package com.xoran.happycubes.utils;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 17.07.16
 * <p>
 * Loop index with fixed max maxSize
 * <p>
 * loopIndex = LoopIndex(5):
 * loopIndex.get(3) -> 3
 * loopIndex.get(6) -> 1
 * loopIndex.get(-3) -> 2
 * loopIndex.get(-6) -> 4
 */

public class LoopIndex {

    private final int maxSize;

    /**
     * Creates instance of LoopIndex with fixed max maxSize
     *
     * @param size Max maxSize
     */
    public LoopIndex(int size) {

        this.maxSize = size;
    }

    /**
     * returns value between 0 to Max Size
     *
     * @param i value which need to convert
     * @return converted value
     */
    public int get(int i) {
        if (i < 0) {
            if (i < -maxSize) {
                i %= maxSize;
            }
            if (i == 0) {
                return 0;
            } else {
                return maxSize + i;
            }
        } else {
            if (i >= maxSize) {
                i %= maxSize;
            }
            return i;
        }
    }
}
