package io.generator;

import model.SquareMatrixWrapper;
import io.InvalidInputValueException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixGenerationManager {
    private final String[] askingMessages = {"Введите порядок матрицы [n] :",
            "Введите требуемую погрешность [ε]:",
    };
    private final String[] errorMessages = {"Ошибка! Не удалось распознать число.",
            "Ошибка! Введенное значение слишком велико.",
            "Ошибка! Введенное значение слишком мало.",
            "Предупреждение! Расчеты для нулевой погрешности могут занять много времени.",
            "Произошла непредвиденная ошибка!",
            "Ошибка! Значение погрешности должно быть положительным.",
    };
    private final double upperLimit = 1000;
    private final double lowerLimit = -1000;
    private final SquareMatrixWrapper squareMatrixWrapper;

    public MatrixGenerationManager() {
        squareMatrixWrapper = new SquareMatrixWrapper();
    }

    public SquareMatrixWrapper getSquareMatrixWrapper() {
        int dimension = this.readDimension();
        squareMatrixWrapper.setDimension( dimension);
        squareMatrixWrapper.setEpsilon( this.readEpsilon() );
        double[][] matrixA = this.generateMatrixA(dimension);
        squareMatrixWrapper.setMatrixA( matrixA );
        squareMatrixWrapper.setMatrixB( this.calculateMatrixB(dimension, matrixA) );
        return squareMatrixWrapper;
    }

    private int readDimension(  ) {
        boolean valid = false;
        int dimension = 0;
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.println( this.askingMessages[0] );
            try {
                dimension = scanner.nextInt();
                if ( dimension>20) {
                    throw new InvalidInputValueException(errorMessages[1]);
                } else if ( dimension<1) {
                    throw new InvalidInputValueException(errorMessages[2]);
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println( this.errorMessages[0] );
                scanner.next();
            } catch (InvalidInputValueException e) {
                System.out.println( e.getMessage() );
            } catch (Exception e) {
                System.out.println(this.errorMessages[5]);
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        return dimension;
    }

    private double readEpsilon() {
        boolean valid = false;
        double epsilon = 0;
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.println( this.askingMessages[1] );
            try {
                epsilon = scanner.nextDouble();
                if (epsilon < 0) {
                    throw new InvalidInputValueException(errorMessages[6]);
                }
                if (epsilon == 0) {
                    System.out.println(this.errorMessages[3]);
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println( this.errorMessages[0] );
                scanner.next();
            } catch (InvalidInputValueException e) {
                System.out.println( e.getMessage() );
            } catch (Exception e) {
                System.out.println(this.errorMessages[4]);
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        return epsilon;
    }

    private double[][] generateMatrixA(int dimension) {
        double[][] matrixA = new double[dimension][dimension];
        double maxDigit;
        int indexOfMax;

        for (int i = 0; i< dimension; i++) {
            maxDigit = -1001;
            indexOfMax = 0;
            matrixA[i] = generateDoubleArray(dimension);
            for (int j = 0; j<dimension;j++) {
                if (matrixA[i][j] > maxDigit) {
                    maxDigit = matrixA[i][j];
                    indexOfMax = j;
                }
            }
            double temp = matrixA[i][0];
            matrixA[i][0] = matrixA[i][indexOfMax];
            matrixA[i][indexOfMax] = temp;

            boolean copiedBiggestElement = false;
            for (int j = 1; j< dimension;j++){
                if (matrixA[i][0] <= matrixA[i][j]) {
                    copiedBiggestElement = true;
                }
            }
            if (copiedBiggestElement) {
                matrixA[i][0] +=1;
            }
            temp = matrixA[i][0];
            matrixA[i][0] = matrixA[i][i];
            matrixA[i][i] = temp;
        }
        return matrixA;
    }

    private double[] generateArrayOfUnknowns(int dimension) {
        return this.generateDoubleArray(dimension);
    }

    private double[] calculateMatrixB(int dimension, double[][] mA) {
        double[] matrixB = new double[dimension];
        double[][] matrixA = mA;
        double[] unknowns = this.generateArrayOfUnknowns(dimension);
        double sumOfRow;
        for (int i =0; i< dimension; i++) {
            sumOfRow = 0;
            for (int j = 0; j<dimension; j++) {
                sumOfRow += unknowns[j] * matrixA[i][j];
            }
            matrixB[i] = sumOfRow;

            System.out.print(unknowns[i]+" ");
        }
        System.out.println();
        return matrixB;
    }

    private double[] generateDoubleArray( int dimension) {
        double[] array = new double[dimension];
        for (int i = 0; i< dimension; i++) {
            array[i] = (Math.random()*(upperLimit-lowerLimit) + lowerLimit);
            array[i] = Math.ceil(array[i]*1000)/1000;
        }
        return array;
    }

}
