import java.util.*;
import java.io.*;

public class sudoku {
  public static void main (String[] args) {
    String filename = args[0];
    matrix A = null;

    try {
      A = matrixReader(filename);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    A.print();
    // A.simpleSolve();
    A.genSolve();
    System.out.println();
    A.print();
  }

  public static matrix matrixReader (String filename)
    throws java.io.FileNotFoundException {
    File file = new File(filename);
    Scanner scan = new Scanner(file);

    int n = scan.nextInt();

    int[] pos = new int[2];
    cell[] hintArray = new cell[n];

    for (int i = 0; i < n; i++) {
      pos[0] = scan.nextInt();
      pos[1] = scan.nextInt();
      ArrayList<Integer> readHint = new ArrayList<Integer>();
      readHint.add(scan.nextInt());

      hintArray[i] = new cell(readHint, pos);
    }
    matrix myMatrix = new matrix(hintArray);
    return myMatrix;
  }
}
