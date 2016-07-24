package com.xoran.happycubes.utils;

import com.xoran.happycubes.cube.CubePart;

import java.util.ArrayList;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 17.07.16
 */
public class CubeUtils {

    /**
     * Returns last element of iterator
     *
     * @param it Iterator
     * @return Last element
     */
    public static boolean getLast(CubePart.SideIterator it) {
        while (it.hasNext()) {
            it.next();
        }
        return it.current();
    }

    /**
     * Chacks that sides of two parts are match:
     * 1. One of corresponding elements of each side should be true, another false
     * 2. First and last elements can be both empty
     * e.g. these two matches
     *   []  []   |    [][][]
     * [][][][]   |[][][][]
     * [][][][][] |  [][][][]
     *   [][][]   |[][][][][]
     *   [][][][] |  [][][]
     *
     * @param cubePart1 First part to compare
     * @param side1     Side of first part to compare
     * @param cubePart2 Second part to compare
     * @param side2     Side of second part to compare
     */
    public static boolean areMatch(CubePart cubePart1, Side side1,
                                   CubePart cubePart2, Side side2) {
        CubePart.SideIterator it1 = cubePart1.getSideIterator(side1);
        CubePart.SideIterator it2 = cubePart2.getSideIterator(side2);


        //first and last can both be empty, can be filled by the third one
        if (it1.next() & it2.next()) {
            return false;
        }

        while (it1.hasNext() && it2.hasNext()) {
            if (it1.next().equals(it2.next())) {
                //if last, both can be empty
                if ((it1.hasNext() || it2.hasNext())
                        || (it1.current() && it2.current())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds all unique parts which matches to given cube part and side
     *
     * @param cubePart1 Cube part
     * @param side1     Cube side
     * @param cubePart2 Cube part to match with
     * @return List of matched unique combinations of cubePart2
     */
    public static ArrayList<CubePart> findAllUniqueMatches(CubePart cubePart1, Side side1, CubePart cubePart2) {
        CubePart tmpCube = cubePart2.copy();
        ArrayList<CubePart> parts = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < CubePart.SIDE_COUNT; i++) {

                boolean duplicate = false;
                for (CubePart part : parts) {
                    if (part.equals(tmpCube)) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    if (areMatch(cubePart1, side1, tmpCube, side1.getOpposite())) {
                        parts.add(tmpCube.copy());
                    }
                }

                tmpCube.rotate();
            }
            tmpCube.mirror();
        }
        return parts;
    }

    /**
     * Finds all unique parts for this CubePart that can be got
     * by rotating and mirroring
     *
     * @param cubePart Cube part
     * @return List of all unique parts
     */
    public static ArrayList<CubePart> findAllUniqueParts(CubePart cubePart) {
        CubePart tmpCube = cubePart.copy();
        ArrayList<CubePart> parts = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < CubePart.SIDE_COUNT; i++) {

                boolean duplicate = false;
                for (CubePart part : parts) {
                    if (part.equals(tmpCube)) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    parts.add(tmpCube.copy());
                }

                tmpCube.rotate();
            }
            tmpCube.mirror();
        }
        return parts;
    }

    /**
     * Checks that corner is filled and not overlapped
     * Checks that parts 2 and 3 matches after folding
     * left top
     * 2
     * 3 1
     * <p>
     * right top
     * 2
     * 1 3
     * <p>
     * left bottom
     * 3 1
     * 2
     * <p>
     * right bottom
     * 1 3
     * 2
     */
    public static boolean checkCorners(CubePart first, CubePart second, CubePart third, boolean top, boolean right) {
        Side horisontalSide;
        if (right) {
            horisontalSide = Side.RIGHT;
        } else {
            horisontalSide = Side.LEFT;
        }

        Side verticalSide;
        if (top) {
            verticalSide = Side.TOP;
        } else {
            verticalSide = Side.BOTTOM;
        }

        int cornersCount = 0;
        if (first.getCorner(verticalSide, horisontalSide)) cornersCount++;
        if (second.getCorner(verticalSide.getOpposite(), horisontalSide)) cornersCount++;
        if (third.getCorner(verticalSide, horisontalSide.getOpposite())) cornersCount++;

        if (cornersCount == 1) {
            CubePart secondTmp = second.copy();
            if (top ^ right) {
                secondTmp.rotateInverse();
            } else {
                secondTmp.rotate();
            }

            return areMatch(secondTmp, verticalSide.getOpposite(), third, verticalSide);
        } else {
            return false;
        }
    }


    /**
     * Checks all corners and parts matches for cross
     * 2
     * 5 1 3
     * 4
     *
     * @return true if all parts matches and corners are valid, false otherwise
     */
    public static boolean checkCrossCorners(CubePart first, CubePart second, CubePart third, CubePart forth, CubePart fifth) {
        return
                checkCorners(first, second, third, true, true)
                        && checkCorners(first, forth, third, false, true)
                        && checkCorners(first, forth, fifth, false, false)
                        && checkCorners(first, second, fifth, true, false);
    }


}
