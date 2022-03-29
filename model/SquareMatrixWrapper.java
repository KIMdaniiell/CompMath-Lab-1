package model;

import model.MatrixWrapperValidity;

public class SquareMatrixWrapper {
    private int dimension;
    private double[][] matrixA;    //coefficient matrix
    private double[] matrixB;      //additional column consisting of the vector b
    private double epsilon;
    private MatrixWrapperValidity validity = MatrixWrapperValidity.NOT_INITIALIZED;

    public SquareMatrixWrapper() {
    }

    @Deprecated
    public void printMatrixA() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                String element = this.matrixA[i][j] + "";
                if (i == j) {
                    System.out.print("["+element+ "]\t"  );
                } else {
                    System.out.print(element+ "  \t");
                }
            }
            System.out.println();
        }
    }
    @Deprecated
    public void printMatrixB() {
        for (int i = 0; i < dimension; i++) {
            String element = this.matrixB[i]+"";
            System.out.println("B["+(i+1)+"] = " +element);
        }
    }

    public void setAugmentedMatrix(double[][] m) {
        double[][] matrixA = new double[this.dimension][this.dimension];
        double[] matrixB = new double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrixA[i][j] = m[i][j];
            }
            matrixB[i] = m[i][dimension];
        }

        this.setMatrixA(matrixA);
        this.setMatrixB(matrixB);
    }

    public void setMatrixA(double[][] matrixA) {
        this.matrixA = matrixA;
        switch (this.validity) {
            case NOT_INITIALIZED:
                this.validity = MatrixWrapperValidity.MATRIX_A_INITIALIZED;
                break;
            case MATRIX_B_INITIALIZED:
                this.validity = MatrixWrapperValidity.INITIALIZED;
                break;
        }
    }

    public void setMatrixB(double[] matrixB) {
        this.matrixB = matrixB;
        switch (this.validity) {
            case NOT_INITIALIZED:
                this.validity = MatrixWrapperValidity.MATRIX_B_INITIALIZED;
                break;
            case MATRIX_A_INITIALIZED:
                this.validity = MatrixWrapperValidity.INITIALIZED;
                break;
        }
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double[] getMatrixB() {
        return matrixB;
    }

    public double[][] getMatrixA() {
        return matrixA;
    }

    public MatrixWrapperValidity getValidity() {
        return validity;
    }
}
