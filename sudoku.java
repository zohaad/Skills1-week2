// sudoku.java
// Jan-Marc Glowienke i6117274
// Zohaad Fazal i6107208
import java.util.*;

public class sudoku {
  private cell[][] cellMatrix;
  private Queue<cell> queue;

  // constructor
  public sudoku (ArrayList<cell> hints) {
    ArrayList<Integer> defaultInput = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++) {
      defaultInput.add(i + 1);
    }

    this.cellMatrix = new cell[9][9];
    int[] pos = new int[2];
    
    // fill the matrix with {1 ... 9} cells
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        pos[0] = i;
        pos[1] = j;
        this.cellMatrix[i][j] = new cell(defaultInput, pos);
      }
    }

    this.queue = new LinkedList<cell>();

    for (int i = 0; i < hints.size(); i++) {
      replace(hints.get(i));
    }
  }

  public void replace (cell myCell) {
    this.cellMatrix[myCell.row()][myCell.col()] = myCell; // doesn't use a copy, so remember to make copies when needed
    if (myCell.size() == 1) {
      this.queue.add(myCell);
    }
  }

  // simpleSolve uses the simple logic rules
  public void simpleSolve () {
    while (!this.queue.isEmpty()) {
      cell x = this.queue.remove(); // remove and retrieve head of queue
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
    }
  }

  public cell[][] returnCellMatrix () {
    return this.cellMatrix;
  }

  public Queue<cell> returnQueue () {
    return this.queue;
  }

  public void rowSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // go through columns
      cell x = this.cellMatrix[myCell.row()][i]; // fix row
      if (i != myCell.col()) { // if we're not on the solution cell..
        int oldSize = x.size();
        x.removeInt(myCell.solution()); // remove value of solution cell
        if (x.solved() && oldSize == 2) { // only add to queue if it has recently been solved
          this.queue.add(x);
        }
      }
    }
  }

  // similarly:
  public void colSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // fix column, go through rows
      cell x = this.cellMatrix[i][myCell.col()];
      if (i != myCell.row()) {
        int oldSize = x.size();
        x.removeInt(myCell.solution());
        if (x.solved() && oldSize == 2) {
          this.queue.add(x);
        }
      }
    }
  }

  public void blockSolve (cell myCell) {
    // first figure out a starting point for the block
    int[] start_point = new int[2];
    start_point[0] = myCell.row();
    start_point[1] = myCell.col();
    // go to left cell in a block:
    while(start_point[0] != 0 && start_point[0] != 3 && start_point[0] != 6) {
      start_point[0]--;
    }
    // go to top cell in a block:
    while(start_point[1] != 0 && start_point[1] != 3 && start_point[1] != 6) {
      start_point[1]--;
    }

    // go through the block:
    for (int i = start_point[0]; i < start_point[0] + 3; i++) {
      for (int j = start_point[1]; j < start_point[1] + 3; j++) {
        cell x = this.cellMatrix[i][j];
        if (!(i == myCell.row() && j == myCell.col())) { // if it's not the solution cell:
          int oldSize = x.size();
          x.removeInt(myCell.solution());
          if (x.solved() && oldSize == 2) {
            this.queue.add(x);
          }
        }
      }
    }
  }

  // using generalization rules
  public void genSolve () {
    while (!this.queue.isEmpty()) {
      // use simpleSolve rules
      cell x = this.queue.remove();
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
      // go through again with generalization rules:
      genRowSolve();
      genColSolve();
      genBlockSolve();
    }
  }

  public void genRowSolve () {
    for (int i = 0; i < 9; i++) { // for every row make List of List of cells
      List<List<cell>> genRow = new ArrayList<>();
      for (int j = 1; j < 10; j++) { // for every number make ArrayList 
        genRow.add(new ArrayList<cell>());
        for (int k = 0; k < 9; k++) { // for every cell (column) in the row
          if (this.cellMatrix[i][k].contains(j)) { // if it contains the number
            genRow.get(j - 1).add(this.cellMatrix[i][k]); // add it to the ArrayList
          }
        }
      }
      // now we have an ArrayList representing {1 ... 9}, containing the cells in ArrayLists. Now we need to pluck out the singleton sets:
      for (int j = 1; j < 10; j++) { // for every number ...
        List<cell> x = genRow.get(j - 1);
        if (x.size() == 1 && x.get(0).size() > 1) { // if only one candidate exists for the number and simpleSolve() couldn't find it...
          // clean the cell up:
          x.get(0).removeExcept(j);
          // and add it to the queue
          this.queue.add(x.get(0));
        }
      }
    }
  }

  // similarly:
  public void genColSolve () {
    for (int i = 0; i < 9; i++) { // for every column
      List<List<cell>> genCol = new ArrayList<>();
      for (int j = 1; j < 10; j++) { // for every number
        genCol.add(new ArrayList<cell>());
        for (int k = 0; k < 9; k++) { // for every row
          if (this.cellMatrix[k][i].contains(j)) {
            genCol.get(j - 1).add(this.cellMatrix[k][i]);
          }
        }
      }
      for (int j = 1; j < 10; j++) {
        List<cell> x = genCol.get(j - 1);
        if (x.size() == 1 && x.get(0).size() > 1) {
          x.get(0).removeExcept(j);
          this.queue.add(x.get(0));
        }
      }
    }
  }

  public void genBlockSolve () {
    // setting block structure
    for (int r = 0; r < 7; r += 3) { // block row
      for (int c = 0; c < 7; c += 3) { // block col
        List<List<cell>> genBlock = new ArrayList<>(); // for every block
        for (int j = 1; j < 10; j++) { // for every number
          genBlock.add(new ArrayList<cell>());
          for (int i = r; i < r + 3; i++) { // for every row
            for (int k = c; k < c + 3; k++) { // for every col
              if (this.cellMatrix[i][k].contains(j)) { // if it contains the number ..
                genBlock.get(j - 1).add(this.cellMatrix[i][k]); // add it to the corresponding ArrayList (type cell)
              }
            }
          }
        }
        for (int j = 1; j < 10; j++) { // for every number
          List<cell> x = genBlock.get(j - 1);
          if (x.size() == 1 && x.get(0).size() > 1) {
            x.get(0).removeExcept(j);
            this.queue.add(x.get(0));
          }
        }
      }
    }
  }

  // a simple printing method for the sudoku
  public void print () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].isEmpty()) { // debug code
          System.out.print("+ ");
        }
        else if (this.cellMatrix[i][j].solved()) {
          System.out.print(this.cellMatrix[i][j].solution() + " ");
        }
        else {
          System.out.print("X ");
        }
        if (j == 2 || j == 5) {
          System.out.print("|");
        }
      }
      System.out.println();
      if (i == 2 || i ==  5) {
        System.out.println("-------------------");
      }
    }
    System.out.println();
  }

  public sudoku bruteForce () { // This uses the enumeration method

    // see if it can be solved with genSolve()
    sudoku A = copy();
    A.genSolve();
    if (!A.contradiction() && A.solved()) {
      return A;
    }

    // make stack of sudoku
    Stack<sudoku> myStack = new Stack<>();

    // make initial stack
    outerloop:
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (A.cellMatrix[i][j].size() > 1) {
          for (int k = 0; k < A.cellMatrix[i][j].size(); k++) { // for all numbers in the unsolved cell
            sudoku B = A.copy(); 
            cell replaceCell = B.cellMatrix[i][j].copy(); // copy the first unsolved cell
            replaceCell.removeExceptIndex(k); // remove all except index k
            B.replace(replaceCell); // replace it in the copied sudoku
            myStack.add(B); // add the guess-sudoku to the stack
          }
          break outerloop;
        }
      }
    }

    int maxStack = 0;
    int iterations = 0;
  
    while (!myStack.isEmpty()) {
      // performance measures
      if (myStack.size() > maxStack) {
        maxStack = myStack.size();
      }
      iterations++;
      // pop it and try to solve it
      A = myStack.pop().copy();
      A.genSolve();
      // 3 things can happen
      // 1. contradiction:
      if (A.contradiction()) { // ignore this guess and continue with the while loop 
        continue;
      }
      // 2. solved:
      else if (A.solved()) {
        // print out performance
        System.out.println("Iterations: " + iterations);
        System.out.println("Max amount of stacks: " + maxStack);
        // return the solved sudoku
        return A;
      }
      // 3. run into another roadblock
      else {
        // similar to making the initial stack:
        outerloop:
        for (int i = 0; i < 9; i++) {
          for (int j = 0; j < 9; j++) {
            if (A.cellMatrix[i][j].size() > 1) { // pick first unsolved cell
              // and add all possible guess-sudokus to the stack
              for (int k = 0; k < A.cellMatrix[i][j].size(); k++) {
                sudoku B = A.copy();
                cell replaceCell = B.cellMatrix[i][j].copy();
                replaceCell.removeExceptIndex(k);
                B.replace(replaceCell);
                myStack.add(B);
              }
              break outerloop;
            }
          }
        }
      }
    }
    return null; // this will only be reached if the sudoku is unsolvable
    // it will then give a Null Pointer Exception in the sudokuSolver.java class
  }

  public sudoku copy () { // this makes a hardcopy
    ArrayList<cell> noHints = new ArrayList<cell>(); // constructor needs ArrayList<cell>
    // provide empty one
    sudoku mySudoku = new sudoku(noHints);
    for (int i = 0; i < 9; i++) { // row
      for (int j = 0; j < 9; j++) { // column
        if (this.cellMatrix[i][j].solved()) {
          mySudoku.queue.add(this.cellMatrix[i][j].copy());
        }
        mySudoku.cellMatrix[i][j] = this.cellMatrix[i][j].copy();
      }
    }
    return mySudoku;
  }

  // contraction happens when a cell is empty
  public boolean contradiction () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  // solved when the cell has size() 1
  public boolean solved () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].size() != 1) {
          return false;
        }
      }
    }
    return true;
  }
}
