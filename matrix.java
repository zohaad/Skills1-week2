import java.util.*;

public class matrix {
  private cell[][] cellMatrix;
  private Queue<cell> queue;

  public matrix (cell[] hints) {
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

    for (int i = 0; i < hints.length; i++) {
      replace(hints[i]);
      this.queue.add(hints[i]);
    }
  }

  public void replace (cell myCell) {
    this.cellMatrix[myCell.row()][myCell.col()] = new cell(myCell.currentCandidates(), myCell.position());
  }

  public void simpleSolve () {
    while (!this.queue.isEmpty()) {
      cell x = this.queue.remove(); // remove and retrieve head of queue
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
    }
  }

  public void rowSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // fix row, go through columns
      cell x = this.cellMatrix[myCell.row()][i];
      if (i != myCell.col()) {
        int p = x.size();
        x.remove(myCell.solution());
        if (x.solved() && p == 2 ) { // only add to queue if it has recently been solved
          this.queue.add(x);
        }
      }
    }
  }

  public void colSolve (cell myCell) {
    for (int i = 0; i < 9; i++) { // fix column, go through rows
      cell x = this.cellMatrix[i][myCell.col()];
      if (i != myCell.row()) {
        int p = x.size();
        x.remove(myCell.solution());
        if (x.solved() && p == 2) {
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
          int p = x.size();
          x.remove(myCell.solution());
          if (x.solved() && p == 2) {
            this.queue.add(x);
          }
        }
      }
    }
  }

  public void print () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.cellMatrix[i][j].solved()) {
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
}
