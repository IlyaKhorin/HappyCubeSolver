package com.xoran.happycubes;

import com.xoran.happycubes.exceptions.SolveException;
import com.xoran.happycubes.exceptions.ValidationException;
import com.xoran.happycubes.solver.CubeSolver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */

public class Task1 extends TaskBase {

    /**
     * Create a computer program that solves the given cubes puzzles and
     * prints the solution(s) in ASCII format into a file in unfolded format
     *
     * @param args
     */
    public static void main(String... args) throws ParseException, ValidationException, IOException, SolveException {
        performTask(CubeSolver.Strategy.SINGLE, new FileOutputStream("task1.txt"));
    }

}
