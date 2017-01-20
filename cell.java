import java.util.*;

public class cell {
  private ArrayList<Integer> hints;
  private int[] position;

  public cell (ArrayList<Integer> input, int[] pos) { // constuctor
    this.hints = new ArrayList<Integer>();
    for (int i = 0; i < input.size(); i++) {  
        this.hints.add(input.get(i));
    }
    this.position = new int[2];
    this.position[0] = pos[0];
    this.position[1] = pos[1];
  }

  public void remove (int i) {
    if (contains(i)) this.hints.remove(Integer.valueOf(i));
  }

  public boolean contains (int i) {
    if (this.hints.contains(i)) return true;
    else return false;
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

  // VERY DANGEROUS
  public int solution() {
    return this.hints.get(0);
  }

  
}
