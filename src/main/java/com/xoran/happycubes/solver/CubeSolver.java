package com.xoran.happycubes.solver;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.cube.CubeSet;
import com.xoran.happycubes.exceptions.SolveException;
import com.xoran.happycubes.utils.CubeUtils;
import com.xoran.happycubes.utils.Side;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 18.07.16
 * <p>
 * Solves CubeSet with the following algorithm:
 * <p>
 * Fixing of any part
 * <p>
 *    0
 * <p>
 * Build a cross around first part
 * <p>
 *   1
 * 4 0 2
 *   3
 * <p>
 * Find a last part
 *   3
 * 2 5 4
 *   1
 * <p>
 * Check that solution is unique
 */
public class CubeSolver {

    private final CubeSet set;
    private final Strategy strategy;
    private HashSet<CubeSolution> solutions;
    private CubePart[] solution;
    private boolean[] flags;
    private int position;
    private boolean terminated;

    /**
     * Creates solver for {@link CubeSet}
     *
     * @param set      Set to solve
     * @param strategy {@link Strategy}
     */
    public CubeSolver(@NotNull CubeSet set, Strategy strategy) {
        this.strategy = strategy;
        this.set = set.copy();
    }

    /**
     * Finds all solutions of current set starting from element startIndex
     *
     * @param startIndex Index of element to start from. From 0 to 5
     * @return List of all unique solutions
     * @throws SolveException if solution wasn't found
     */
    public List<CubeSolution> solve(int startIndex) throws SolveException {
        if (startIndex < 0
                || startIndex > 5) {
            throw new IllegalArgumentException("Index should be between 0 and 5");
        }

        //Initialization
        {
            solution = new CubePart[CubeSet.SIZE];
            flags = new boolean[CubeSet.SIZE];
            solutions = new HashSet<>();
            position = 0;
            terminated = false;
        }

        //adding first element to solution
        final CubePart start = set.getPart(startIndex).copy();
        addToSolution(startIndex, start);
        //recursive solving
        solveCrossImpl(Side.getByIndex(0));

        if (solutions.isEmpty()) {
            throw new SolveException("There is no solutions", set);
        }

        return solutions.stream().collect(Collectors.toList());
    }

    /**
     * Adds element to solution and marks that this element is already used
     */
    private void addToSolution(int index, CubePart cubePart) {
        flags[index] = true;
        solution[position] = cubePart;
        position++;
    }

    /**
     * Removes element from solution
     */
    private void removeFromSolution(int index) {
        flags[index] = false;
        position--;
    }

    /**
     * Builds cross around first element
     * Search matched part at particular side, using available parts of set
     * When all sides are matched with other parts, we can match last part
     *
     * @param side Current side to match
     */
    private void solveCrossImpl(Side side) {
        //solving can be terminated
        if (terminated) {
            return;
        }

        // when cross is found we can match last element
        if (position == CubeSet.SIZE - 1) {
            matchLast();
            return;
        }

        //get indexes of available parts
        for (Integer index : getAvailable()) {
            //Iterates through all unique matches to current side of the first element
            for (CubePart match : CubeUtils.findAllUniqueMatches(solution[0], side, set.getPart(index))) {
                addToSolution(index, match);
                //Continue find match for the next side
                solveCrossImpl(side.getNext());
                removeFromSolution(index);
            }
        }

    }

    /**
     * Check that last part matches to the built cross
     * Built cross:
     *   3
     * 4 5 2
     *   1
     */
    private void matchLast() {
        //solving can be terminated
        if (terminated) {
            return;
        }
        //get last part
        final int last = getAvailable().get(0);
        //get all unique combinations of last part
        for (CubePart part : CubeUtils.findAllUniqueParts(set.getPart(last))) {
            boolean matches = true;
            //check part with all sides
            for (Side side : Side.values()) {
                if (!CubeUtils.areMatch(solution[side.getIndex() + 1], side, part, side)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                //according to checks found side is mirrored
                part.mirror();
                addToSolution(last, part);
                checkCorners();
                removeFromSolution(last);
            }
        }

    }

    /**
     * Checks that corners are filled and not overlapped
     */
    private void checkCorners() {
        //solving can be terminated
        if (terminated) {
            return;
        }
        //check front cross
        if (CubeUtils.checkCrossCorners(
                solution[0],
                solution[1],
                solution[2],
                solution[3],
                solution[4])) {

            //check back cross
            //some of sides should be rotated to correspond to cross
            if (CubeUtils.checkCrossCorners(
                    solution[5],
                    solution[1].copy().rotate().rotate(),
                    solution[4],
                    solution[3].copy().rotate().rotate(),
                    solution[2])) {
                // all checks done, this is one of solutions
                addSolutionIfUnique(new CubeSolution(
                        solution[0],
                        solution[1],
                        solution[2],
                        solution[3],
                        solution[4],
                        solution[5]));
                //single solution found
                if (strategy == Strategy.SINGLE) {
                    terminated = true;
                }
            }
        }
    }

    /**
     * Rotates and mirrors solution and checks that it is unique
     *
     * @param set possible solution
     */
    private void addSolutionIfUnique(CubeSolution set) {
        final CubeSolution tmp = set.copy();
        boolean isUnique = true;
        //mirror loop
        for (int i = 0; i < 2; i++) {
            //rotation loop
            for (int j = 0; j < 4; j++) {
                //checks hash map
                if (solutions.contains(tmp)) {
                    isUnique = false;
                    break;
                }
                tmp.rotate();
            }
            if (!isUnique) {
                break;
            }
            tmp.mirror();
        }
        if (isUnique) {
            solutions.add(set);
        }
    }

    /**
     * Gives indexes of parts that are still not used in current solutions
     *
     * @return List of all available indexes
     */
    private ArrayList<Integer> getAvailable() {
        final ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < flags.length; i++) {
            if (!flags[i])
                indexes.add(i);
        }
        return indexes;
    }

    public enum Strategy {
        SINGLE,
        ALL
    }
}
