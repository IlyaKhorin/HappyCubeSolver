package com.xoran.happycubes.exceptions;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.cube.CubeSet;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */

/**
 * Base class for cube exceptions
 */
public class CubeException extends Exception {

    /**
     * Simple Exception with message {@link Exception#Exception(String)}
     * @param message
     *      Exception's message
     */
    public CubeException(String message){
        super(message);
    }

    /**
     * Exception with message and related {@link CubePart}
     * @param message
     *      Exception's message
     * @param part
     *      Related {@link CubePart}
     */
    public CubeException(String message, CubePart part){
        super(message + "\n" +part.toString());
    }

    /**
     * Exception with message and related {@link CubeSet}
     * @param message
     *      Exception's message
     * @param set
     *      Related {@link CubeSet}
     */
    public CubeException(String message, CubeSet set){
        super(message + "\n" +set.toString());
    }
}
