import java.util.*;
import java.io.*;

public class Sudoku {
  public static void main (String[] args) {
    String filename = args[0];
    Matrix A = null;

    try {
      A = matrixMaker(filename);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    A.print();
    A.simpleSolve();
    System.out.println();
    A.print();
  }

  public static Matrix matrixMaker (String filename)
    throws java.io.FileNotFoundException {
    File file = new File(filename);
    Scanner scan = new Scanner(file);

    int n = scan.nextInt();

    int[] pos = new int[2];
    Cell[] givenHintArray = new Cell[n];

    for (int i = 0; i < n; i++) {
      pos[0] = scan.nextInt();
      pos[1] = scan.nextInt();
      ArrayList<Integer> givenHint = new ArrayList<Integer>();
      givenHint.add(scan.nextInt());
      givenHintArray[i] = new Cell(givenHint, pos);
    }
    Matrix myMatrix = new Matrix(givenHintArray);
    return myMatrix;
  }
}
