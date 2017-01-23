import java.util.*;
import java.io.*;

public class sudokuSolver {
  public static void main (String[] args) {
    String filename = args[0];
    sudoku A = null;

    try {
      A = sudokuReader(filename);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    A.print();
    // A.simpleSolve();
    // A.genSolve();
    boolean a = A.bruteForce();
    System.out.println();
    A.print();
    System.out.println(a);
  }

  public static sudoku sudokuReader (String filename)
    throws java.io.FileNotFoundException {
    File file = new File(filename);
    Scanner scan = new Scanner(file);

    int n = scan.nextInt();

    int[] pos = new int[2];
    ArrayList<cell> hintAL = new ArrayList<cell>();

    for (int i = 0; i < n; i++) {
      pos[0] = scan.nextInt();
      pos[1] = scan.nextInt();
      ArrayList<Integer> readHint = new ArrayList<Integer>();
      readHint.add(scan.nextInt());

      hintAL.add(new cell(readHint, pos));
    }
    return new sudoku(hintAL);
  }
}
