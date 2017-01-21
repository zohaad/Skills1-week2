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
      cell x = this.queue.poll(); // remove and retrieve head of queue
      rowSolve(x);
      colSolve(x);
      blockSolve(x);
    }
  }

  public void rowSolve (cell myCell) {
    cell x;
    for (int i = 0; i < 9; i++) { // fix row, go through columns
      x = this.cellMatrix[myCell.row()][i];
      if (i != myCell.col()) {
        x.remove(myCell.solution());
        if (x.solved()) {
          this.queue.add(x);
        }
      }
    }
  }

  public void colSolve (cell myCell) {
    cell x;
    for (int i = 0; i < 9; i++) { // fix column, go through rows
      x = this.cellMatrix[i][myCell.col()];
      if (i != myCell.row()) {
        x.remove(myCell.solution());
        if (x.solved()) {
          this.queue.add(x);
        }
      }
    }
  }

  public void blockSolve (cell myCell) {
    cell x;
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
      for (int j = start_point[1]; i < start_point[1] + 3; j++) {
        x = this.cellMatrix[i][j];
        if (!(i == myCell.row() && j == myCell.col())) {
          x.remove(myCell.solution());
          if (x.solved()) {
            this.queue.add(x);
          }
        }
      }
    }
  }

  public void print () {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(this.cellMatrix[i][j].solution() + " ");
      }
      System.out.println();
    }
  }
}
