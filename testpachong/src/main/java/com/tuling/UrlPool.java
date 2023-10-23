package com.tuling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Document;
import org.jsoup.Jsoup;


public class UrlPool {

    public static void main(String[] args) {
        String url = "https://www.nipic.com/";GetUrl(url);
        //for (int i = 1; i <= 32; i++){}

    }

    static List<String> fineUrls = new ArrayList<>();

    public static void GetUrl(String baseUrl){
        Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>();
        String oldLinkHost = "";
        Pattern  p = Pattern.compile("(https?://)?[^/\\s]*");
        Matcher m = p.matcher(baseUrl);
        if(m.find()){
            oldLinkHost = m.group();
        }
        oldMap.put(baseUrl, false);
        oldMap = CrawlLinks(oldLinkHost, oldMap);


        for(Map.Entry<String, Boolean> mapping : oldMap.entrySet()){
            System.out.println("链接： " + mapping.getKey());
            if(mapping.getKey().contains("http://scjg.hangzhou.gov.cn/art/")){
                fineUrls.add(mapping.getKey());
            }
        }
    }

    public static Map<String, Boolean> CrawlLinks(String oldLinkHost, Map<String, Boolean> oldMap){
        Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();
        String oldLink = "";
        for(Map.Entry<String, Boolean> mapping : oldMap.entrySet()){
            System.out.println("刷新链接： " + mapping.getKey());
            if(!mapping.getValue()){}
                oldLink = mapping.getKey();
                try {
                    URL url = new URL(oldLink);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    if(connection.getResponseCode() == 200){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        Pattern  p = Pattern.compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
                        Matcher matcher = null;
                        String line = "";
                        while((line = reader.readLine()) != null){
                            matcher = p.matcher(line);
                            if(matcher.find()){
                                String newLink = matcher.group(1).trim();
                                if(!newLink.startsWith("http")){
                                    if(newLink.startsWith("/")){
                                        newLink = oldLinkHost + newLink;
                                    }else {
                                        newLink = oldLinkHost + "/" + newLink;
                                    }
                                }
                                if(newLink.endsWith("/")){
                                    newLink = newLink.substring(0, newLink.length() - 1);
                                }
                                if(!oldMap.containsKey(newLink) && !newMap.containsKey(newLink) && newLink.startsWith(oldLinkHost)){
                                    newMap.put(newLink, false);
                                }
                            }
                        }
                    }
                }catch (Exception e){

                }finally {

                }
            oldMap.replace(oldLink, false, true);
        }
        if(!newMap.isEmpty()){
            oldMap.putAll(newMap);
            oldMap.putAll(CrawlLinks(oldLinkHost, oldMap));
        }
        return oldMap;
    }

    public static void GetUrls(String url) throws IOException {
        List<String> getUrls = new ArrayList<>();
        Document doc = (Document) Jsoup.parse(url);
        int allPages = 32;
        Map<String, Integer> keyMap = new HashMap<String, Integer>();
        for (int i = 1; i <=32; i++){
            String nowUrl = "http://scjg.hangzhou.gov.cn/col/col1694849/index.html?uid=6484082&pageNum=" + i;
            Document document = (Document) Jsoup.connect(nowUrl).timeout(30000).get();
        }

    }
}
