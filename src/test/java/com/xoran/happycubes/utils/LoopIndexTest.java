package com.xoran.happycubes.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 22.07.16
 */
public class LoopIndexTest {

    private  static final LoopIndex loopIndex = new LoopIndex(5);

    @Test
    public void testPositive(){
        Assert.assertEquals(3,loopIndex.get(3));
    }

    @Test
    public void testPositiveOverMax(){
        Assert.assertEquals(1,loopIndex.get(6));
    }

    @Test
    public void testNegative(){
        Assert.assertEquals(4,loopIndex.get(-1));
    }

    @Test
    public void testNegativeOverMax(){
        Assert.assertEquals(3,loopIndex.get(-7));
    }

    @Test
    public void testZeros(){
        Assert.assertEquals(0,loopIndex.get(0));
        Assert.assertEquals(0,loopIndex.get(5));
        Assert.assertEquals(0,loopIndex.get(-5));
        Assert.assertEquals(0,loopIndex.get(10));
        Assert.assertEquals(0,loopIndex.get(-10));
    }
}
