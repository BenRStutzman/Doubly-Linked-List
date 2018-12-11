// Given by Charles Cooley

/**
 * A simple interface for a list
 */
public interface List {
    void clear(); // empty the list
    int length(); // return the current number of elements in the list
    ListIndex getIndex(int where);  // return a ListIndex object and calls position on it
    Object getValue(ListIndex where); // return the element value at the location specified
    void setValue(Object it, ListIndex where); // replace the element value at the location specified
    void insert(Object it, ListIndex where); // insert it at the location or if where is null append to the end of the list
    Object remove(ListIndex where); // remove and return the element at the location or from the end of the list if where is null
    String toString(); // should return the elements of the list (but without a marker indicating current position)
}