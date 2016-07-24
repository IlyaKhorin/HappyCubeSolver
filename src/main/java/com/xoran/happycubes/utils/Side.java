package com.xoran.happycubes.utils;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 22.07.16
 */

/**
 * Represents enum of sides in clockwise direction
 *             TOP(0)
 *          __________
 *         |          |
 * LEFT(3) |          |RIGHT (1)
 *         |          |
 *         |__________|
 *           BOTTOM(2)
 */
public enum Side {
    TOP(0),
    RIGHT(1),
    BOTTOM(2),
    LEFT(3);

    private static final LoopIndex SIDE_INDEX = new LoopIndex(Side.values().length);

    private final int index;

    /**
     * Creates side with related integer index
     *
     * @param index side index
     */
    Side(int index) {
        this.index = index;
    }

    /**
     * return Side by index
     *
     * @param index index, related to Side. Index will be looped by {@link LoopIndex}
     * @return Side related to index
     */
    public static Side getByIndex(int index) {
        index = SIDE_INDEX.get(index);
        for (Side side : Side.values()) {
            if (side.getIndex() == index) {
                return side;
            }
        }
        throw new IllegalArgumentException("Side with index " + index + " doesn't exist");
    }

    /**
     * Get related index
     *
     * @return integer index related to Side
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns opposite side to this side
     *
     * @return Opposite side
     */
    public Side getOpposite() {
        return getByIndex(getIndex() + 2);
    }

    /**
     * Returns next side in clockwise direction
     *
     * @return Next side
     */
    public Side getNext() {
        return getByIndex(getIndex() + 1);
    }

    /**
     * is side RIGHT or LEFT
     *
     * @return True if RIGHT or LEFT
     */
    public boolean isHorizontal() {
        return getIndex() % 2 > 0;
    }

    /**
     * is side TOP or BOTTOM
     *
     * @return True if TOP or BOTTOM
     */
    public boolean isVertical() {
        return getIndex() % 2 == 0;
    }

}
