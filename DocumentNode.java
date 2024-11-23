public class DocumentNode {
    int docID;
    String word;
    LinkedList<String> words;

    public DocumentNode(int docID, LinkedList<String> words) {
        this.docID = docID;
        this.words = words;
    }

    public DocumentNode(int docID, String word) {
        this.docID = docID;
        this.word = word;

    }
}