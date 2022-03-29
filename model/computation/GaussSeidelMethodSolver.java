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
        System.out.println("Столбец неизвестных: \t\t\t\t\t\t"+"Столбец погрешностей: ");
        for (int i = 0 ; i < dimension ; i++) {
            String message = "\t X["+(i+1)+"] = "+xCurIter[i];
            while (message.length() <40) {
                message+=" ";
            }
            System.out.print(message);
            System.out.println("\t r["+(i+1)+"] = "+Math.abs( xCurIter[i] - xPrevIter[i]));
        }
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
