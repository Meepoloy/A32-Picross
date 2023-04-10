
package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * 
 */
public class SaveBoard {

    /**
     * Boolean for file validity
     */
    private boolean isFileValid;

    /**
     * 
     */
    private int[] solution;// int in base 10

    SaveBoard(File file, GameModel model) {
        solution = new int[model.getDimension()];
        readSolution(model.getTempArr());

        System.out.println(Arrays.toString(solution));
        writeFile(model, file);
    }

    private boolean writeFile(GameModel model, File file) {
        String content = Integer.toString(model.getDimension());
        for (int i = 0; i < model.getDimension(); i++) {
            content += " " + solution[i];
        }
        try {
            FileWriter myWriter = new FileWriter(file);

            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return true;

    }

    private boolean openFile(GameModel model, File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return true;

    }

    public void readSolution(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            solution[i] = covertToBase10(arr[i]);
        }
    }

    // private void printArr(GameModel model, File file) {
    // isFileValid = openFile(model, file);
    // if (!isFileValid)
    // return;

    // for (int i : boardRow) {
    // System.out.print(i + " ");
    // }
    // }

    public int covertToBase10(int[] arr) {
        String base2 = Arrays.toString(arr);
        System.out.println(base2);
        base2 = "";
        for (int i = 0; i < arr.length; i++)
            base2 += Integer.toString(arr[i]);

        System.out.println(base2);
        return Integer.parseInt(base2, 2);
    }

    // private void readSolution(GameModel model) {
    // // boardRow
    // int[][] base2Solution = new int[inputDim][inputDim];
    // // for (int i : boardRow) {
    // // System.out.print(i + " ");
    // // System.out.println("boardRow[]" + Arrays.toString(covertToBinary(i)));

    // // for (int[] js : base2Solution) {
    // // // js = Arrays.copyOf(covertToBinary(i), js.length);
    // // for (int k = 0; k < js.length; k++) {
    // // js[k] = covertToBinary(i)[k];
    // // }
    // // }
    // // }

    // for (int i = 0; i < inputDim; i++) {
    // for (int j = 0; j < inputDim; j++) {
    // base2Solution[i][j] = covertToBinary(boardRow[i])[j];
    // }
    // }
    // // System.out.println("***************************************************");

    // // for (int[] is : base2Solution) {
    // // for (int is2 : is) {
    // // System.out.print(is2 + " ");
    // // }
    // // System.out.println();
    // // }
    // System.out.println("In loadboard class");

    // model.generatePicross(true, base2Solution);
    // }

    public boolean getStatus() {
        return isFileValid;
    }
}
