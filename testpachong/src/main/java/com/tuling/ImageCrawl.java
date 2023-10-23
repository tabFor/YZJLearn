package com.tuling;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImageCrawl {

  private static String url = "https://www.beijing.gov.cn/ywdt/zwzt/htsfwb/";

  public static void main(String[] args) throws IOException {
    Document document = Jsoup.connect(url).get();
    Elements elements = document.select("li");
    for (int i = 0; i < elements.size(); i++) {
      Elements imgelements = elements.get(i).select("span > a");
      Connection.Response response = Jsoup.connect(imgelements.attr("href")).ignoreContentType(true)
          .userAgent(
              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.60")
          .execute();
      System.out.println(response);
      String name = imgelements.attr("title");
      ByteArrayInputStream stream = new ByteArrayInputStream(response.bodyAsBytes());
      FileUtils.copyInputStreamToFile(stream, new File("D://contract//" + name + ".doc"));
      System.out.println(name);
    }
  }

}
