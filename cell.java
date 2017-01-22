import java.util.*;

public class cell {
  private ArrayList<Integer> candidates;
  private int[] position;

  public cell (ArrayList<Integer> givenCandidates, int[] pos){
    
    this.candidates = new ArrayList<Integer>();

    for (int i = 0; i < givenCandidates.size(); i++) {
      this.candidates.add(givenCandidates.get(i));
    }

    this.position = new int[2];
    this.position[0] = pos[0];
    this.position[1] = pos[1];
  }

  public boolean isEmpty () {
    return this.candidates.isEmpty();
  }

  public int size () {
    return this.candidates.size();
  }

  public boolean solved () {
    return size() == 1;
  }

  public boolean contains (int i) {
    return this.candidates.contains(i);
  }

  // kind of dangerous
  public int solution () {
    return this.candidates.get(0);
  }

  public void remove (int i) {
    if (contains(i)) {
      this.candidates.remove(this.candidates.indexOf(i));
    }
  }

  public void removeExcept (int i) {
    for (int j = 1; j < 10; j++) {
      if (j != i) {
        remove(j);
      }
    }
  }

  public void removeExceptIndex (int i, int size) {
    int count = 0;
    while (count < i) {
      this.candidates.remove(count);
      count++;
    }
    while (count + 1 < size) { 
      this.candidates.remove(count + 1);
      count++;
    }

  }

  public ArrayList<Integer> currentCandidates () {
    return this.candidates;
  }

  public int row () {
    return position()[0];
  }


  public int col () {
    return position()[1];
  }

  public int[] position () {
    return this.position;
  } 
  
  public cell copy () { // returns a "hard" copy Java doesn't have hard copies, but this is good enough
    return new cell(currentCandidates(), position());
  }
}
