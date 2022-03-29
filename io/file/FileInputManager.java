package io.file;

import model.SquareMatrixWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInputManager {
    private final String[] askingMessages = {};
    private final String[] errorMessages = {
            "Ошибка! Количество элементов не совпадает размерностью матрицы.",
            "Ошибка! Файл не найден.",
            "Ошибка! Не удалось прочитать данные из файла.",
            "Ошибка! Введенное значение слишком велико.",
            "Ошибка! Введенное значение слишком мало.",
            "Ошибка! Значение погрешности должно быть положительным.",
            "Предупреждение! Расчеты для нулевой погрешности могут занять много времени.",
    };
    private final SquareMatrixWrapper squareMatrixWrapper;
    private File file ;
    private Scanner scanner ;

    public FileInputManager() {
        squareMatrixWrapper = new SquareMatrixWrapper();

    }

    public SquareMatrixWrapper getSquareMatrixWrapper(String inputFilePath) {
        try {
            file = new File("input.txt");
            scanner = new Scanner(file);
            int dimension = this.readDimension();
            squareMatrixWrapper.setDimension( dimension);
            squareMatrixWrapper.setAugmentedMatrix( this.readAugmentedMatrix(dimension) );
            squareMatrixWrapper.setEpsilon( this.readEpsilon() );
        } catch (InvalidFileInputValueException e) {
            System.out.println( e.getMessage() );
            System.exit(1);
        }  catch (FileNotFoundException e) {
            System.out.println( this.errorMessages[1] );
            System.exit(1);
        }
        return squareMatrixWrapper;
    }

    private int readDimension( ) throws InvalidFileInputValueException {
        int dimension = 0;

        try {
            if (scanner.hasNext()){
                dimension = Integer.parseInt( scanner.nextLine() ) ;
                if (dimension>20) {
                    throw new InvalidFileInputValueException( this.errorMessages[2] + "\n\t"
                            +this.errorMessages[3] + "\n\t"
                            +"[значение порядка матрицы]" );
                }
                if (dimension<1) {
                    throw new InvalidFileInputValueException( this.errorMessages[2] + "\n\t"
                            +this.errorMessages[4] + "\n\t"
                            +"[значение порядка матрицы]" );
                }
                return dimension;
            }
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
        }catch (NumberFormatException e) {
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
        }
    }

    private double readEpsilon() throws InvalidFileInputValueException {
        double epsilon;

        try {
            if (scanner.hasNext()){
                epsilon = Double.parseDouble(scanner.nextLine());
                if (epsilon<0) {
                    throw new InvalidFileInputValueException( this.errorMessages[2] + "\n\t"
                            +this.errorMessages[5] + "\n\t"
                            +"[значение требуемой погрешности]" );
                }
                if (epsilon == 0) {
                    System.out.println( this.errorMessages[6] );
                }
                return epsilon;
            }
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение требуемой погрешности]" );
        } catch (NumberFormatException e) {
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
        }
    }

    private double[][] readAugmentedMatrix(int dimension) throws InvalidFileInputValueException {
        double[][] array = new double[dimension][dimension+1];  // +1 is for Matrix-B elements

        for (int i = 0; i < dimension; i++) {
            array[i] = this.readDoubleArray(i, dimension);
        }
        return array;
    }

    private double[] readDoubleArray(int rowNumber, int dimension ) throws InvalidFileInputValueException {
        dimension = dimension+1; // +1 is for Matrix-B elements
        double[] array = new double[dimension];

        if (scanner.hasNext()){
            String line = scanner.nextLine();
            line = line.trim();
            String[] rawValues = line.split(" ");
            if (rawValues.length != dimension)
                throw new InvalidFileInputValueException( this.errorMessages[2] + "\n\t"
                        +this.errorMessages[0] + "\n\t"
                        +"[данные "+rowNumber+" ряда матрицы]" );
            for (int i = 0; i < dimension; i++) {
                array[i] = Double.parseDouble(rawValues[i]);
            }

            return array;
        } else {
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[данные "+rowNumber+" ряда матрицы]" );
        }
    }
}
