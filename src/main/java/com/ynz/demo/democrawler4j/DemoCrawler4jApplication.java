package com.ynz.demo.democrawler4j;

import com.ynz.demo.democrawler4j.webcrawerler.HtmlCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DemoCrawler4jApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoCrawler4jApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File savedFiles = new File("src/test/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(savedFiles.getAbsolutePath());
        config.setMaxDepthOfCrawling(2);
        config.setMaxPagesToFetch(500);
        config.setPolitenessDelay(300);
        config.setResumableCrawling(true);


        int numCrawlers = Runtime.getRuntime().availableProcessors();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);


        controller.addSeed("https://finance.yahoo.com/quote/BFT?p=BFT&.tsrc=fin-srch");

        CrawlController.WebCrawlerFactory<HtmlCrawler> factory = HtmlCrawler::new;
        controller.start(factory, numCrawlers);

    }
}
