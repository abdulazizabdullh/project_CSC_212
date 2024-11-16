import java.io.*;

public class DocumentProcessor {
    private LinkedList<String> stopWords;

    public DocumentProcessor(String stopWordsFile) throws IOException {
        stopWords = new LinkedList<>();
        loadStopWords(stopWordsFile);
    }

    private void loadStopWords(String stopWordsFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(stopWordsFile));
        String line;
        while ((line = br.readLine()) != null) {
            stopWords.insert(line.trim().toLowerCase());
        }
        br.close();
    }

    public LinkedList<String> processDocument(String document) {
        LinkedList<String> words = new LinkedList<>();
        String[] tokens = document.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
        	String token = tokens[i];
            if (!stopWords.contains(token) && !token.isEmpty()) {
                words.insert(token);
            }
        }
        return words;
    }
}

