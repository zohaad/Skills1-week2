import java.util.*;
import java.io.*;

public class sudoku {
  public static void main (String args[]) {
    String filename = "Sudoku1.txt";
    matrix A = null;

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

  public static matrix matrixMaker (String filename)
    throws java.io.FileNotFoundException {
      File file = new File(filename);
      Scanner scan = new Scanner(file);

      int n = scan.nextInt();

      int[] pos = new int[2];
      cell[] givenHintsArray = new cell[n];

      for (int i = 0; i < n; i++) {
        pos[0] = scan.nextInt();
        pos[1] = scan.nextInt();
        ArrayList<Integer> givenHint = new ArrayList<Integer>();
        givenHint.add(scan.nextInt());
        givenHintsArray[i] = new cell(givenHint, pos);
      }
      matrix myMatrix = new matrix(givenHintsArray);
      return myMatrix;
  }
}
