package com.xoran.happycubes.cube;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 16.07.16
 * <p>
 * Represents set of cube parts
 * representation:
 * CubePart[6]
 * 0 1 2
 * 3 4 5
 */
public class CubeSet {
    public static final int ROW_COUNT = 2;
    public static final int COL_COUNT = 3;
    public static final int SIZE = COL_COUNT * ROW_COUNT;

    protected final CubePart[] cubeParts;

    /**
     * Creates new instance of Cube set from six {@link CubePart}
     */
    public CubeSet(
            @NotNull CubePart first,
            @NotNull CubePart second,
            @NotNull CubePart third,
            @NotNull CubePart forth,
            @NotNull CubePart fifth,
            @NotNull CubePart sixth) {
        cubeParts = new CubePart[]{
                first,
                second,
                third,
                forth,
                fifth,
                sixth
        };
    }

    /**
     * Copy constructor
     *
     * @param set Cube set to copy
     */
    protected CubeSet(@NotNull CubeSet set) {
        cubeParts = new CubePart[SIZE];
        for (int i = 0; i < set.cubeParts.length; i++) {
            cubeParts[i] = set.cubeParts[i].copy();
        }
    }

    public CubePart getPart(int index){
        return cubeParts[index];
    }

    /**
     * Creates a copy of current set
     *
     * @return
     */
    @NotNull
    public CubeSet copy() {
        return new CubeSet(this);
    }

    /**
     * Converts set to string by converting each {@link CubePart} to string
     * and arranging in the following order:
     * <p>
     * 1 2 3
     * 4 5 6
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int row = 0; row < ROW_COUNT; row++) {
            final String[][] cols = new String[COL_COUNT][CubePart.SIDE_SIZE];
            for (int i = 0; i < COL_COUNT; i++) {
                cols[i] = cubeParts[COL_COUNT * row + i].toString().split("\n");
            }
            for (int i = 0; i < CubePart.SIDE_SIZE; i++) {
                for (int j = 0; j < COL_COUNT; j++) {
                    sb.append(cols[j][i]);
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Sets are equals if each {@link CubePart} in corresponding places of two sets are equal
     *
     * @see CubePart#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CubeSet cubeSet = (CubeSet) o;

        for (int i = 0; i < SIZE; i++) {
            if (!Objects.equals(cubeParts[i], cubeSet.cubeParts[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates hash code corresponding to equals method
     * @see CubeSet#equals(Object)
     * @see CubePart#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        for (int i = 0; i < SIZE; i++) {
            result = 37 * result + cubeParts[i].hashCode();
        }
        return result;
    }
}
