import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {

        String stopWordsFile = "C:\\Users\\aasaa\\workspace\\pro_212\\src\\stop.txt"; // Replace with the actual path
        String csvFile = "C:\\Users\\aasaa\\workspace\\pro_212\\src\\dataset.csv"; // Replace with the actual path


        DocumentProcessor processor = new DocumentProcessor(stopWordsFile);
        Index index = new Index();
        InvertedIndex invertedIndex = new InvertedIndex();
        InvertedIndexBST invertedIndexBST = new InvertedIndexBST();

        Scanner scanner = new Scanner(System.in);

        System.out.println("----Search Engine----");
        System.out.println("1. Use Index");
        System.out.println("2. Use Inverted Index");
        System.out.println("3. Use Inverted Index with BST");
        System.out.print("Choose an index option: ");
        int indexChoice = scanner.nextInt();
        scanner.nextLine();


        QueryProcessor queryProcessor = null;
        int identify;
        if (indexChoice == 1) {
            queryProcessor = new QueryProcessor(index);
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            int docID = 0;
            identify = 0;
            while ((line = br.readLine()) != null) {
                LinkedList<String> words = processor.processDocument(line);
                if (words.size() > 0) {
                    String firstWord = words.get(0);
                    firstWord = firstWord.replaceAll("^[0-9]+", "");
                    words.set(0, firstWord);
                }
                index.addDocument(docID, words);
                docID++;
            }

            br.close();
        } else if (indexChoice == 2) {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            identify = 1;
            int docID = 0;
            queryProcessor = new QueryProcessor(invertedIndex);
            while ((line = br.readLine()) != null) {
                LinkedList<String> words = processor.processDocument(line);
                if (words.size() > 0) {
                    String firstWord = words.get(0);
                    firstWord = firstWord.replaceAll("^[0-9]+", "");
                    words.set(0, firstWord);
                }

                for (int j = 0; j < words.size(); j++) {
                    String word = words.get(j);
                    invertedIndex.addDocument(docID, word);
                }

                docID++;
            }

            br.close();
        } else if (indexChoice == 3) {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            identify = 2;
            int docID = 0;
            queryProcessor = new QueryProcessor(invertedIndexBST);
            while ((line = br.readLine()) != null) {
                LinkedList<String> words = processor.processDocument(line);
                if (words.size() > 0) {
                    String firstWord = words.get(0);
                    firstWord = firstWord.replaceAll("^[0-9]+", "");
                    words.set(0, firstWord);
                }
                for (int j = 0; j < words.size(); j++) {
                    String word = words.get(j);
                    invertedIndexBST.addDocument(docID, word);
                }

                docID++;
            }

            br.close();
        } else {
            System.out.println("Invalid.");
            identify = 0;
        }
        while (true) {
            System.out.println("----Query Options----");
            System.out.println("1. Boolean Retrieval Search");
            System.out.println("2. Ranked Retrieval Search");
            System.out.print("Choose a query option: ");
            int queryChoice = scanner.nextInt();
            scanner.nextLine();

            if (queryChoice == 1) {
                System.out.print("Enter your Boolean query: ");
                String query = scanner.nextLine();
                queryProcessor.processBooleanQuery(query, queryProcessor);
            } else if (queryChoice == 2) {
                System.out.print("Enter your Ranked Retrieval: ");
                String query = scanner.nextLine();
                if (identify == 0)
                    queryProcessor.processRankedQuery(query, index);
                else if (identify == 1)
                    queryProcessor.processRankedQuery(query, invertedIndex);
                else if (identify == 2)
                    queryProcessor.processRankedQuery(query, invertedIndexBST);
            } else if (queryChoice == 0) {
                System.out.println("0. Exit");
                scanner.close();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}