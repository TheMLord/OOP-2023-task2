package gui;

import java.io.*;
import java.util.HashMap;

public class FileConfig {

    public static void createTxtFile(HashMap<String, String> map) throws IOException {

        // Получаем путь к домашней директории пользователя
        String homeDir = System.getProperty("user.home");

        // Создаем новый файл txt в домашней директории пользователя
        File file = new File(homeDir + File.separator + "config.txt");
        file.createNewFile();

        // Открываем файл для записи
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Перебираем все элементы HashMap и записываем их в файл
            for (HashMap.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }
    public static HashMap<String, String> readTxtConfig() {
        String homeDir = System.getProperty("user.home");
        String fileName = homeDir + File.separator + "config.txt";
        HashMap<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    configMap.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + fileName + ": " + e.getMessage());
        }
        return configMap;
    }

}