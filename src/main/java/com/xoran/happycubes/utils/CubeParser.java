package com.xoran.happycubes.utils;

import com.xoran.happycubes.cube.CubePart;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 * <p>
 * Helps to parse from string to cube
 */
public class CubeParser {

    /**
     * Represents protrusion in string
     */
    private static final String TRUE_SYMBOL = "[]";

    /**
     * Represents notch in string
     */
    private static final String FALSE_SYMBOL = "  ";

    /**
     * {@link LoopIndex},  used by navigating through edge
     */
    private static final LoopIndex loopIndex = new LoopIndex(CubePart.SIZE);

    /**
     * Sets edge at particular index
     *
     * @param index Index of edge lopped by {@link LoopIndex}
     * @param value True if it is notch, false if protrusion
     */
    private static void setEdge(boolean[] edge, int index, boolean value) {
        edge[loopIndex.get(index)] = value;
    }


    /**
     * Parse string into boolean array
     *
     * @param str String to parse
     * @return Boolean array which represents edge
     */
    @NotNull
    public static boolean[] fromString(@NotNull String str) throws ParseException {
        if (str == null || str.isEmpty()) {
            throw new ParseException("String should contain rows and columns", 0);
        }
        final String[] rows = str.split("\n");
        if (rows.length != CubePart.SIDE_SIZE) {
            throw new ParseException("Wrong count of rows: expected - " + CubePart.SIDE_SIZE + ", actual - " + rows.length, 0);
        }
        final boolean[] edge = new boolean[CubePart.SIZE];
        final int substringSize = 2;
        final int rowSize = CubePart.SIDE_SIZE * substringSize;

        for (int i = 0; i < CubePart.SIDE_SIZE; i++) {
            if (rows[i].length() < rowSize) {
                throw new ParseException("Row " + i + " is not valid", rows[i].length());
            }
        }

        //first row
        for (int i = 0; i < CubePart.SIDE_SIZE; i++) {
            int start = i * substringSize;
            String substring = rows[0].substring(start, start + substringSize);
            try {
                setEdge(edge, i, toBool(substring));
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage(), start);
            }
        }

        //central rows
        for (int i = 1; i < CubePart.SIDE_SIZE - 1; i++) {
            //first
            int start = 0;
            String substring = rows[i].substring(start, start + substringSize);
            try {
                setEdge(edge, -i, toBool(substring));
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage(), i * CubePart.SIDE_SIZE + start);
            }
            //last
            start = rowSize - substringSize;
            substring = rows[i].substring(start, start + substringSize);
            try {
                setEdge(edge, i + CubePart.SIDE_SIZE - 1, toBool(substring));
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage(), i * CubePart.SIDE_SIZE + start);
            }
        }

        //last row
        for (int i = 0; i < CubePart.SIDE_SIZE; i++) {
            int start = i * substringSize;
            int rowId = CubePart.SIDE_SIZE - 1;
            String substring = rows[rowId].substring(start, start + substringSize);
            try {
                setEdge(edge, CubePart.SIZE - CubePart.SIDE_SIZE - i + 1, toBool(substring));
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage(), rowId * CubePart.SIDE_SIZE + start);
            }
        }

        return edge;
    }

    /**
     * Converts string to boolean
     */
    private static boolean toBool(String str) {
        if (str.equals(TRUE_SYMBOL)) {
            return true;
        } else if (str.equals(FALSE_SYMBOL)) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid symbol " + str);
        }
    }

    @NotNull
    public static String toString(@NotNull CubePart cubePart) {
        final StringBuilder sb = new StringBuilder();

        //first row
        final CubePart.SideIterator top = cubePart.getSideIterator(Side.TOP);
        while (top.hasNext()) {
            sb.append(fromBool(top.next()));
        }
        sb.append("\n");

        //central rows
        final CubePart.SideIterator right = cubePart.getSideIterator(Side.RIGHT);
        final CubePart.SideIterator left = cubePart.getSideIterator(Side.LEFT);
        right.next();
        left.next();
        for (int i = 1; i < CubePart.SIDE_SIZE - 1; i++) {
            //first
            sb.append(fromBool(left.next()));
            //central
            for (int j = 1; j < CubePart.SIDE_SIZE - 1; j++) {
                sb.append(fromBool(true));
            }
            //last
            sb.append(fromBool(right.next()));
            sb.append("\n");
        }

        //last row
        final CubePart.SideIterator bottom = cubePart.getSideIterator(Side.BOTTOM);
        while (bottom.hasNext()) {
            sb.append(fromBool(bottom.next()));
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Converts boolean to string
     */
    private static String fromBool(boolean val) {
        return val ? TRUE_SYMBOL : FALSE_SYMBOL;
    }

}
