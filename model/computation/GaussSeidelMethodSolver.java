package model.computation;
import model.SquareMatrixWrapper;


public class GaussSeidelMethodSolver {
    private final String[] errorMessages = {
    };
    private SquareMatrixWrapper squareMatrixWrapper;

    public boolean solve(SquareMatrixWrapper squareMatrixWrapper) {
        this.squareMatrixWrapper = squareMatrixWrapper;
        double[][] matrixA = squareMatrixWrapper.getMatrixA();
        double[] matrixB = squareMatrixWrapper.getMatrixB();
        int dimension = squareMatrixWrapper.getDimension();
        double[] xPrevIter = new double[dimension];
        double[] xCurIter = new double[dimension];
        int iterationCounter = 0;


        do {
            iterationCounter += 1;
            System.arraycopy(xCurIter, 0, xPrevIter, 0, dimension);

            for (int i = 0; i<dimension; i++) {
                double sum_1 = 0;
                double sum_2 = 0;
                for (int k = 0; k< i; k++) {
                    sum_1 += matrixA[i][k] * xCurIter[k];
                }
                for (int k = i+1; k < dimension; k++) {
                    sum_2 += matrixA[i][k] * xPrevIter[k];
                }
                xCurIter[i] =  - (sum_1 + sum_2 - matrixB[i])/matrixA[i][i];
            }
        } while ( notPrecise(xPrevIter, xCurIter, squareMatrixWrapper.getEpsilon()) );

        System.out.println("Количество итераций: "+iterationCounter);
        System.out.println("Столбец неизвестных: ");
        for (double d: xCurIter) {
            System.out.print(String.format("%.7f ",d));
        }
        System.out.println();
        System.out.println("Столбец погрешностей: ");
        for (int i = 0; i<dimension; i++) {
            System.out.print(String.format("%.7f ",Math.abs( xCurIter[i] - xPrevIter[i])));
        }
        System.out.println();
        return true;
    }

    private boolean notPrecise (double[] xPrevIter, double[] xCurIter, double epsilon) {
        for (int i = 0; i < xCurIter.length; i++) {
            if (Math.abs((xCurIter[i] - xPrevIter[i])) > epsilon)
                return true;
        }
        return false;
    }
}
