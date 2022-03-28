package io;

import computation.SquareMatrixWrapper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixInputManager {
    String[] askingMessages = {"Введите порядок матрицы [n] :",
            "Введите требуемую погрешность [ε]:",
            "Введите элементы %d ряда матрицы:",
            "Выберите режим ввода данных:\n\t-пользовательский ввод[0]\n\t-ввод из файла[1]\n\t-генерация случайных матриц [2]"};
    String[] errorMessages = {"Ошибка! Не удалось распознать число.",
            "Ошибка! Введенное значение слишком велико.",
            "Ошибка! Введенное значение слишком мало.",
            "Ошибка! Введенное значение не соответствует ни одному допустимому значению.",
            "Предупреждение! Расчеты для нулевой погрешности могут занять много времени.",
            "Произошла непредвиденная ошибка!",
            "Ошибка! Количество элементов не совпадает размерностью матрицы."};


    public SquareMatrixWrapper readSquareMatrixWrapper(){
        SquareMatrixWrapper squareMatrixWrapper = new SquareMatrixWrapper();


        switch ( this.readEntryMode() ) {
            case MANUAL:
                int dimension = this.readDimension();
                squareMatrixWrapper.setDimension( dimension);
                squareMatrixWrapper.setAugmentedMatrix( this.readAugmentedMatrix(dimension) );
                squareMatrixWrapper.setEpsilon( this.readEpsilon() );
                break;
            case FILE:
                System.out.println("Пока не реализовано!");
                //TODO
                break;
            case GENERATE:
                System.out.println("Пока не реализовано!");
                //TODO
                break;
        }
        return squareMatrixWrapper;
    }

    private DataEntryMode readEntryMode() {
        boolean valid = false;
        int modeNumber = 0;
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.println( this.askingMessages[3]);
            try {
                modeNumber = scanner.nextInt();
                if ((modeNumber != 0)&&(modeNumber != 1)&&(modeNumber != 2))
                    throw new InvalidInputValueException( errorMessages[3]);
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
        if (modeNumber == 0)
            return DataEntryMode.MANUAL;
        if (modeNumber == 1)
            return DataEntryMode.FILE;
        return DataEntryMode.GENERATE;
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
                if (epsilon == 0) {
                    System.out.println(this.errorMessages[4]);
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println( this.errorMessages[0] );
                scanner.next();
            } catch (Exception e) {
                System.out.println(this.errorMessages[5]);
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

    private double[] readDoubleArray(int rowNumber, int dimension ) {
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
                    throw new InvalidInputValueException( this.errorMessages[6] );
                for (int i = 0; i < dimension; i++) {
                    array[i] = Double.parseDouble(rawValues[i]);
                }
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println( this.errorMessages[0] );
            } catch (InvalidInputValueException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(this.errorMessages[5]);
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        return array;
    }

}
