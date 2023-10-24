import java.io.*;

public class RemoveExtraSpacesInFiles {
    public static void main(String[] args) {
        // 指定要处理的文件夹路径
        File folder = new File("G:\\YzjLearn\\YZJLearn\\untitled\\src");

        if (folder.exists() && folder.isDirectory()) {
            // 获取文件夹中的所有文件
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    processFile(file);
                }
            }

            System.out.println("所有文件已成功处理。");
        } else {
            System.out.println("指定的文件夹不存在或不是文件夹。");
        }
    }

    private static void processFile(File file) {
        try {
            // 读取原始文本文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder modifiedText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // 使用正则表达式去掉第一个空格后的所有空格
                String modifiedLine = line.replaceFirst(" ", " ").replaceAll("  +", "");
                modifiedText.append(modifiedLine).append("\n");
            }

            reader.close();

            // 将修改后的文本写回文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(modifiedText.toString());
            writer.close();

            System.out.println("文件 " + file.getName() + " 已成功处理。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
