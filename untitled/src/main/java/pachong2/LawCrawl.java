package pachong2;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LawCrawl {
  public static void CrawlLaw() throws IOException {
    Document document= Jsoup.connect("http://www.dffyw.com/faguixiazai/xf/200311/20031111130912.htm").get();
    Elements lawElements=document.select("div.rich_media_content>p");
    //System.out.println(lawElements);
    //System.out.println(lawElements.size());
    for(int i=0;i< lawElements.size();i++){
      Elements elements=lawElements.get(i).select("p");
      System.out.println(elements);
    }
    File file=new File("untitled\\src\\宪法.txt");
    FileWriter fileWriter=new FileWriter(file);
    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
    for(Element element:lawElements){
      String temp=element.text();
      System.out.println(temp);
      bufferedWriter.write(temp);
      bufferedWriter.newLine();
    }
    bufferedWriter.close();
  }

  public static void main(String[] args) throws IOException {
    CrawlLaw();
  }
  public static boolean ifNewLine(String temp){
    int pos=temp.indexOf("第");
    if(pos==0){
      return true;
    }
    return false;
  }

}
