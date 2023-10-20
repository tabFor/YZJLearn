package pachong;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.print.Doc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class LawCrawl {
  public static void CrawlLaw() throws IOException {
    Document document=Jsoup.connect("https://www.66law.cn/tiaoli/153012.aspx").get();
    System.out.println(document);
    Elements lawElements=document.select("div.detail-nr>p");
    //System.out.println(lawElements);
    for(int i=0;i< lawElements.size();i++){
      Elements elements=lawElements.get(i).select("h1,p,,a");
      System.out.println(elements);
    }
    File file=new File("untitled\\src\\民法典.txt");
    FileWriter fileWriter=new FileWriter(file);
    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
    for(Element element:lawElements){
      String temp=element.text();
      if(ifNewLine(temp)){
        bufferedWriter.newLine();
        bufferedWriter.write(temp);
        bufferedWriter.newLine();

      }else{
        bufferedWriter.write(temp);
        bufferedWriter.newLine();
      }



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
