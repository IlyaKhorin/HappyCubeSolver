package com.xoran.happycubes.utils;

import com.xoran.happycubes.solver.CubeSolution;

import java.io.*;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */
public class SolutionStreamWriter implements Closeable {
    private final Writer writer;

    public SolutionStreamWriter(OutputStream stream) {
        writer = new PrintWriter(stream);
    }

    public void writeSolution(CubeSolution solution) throws IOException {
        writer.write(solution.toString());
        writer.write("\n");
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
