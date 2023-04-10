package driver;

import java.util.Arrays;

public class Try {

    static int dimension;

    public static void main(String[] args) {

    }

    public static int covertToBase10(int[] arr) {
        String base2 = Arrays.toString(arr);
        System.out.println(base2);
        base2 = "";
        for (int i = 0; i < arr.length; i++)
            base2 += Integer.toString(arr[i]);

        System.out.println(base2);
        return Integer.parseInt(base2, 2);
    }

    public static void readingBoardFromServer(String message) {
        String[] boardStrings = message.split(",");
        dimension = Integer.parseInt(boardStrings[0]);
        int[][] boardSolution = new int[dimension][dimension];
        int[] solution = new int[boardStrings.length - 1];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = Integer.parseInt(boardStrings[i + 1]);
        }
        for (int i = 0; i < dimension; i++) {
            int[] row = covertToBinary(Integer.parseInt(boardStrings[i + 1]));
            for (int j = 0; j < dimension; j++) {
                boardSolution[i][j] = row[j];
            }
        }
        System.out.println("*******************");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(boardSolution[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] covertToBinary(int x) {

        String base2 = Integer.toString(x, 2);
        int[] row = new int[dimension];

        int i = dimension - 1, j = base2.length() - 1;
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
}
