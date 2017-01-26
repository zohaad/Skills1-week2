// sudokuSolver.java
// Jan-Marc Glowienke i6117274
// Zohaad Fazal i6107208

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
    sudoku B = A.bruteForce();
    System.out.println();
    B.print();
  }

  // This reads the sudoku from a file
  public static sudoku sudokuReader (String filename)
    throws java.io.FileNotFoundException {
    File file = new File(filename);
    Scanner scan = new Scanner(file);

    int n = scan.nextInt();

    int[] pos = new int[2];
    ArrayList<cell> hintAL = new ArrayList<cell>();

    // reading in the hints
    for (int i = 0; i < n; i++) {
      pos[0] = scan.nextInt();
      pos[1] = scan.nextInt();
      ArrayList<Integer> readHint = new ArrayList<Integer>();
      readHint.add(scan.nextInt());

      // making a cell object and add it to the hintAL 
      hintAL.add(new cell(readHint, pos));
    }
    // which is then passed to the constructor of sudoku.java
    return new sudoku(hintAL);
  }
}
