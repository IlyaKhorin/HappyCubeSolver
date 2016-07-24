package com.xoran.happycubes;


import com.xoran.happycubes.cube.CubePart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 16.07.16
 */
public class CubeBaseTest {
    Logger log = LogManager.getLogger(CubeBaseTest.class);

    protected void assertCubeToString(CubePart cubePart, String cubePartString) {
        String cubePartToString = cubePart.toString();
        log.info("Assert equals: \n" +
                "ecpected:\n"
                + cubePartString
                + "actual \n"
                + cubePartToString);
        assertEquals(cubePartString, cubePartToString);
    }

    public enum TestSet {
        BLUE(
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
        ),
        RED(
                "      [][]\n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "  []  [][]\n",

                "  []  []  \n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "  []      \n",

                "  [][]  []\n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "[]    [][]\n",

                "    []    \n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "    []    \n",

                "    [][]  \n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "[]  []    \n",

                "  [][]    \n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "[][]  [][]\n"
        ),
        PURPLE(
                "[][]  []  \n" +
                "[][][][]  \n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "    []    \n",

                "      [][]\n" +
                "[][][][]  \n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "  []  []  \n",

                "  []      \n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "    []    \n",

                "[][]  [][]\n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "  [][][]  \n" +
                "  []  []  \n",

                "    []  []\n" +
                "  [][][][]\n" +
                "[][][][][]\n" +
                "[][][][]  \n" +
                "[]  [][]  \n",

                "  []  [][]\n" +
                "  [][][]  \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "[][]  []  \n"
        ),
        YELLOW(
                "    []    \n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "  []  []  \n",

                "    []  []\n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "[][][][]  \n" +
                "  []  []  \n",

                "    []  []\n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "[][][][][]\n" +
                "[]  []    \n",

                "[]  []  []\n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "[]  []    \n",

                "    []    \n" +
                "  [][][][]\n" +
                "[][][][]  \n" +
                "  [][][][]\n" +
                "[][]  []  \n",

                "  []  []  \n" +
                "  [][][]  \n" +
                "[][][][][]\n" +
                "  [][][]  \n" +
                "  []  [][]\n"
        );

        private final String first;
        private final String second;
        private final String third;
        private final String forth;
        private final String fifth;
        private final String sixth;

        TestSet(String first, String second, String third, String forth, String fifth, String sixth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.forth = forth;
            this.fifth = fifth;
            this.sixth = sixth;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

        public String getThird() {
            return third;
        }

        public String getForth() {
            return forth;
        }

        public String getFifth() {
            return fifth;
        }

        public String getSixth() {
            return sixth;
        }
    }

}
