package com.xoran.happycubes.solver;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.cube.CubeSet;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 16.07.16
 * <p>
 * Represents solution of cube set as cube set in the following format
 *   1
 * 4 0 2
 *   3
 *   5
 */
public class CubeSolution extends CubeSet {

    /**
     * Creates representation of solution from six parts
     *   1
     * 4 0 2
     *   3
     *   5
     *
     * @see CubePart
     */
    public CubeSolution(
            @NotNull CubePart first,
            @NotNull CubePart second,
            @NotNull CubePart third,
            @NotNull CubePart forth,
            @NotNull CubePart fifth,
            @NotNull CubePart sixth) {
        super(first, second, third, forth, fifth, sixth);
    }

    /**
     * creates solution from {@link CubeSet}
     *
     * @param set set of {@link CubePart}
     */
    private CubeSolution(@NotNull CubeSet set) {
        super(set);
    }

    /**
     * Copies current solution
     *
     * @return Deep copy of solution
     */
    @NotNull
    @Override
    public CubeSolution copy() {
        return new CubeSolution(super.copy());
    }

    /**
     * Rotate solution:
     * ' - rotated
     * " - rotated inverse
     *   1         4'
     * 4 0 2 -> 3' 0' 1'
     *   3         2'
     *   5         5"
     *
     * @return return this
     */
    @NotNull
    public CubeSolution rotate() {
        for (int i = 0; i < cubeParts.length - 1; i++) {
            cubeParts[i].rotate();
        }
        cubeParts[5].rotateInverse();

        final int shift = 1;
        final CubePart tmp = cubeParts[shift + 3];
        System.arraycopy(cubeParts, 1, cubeParts, 2, 3);
        cubeParts[shift] = tmp;
        return this;
    }

    /**
     * Mirror solution:
     * ' - mirrored
     *   1         1'
     * 4 0 2 -> 2' 0' 4'
     *   3         3'
     *   5         5'
     *
     * @return return this
     */
    @NotNull
    public CubeSolution mirror() {
        for (int i = 0; i < cubeParts.length; i++) {
            cubeParts[i].mirror();
        }

        final CubePart tmp = cubeParts[2];
        cubeParts[2] = cubeParts[4];
        cubeParts[4] = tmp;
        return this;
    }

    /**
     * Returns representation of solution from six parts in unfolded format:
     * 4 0 2
     *   3
     *   5
     *   1
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final String[] split4 = cubeParts[4].toString().split("\n");
        final String[] split2 = cubeParts[2].toString().split("\n");
        final String[] split0 = cubeParts[0].toString().split("\n");

        final String offsetString = "          ";
        for (int i = 0; i < CubePart.SIDE_SIZE; i++) {
            sb.append(split4[i]).append(split0[i]).append(split2[i]).append("\n");
        }
        for (String s : cubeParts[3].toString().split("\n")) {
            sb.append(offsetString);
            sb.append(s).append("\n");
        }

        CubePart cubePart5 = cubeParts[5].copy();
        cubePart5.rotate();
        cubePart5.rotate();

        for (String s : cubePart5.toString().split("\n")) {
            sb.append(offsetString);
            sb.append(s).append("\n");
        }

        for (String s : cubeParts[1].toString().split("\n")) {
            sb.append(offsetString);
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
