package pachong4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class lawcrawl {
  public static void CrawlLaw(String url,String name) throws IOException {
    Document document= Jsoup.connect(url).get();
    Elements getLaw=document.select("div.rich_media_content");
    File file=new File("untitled\\src\\"+name+".txt");
    FileWriter fileWriter=new FileWriter(file);
    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
    for(int i=0;i<getLaw.size();i++){
      Elements elements=getLaw.get(i).select("div");
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
    GetUrl();
  }
  public static void GetUrl() throws IOException {
    WebDriver webDriver = new ChromeDriver();
    try {
      webDriver.get("http://www.dffyw.com/faguixiazai/msf/index.html");
      int totalPages = 18;
      for (int page = 1; page <= totalPages; page++) {
        // 使用显示等待等待新页面加载
        //WebDriverWait wait = new WebDriverWait(webDriver, 100);
        //wait.until(ExpectedConditions.urlContains("page=" + (page + 1)));
        String tempurl=webDriver.getCurrentUrl();
        System.out.println(tempurl);
        Document document=Jsoup.connect(tempurl).get();
        Elements lawElements = document.select("div.rich_media_inner");
        for(int i=0;i< lawElements.size();i++){
          Elements elements=lawElements.get(i).select("li");
          Elements elements1=elements.select("a");
          System.out.println(elements1);
          for(Element element:elements1){
            //System.out.println(element);
            String urltemp="http://www.dffyw.com/";
            //Connection.Response response=Jsoup.connect("http://www.dffyw.com/"+element.attr("href")).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36").ignoreContentType(true).execute();
            urltemp+=element.attr("href");
            String name=element.text();
            /*if(name.length()==0||name.equals("现行有效")||name.equals("失效")||name.equals("尚未生效")||name.equals("已被修改")||name.equals("部分失效")){
              continue;
            }*/
            System.out.println(name+":"+"https://www.dffyw.com/"+urltemp);
            CrawlLaw(urltemp,name);
          }
        }
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        WebElement nextPageButton = webDriver.findElement(By.linkText("下一页"));
        nextPageButton.click();
      }
    } finally {
      // 关闭浏览器
      webDriver.quit();
    }
  }
  public static boolean IfGet(String name){
    String tempName="中华人民共和国";
    String[] temp=new String[]{"消费者权益保护法","产品质量法","食品安全法","商标法","电子商务法","竞争法","价格法","产品责任法","广告法",
    "商业特许经营管理办法","知识产权法","专利法","著作权法","商业秘密法","不正当竞争法","网络交易管理办法","国际贸易法","进出口管理条例","外商投资法",
    "个人信息保护法","数据安全法","税收法","关税法","保险法","金融法","证券法","企业法","商事制度改革法","国有资产法","房地产法","土地管理法","建筑法",
    "交通运输法","货物运输法","仓储法","物权法","信用证法","信托法","保函法","担保法","破产法","企业破产法","证券交易法","投资法","外汇管理法","国际私法","国际商事仲裁法","国际合同法","国际买卖法"};
    for(int i=0;i<50;i++){
      if(name.equals(tempName+temp)){
        return true;
      }
    }
    return false;
  }
}


