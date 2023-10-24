import java.io.*;
import java.nio.charset.StandardCharsets;

public class txtDo {
    public static void main(String[] args) {
        String folderPath = "your_folder_path_here"; // 指定文件夹路径
        convertFilesToUTF8(new File(folderPath));
    }

    public static void convertFilesToUTF8(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        convertFilesToUTF8(file);
                    } else if (file.isFile() && file.getName().endsWith(".txt")) {
                        convertFileToUTF8(file);
                    }
                }
            }
        }
    }

    public static void convertFileToUTF8(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            reader.close();

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
