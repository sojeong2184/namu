package com.durianfirst.durian.service;

import com.durianfirst.durian.entity.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DurianService {
    private static String News_URL = "https://www.goodchoice.kr/product/result?keyword=%ED%99%94%EC%84%B1";

    public List<News> getNewsDatas() throws IOException {
        List<News> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();

        Elements contents = document.select("div.list_wrap li");

        for (Element content : contents) {
            News news = News.builder()
                    .image(content.select("img.lazy").attr("abs:src")) // 이미지
                    .subject(content.select("strong").text())		// 제목
                    .url(content.select("a").attr("abs:href"))		// 링크
                    .build();
            newsList.add(news);
        }

        return newsList;
    }
}
