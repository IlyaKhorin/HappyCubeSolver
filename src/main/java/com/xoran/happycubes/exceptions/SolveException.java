package com.xoran.happycubes.exceptions;

import com.xoran.happycubes.cube.CubeSet;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */
public class SolveException extends CubeException {
    public SolveException(String message, CubeSet set) {
        super(message, set);
    }
}
