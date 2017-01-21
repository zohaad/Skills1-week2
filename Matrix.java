import java.util.*;
public class Matrix {
  private Cell[][] cellMatrix;
  private Queue<Cell> queue;

  public Matrix (Cell[] givenCells) {
    ArrayList<Integer> defaultInput = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++) {
      defaultInput.add(i + 1);
    }

    cellMatrix = new Cell[9][9];
    int[] pos = new int[2];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        pos[0] = i;
        pos[1] = j;
        this.cellMatrix[i][j] = new Cell(defaultInput, pos);
      }
    }

    this.queue = new Queue<Cell>;

    for (int i = 0; i < givenCells.length; i++) {
      replaceCell(givenCells[i]);
      this.queue.add(givenCells[i]);
    }

  }
  public void replaceCell (Cell newCell) {
    this.cellMatrix[newCell.row()][newCell.col()] = new Cell(newCell.currentHints(), newCell.position());
  }

  public void simpleSolve () {
    rowSolve();
    //colSolve();
    //blockSolve();
  }

  public void rowSolve (Cell myCell) {
    Cell x;
    for (int i = 0; i < 9; i++) {
      x = this.cellMatrix[myCell.row()][i];
      if (i != myCell.col()) {
        x.remove(myCell.solution());
        if (x.solved()) { // if the Cell is solved, add it to the queue
          this.queue.add(x);
        }
      }
    }
  }

  public void colSolve (Cell myCell) {
    Cell y;
    for (int i = 0; i < 9; i++) {
      y = this.cellMatrix[i][myCell.row()];
      if (i != myCell.row()) {
        y.remove(myCell.solution());
        if (y.solved()) {
          this.queue.add(y);
        }
      }
    }
  }

  public void blockSolve (Cell myCell) {
    Cell z;
    // figure out starting point
    int[] start_point = new int[2];
    start_point[0] = myCell.row();
    start_point[1] = myCell.col();
    while (start_point[0] != 0 && start_point[0] != 3 && start_point[0] != 6) {
      start_point[0]--;
    }
    while (start_point[1] != 0 && start_point[1] != 3 && start_point[1] != 6) {
      start_point[1]--;
    }

    for (int i = start_point[0]; i < start_point[0] + 3; i++) {
      for (int j = start_point[1]; i < start_point[1] + 3; i++) {
        z = this.cellMatrix[i][j];
        if (!(i == myCell.row() && j == myCell)) {
          z.remove(myCell.solution());
          if (z.solved()) {
            this.queue.add(z);
          }
        }
      }
    }
  }
}


