package io;

import model.SquareMatrixWrapper;
import io.console.ConsoleInputManager;
import io.file.FileInputManager;
import io.generator.MatrixGenerationManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixInputManager {
    private final String[] askingMessages = {
            "Выберите режим ввода данных:\n\t-пользовательский ввод[0]\n\t-ввод из файла[1]\n\t-генерация случайных матриц [2]"
    };
    private final String[] errorMessages = {"Ошибка! Не удалось распознать число.",
            "Ошибка! Введенное значение не соответствует ни одному допустимому значению.",
            "Произошла непредвиденная ошибка!",
    };


    public SquareMatrixWrapper readSquareMatrixWrapper(String inputFilePath){
        switch ( this.readEntryMode() ) {
            case MANUAL:
                ConsoleInputManager consoleInputManager = new ConsoleInputManager();
                return consoleInputManager.getSquareMatrixWrapper();
            case FILE:
                FileInputManager fileInputManager = new FileInputManager();
                return fileInputManager.getSquareMatrixWrapper(inputFilePath);
            case GENERATE:
                MatrixGenerationManager matrixGenerationManager = new MatrixGenerationManager();
                return matrixGenerationManager.getSquareMatrixWrapper();
            default:
                return new SquareMatrixWrapper();
        }
    }

    private DataEntryMode readEntryMode() {
        boolean valid = false;
        int modeNumber = 0;
        Scanner scanner = new Scanner(System.in);

        while (!valid) {
            System.out.println( this.askingMessages[0]);
            try {
                modeNumber = Integer.parseInt(scanner.nextLine().trim());
                if ((modeNumber != 0)&&(modeNumber != 1)&&(modeNumber != 2))
                    throw new InvalidInputValueException( errorMessages[1]);
                valid = true;
            } catch ( NumberFormatException e) {
                System.out.println( this.errorMessages[0] );
            } catch (InvalidInputValueException e) {
                System.out.println( e.getMessage() );
            } catch (Exception e) {
                System.out.println(this.errorMessages[2]);
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

}
