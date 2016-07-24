package com.xoran.happycubes;

import com.xoran.happycubes.cube.CubePart;
import com.xoran.happycubes.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Ilya Khorin ilya.xelpy@gmail.com
 * 17.07.16
 */
@RunWith(Parameterized.class)
public class CubePartActionsTest extends CubeBaseTest {

    private String cubePartString;
    private int rotate;

    public CubePartActionsTest(String cubePartString, int rotate) {

        this.cubePartString = cubePartString;
        this.rotate = rotate;
    }

    //rotations of BLUE_6
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {
                                "  []  []  \n" +
                                "  [][][][]\n" +
                                "[][][][]  \n" +
                                "  [][][][]\n" +
                                "[][]  [][]\n", 4},
                        {
                                "[]  []    \n" +
                                "[][][][][]\n" +
                                "  [][][]  \n" +
                                "[][][][][]\n" +
                                "[][]  []  \n", 1},
                        {
                                "[][]  [][]\n" +
                                "[][][][]  \n" +
                                "  [][][][]\n" +
                                "[][][][]  \n" +
                                "  []  []  \n", 2},
                        {
                                "  []  [][]\n" +
                                "[][][][][]\n" +
                                "  [][][]  \n" +
                                "[][][][][]\n" +
                                "    []  []\n", 3},
                });
    }

    @Test
    public void testRotate() throws ParseException, ValidationException {
        CubePart cubePart = CubePart.parse(TestSet.BLUE.getSixth());
        for(int i=0;i<rotate;i++){
            cubePart.rotate();
        }
        assertCubeToString(cubePart,cubePartString);
    }

    @Test
    public void testRotateInverse() throws ParseException, ValidationException {
        CubePart cubePart = CubePart.parse(TestSet.BLUE.getSixth());
        if(rotate < 4){
            rotate = 4-rotate;
        }
        for(int i=0;i<rotate;i++){
            cubePart.rotateInverse();
        }
        assertCubeToString(cubePart,cubePartString);
    }

    @Test
    public void testMirror() throws ParseException, ValidationException {
        CubePart cubePart = CubePart.parse(TestSet.BLUE.getSixth());

        cubePart.mirror();

        for(int i=0;i<rotate;i++){
            cubePart.rotateInverse();
        }

        cubePartString = mirrorString(cubePartString);

        assertCubeToString(cubePart,cubePartString);
    }

    private String mirrorString(String str){
        StringBuilder sb =new StringBuilder();
        for(String row: str.split("\n")){
            sb.append(new StringBuilder(row).reverse().toString().replace("][","[]")).append("\n");
        }
        return sb.toString();
    }

}
