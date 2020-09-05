package tasks.wordcounter;

/*
Программа считает количество каждого слова, которое встречается в файле. Количество слов должно включать только слова
(содержащие только буквы алфавита, без знаков препинания)
 */
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class WordCounter {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = bufferedReader.readLine();
        bufferedReader.close();

        FileInputStream fileInputStream = new FileInputStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream));
        TreeMap<String, Integer> wordMap = new TreeMap<>();
        String line = fileReader.readLine();

        while (line != null) {
            // replace punctuation marks in a line on a space
            line = line.replace(".", " ");
            line = line.replace(",", " ");

            for (String word : line.split(" ")) {  //
                if (wordMap.containsKey(word)) { // checks if the given key is in the map
                    int count = wordMap.get(word); // get the value by key
                    count++;
                    wordMap.put(word, count); // updated map (key, value)
                } else {
                    wordMap.put(word, 1);
                }
            }
            line = fileReader.readLine();
        }
        wordMap.remove("");

        fileReader.close();
        // create file where save result with counted words
        String reportFileName = fileName + ".report";
        File reportFile = new File(reportFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(reportFile);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        for (Map.Entry<String, Integer> pair : wordMap.entrySet()) { // returns a set of key-values
            String key = pair.getKey();
            Integer value = pair.getValue();
            bufferedWriter.write(key + " " + value); // writes text to the output stream
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}

