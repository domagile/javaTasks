package tasks.petcrud;

/*
Программа реализует CRUD для файла c таблицей, которая содержит данные о животных.

Формат таблицы в файле:
id petName ownerName phoneNumber

Значения параметров:
id - 6 символов;
petName - имя домашнего животного, 15 символов;
ownerName - имя владельца животного, 35 символов;
phoneNumber - номер телефона владельца животного, 10 символов.

Программа запускается с одним из следующих наборов параметров:
создание: "-c petName ownerName phoneNumber"
апдейт: "-u id petName ownerName phoneNumber"
удаление: "-d id petName ownerName phoneNumber"

 */


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PetCRUD {
    public static void main(String[] args) throws IOException {
        BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = fileNameReader.readLine();
        fileNameReader.close();

        List<String> petTable = new ArrayList<>();
        int index = 0;

        String petName = null;
        String ownerName = null;
        String phoneNumber = null;


        if (args[0].equals("-u") || args[0].equals("-d")) {
            // format strings for parameters Update and Delete
            petName = String.format("%-15.15s", args[2]);
            ownerName = String.format("%-35.35s", args[3]);
            phoneNumber = String.format("%-10d", Integer.parseInt(args[4]));

            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = fileReader.readLine();
            while (line != null) {  // add lines to List<String> petTable from file
                petTable.add(line);
                line = fileReader.readLine();
            }
            fileReader.close();

            // index search line
            for (int i = 0; i < petTable.size(); i++) {
                if (petTable.get(i).startsWith(args[1])) {
                    index = i;
                    break;
                }
            }
        }

        if (args[0].equals("-c")) {
            // format strings for parameter Create
            petName = String.format("%-15.15s", args[1]);
            ownerName = String.format("%-35.35s", args[2]);
            phoneNumber = String.format("%-10d", Integer.parseInt(args[3]));

            String currentLine = null;
            String lastLine = null;
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileInputStream));

            // search last line and max Id
            while ((currentLine = fileReader.readLine()) != null) {
                lastLine = currentLine;
            }
            int idMax = Integer.parseInt(lastLine.substring(0, 6).trim()) + 1;
            String idString = String.format("%-6d", idMax);


            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(idString + petName + ownerName + phoneNumber);
            bufferedWriter.close();


        } else if (args[0].equals("-u")) {
            String currentString = petTable.get(index);
            String id = currentString.substring(0, 6);
            String finalString = id + petName + ownerName + phoneNumber;
            petTable.set(index, finalString);
            writeTable(petTable, fileName);

        } else if (args[0].equals("-d")) {
            petTable.remove(index);
            writeTable(petTable, fileName);
        }
    }


    public static void writeTable(List<String> petTable, String fileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        for (int i = 0; i < petTable.size(); i++) {
            bufferedWriter.write(petTable.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
