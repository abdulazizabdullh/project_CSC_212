public class QueryProcessor {
    private Index index;

    public QueryProcessor(Index index) {
        this.index = index;
    }

    public LinkedList<Integer> andQuery(String word1, String word2) {
        LinkedList<Integer> result = (LinkedList<Integer>) index.search(word1).retainAll(index.search(word2));
        return result;
    }

    public LinkedList<Integer> orQuery(String word1, String word2) {
        LinkedList<Integer> result = index.search(word1);
        LinkedList<Integer> other = index.search(word2);

        for (int i = 0; i < other.size(); i++) {
            if (!result.contains(other.get(i))) {
                result.insert(other.get(i));
            }
        }
        return result;
    }
}

