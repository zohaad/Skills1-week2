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

  public void rowSolve () {
    
  }

}


