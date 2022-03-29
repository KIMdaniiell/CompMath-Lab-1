import model.SquareMatrixWrapper;
import io.MatrixInputManager;
import model.computation.GaussSeidelConditionChecker;
import model.computation.GaussSeidelMethodSolver;

public class Main {

    public static void main(String[] args) {
        MatrixInputManager matrixInputManager = new MatrixInputManager();
        SquareMatrixWrapper squareMatrixWrapper = matrixInputManager.readSquareMatrixWrapper("input.txt");

        /**squareMatrixWrapper.printMatrixA();
        System.out.println();
        squareMatrixWrapper.printMatrixB();
        System.out.println();**/

        GaussSeidelConditionChecker gaussSeidelConditionChecker = new GaussSeidelConditionChecker();
        if (gaussSeidelConditionChecker.check(squareMatrixWrapper)) {
            System.out.println("###Проверка пройдена###");
            GaussSeidelMethodSolver gaussSeidelMethodSolver = new GaussSeidelMethodSolver();
            gaussSeidelMethodSolver.solve(squareMatrixWrapper);
        }
    }
}
