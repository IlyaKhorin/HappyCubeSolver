package com.xoran.happycubes.cube;

import com.xoran.happycubes.exceptions.ValidationException;
import com.xoran.happycubes.utils.CubeUtils;
import com.xoran.happycubes.utils.LoopIndex;
import com.xoran.happycubes.utils.Side;

import java.text.ParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 16.07.16
 * Represents one of cube edge
 * <p>
 * boolean[16]
 *             --->
 *   [ 0] [ 1] [ 2] [ 3] [ 4]
 * | [15] (  ) (  ) (  ) [ 5] |
 * | [14] (  ) (  ) (  ) [ 6] |
 * V [13] (  ) (  ) (  ) [ 7] V
 *   [12] [11] [10] [ 9] [ 8]
 *             --->
 */
public class CubePart {

    public static final int SIDE_SIZE = 5;
    public static final int SIDE_COUNT = 4;
    public static final int SIZE = (SIDE_SIZE - 1) * SIDE_COUNT;

    private static final LoopIndex loopIndex = new LoopIndex(SIZE);
    private static final String TRUE_SYMBOL = "[]";
    private static final String FALSE_SYMBOL = "  ";

    private final boolean[] edge;
    private int orientation;
    private boolean mirrored;

    private CubePart(){
        edge = new boolean[SIZE];
        orientation = 0;
        mirrored = false;
    }

    /**
     * Copy constructor
     *
     * @param cubePart Cube part to copy from
     */
    private CubePart(CubePart cubePart) {
        this.edge = cubePart.edge.clone();
        this.orientation = cubePart.orientation;
        this.mirrored = cubePart.mirrored;
    }

    /**
     * Parse string and creates new CubePart from string in the following format:
     * "    []    \n" +
     * "  [][][]  \n" +
     * "[][][][][]\n" +
     * "  [][][]  \n" +
     * "    []    \n",
     *
     * @param cubePartString String to parse
     */
    public static CubePart parse(String cubePartString) throws ParseException, ValidationException {
        final CubePart cubePart = new CubePart();
        final boolean[] booleans = fromString(cubePartString);
        System.arraycopy(booleans, 0, cubePart.edge, 0, booleans.length);
        checkValid(cubePart);
        return cubePart;
    }

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
     * Checks that edge is valid:
     * Each side should not be flat
     * Edge should be physically possible
     */
    private static void checkValid(CubePart cubePart) throws ValidationException {
        for (int i = 0; i < SIDE_COUNT; i++) {

            //check corner
            final int corner = i * (SIDE_SIZE - 1);
            if (cubePart.getEdge(corner)) {
                //check that corner is physically possible
                if (!cubePart.getEdge(corner - 1)
                        && !cubePart.getEdge(corner + 1)) {
                    throw new ValidationException("Not valid part, invalid corner " + corner,cubePart);
                }
            }

            //check that at least one notch and one protrusion exists on this side
            boolean notchExists = false;
            boolean protrusionExists = false;
            for (int j = corner; j < corner + SIDE_SIZE; j++) {
                if (cubePart.getEdge(j)) {
                    notchExists = true;
                } else {
                    protrusionExists = true;
                }
            }

            if (!notchExists) {
                throw new ValidationException("Not valid part, flat side without notches " + Side.getByIndex(i),cubePart);
            }

            if (!protrusionExists) {
                throw new ValidationException("Not valid part, flat side without protrusion" + Side.getByIndex(i),cubePart);
            }
        }
    }

    /**
     * Parse string into boolean array
     *
     * @param str String to parse
     * @return Boolean array which represents edge
     */
    private static boolean[] fromString(String str) throws ParseException {
        if (str == null || str.isEmpty())
            throw new ParseException("String should contain rows and columns",0);
        final String[] rows = str.split("\n");
        if (rows.length != SIDE_SIZE) {
            throw new ParseException("Wrong count of rows: expected - " + SIDE_SIZE + ", actual - " + rows.length,0);
        }
        final boolean[] edge = new boolean[SIZE];
        final int substringSize = 2;
        final int rowSize = SIDE_SIZE * substringSize;

        for (int i = 0; i < SIDE_SIZE; i++) {
            if (rows[i].length() < rowSize) {
                throw new ParseException("Row " + i + " is not valid",rows[i].length());
            }
        }

        //first row
        for (int i = 0; i < SIDE_SIZE; i++) {
            int start = i * substringSize;
            String substring = rows[0].substring(start, start + substringSize);
            try {
                setEdge(edge,i, toBool(substring));
            } catch (IllegalArgumentException e){
                throw  new ParseException(e.getMessage(),start);
            }
        }

        //central rows
        for (int i = 1; i < SIDE_SIZE - 1; i++) {
            //first
            int start = 0;
            String substring = rows[i].substring(start, start + substringSize);
            try {
                setEdge(edge, -i, toBool(substring));
            } catch (IllegalArgumentException e){
                throw  new ParseException(e.getMessage(),i*SIDE_SIZE+start);
            }
            //last
            start = rowSize - substringSize;
            substring = rows[i].substring(start, start + substringSize);
            try {
                setEdge(edge, i + SIDE_SIZE - 1, toBool(substring));
            } catch (IllegalArgumentException e){
                throw  new ParseException(e.getMessage(),i*SIDE_SIZE+start);
            }
        }

        //last row
        for (int i = 0; i < SIDE_SIZE; i++) {
            int start = i * substringSize;
            int rowId = SIDE_SIZE - 1;
            String substring = rows[rowId].substring(start, start + substringSize);
            try {
                setEdge(edge, SIZE - SIDE_SIZE - i + 1, toBool(substring));
            } catch (IllegalArgumentException e){
                throw  new ParseException(e.getMessage(),rowId*SIDE_SIZE+start);
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

    /**
     * Gets edge at particular index
     *
     * @param index Index of edge lopped by {@link LoopIndex}
     * @return True if it is notch, false if protrusion
     */
    private boolean getEdge(int index) {
        return edge[loopIndex.get(index)];
    }

    /**
     * Represents side of edge.
     * Returns {@link SideIterator} for particular {@link Side}.
     * Direction from left to right, from top to bottom.
     *
     * @param side Side of edge
     * @return Side iterator
     * @see SideIterator
     * @see Side
     */
    public SideIterator getSideIterator(Side side) {
        boolean newInverse = false;
        Side newSide = side;
        if (mirrored) {
            switch (side) {
                case TOP:
                    newInverse = true;
                    break;
                case RIGHT:
                    newSide = Side.LEFT;
                    newInverse = true;
                    break;
                case BOTTOM:
                    break;
                case LEFT:
                    newSide = Side.RIGHT;
                    break;
            }
        } else {
            switch (newSide) {
                case LEFT:
                case BOTTOM:
                    newInverse = true;
                    break;
            }
        }
        return new SideIterator(newSide, newInverse);
    }

    /**
     * Gets corner value of edge
     *
     * @param vertical   Vertical {@link Side} of corner.
     * @param horizontal Horisontal {@link Side} of corner.
     * @return True if it is notch, false if protrusion
     */
    public boolean getCorner(Side vertical, Side horizontal) {
        final SideIterator sideIterator = getSideIterator(vertical);
        switch (horizontal) {
            case RIGHT:
                return CubeUtils.getLast(sideIterator);
            case LEFT:
                return sideIterator.next();
        }
        throw new IllegalArgumentException("Wrong sides");
    }

    /**
     * Rotates edge in clockwise direction
     *
     * @return Returns this
     */
    public CubePart rotate() {
        return rotate(false);
    }

    /**
     * Rotates edge in counterclockwise direction
     *
     * @return Returns this
     */
    public CubePart rotateInverse() {
        return rotate(true);
    }

    /**
     * Rotates edge
     *
     * @param inverse True if clockwise, false otherwise
     * @return Returns this
     */
    private CubePart rotate(boolean inverse) {
        inverse = inverse ^ mirrored;
        if (inverse) {
            orientation++;
        } else {
            orientation--;
        }
        return this;
    }

    /**
     * Mirrors edge
     *
     * @return Returns this
     */
    public CubePart mirror() {
        mirrored = !mirrored;
        return this;
    }

    /**
     * Creates deep copy of edge
     *
     * @return deep copy
     */
    public CubePart copy() {
        return new CubePart(this);
    }

    /**
     * Two edges are equal if their sides are equal consider all rotations and mirrors
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CubePart cubePart = (CubePart) o;

        for (Side side : Side.values()) {
            final CubePart.SideIterator it1 = this.getSideIterator(side);
            final CubePart.SideIterator it2 = cubePart.getSideIterator(side);

            while (it1.hasNext() && it2.hasNext()) {
                if (!it1.next().equals(it2.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculates hash code corresponding to equals method
     * @see CubePart#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 17;
        for (Side side : Side.values()) {
            final SideIterator iterator = getSideIterator(side);
            while (iterator.hasNext()) {
                result = 31 * result + (iterator.next() ? 1 : 0);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        //first row
        final SideIterator top = getSideIterator(Side.TOP);
        while (top.hasNext()) {
            sb.append(fromBool(top.next()));
        }
        sb.append("\n");

        //central rows
        final SideIterator right = getSideIterator(Side.RIGHT);
        final SideIterator left = getSideIterator(Side.LEFT);
        right.next();
        left.next();
        for (int i = 1; i < SIDE_SIZE - 1; i++) {
            //first
            sb.append(fromBool(left.next()));
            //central
            for (int j = 1; j < SIDE_SIZE - 1; j++) {
                sb.append(fromBool(true));
            }
            //last
            sb.append(fromBool(right.next()));
            sb.append("\n");
        }

        //last row
        final SideIterator bottom = getSideIterator(Side.BOTTOM);
        while (bottom.hasNext()) {
            sb.append(fromBool(bottom.next()));
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * Converts boolean to string
     */
    private String fromBool(boolean val) {
        return val ? TRUE_SYMBOL : FALSE_SYMBOL;
    }

    /**
     * Gets iterator to iterate through side consider rotations and mirrors
     *          --->
     *   [ 0] [ 1] [ 2] [ 3] [ 4]
     * | [15] (  ) (  ) (  ) [ 5] |
     * | [14] (  ) (  ) (  ) [ 6] |
     * V [13] (  ) (  ) (  ) [ 7] V
     *   [12] [11] [10] [ 9] [ 8]
     *          --->
     */
    public class SideIterator implements Iterator<Boolean> {

        private final int start;
        private final int end;
        private final boolean inverse;
        private final LoopIndex loopIndex = new LoopIndex(Side.values().length);
        private int cursor;

        /**
         * Creates new instance of side iterator
         *
         * @param side    Side to iterate through
         * @param inverse If true then first element is top(left), else bottom(right)
         */
        public SideIterator(Side side, boolean inverse) {
            this.inverse = inverse;
            start = (SIDE_SIZE - 1) * loopIndex.get(side.getIndex() + orientation);
            end = start + SIDE_SIZE - 1;
            if (inverse) {
                cursor = end;
            } else {
                cursor = start;
            }
        }

        public boolean hasNext() {
            return inverse ? cursor >= start : cursor <= end;
        }

        public Boolean next() {
            if (this.hasNext()) {
                final int current = cursor;
                if (inverse) {
                    cursor--;
                } else {
                    cursor++;
                }
                return getEdge(current);
            }
            throw new NoSuchElementException();
        }

        public Boolean current() {
            return getEdge(inverse ? cursor + 1 : cursor - 1);
        }
    }

}
