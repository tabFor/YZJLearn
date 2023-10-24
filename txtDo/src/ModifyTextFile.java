import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyTextFile {
    public static void main(String[] args) {
        String folderPath = "g:\\YzjLearn\\YZJLearn\\untitled\\src"; // 指定包含文件的文件夹路径
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            processFilesInFolder(folder);
        } else {
            System.out.println("指定的路径不是一个文件夹或路径不存在。");
        }
    }

    public static void processFilesInFolder(File folder) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processFilesInFolder(file); // 递归处理子文件夹
                } else if (file.isFile() && file.getName().endsWith(".txt")) {
                    processTextFile(file);
                }
            }
        }
    }

    public static void processTextFile(File file) {
        try {
            // 读取原始文本文件
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder text = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line);
            }

            reader.close();

            // 使用正则表达式在"第...条"前插入换行符
            String modifiedText = text.toString().replaceAll("第[一二三四五六七八九十百千万亿零壹贰叁肆伍陆柒捌玖拾佰仟万亿]+条\\s[^第]*", "\n$0");

            // 将修改后的文本写回文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(modifiedText);
            writer.close();

            System.out.println("文件已成功修改。");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/* try {
            // 读取原始文本文件
            BufferedReader reader = new BufferedReader(new FileReader("G:\\YzjLearn\\YZJLearn\\untitled\\src\\治安管理处罚法.txt"));
            StringBuilder text = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line);
            }

            reader.close();

            // 使用正则表达式在"第...条"前插入换行符
            String modifiedText = text.toString().replaceAll("第", "\n第");

            // 将修改后的文本写回文件
            BufferedWriter writer = new BufferedWriter(new FileWriter("G:\\YzjLearn\\YZJLearn\\untitled\\src\\治安管理处罚法.txt"));
            writer.write(modifiedText);
            writer.close();

            System.out.println("文件已成功修改。");

        } catch (IOException e) {
            e.printStackTrace();
        } */