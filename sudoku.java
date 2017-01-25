import java.util.*;

public class sudoku {
  private cell[][] cellMatrix;
  private Queue<cell> queue;

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
      this.queue.add(hints.get(i));
    }
  }

  public void replace (cell myCell) {
    this.cellMatrix[myCell.row()][myCell.col()] = myCell;
  }

  public void simpleSolve () {
    while (!this.queue.isEmpty()) {
      cell x = this.queue.remove(); // remove and retrieve head of queue
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
      // print();
      // System.out.println(this.queue.size());
    }
  }

  public void rowSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // fix row, go through columns
      cell x = this.cellMatrix[myCell.row()][i];
      if (i != myCell.col()) {
        int oldSize = x.size();
        x.remove(myCell.solution());
        if (x.solved() && oldSize == 2) { // only add to queue if it has recently been solved
          this.queue.add(x);
        }
      }
    }
  }

  public void colSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // fix column, go through rows
      cell x = this.cellMatrix[i][myCell.col()];
      if (i != myCell.row()) {
        int oldSize = x.size();
        x.remove(myCell.solution());
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
    while(start_point[0] != 0 && start_point[0] != 3 && start_point[0] != 6) {
      start_point[0]--;
    }
    while(start_point[1] != 0 && start_point[1] != 3 && start_point[1] != 6) {
      start_point[1]--;
    }

    for (int i = start_point[0]; i < start_point[0] + 3; i++) {
      for (int j = start_point[1]; j < start_point[1] + 3; j++) {
        cell x = this.cellMatrix[i][j];
        if (!(i == myCell.row() && j == myCell.col())) {
          int oldSize = x.size();
          x.remove(myCell.solution());
          if (x.solved() && oldSize == 2) {
            this.queue.add(x);
          }
        }
      }
    }
  }

  public void genSolve () {
    // while Q is not empty do simpleSolve and genSolve
    while (!this.queue.isEmpty()) {
      cell x = this.queue.remove();
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
      genRowSolve();
      genColSolve();
      genBlockSolve();
      // System.out.println(this.queue.size());
    }
  }

  public void genRowSolve () {
    for (int i = 0; i < 9; i++) { // for every row
      List<List<cell>> genRow = new ArrayList<>();
      for (int j = 1; j < 10; j++) { // for every number
        genRow.add(new ArrayList<cell>());
        for (int k = 0; k < 9; k++) { // for every cell (column) in the row
          if (this.cellMatrix[i][k].contains(j)) {
            genRow.get(j - 1).add(this.cellMatrix[i][k]);
          }
        }
      }
      // now we have an ArrayList representing {1 ... 9}, containing the cells in an arraylist. Now we need to pluck out the singleton sets:
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
      }
      System.out.println();
    }
    System.out.println();
  }

  public sudoku bruteForce () { // this uses enumeration method
    sudoku A = copy();
    A.genSolve();
    if (!A.contradiction() && A.solved()) {
      return A;
    }
    mainloop:
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].size() > 1) {
          cell missingCell = this.cellMatrix[i][j].copy();

          for (int p = 0; p < missingCell.size(); p++) {
            cell testCell = missingCell.copy();
            testCell.removeExceptIndex(p);
            sudoku oldA = A.copy();
            A.replace(testCell);
            A.genSolve();

            if (A.contradiction()) {
              A = oldA.copy();
              continue;
            }
            else if (A.solved()) {
              return A;
            }
            else {
              return A.bruteForce();
            }
          }
        }
      }
    }
    return null;
  }

  public sudoku copy () {
    ArrayList<cell> allHints = new ArrayList<cell>();
    for (int i = 0; i < 9; i++) { // row
      for (int j = 0; j < 9; j++) { // column
        if (this.cellMatrix[i][j].solved()) {
          allHints.add(this.cellMatrix[i][j].copy());
        }
      }
    }
    return new sudoku(allHints);
  }

  public boolean contradiction () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].isEmpty()) {
          return true;
        }
      }
    }
    return true;
  }

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
