package com.autotest;
import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
    public static void main(String[] args) {
        try {
            
            if (args.length < 1) 
            {
                System.out.println("Usage: java -jar selenium-autotest-upgrade-1.0-SNAPSHOT-shaded.jar <target-url>");
                return;
            }

            String url = args[0];
            System.out.println("Fetching: " + url);

                Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0")
                .header("Accept", "*/*")
                .header("Accept-Language", "en-US,en;q=0.9")
                .timeout(10000)
                .ignoreHttpErrors(true)
                .get();

            System.out.println("Analyzing page and generating test cases...");

            List<TestCase> cases = TestCaseGenerator.generate(doc, url);

            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            String websiteName = sanitize(url);
            String outFile = "output/" + websiteName + "_testcases.xlsx";
            ExcelExporter.export(outFile, cases);
            System.out.println("Saved: " + outFile);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String sanitize(String url) {
        return url.replaceFirst("https?://", "")
                .replaceAll("[\\/:\\\\\\s]+", "_")
                .replaceAll("[^a-zA-Z0-9_]+", "_");
    }
}
