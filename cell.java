// cell.java
// Jan-Marc Glowienke i6117274
// Zohaad Fazal i6107208

import java.util.*;

public class cell {
  private ArrayList<Integer> candidates;
  private int[] position;

  // constructor
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

  public int solution () {
    if (!isEmpty()) {
      return this.candidates.get(0);
    }
    else {
      return 0;
    }
  }

  public void removeInt (int i) {
    if (contains(i)) {
      this.candidates.remove(this.candidates.indexOf(i)); // gets index of the int and removes it
    }
  }

  public void removeIndex (int i) {
    if (contains(this.candidates.get(i))) {
      this.candidates.remove(i);
    }
  }

  public void removeExcept (int i) {
    this.candidates.clear();
    this.candidates.add(i);
  }

  public void reset () {
    this.candidates.clear();
    for (int i = 1; i < 10; i++) {
      this.candidates.add(i);
    }
  }

  public void removeExceptIndex (int i) {
    int x = this.candidates.get(i);
    this.candidates.clear();
    this.candidates.add(x);
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
  
  public cell copy () { // returns a "hard" copy (as Java doesn't have hard copies)
    return new cell(currentCandidates(), position());
  }
}
