package com.controller; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/scrape")
public class ScrapeController {

    @GetMapping("/ldlc")
    @ResponseBody
    public String scrapeLdlc(@RequestParam("url") String url) throws IOException {
        // String url = "https://www.ldlc.com/recherche/4070/";
        String command = "curl -s " + url;

        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder htmlContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            htmlContent.append(line);
        }

        Document doc = Jsoup.parse(htmlContent.toString());
        Elements products = doc.select(".listing-product");

        StringBuilder result = new StringBuilder();
        for (Element product : products) {
            String productName = product.select(".title-3").text();
            String productPrice = product.select(".price").text();
            result.append("Product: ").append(productName).append("\n");
            result.append("Price: ").append(productPrice).append("\n\n");
        }

        return result.toString();
    }
}
