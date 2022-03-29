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
            for(int i = 0; i<dimension; i++) {
                xPrevIter[i] = xCurIter[i];
            }
            for (int i = 0; i<dimension; i++) {
                double sum_1 = 0;
                double sum_2 = 0;
                for (int j = 0; j< i; j++) {
                    sum_1 += matrixA[i][j] * xCurIter[j];
                }
                for (int j = i+1; j < dimension; j++) {
                    sum_2 += matrixA[i][j] * xPrevIter[j];
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
