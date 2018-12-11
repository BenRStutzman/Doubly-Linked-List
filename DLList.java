/**
 * This is a doubly-linked list that implements the List interface given by Charles Cooley
 */
public class DLList implements List {
    // Adapted from the AList class in OpenDSA, chapter 11.2
    // and the Doubly-linked list in OpenDSA, chapter 9.6
    
    /**
     * This is a class for links in a doubly-linked list
     */
    private class Link {
      // Adapted from OpenDSA, chapter 9.4
      
      public Object elem; // Actual object the link holds
      public Link prev;
      public Link next;
      
      // Constructors
      // Create a new link containing it, specifying the other links it attaches to
      Link(Object it, Link comesFrom, Link goesTo) {
          prev = comesFrom;
          next = goesTo;
          elem = it;
      }
      // Create a new null link, with prev and next pointing to null
      Link() {
          this(null, null, null);
      }
    
    }
    
    /**
     * This is a doubly-linked list index that implements the ListIndex interface given by Charles Cooley
     */
    private class DLListIndex implements ListIndex {
        
        public DLList list; // The list it's using
        public Link curr;   // The link it's currently pointing to
        
        // Constructors
        // Create a new list index pointing to link which in list what
        DLListIndex(DLList what, Link which) {
            list = what;
            curr = which;
        }
        // Create a new list index pointing to the first real link in list what
        DLListIndex(DLList what) {
            this(what, what.dummy.next);
        }
        
        // move to previous node and return true, or return false if already at beginning
        public boolean prev() {
            if (curr == list.dummy.next) return false;
            curr = curr.prev;
            return true;
        }
        // move to next node and return true, or return false if already at end
        public boolean next() {
            if (atEnd()) return false;
            curr = curr.next;
            return (!atEnd());
        }
        // return true if currently past the last element; false if not
        public boolean atEnd() {
            if (curr == list.dummy) return true;
            return false;
        }
        // move to a specific position; if value is out of range, move to end
        public void position(int where) {
            if (where >= list.length) {
                curr = list.dummy;
                return;
            }
            curr = list.dummy.next;
            for (int i = 0; i < where; i++) {
                curr = curr.next;
            }
        }
        // create a copy of this object pointing to the same location
        public ListIndex duplicate() {
            return new DLListIndex(list, curr);
        }
        // return the index position of the current node in the list as a string
        public String toString() {
            ListIndex i = this.duplicate();
            int pos = 0;
            while (i.prev()) pos++;
            return Integer.toString(pos);
        }
        
    }
    
    private int length;
    private Link dummy; // Serves as both a head and tail link
    
    // Constructor
    // Create a new list with a dummy link pointing to itself
    DLList() {
        this.clear();
    }
    
    // empty the list
    public void clear() {
        length = 0;
        dummy = new Link();
        dummy.prev = dummy;
        dummy.next = dummy;
    }
    // return the current number of elements in the list
    public int length() {
        return length;
    }
    // return a ListIndex object and call position on it to move it to where
    public ListIndex getIndex(int where) {
        DLListIndex i = new DLListIndex(this);
        i.position(where);
        return i;
    }
    // return the element value at the location specified
    public Object getValue(ListIndex where) {
        return ((DLListIndex) where).curr.elem;
    }
    // replace the element value at the location specified
    public void setValue(Object it, ListIndex where) {
        if (((DLListIndex) where).curr != dummy) ((DLListIndex) where).curr.elem = it;
    }
    // insert it at the location or if where is null append to the end of the list
    public void insert(Object it, ListIndex where) {
        if (where == null) where = new DLListIndex(this, dummy);
        Link curr = ((DLListIndex) where).curr;
        curr = new Link(it, curr.prev, curr);
        curr.prev.next = curr;
        curr.next.prev = curr;
        where.prev();
        length++;        
    }
    // remove and return the element at the location or from the end of the list if where is null
    public Object remove(ListIndex where) {
        if (where == null) where = new DLListIndex(this, dummy.prev);
        Link curr = ((DLListIndex) where).curr;
        if (curr == dummy) return null;
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        Link old = curr; // keep track of the old node before we move on
        where.next(); // move index to the next node, so it stays on the list
        Object it = old.elem; // keep track of the object we want to return
        old.prev = null;
        old.next = null;
        old.elem = null;// get rid of the old node so we can't access it with other indices
        length--;
        return it;
    }
    // return the elements of the list as a string like [A, B, C]
    public String toString() {
        if (length == 0) return "[]";
        StringBuilder listString = new StringBuilder();
        listString.append("[");
        DLListIndex i = new DLListIndex(this);
        listString.append(String.valueOf(i.curr.elem));
        i.next();
        while (!i.atEnd()) {
            listString.append(", ");
            listString.append(String.valueOf(i.curr.elem));
            i.next();
        }
        listString.append("]");
        return listString.toString();
    }
    
}   