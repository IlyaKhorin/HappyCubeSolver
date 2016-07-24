package com.xoran.happycubes.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 23.07.16
 */
public class SideTest {

    private static final Side TOP = Side.TOP;
    private static final Side RIGHT = Side.RIGHT;
    private static final Side BOTTOM = Side.BOTTOM;
    private static final Side LEFT = Side.LEFT;

    @Test
    public void testGetOpposite(){
        Assert.assertEquals(TOP,BOTTOM.getOpposite());
        Assert.assertEquals(RIGHT,LEFT.getOpposite());
        Assert.assertEquals(BOTTOM,TOP.getOpposite());
        Assert.assertEquals(LEFT,RIGHT.getOpposite());
    }

    @Test
    public void testGetNext(){
        Assert.assertEquals(TOP,LEFT.getNext());
        Assert.assertEquals(RIGHT,TOP.getNext());
        Assert.assertEquals(BOTTOM,RIGHT.getNext());
        Assert.assertEquals(LEFT,BOTTOM.getNext());
    }

    @Test
    public void testIsHorizontal(){
        Assert.assertFalse(TOP.isHorizontal());
        Assert.assertTrue(RIGHT.isHorizontal());
        Assert.assertFalse(BOTTOM.isHorizontal());
        Assert.assertTrue(LEFT.isHorizontal());
    }

    @Test
    public void testIsVertical(){
        Assert.assertTrue(TOP.isVertical());
        Assert.assertFalse(RIGHT.isVertical());
        Assert.assertTrue(BOTTOM.isVertical());
        Assert.assertFalse(LEFT.isVertical());
    }
}
