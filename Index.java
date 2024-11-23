public class Index {
    private LinkedList<DocumentNode> documents;

    public Index() {
        documents = new LinkedList<>();
    }

    public void addDocument(int docID, LinkedList<String> words) {
        documents.insert(new DocumentNode(docID, words));
    }

    public LinkedList<DocumentNode> getDocuments() {
        return documents;
    }

    public LinkedList<Integer> search(String word) {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < documents.size(); i++) {
            DocumentNode document = documents.get(i);
            if (document.words.contains(word)) {
                result.insert(document.docID);
            }
        }
        return result;
    }
}

