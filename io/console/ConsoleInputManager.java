package io.console;

import model.SquareMatrixWrapper;
import io.InvalidInputValueException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputManager {
    private final String[] askingMessages = {"Введите порядок матрицы [n] :",
            "Введите требуемую погрешность [ε]:",
            "Введите элементы %d ряда матрицы:",
    };
    private final String[] errorMessages = {"Ошибка! Не удалось распознать число.",
            "Ошибка! Введенное значение слишком велико.",
            "Ошибка! Введенное значение слишком мало.",
            "Предупреждение! Расчеты для нулевой погрешности могут занять много времени.",
            "Произошла непредвиденная ошибка!",
            "Ошибка! Количество элементов не совпадает размерностью матрицы.",
            "Ошибка! Значение погрешности должно быть положительным.",
    };
    private final SquareMatrixWrapper squareMatrixWrapper;

    public ConsoleInputManager() {
        squareMatrixWrapper = new SquareMatrixWrapper();
    }

    public SquareMatrixWrapper getSquareMatrixWrapper() {
        int dimension = this.readDimension();
        squareMatrixWrapper.setDimension( dimension);
        squareMatrixWrapper.setAugmentedMatrix( this.readAugmentedMatrix(dimension) );
        squareMatrixWrapper.setEpsilon( this.readEpsilon() );
        return squareMatrixWrapper;
    }

    private int readDimension(  ) {
        boolean valid = false;
        int dimension = 0;
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.println( this.askingMessages[0] );
            try {
                dimension = Integer.parseInt(scanner.nextLine());
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
                String str = scanner.nextLine();
                epsilon = Double.parseDouble(str);
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

    private double[][] readAugmentedMatrix(int dimension) {
        double[][] array = new double[dimension][dimension+1];  // +1 is for Matrix-B elements

        for (int i = 0; i < dimension; i++) {
            array[i] = this.readDoubleArray(i,dimension);
        }
        return array;
    }

    private double[] readDoubleArray(int rowNumber, int dimension) {
        boolean valid = false;
        dimension = dimension+1; // +1 is for Matrix-B elements
        double[] array = new double[dimension];
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.printf( askingMessages[2]+"\n",rowNumber+1);
            try {
                String line = scanner.nextLine();
                line = line.trim();
                String[] rawValues = line.split(" ");
                if (rawValues.length != dimension)
                    throw new InvalidInputValueException( this.errorMessages[5] );
                for (int i = 0; i < dimension; i++) {
                    array[i] = Double.parseDouble(rawValues[i]);
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println( this.errorMessages[0] );
            } catch (InvalidInputValueException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(this.errorMessages[4]);
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        return array;
    }
}
