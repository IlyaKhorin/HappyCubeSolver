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
public class Task2 extends TaskBase {

    /**
     * Additional challenge 1 : All unique solutions :
     * In the base task you are only required to find one solution and to
     * print it into a file. The additional challenge 1 is to extend your
     * program to find all unique solutions. A solution is unique if it
     * cannot be transformed into another solution by rotating or mirroring
     * in 3 dimensions.
     *
     * @param args
     */
    public static void main(String... args) throws ParseException, ValidationException, IOException, SolveException {
        performTask(CubeSolver.Strategy.ALL, new FileOutputStream("task2.txt"));
    }

}
