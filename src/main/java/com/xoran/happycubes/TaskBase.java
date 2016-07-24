package com.xoran.happycubes;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.cube.CubeSet;
import com.xoran.happycubes.exceptions.SolveException;
import com.xoran.happycubes.exceptions.ValidationException;
import com.xoran.happycubes.solver.CubeSolution;
import com.xoran.happycubes.solver.CubeSolver;
import com.xoran.happycubes.utils.SolutionStreamWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 24.07.16
 */
public class TaskBase {

    /**
     * Performs task with differrnt strategy
     * @param strategy
     *      {@link CubeSolver.Strategy}
     * @param stream
     *      stream to write solution to
     * @throws IOException
     *      if write operation fails
     * @throws SolveException
     *      if solution wasn't found
     * @throws ParseException
     *      if CubeSet couldn't be parsed
     * @throws ValidationException
     *      if parsed Cube parts are not valid
     */
    protected static void performTask(CubeSolver.Strategy strategy, OutputStream stream) throws IOException, SolveException, ParseException, ValidationException {
        //using this approach just to demonstrate solving
        final String[] partString = generateTestStrings();
        CubeSet cubeSet = new CubeSet(
                CubePart.parse(partString[0]),
                CubePart.parse(partString[1]),
                CubePart.parse(partString[2]),
                CubePart.parse(partString[3]),
                CubePart.parse(partString[4]),
                CubePart.parse(partString[5])
        );

        CubeSolver solver = new CubeSolver(cubeSet, strategy);
        try(SolutionStreamWriter writer  = new SolutionStreamWriter(stream)) {
            List<CubeSolution> solutions = solver.solve(0);
            for (CubeSolution solution : solutions) {
                writer.writeSolution(solution);
            }
        }
    }

    /**
     * this strings are taken from CubeBaseTest
     * to demonstrate solving
     * @return
     *      Blue set
     */
    private static String[] generateTestStrings(){
        return new String[]{
                "    []    \n" +
                        "  [][][]  \n" +
                        "[][][][][]\n" +
                        "  [][][]  \n" +
                        "    []    \n",

                "[]  []  []\n" +
                        "[][][][][]\n" +
                        "  [][][]  \n" +
                        "[][][][][]\n" +
                        "[]  []  []\n",

                "    []    \n" +
                        "  [][][][]\n" +
                        "[][][][]  \n" +
                        "  [][][][]\n" +
                        "    []    \n",

                "  []  []  \n" +
                        "[][][][]  \n" +
                        "  [][][][]\n" +
                        "[][][][]  \n" +
                        "[][]  []  \n",

                "  []  []  \n" +
                        "[][][][][]\n" +
                        "  [][][]  \n" +
                        "[][][][][]\n" +
                        "[]  []    \n",

                "  []  []  \n" +
                        "  [][][][]\n" +
                        "[][][][]  \n" +
                        "  [][][][]\n" +
                        "[][]  [][]\n"
        };
    }

}
