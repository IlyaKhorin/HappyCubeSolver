package com.xoran.happycubes;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.exceptions.ValidationException;
import com.xoran.happycubes.solver.CubeSolution;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.HashSet;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 23.07.16
 */
public class CubeSolutionTest extends CubeBaseTest {

    /**
     * Tests {@link CubeSolution#equals(Object)} and {@link CubeSolution#hashCode()}
     */
    @Test
    public void testCubeSolutionHashSet() throws ParseException, ValidationException {
        final HashSet<CubeSolution> sets = new HashSet<>();

        final CubeSolution cubeSolution1 = new CubeSolution(
                CubePart.parse(TestSet.BLUE.getFirst()),
                CubePart.parse(TestSet.BLUE.getSecond()),
                CubePart.parse(TestSet.BLUE.getThird()),
                CubePart.parse(TestSet.BLUE.getForth()),
                CubePart.parse(TestSet.BLUE.getFifth()),
                CubePart.parse(TestSet.BLUE.getSixth())
        );

        final CubeSolution cubeSolution2 = new CubeSolution(
                CubePart.parse(TestSet.BLUE.getFirst()),
                CubePart.parse(TestSet.BLUE.getSecond()),
                CubePart.parse(TestSet.BLUE.getThird()),
                CubePart.parse(TestSet.BLUE.getForth()),
                CubePart.parse(TestSet.BLUE.getFifth()),
                CubePart.parse(TestSet.BLUE.getSixth())
        );


        CubeSolution cubeSolution3 = cubeSolution2.copy();

        CubeSolution cubeSolution4 = cubeSolution3.copy();
        cubeSolution4.rotate().rotate().rotate().rotate();

        CubeSolution cubeSolution5 = cubeSolution4.copy();
        cubeSolution5.mirror().mirror();

        //Asserts
        {
            sets.add(cubeSolution1);

            Assert.assertTrue(sets.contains(cubeSolution1));
            Assert.assertTrue(sets.contains(cubeSolution2));
            Assert.assertTrue(sets.contains(cubeSolution3));
            Assert.assertTrue(sets.contains(cubeSolution4));
            Assert.assertTrue(sets.contains(cubeSolution5));
        }
    }
}
