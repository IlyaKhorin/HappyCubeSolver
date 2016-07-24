package com.xoran.happycubes;


import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Parse test is simple and tests big part of internal methods
 */
@RunWith(Parameterized.class)
public class CubePartTest extends CubeBaseTest {

    private String cubePartString;

    public CubePartTest(String cubePartString) {

        this.cubePartString = cubePartString;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {TestSet.BLUE.getFirst()},
                        {TestSet.BLUE.getSecond()},
                        {TestSet.BLUE.getThird()},
                        {TestSet.BLUE.getForth()},
                        {TestSet.BLUE.getFifth()},
                        {TestSet.BLUE.getSixth()}
                });
    }

    @Test
    public void testParse() throws ParseException, ValidationException {
        CubePart cubePart = CubePart.parse(cubePartString);
        assertCubeToString(cubePart, cubePartString);
    }
}
