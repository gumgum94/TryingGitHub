class RefListNode {  // The Node for RefList nodes.
    int data;
    RefListNode next;

    RefListNode(int data) { // passing value RefList
        this.data = data;
        this.next = next;}
    public String toString() {
        return String.valueOf(this.data); // This will print the String instead of a bunch of memory-info stuff
    }
    }

public class RefList { // The RefList receives location
    RefListNode root;

    void add(int data) { // Adding method to refList from WordlIst passign location
        RefListNode locNode = new RefListNode(data);
        if (root == null) {root = locNode;} // Make a first node comming.
        else {
            RefListNode Begin = root;
            while (Begin.next != null)// find the available one
                {Begin = Begin.next;} // untill findout Begin.next==null.
            Begin.next = locNode;//linked list to next node.
        }
    }

    public String toString() { //To make a value readable.
        String ref = "";
        RefListNode Begin = root;
        while (Begin != null) {
            ref += Begin + " ";
            Begin = Begin.next;
        }
        return ref;
    }
}