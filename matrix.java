public class matrix {
  private cell[][] cellMatrix;
  private Stack queue;

  public matrix (cell[] givenHints) { // constructor
    int[] defaultInput = new int[9];
    for (int i = 0; i < 9; i++) {
        defaultInput[i] = i + 1;
    }
    this.cellMatrix = new cell[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        this.cellMatrix[i][j] = new cell(defaultInput);
      }
    }
    for (int i = 0; i < givenHints.length; i++) { // making the queue filled with stacks
      this.queue.push(givenHints[i]);
    }
  }


  public void replace(int[] entry, cell myCell) { 
    this.cellMatrix[entry[0]][entry[1]] = new cell(myCell.)
  }
}
