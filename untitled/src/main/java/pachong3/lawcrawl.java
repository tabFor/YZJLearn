package pachong3;

import java.awt.print.Pageable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pachong.LawCrawl;

public class lawcrawl {
  public static void CrawlLaw(String url,String name) throws IOException {
    Document document=Jsoup.connect(url).get();
    Elements getLaw=document.select("div.detail-nr");
    File file=new File("untitled\\src\\"+name+".txt");
    FileWriter fileWriter=new FileWriter(file);
    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
    for(int i=0;i<getLaw.size();i++){
      Elements elements=getLaw.get(i).select("p");
      for(Element element:elements){
        String temp=element.text();
        //System.out.println(temp);
        bufferedWriter.write(temp);
        bufferedWriter.newLine();
      }
      bufferedWriter.close();
    }
  }

  public static void main(String[] args) throws IOException {
    getUrl();
  }
  public static void getUrl()throws IOException {
    Document document = Jsoup.connect("https://www.66law.cn/tiaoli/search.aspx?pushUnit=%E5%85%A8%E5%9B%BD%E4%BA%BA%E6%B0%91%E4%BB%A3%E8%A1%A8%E5%A4%A7%E4%BC%9A%E5%B8%B8%E5%8A%A1%E5%A7%94%E5%91%98%E4%BC%9A&valid=1&potenceLevel=2")
        .get();
    Elements lawElements = document.select("div.fagui-list.mt30");
    //System.out.println(lawElements.size());
    for(int i=0;i< lawElements.size();i++){
      Elements elements=lawElements.get(i).select("li");
      Elements elements1=elements.select("h3>a");
      //System.out.println(elements1);
      for(Element element:elements1){
        //System.out.println(element);
        String urltemp="https://www.66law.cn";
        urltemp+=element.attr("href");
        String name=element.text();
        //System.out.println(name+":"+urltemp);
        CrawlLaw(urltemp,name);
      }
    }
    WebDriver webDriver = new ChromeDriver();
    try {
      webDriver.get("https://www.66law.cn/tiaoli/search.aspx?pushUnit=%E5%85%A8%E5%9B%BD%E4%BA%BA%E6%B0%91%E4%BB%A3%E8%A1%A8%E5%A4%A7%E4%BC%9A%E5%B8%B8%E5%8A%A1%E5%A7%94%E5%91%98%E4%BC%9A&valid=1&potenceLevel=2");

      int totalPages = 13;
      for (int page = 1; page <= totalPages; page++) {
        WebElement nextPageButton = webDriver.findElement(By.linkText("下一页"));
        nextPageButton.click();
        // 使用显示等待等待新页面加载
        WebDriverWait wait = new WebDriverWait(webDriver, 100);
        //wait.until(ExpectedConditions.urlContains("page=" + (page + 1)));
        // 等待和提取数据
        // ...
        // 在下一页之前等待一段时间以防止访问过于频繁
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } finally {
      // 关闭浏览器
      webDriver.quit();
    }
  }
}
