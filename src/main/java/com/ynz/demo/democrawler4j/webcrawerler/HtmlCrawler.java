package com.ynz.demo.democrawler4j.webcrawerler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
public class HtmlCrawler extends WebCrawler {

    private final static Pattern EXCLUSIONS
            = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches()
                && urlString.startsWith("https://finance.yahoo.com/");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        log.info(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            // do something with the collected data

            log.info(title);
            log.info(String.valueOf(text.length()));
            log.info(String.valueOf(html.length()));
        }
    }

}
