import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
       
    	String stopWordsFile = "C:\\Users\\aasaa\\workspace\\pro_212\\src\\stop.txt"; // Replace with the actual path
    	String csvFile = "C:\\Users\\aasaa\\workspace\\pro_212\\src\\dataset.csv"; // Replace with the actual path

    
        DocumentProcessor processor = new DocumentProcessor(stopWordsFile);
        Index index = new Index();

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        int docID = 0;
        while ((line = br.readLine()) != null) {
            LinkedList<String> words = processor.processDocument(line);
            index.addDocument(docID, words);
            docID++;
        }
        br.close();

        QueryProcessor queryProcessor = new QueryProcessor(index);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("----Search Engine----");
            System.out.println("1. Boolean Retrieval Search");
            System.out.println("2. Ranked Retrieval Search");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            if (choice == 0) {
                System.out.println("Exit");
                break;
            } else if (choice == 1) {
                System.out.print("Enter your Boolean query : ");
                String query = scanner.nextLine();
                processBooleanQuery(query, queryProcessor);
            } else if (choice == 2) {
                System.out.print("Enter your Ranked Retrieval: ");
                String query = scanner.nextLine();
                processRankedQuery(query, index);
            } else {
                System.out.println("Invalid choice . Please try again. ");
            }
        }
        scanner.close();
    }

    private static void processBooleanQuery(String query, QueryProcessor queryProcessor) {
        String[] parts = query.split(" ");
        if (parts.length == 3) {
            String word1 = parts[0];
            String operator = parts[1].toUpperCase();
            String word2 = parts[2];
            LinkedList<Integer> result;
            
            if (operator.equals("AND")) {
                result = queryProcessor.andQuery(word1, word2);
                printFormattedResult(query, result);
            } else if (operator.equals("OR")) {
                result = queryProcessor.orQuery(word1, word2);
                printFormattedResult(query, result);
            } else {
                System.out.println("Invalid .");
            }
        } else {
            System.out.println("Invalid  format.");
        }
    }

    private static void processRankedQuery(String query, Index index) {
        String[] words = query.toLowerCase().split(" ");
        LinkedList<DocumentScore> rankedResults = new LinkedList<>();
        
        for (int i = 0; i < index.getDocuments().size(); i++) {
            DocumentNode document = index.getDocuments().get(i);
            int score = 0;
            for (String word : words) {
                if (document.words.contains(word)) {
                    score++;
                }
            }
            if (score > 0) {
                rankedResults.insert(new DocumentScore(document.docID, score));
            }
        }
        
        sortRankedResults(rankedResults);
        System.out.println("Ranked Retrieval Results: '" + query + "'");
        for (int i = 0; i < rankedResults.size(); i++) {
            DocumentScore result = rankedResults.get(i);
            System.out.println("DocID: " + result.docID + " Score: " + result.score);
        }
    }

    private static void sortRankedResults(LinkedList<DocumentScore> list) {
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

    private static void printFormattedResult(String query, LinkedList<Integer> result) {
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