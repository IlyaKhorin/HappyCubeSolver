package com.xoran.happycubes.exceptions;

import com.xoran.happycubes.cube.CubePart;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */
public class ValidationException extends CubeException {
    public ValidationException(String message, CubePart part) {
        super(message, part);
    }
}
