import java.util.*;

public class matrix {
  private cell[][] cellMatrix;
  private Queue<cell> queue; // a nice FIFO structure

  public matrix (cell[] givenCells) { // constructor
    // making the default input
    ArrayList<Integer> defaultInput = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++) {
        defaultInput.add(i + 1); // 1 ... 9
    }

    this.cellMatrix = new cell[9][9];
    int position[] = new int[2];
    for (int i = 0; i < 9; i++) {
      position[0] = i;
      for (int j = 0; j < 9; j++) {
        position[1] = j;
        this.cellMatrix[i][j] = new cell(defaultInput, position);
      }
    }
    queue = new LinkedList<cell>();
    for (int i = 0; i < givenCells.length; i++) {
      this.queue.add(givenCells[i]); // making the queue filled with cells
      replace(givenCells[i]); // replace the matrix entries
    }
  }

  public void replace(cell myCell) { 
    this.cellMatrix[myCell.position()[0]][myCell.position()[1]] = new cell(myCell.currentHints(), myCell.position());
  }

  public void simpleSolve () {
    while (queue.size() < 81) {
      cell currentCell = queue.poll(); // get first in queue and remove it
      rowSolve(currentCell);
      colSolve(currentCell);
      blockSolve(currentCell);
      queue.add(currentCell); // add as last in queue
    }
  }

  public void rowSolve (cell myCell) {
    cell x;
    for (int i = 0; i < 9; i++) {
      x = cellMatrix[myCell.row()][i];
      if (i != myCell.col()) {
        x.remove(myCell.solution());
        if (x.size() == 1) queue.add(x); // if size is 1, add to the queue
      }
    }
  }

  public void colSolve (cell myCell) {
    cell x;
    for (int i = 0; i < 9; i++) {
      x = cellMatrix[i][myCell.col()];
      if (i != myCell.row()) {
        x.remove(myCell.solution());
        if (x.size() == 1) queue.add(x);
      }
    }
  }

  public void blockSolve (cell myCell) {
    cell x;
    // figure out a starting point
    int[] start_point = new int[2];
    start_point[0] = myCell.row();
    start_point[1] = myCell.col();
    while (start_point[0] != 0 || start_point[0] != 3 || start_point[0] != 6) start_point[0]--;
    while (start_point[1] != 0 || start_point[1] != 3 || start_point[1] != 6) start_point[1]--;
    for (int i = start_point[0]; i < start_point[0] + 3; i++) {
      for (int j = start_point[1]; j < start_point[1] + 3; j++) {
        if (!(i == myCell.row() && j == myCell.col())){
          cellMatrix[i][j].remove(myCell.solution());
          if (cellMatrix[i][j].size() == 1) queue.add(cellMatrix[i][j]);
        }
      }
    }
  }
}
