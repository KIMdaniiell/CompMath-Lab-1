package io.file;

import computation.SquareMatrixWrapper;
import io.InvalidInputValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInputManager {
    private final String[] askingMessages = {};
    private final String[] errorMessages = {
            "Ошибка! Количество элементов не совпадает размерностью матрицы.",
            "Ошибка! Файл не найден.",
            "Ошибка! Не удалось прочитать данные из файла."
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

        if (scanner.hasNext()){
            try {
                dimension = Integer.parseInt( scanner.nextLine() ) ;
                return dimension;
            } catch (NumberFormatException e) {
                throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
            }
        } else {
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
        }
    }

    private double readEpsilon() throws InvalidFileInputValueException {
        double epsilon;

        if (scanner.hasNext()){
            try {
                epsilon = Double.parseDouble(scanner.nextLine());
                return epsilon;
            } catch (NumberFormatException e) {
                throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение порядка матрицы]" );
            }
        } else {
            throw new InvalidFileInputValueException( this.errorMessages[2]+"\t[значение требуемой погрешности]" );
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
