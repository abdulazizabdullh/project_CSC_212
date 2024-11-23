public class InvertedIndexBST {
    BST<String, LinkedList<Integer>> invertedIndexBST;

    public InvertedIndexBST() {
        invertedIndexBST = new BST<>();
    }

    public void addDocument(int docID, String word) {
        if (invertedIndexBST.find(word)) {
            LinkedList<Integer> docList = invertedIndexBST.retrieve();
            if (!docList.contains(docID)) {
                docList.insert(docID);
                invertedIndexBST.update(docList);
            }
        } else {
            LinkedList<Integer> docList = new LinkedList<>();
            docList.insert(docID);
            invertedIndexBST.insert(word, docList);
        }
    }

    public LinkedList<Integer> search(String word) {
        LinkedList<Integer> result = new LinkedList<>();
        if (invertedIndexBST.find(word)) {
            result = invertedIndexBST.retrieve();
        }
        return result;
    }
}