package com.durianfirst.durian.service;

import com.durianfirst.durian.entity.Items;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaangnService {

    private static String Items_URL = "https://www.daangn.com/search/%EC%88%98%EC%9B%90/";

    public List<Items> getItemsDatas() throws IOException {
        List<Items> itemsList = new ArrayList<>();
        Document document = Jsoup.connect(Items_URL).get();

        Elements contents = document.select("div.articles-wrap article");

        for (Element content : contents) {
            Items items = Items.builder()
                    .image(content.select("img").attr("abs:src")) // 이미지
                    .title(content.select("span.article-title").text())		// 제목
                    .content(content.select("span.article-content").text())   //내용
                    .address(content.select("p.article-region-name").text())   //주소
                    .price(content.select("p.article-price ").text())     //가격
                    .heart(content.select("span.article-watch").text())     //찜
                    .url(content.select("a").attr("abs:href"))		// 링크
                    .build();
            itemsList.add(items);
        }

        return itemsList;
    }
}
