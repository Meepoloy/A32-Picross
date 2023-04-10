package game;

import java.io.File;
import java.util.Scanner;

/**
 * This class is resposible for loading/reading solution from file
 */
public class LoadBoard {

    /**
     * Dimension from input
     */
    private int inputDim;

    /**
     * board solution from input in base 10
     */
    private int[] boardRow; // in base 10

    /**
     * Boolean that for file validity
     */
    private boolean isFileValid;

    /**
     * Scanner obj
     */
    private Scanner input;

    /**
     * LoadBoard constructor
     * 
     * @param file  File chosen
     * @param model GameModel obj
     */
    LoadBoard(File file, GameModel model) {
        inputDim = 0;
        printArr(model, file);
        model.setDimension(inputDim);
        setSolution(model);
    }

    /**
     * Private method that opens selected file
     * 
     * @param model GameModel obj
     * @param file  File selected
     * @return boolean status of the method whether successful or not
     */
    private boolean openFile(GameModel model, File file) {
        int count = 0;
        try {
            // boardObj = new File(filePath);
            input = new Scanner(file);
            if (input.hasNextInt()) {
                inputDim = input.nextInt();
                if (inputDim == 0) {
                    input.close();
                    return false;
                }
                boardRow = new int[inputDim];
                // count = inputDim;
                System.out.println(inputDim + " = " + checkDim(inputDim, model));
                if (checkDim(inputDim, model))
                    while (input.hasNextInt()) {
                        int x = input.nextInt();
                        if (isValidBase10(x)) {
                            boardRow[count] = x;
                        } else {
                            input.close();
                            return false;
                        }
                        count++;
                    }
            }
            input.close();
            if (count != boardRow.length)
                return false;
        } catch (Exception e) {
            input.close();
            System.out.println("Error Opening File");
            return false;

        }
        return true;
    }

    /**
     * Method that check the dim whether it falls under supported dimension
     * 
     * @param inputDim Int from file
     * @param model    GameModel obj
     * @return boolean status of the method whether successful or not
     */
    private boolean checkDim(int inputDim, GameModel model) {
        for (String i : model.getDimesionList()) {
            if (Integer.parseInt(i) == inputDim)
                return true;
        }
        return false;
    }

    /**
     * Private method that validates input int whether is a suitable base10
     * 
     * @param x int from file
     * @return boolean status of the method whether successful or not
     */
    private boolean isValidBase10(int x) {
        int upperLimit = (int) Math.pow(2, inputDim) - 1, lowerLimit = 0;
        if (x <= upperLimit && x >= lowerLimit)
            return true;

        return false;
    }

    /**
     * Method that prints the contents of the file to the console
     * 
     * @param model Game model obj
     * @param file  File selected
     */
    private void printArr(GameModel model, File file) {
        isFileValid = openFile(model, file);
        if (!isFileValid)
            return;

        for (int i : boardRow) {
            System.out.print(i + " ");
        }
    }

    /**
     * Method that converts int in base to base 2
     * 
     * @param x int base 10
     * @return int[] that contains the binary
     */
    private int[] covertToBinary(int x) {

        String base2 = Integer.toString(x, 2);
        int[] row = new int[inputDim];

        int i = inputDim - 1, j = base2.length() - 1;
        while (i >= 0) {
            if (j >= 0) {
                row[i] = base2.charAt(j) - '0';
            } else
                row[i] = 0;

            i--;
            j--;
        }

        return row;
    }

    /**
     * Method thats sets the solution in Model
     * 
     * @param model Gamemodel obj
     */
    private void setSolution(GameModel model) {
        int[][] base2Solution = new int[inputDim][inputDim];

        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < inputDim; j++) {
                base2Solution[i][j] = covertToBinary(boardRow[i])[j];
            }
        }
        model.generatePicross(true, base2Solution);
    }

    /**
     * Boolean that check for the status of reading file
     * 
     * @return boolean status of the method whether successful or not
     */
    public boolean getStatus() {
        return isFileValid;
    }
}
