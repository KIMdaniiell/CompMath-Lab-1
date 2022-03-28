import computation.SquareMatrixWrapper;
import io.MatrixInputManager;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MatrixInputManager matrixInputManager = new MatrixInputManager();
        SquareMatrixWrapper squareMatrixWrapper = matrixInputManager.readSquareMatrixWrapper();

        squareMatrixWrapper.printMatrixA();
        squareMatrixWrapper.printMatrixB();
    }
}
