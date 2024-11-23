public class QueryProcessor {
    private Index index;
    private InvertedIndex invertedIndex;
    private InvertedIndexBST invertedIndexBST;
    private int identify;

    public QueryProcessor(Index index) {
        this.index = index;
        identify = 0;
    }

    public QueryProcessor(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
        identify = 1;
    }

    public QueryProcessor(InvertedIndexBST invertedIndexBST) {
        this.invertedIndexBST = invertedIndexBST;
        identify = 2;
    }

    public LinkedList<Integer> andQuery(String word1, String word2) {
        if (identify == 0) {
            return (LinkedList<Integer>) index.search(word1).retainAll(index.search(word2));
        } else if (identify == 1) {
            return (LinkedList<Integer>) invertedIndex.search(word1).retainAll(invertedIndex.search(word2));
        } else if (identify == 2) {
            return (LinkedList<Integer>) invertedIndexBST.search(word1).retainAll(invertedIndexBST.search(word2));
        } else
            return null;
    }

    public LinkedList<Integer> orQuery(String word1, String word2) {
        LinkedList<Integer> result;
        LinkedList<Integer> other;

        if (identify == 0) {
            result = index.search(word1);
            other = index.search(word2);
        } else if (identify == 1) {
            result = invertedIndex.search(word1);
            other = invertedIndex.search(word2);
        } else if (identify == 2) {
            result = invertedIndexBST.search(word1);
            other = invertedIndexBST.search(word2);
        } else
            return result = null;

        for (int i = 0; i < other.size(); i++) {
            if (!result.contains(other.get(i))) {
                result.insert(other.get(i));
            }
        }
        return result;
    }

    public LinkedList<Integer> andQuery(LinkedList<Integer> list, String word) {
        LinkedList<Integer> wordDocs = null;

        if (identify == 0) {
            wordDocs = index.search(word);
        } else if (identify == 1) {
            wordDocs = invertedIndex.search(word);
        } else if (identify == 2) {
            wordDocs = invertedIndexBST.search(word);
        }

        LinkedList<Integer> result = new LinkedList<>();
        if (wordDocs == null) {
            return result; // Return empty result if the word is not found
        }

        for (int i = 0; i < list.size(); i++) {
            Integer docID = list.get(i);
            if (wordDocs.contains(docID)) {
                result.insert(docID);
            }
        }
        return result;
    }

    public LinkedList<Integer> orQuery(LinkedList<Integer> list, String word) {
        LinkedList<Integer> wordDocs = null;

        if (identify == 0) {
            wordDocs = index.search(word);
        } else if (identify == 1) {
            wordDocs = invertedIndex.search(word);
        } else if (identify == 2) {
            wordDocs = invertedIndexBST.search(word);
        }

        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            result.insert(list.get(i));
        }

        if (wordDocs != null) {
            for (int i = 0; i < wordDocs.size(); i++) {
                Integer docID = wordDocs.get(i);
                if (!result.contains(docID)) {
                    result.insert(docID);
                }
            }
        }
        return result;
    }

    public void processBooleanQuery(String query, QueryProcessor queryProcessor) {
        String[] parts = query.split(" ");
        LinkedList<Integer> result = new LinkedList<>();
        if (parts.length == 3) {
            String word1 = parts[0];
            String operator1 = parts[1].toUpperCase();
            String word2 = parts[2];


            if (operator1.equals("AND")) {
                result = queryProcessor.andQuery(word1, word2);
                printFormattedResult(query, result);
            } else if (operator1.equals("OR")) {
                result = queryProcessor.orQuery(word1, word2);
                printFormattedResult(query, result);
            } else {
                System.out.println("Invalid .");
            }
        } else if (parts.length == 5) {
            String word1 = parts[0];
            String operator1 = parts[1].toUpperCase();
            String word2 = parts[2];
            String operator2 = parts[3].toUpperCase();
            String word3 = parts[4];

            LinkedList<Integer> inreResult = new LinkedList<>();

            if (operator2.equals("AND")) {
                // Evaluate the AND part first
                inreResult = queryProcessor.andQuery(word2, word3);
                if (operator1.equals("OR")) {
                    result = queryProcessor.orQuery(inreResult, word1);
                } else if (operator1.equals("AND")) {
                    result = queryProcessor.andQuery(inreResult, word1);
                } else {
                    System.out.println("Invalid operator.");
                    return;
                }
            } else if (operator1.equals("AND")) {
                // Evaluate the first AND
                inreResult = queryProcessor.andQuery(word1, word2);
                result = queryProcessor.orQuery(inreResult, word3);
            } else if (operator1.equals("OR")) {
                // Evaluate OR first if no AND follows
                inreResult = queryProcessor.orQuery(word1, word2);
                result = queryProcessor.orQuery(inreResult, word3);
            } else {
                System.out.println("Invalid operator.");
                return;
            }
            printFormattedResult(query, result);

        } else {
            System.out.println("Invalid  format.");
        }
    }

    public void processRankedQuery(String query, Index index) {
        String[] words = query.toLowerCase().split(" ");
        LinkedList<DocumentScore> rankedResults = new LinkedList<>();

        for (int i = 0; i < index.getDocuments().size(); i++) {
            DocumentNode document = index.getDocuments().get(i);
            int score = 0;

            for (int j = 0; j < words.length; j++) {
                String word = words[j];
                int count = countOccurrences(document.words, word);
                score += count;
            }
            if (score > 0) {
                rankedResults.insert(new DocumentScore(document.docID, score));
            }
        }

        sortRankedResults(rankedResults);
        printRankedResults(query, rankedResults);
    }

    // Perform Ranked Retrieval for InvertedIndex
    public void processRankedQuery(String query, InvertedIndex index) {
        String[] words = query.toLowerCase().split(" ");
        LinkedList<DocumentScore> rankedResults = new LinkedList<>();

        // Iterate through each word in the query
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            LinkedList<Integer> docIDs = index.search(word);
            for (int j = 0; j < docIDs.size(); j++) {
                int docID = docIDs.get(j);
                boolean found = false;

                // Update the score for existing document scores
                for (int x = 0; x < rankedResults.size(); x++) {
                    DocumentScore ds = rankedResults.get(x);
                    if (ds.docID == docID) {
                        ds.score++;
                        found = true;
                        break;
                    }
                }

                // If document score not found, create a new one
                if (!found) {
                    rankedResults.insert(new DocumentScore(docID, 1));
                }
            }
        }

        sortRankedResults(rankedResults);
        printRankedResults(query, rankedResults);
    }

    // Perform Ranked Retrieval for InvertedIndexBST
    public void processRankedQuery(String query, InvertedIndexBST index) {
        String[] words = query.toLowerCase().split(" ");
        LinkedList<DocumentScore> rankedResults = new LinkedList<>();

        // Iterate through each word in the query
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            LinkedList<Integer> docIDs = index.search(word);
            for (int j = 0; j < docIDs.size(); j++) {
                int docID = docIDs.get(j);
                boolean found = false;

                // Update the score for existing document scores
                for (int x = 0; x < rankedResults.size(); x++) {
                    DocumentScore ds = rankedResults.get(x);
                    if (ds.docID == docID) {
                        ds.score++;
                        found = true;
                        break;
                    }
                }

                // If document score not found, create a new one
                if (!found) {
                    rankedResults.insert(new DocumentScore(docID, 1));
                }
            }
        }

        sortRankedResults(rankedResults);
        printRankedResults(query, rankedResults);
    }

    // Helper method to count occurrences of a word in a document
    public int countOccurrences(LinkedList<String> words, String word) {
        int count = 0;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(word)) {
                count++;
            }
        }
        return count;
    }

    // Sort ranked results by score in descending order
    public void sortRankedResults(LinkedList<DocumentScore> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).score < list.get(j).score) {
                    DocumentScore temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    // Print the ranked retrieval results
    public void printRankedResults(String query, LinkedList<DocumentScore> rankedResults) {
        System.out.println("Ranked Retrieval Results: '" + query + "'");
        for (int i = 0; i < rankedResults.size(); i++) {
            DocumentScore result = rankedResults.get(i);
            System.out.println("DocID: " + result.docID + " Score: " + result.score);
        }
    }


    public void printFormattedResult(String query, LinkedList<Integer> result) {
        System.out.print("Q: " + query + "\nResult: {");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}\n");
    }
}