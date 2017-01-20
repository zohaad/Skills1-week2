import java.util.*;

public class cell {
  private List<Integer> hints;

  public cell (int[] input) { // constuctor
    this.hints = new ArrayList<Integer>();
    for (int i = 0; i < input.length; i++) {  
        this.hints.add(input[i]);
    }
  }

  public void remove (int i) {
    this.hints.remove(Integer.valueOf(i));
  }

  public boolean contains (int i) {
    if (this.hints.contains(i)) return true;
    else return false;
  }

  public int size() {
      return this.hints.size();
  }

  
}
