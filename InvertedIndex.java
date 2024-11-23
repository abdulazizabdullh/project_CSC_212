public class InvertedIndex {
    private LinkedList<WordNode> invertedIndex;

    public InvertedIndex() {
        invertedIndex = new LinkedList<>();
    }

    public LinkedList<WordNode> getDocuments() {
        return invertedIndex;
    }

    public void addDocument(int docID, String word) {
        boolean found = false;
        for (int i = 0; i < invertedIndex.size(); i++) {
            WordNode node = invertedIndex.get(i);
            if (node.word.equals(word)) {
                // Add the document ID to the existing word node if not already present
                if (!node.docIDs.contains(docID)) {
                    node.docIDs.insert(docID);
                }
                found = true;
                break;
            }
        }
        // If the word is not found, create a new node
        if (!found) {
            LinkedList<Integer> docs = new LinkedList<>();
            docs.insert(docID);
            invertedIndex.insert(new WordNode(word, docs));
        }
    }

    public LinkedList<Integer> search(String word) {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < invertedIndex.size(); i++) {
            WordNode node = invertedIndex.get(i);
            if (node.word.equals(word)) {
                result = node.docIDs;
                break;
            }
        }
        return result;
    }

}
