package com.xoran.happycubes.utils;

import com.xoran.happycubes.CubeBaseTest;
import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.exceptions.ValidationException;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 17.07.16
 */
public class UtilsTest extends CubeBaseTest {

    @Test
    public void testSideMatch() throws ParseException, ValidationException {
        assertTrue(CubeUtils.areMatch(CubePart.parse(TestSet.BLUE.getSixth()), Side.BOTTOM,
                CubePart.parse(TestSet.BLUE.getFirst()), Side.TOP));

        assertTrue(CubeUtils.areMatch(CubePart.parse(TestSet.BLUE.getSecond()), Side.TOP,
                CubePart.parse(TestSet.BLUE.getFifth()), Side.TOP));
        CubePart cube4 = CubePart.parse(TestSet.BLUE.getFifth());
        CubePart cube3 = CubePart.parse(TestSet.BLUE.getForth());
        cube4.rotate();
        cube3.mirror();
        cube4.rotate();
        cube4.rotate();
        assertFalse(CubeUtils.areMatch(cube3, Side.RIGHT,cube4, Side.RIGHT));
    }

    @Test
    public void testSideMatchWithRotation() throws ParseException, ValidationException {
        CubePart cubePart1 = CubePart.parse(TestSet.BLUE.getSecond());
        cubePart1.rotate();
        assertTrue(CubeUtils.areMatch(cubePart1, Side.RIGHT,
                CubePart.parse(TestSet.BLUE.getFifth()), Side.TOP));

        CubePart cubePart2 = CubePart.parse(TestSet.BLUE.getFifth());
        cubePart2.rotate();
        cubePart2.rotate();
        cubePart2.rotate();

        assertTrue(CubeUtils.areMatch(cubePart1, Side.RIGHT,
                CubePart.parse(TestSet.BLUE.getFifth()), Side.RIGHT));
    }

    @Test
    public void testSideMatchWithEmptyCorner() throws ParseException, ValidationException {
        assertTrue(CubeUtils.areMatch(CubePart.parse(TestSet.BLUE.getSecond()), Side.RIGHT,
                CubePart.parse(TestSet.BLUE.getThird()), Side.LEFT));

        assertFalse(CubeUtils.areMatch(CubePart.parse(TestSet.BLUE.getSecond()), Side.RIGHT,
                CubePart.parse(TestSet.BLUE.getSixth()), Side.LEFT));
    }

    @Test
    public void testFindAllMatches() throws ParseException, ValidationException {
        CubePart cubePart1 = CubePart.parse(TestSet.BLUE.getFirst());
        CubePart cubePart2 = CubePart.parse(TestSet.BLUE.getSecond());
        ArrayList<CubePart> allMatches = CubeUtils.findAllUniqueMatches(cubePart1, Side.BOTTOM, cubePart2);
        assertEquals("Wrong number of unique matches",1,allMatches.size());
        //rotated BLUE_2 is the unique match
        cubePart2.rotate();
        assertCubeToString(allMatches.get(0),cubePart2.toString());
    }


    /**
     *1          3            6
     *    ||          ||        ||  ||             
     *  ||||||      ||||||||    ||||||||    
     *||||||||||  ||||||||    ||||||||            
     *  ||||||      ||||||||    ||||||||    
     *    ||          ||      ||||  ||||      
     *
     *
     */
    @Test
    public void testCorners() throws ParseException, ValidationException {
        CubePart part1 = CubePart.parse(TestSet.BLUE.getFirst());
        CubePart part3 = CubePart.parse(TestSet.BLUE.getThird());
        CubePart part6 = CubePart.parse(TestSet.BLUE.getSixth());

        assertFalse(part1.getCorner(Side.TOP, Side.LEFT));
        assertFalse(part1.getCorner(Side.TOP, Side.RIGHT));
        assertFalse(part1.getCorner(Side.BOTTOM, Side.LEFT));
        assertFalse(part1.getCorner(Side.BOTTOM, Side.RIGHT));

        assertFalse(part3.getCorner(Side.TOP, Side.LEFT));
        assertFalse(part3.getCorner(Side.TOP, Side.RIGHT));
        assertFalse(part3.getCorner(Side.BOTTOM, Side.LEFT));
        assertFalse(part3.getCorner(Side.BOTTOM, Side.RIGHT));

        assertFalse(part6.getCorner(Side.TOP, Side.LEFT));
        assertFalse(part6.getCorner(Side.TOP, Side.RIGHT));
        assertTrue(part6.getCorner(Side.BOTTOM, Side.LEFT));
        assertTrue(part6.getCorner(Side.BOTTOM, Side.RIGHT));

        part6.mirror();
        part6.rotate();
        assertTrue(part6.getCorner(Side.TOP, Side.LEFT));
        assertFalse(part6.getCorner(Side.TOP, Side.RIGHT));
        assertTrue(part6.getCorner(Side.BOTTOM, Side.LEFT));
        assertFalse(part6.getCorner(Side.BOTTOM, Side.RIGHT));

    }

}
