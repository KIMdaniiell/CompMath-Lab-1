package model.computation;

import model.SquareMatrixWrapper;

public class GaussSeidelConditionChecker {
    private final String[] errorMessages = {
            "Ошибка! На ведущей диагонали есть нулевой элемент.",
            "Ошибка! Не выполняется строгое условие сходимости метода.",
            "Ошибка! Не выполняется нестрогое условие сходимости метода.",
    };
    private SquareMatrixWrapper squareMatrixWrapper;

    public boolean check(SquareMatrixWrapper squareMatrixWrapper) {
        this.squareMatrixWrapper = squareMatrixWrapper;
        return this.checkConvergenceCondition()
                && this.checkIfNoZeros();
    }

    private boolean checkConvergenceCondition() {
        double[][] matrixA = this.squareMatrixWrapper.getMatrixA();
        int dimension = this.squareMatrixWrapper.getDimension();
        boolean satisfiesNonStrictConvergence = false;
        double rowSum;
        for (int i=0; i<dimension; i++ ) {
            rowSum = 0;
            for (int j=0; j<dimension; j++) {
                rowSum += Math.abs(matrixA[i][j]);
            }
            rowSum -= matrixA[i][i];
            if (matrixA[i][i] < rowSum) {
                System.out.println( this.errorMessages[2]
                        + "\n\t |aii| >= S( |aik| ) , k != i");
                return false;
            }
            if (matrixA[i][i] > rowSum) {
                satisfiesNonStrictConvergence = true;
            }
        }
        if (!satisfiesNonStrictConvergence)
            System.out.println( this.errorMessages[1]
                    + "\n\t aii > S( aik ) , k != i");
        return satisfiesNonStrictConvergence;
    }

    private boolean checkIfNoZeros() {
        double[][] matrixA = this.squareMatrixWrapper.getMatrixA();
        int dimension = this.squareMatrixWrapper.getDimension();
        for (int i=0; i<dimension; i++ ) {
            if (matrixA[i][i] == 0) {
                System.out.println( this.errorMessages[0] );
                return false;
            }
        }
        return true;
    }
}
