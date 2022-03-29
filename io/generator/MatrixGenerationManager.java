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
                dimension = Integer.parseInt(scanner.nextLine().trim());
                if ( dimension>20) {
                    throw new InvalidInputValueException(errorMessages[1]);
                } else if ( dimension<1) {
                    throw new InvalidInputValueException(errorMessages[2]);
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println( this.errorMessages[0] );
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
                epsilon = Double.parseDouble(scanner.nextLine().trim());
                if (epsilon < 0) {
                    throw new InvalidInputValueException(errorMessages[6]);
                }
                if (epsilon == 0) {
                    System.out.println(this.errorMessages[3]);
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println( this.errorMessages[0] );
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
            matrixA[i] = generateDoubleArray(dimension);
            double rowSum = 0;
            for (double d: matrixA[i]) {
                rowSum += Math.abs(d);
            }
            matrixA[i][i] = rowSum;
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


        }
        /*System.out.print("Стобец сгенерированных неизвестных:\n\t");
        for (double unkown: unknowns) {
            System.out.print(unkown+" ");
        }
        System.out.println();*/
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
