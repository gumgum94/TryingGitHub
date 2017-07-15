
class WordListNode { //  THe node of WordList
    String word;

    WordListNode next;
    RefList refList; // Link to the Node RefList function.
    public WordListNode(String word) { // pass value to Node.
        this.word = word;
        this.next = next;
    }
}
public class WordList {  //This is the linked list including main nodes of word in program

    WordListNode root; //This is a sentinal node which is declared in WordListNode

    void add(String word, int ref) {

        WordListNode x = new WordListNode(word);// New node is created with the "word" come from WordIndex.
        RefList list = new RefList();// Link to the Reflist node function.
        list.add(ref); // connect to RefList add function for the location numbered.
        x.refList = list; // connect to RefList add function to WordList.
        if (root == null) {root = x;} // For the first node comming it turns to a root.

        else if (root.word.compareTo(word) == 0) {root.refList.add(ref);}// compare if Word is different, link to next location.
        else if (root.word.compareTo(word) > 0)// It's used to sort the word linked list if change the arrangement.
            {x.next = root;
            root = x;}
        else { // The Fourth Case
            WordListNode nextone = root.next; //declare node in linkedlist
            WordListNode prevone= root;
            boolean sus = false;
            while (nextone != null) { // If there is a nodes next tto a root
                if (word.compareTo(nextone.word) < 0) {
                    break;
                } else if (word.compareTo(nextone.word) == 0) {
                    sus= true;
                    nextone.refList.add(ref);
                    break;
                }
                prevone= nextone;
                nextone  = nextone.next;}

            if (sus==false) {
                x.next = prevone.next;
                prevone.next = x;
            }
        }
    }

    public void print() { //Printing the linked list on WordList
        WordListNode Begin = root;
        while (Begin != null) { // read the file untill finish the WordList
            System.out.println(Begin.word + " " + Begin.refList); // print the Word then RefList behind.
            Begin = Begin.next;}
    }
}
