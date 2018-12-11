// Given by Charles Cooley

/**
 * A simple interface for a list index, which points to an element in a list
 */
interface ListIndex {
    boolean next(); // move to next node returning false if already at end
    boolean prev(); // move to previous node returning false if already at beginning
    void position(int where);  // move to specific position (if value is out of range, move to end, i.e. just past the last element!)
    ListIndex duplicate(); // create a copy of this object pointing to the same location
    String toString(); // returns the index position of the current node in the list as a string
    boolean atEnd(); // return true if currently past the last element.
}
