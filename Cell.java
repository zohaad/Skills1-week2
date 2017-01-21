import java.util.*;
public class Cell {
  private ArrayList<Integer> hints;
  private int[] position;

  public Cell (ArrayList<Integer> hintsGiven, int[] positionGiven) {
    this.position = new int[2];
    this.position[0] = positionGiven[0];
    this.position[1] = positionGiven[1];

    this.hints = new ArrayList<Integer>();

    for (int i = 0; i < hintsGiven.size(); i++) {
      this.hints.add(hintsGiven.get(i));
    }



  }

  public boolean empty() {
    if (this.hints.isEmpty()) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean solved() {
    if (this.hints.size() == 1) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean contains (int i) {
    if (this.hints.contains(i)) {
      return true;
    }
    else {
      return false;
    }
  }

  public int solution () {
    return this.hints.get(0);
  }

  public void remove (int i) {
    if (contains(i)) {
      this.hints.remove(this.hints.indexOf(i));
    }
  }

  public int size () {
    return this.hints.size();
  }

  public int[] position () {
    return this.position;
  }

  public int row () {
    return this.position[0];
  }

  public int col () {
    return this.position[1];
  }

  public ArrayList<Integer> currentHints () {
    return this.hints;
  }

  public String print () {
    if (solved()) {
      return this.hints.get(0) + "";
    }
    else {
      return " ";
    }
  }
}
