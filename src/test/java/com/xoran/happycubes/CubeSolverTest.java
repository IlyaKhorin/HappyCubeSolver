package com.xoran.happycubes;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.cube.CubeSet;
import com.xoran.happycubes.exceptions.SolveException;
import com.xoran.happycubes.exceptions.ValidationException;
import com.xoran.happycubes.solver.CubeSolution;
import com.xoran.happycubes.solver.CubeSolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 22.07.16
 *
 * Tests that all sets has particular count of unique solutions
 */
@RunWith(Parameterized.class)
public class CubeSolverTest extends CubeBaseTest {

    private static final Logger log = LogManager.getLogger(CubeSolverTest.class);
    private final TestSet testSet;
    private final int solutionsCount;

    public CubeSolverTest(TestSet testSet, int solutionsCount) {
        this.testSet = testSet;
        this.solutionsCount = solutionsCount;
    }

    @Parameterized.Parameters(name = "{index}: Set: {0}; solutions:{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {TestSet.BLUE, 3},
                        {TestSet.RED, 1},
                        {TestSet.PURPLE, 1},
                        {TestSet.YELLOW, 4}
                });
    }

    @Test
    public void testAllUniqueSolutions() throws ParseException, ValidationException, SolveException {
        CubeSet set = new CubeSet(
                CubePart.parse(testSet.getFirst()),
                CubePart.parse(testSet.getSecond()),
                CubePart.parse(testSet.getThird()),
                CubePart.parse(testSet.getForth()),
                CubePart.parse(testSet.getFifth()),
                CubePart.parse(testSet.getSixth())
        );

        CubeSolver solver = new CubeSolver(set, CubeSolver.Strategy.ALL);
        for (int i = 0; i < CubeSet.SIZE; i++) {
            List<CubeSolution> solutions = solver.solve(i);
            solutions.stream().forEach(s -> log.info("\n" + s.toString()));
            Assert.assertEquals(solutionsCount, solutions.size());
        }
    }

    @Test
    public void testFirstUniqueSolution() throws ParseException, ValidationException, SolveException {
        CubeSet set = new CubeSet(
                CubePart.parse(testSet.getFirst()),
                CubePart.parse(testSet.getSecond()),
                CubePart.parse(testSet.getThird()),
                CubePart.parse(testSet.getForth()),
                CubePart.parse(testSet.getFifth()),
                CubePart.parse(testSet.getSixth())
        );

        CubeSolver solver = new CubeSolver(set, CubeSolver.Strategy.SINGLE);
        for (int i = 0; i < CubeSet.SIZE; i++) {
            List<CubeSolution> solutions = solver.solve(i);
            solutions.stream().forEach(s -> log.info("\n" + s.toString()));
            Assert.assertEquals(1, solutions.size());
        }
    }

}
