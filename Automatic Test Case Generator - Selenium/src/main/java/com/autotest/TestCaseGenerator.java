package com.autotest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCaseGenerator {

    private static int counter = 1;

    public static String genId(String module) {
        return "TC_" + module.toUpperCase() + "_" + String.format("%04d", counter++);
    }

    public static List<TestCase> generate(Document doc, String baseUrl) {
        counter = 1;
        List<TestCase> out = new ArrayList<>();

        // Smoke test
        out.add(new TestCase(
                "Smoke",
                "Page loads successfully",
                "Open the URL and verify page title and no server error",
                "1. Open " + baseUrl + " in browser",
                "Page title is present and HTTP status is OK",
                "High"
        ));

        out.addAll(generateInputTests(doc));
        out.addAll(generateButtonTests(doc));
        out.addAll(generateLinkTests(doc));
        out.addAll(generateFormTests(doc));
        out.addAll(generateTableTests(doc));

        return out;
    }

    // Input tests
    private static List<TestCase> generateInputTests(Document doc) {
        List<TestCase> list = new ArrayList<>();
        Elements inputs = doc.select("input, textarea, select");
        Map<String, Integer> seen = new HashMap<>();

        for (Element e : inputs) {
            String tag = e.tagName();
            String type = e.attr("type");
            if (type.isEmpty()) type = tag.equals("textarea") ? "textarea" : tag.equals("select") ? "select" : "text";

            String name = !e.attr("name").isEmpty() ? e.attr("name") : !e.id().isEmpty() ? e.id() : "field";
            int idx = seen.getOrDefault(name, 0) + 1;
            seen.put(name, idx);
            String label = name + (idx > 1 ? "#" + idx : "");
            String scenario = "Input Field: " + label;

            // Visibility
            list.add(new TestCase(
                    "Input",
                    scenario,
                    "Check if '" + label + "' is visible on the page",
                    "1. Locate the input field '" + label + "'",
                    "Field is visible",
                    "High"
            ));

            // Input acceptance
            list.add(new TestCase(
                    "Input",
                    scenario,
                    "Verify '" + label + "' accepts input of type '" + type + "'",
                    "1. Enter valid input of type '" + type + "'",
                    "Input accepted",
                    "High"
            ));

            if (type.equalsIgnoreCase("password")) {
                list.add(new TestCase(
                        "Input",
                        scenario,
                        "Verify password masking for '" + label + "'",
                        "1. Enter password in '" + label + "'",
                        "Password characters masked",
                        "High"
                ));
            }

            if (type.equalsIgnoreCase("email") || name.toLowerCase().contains("email")) {
                list.add(new TestCase(
                        "Input",
                        scenario,
                        "Verify email validation for '" + label + "'",
                        "1. Enter invalid email\n2. Submit form",
                        "Validation error shown",
                        "High"
                ));
            }

            if (e.hasAttr("required")) {
                list.add(new TestCase(
                        "Input",
                        scenario,
                        "Verify required field validation for '" + label + "'",
                        "1. Leave empty\n2. Submit form",
                        "Error shown",
                        "High"
                ));
            }

            String maxlen = e.attr("maxlength");
            if (!maxlen.isEmpty()) {
                list.add(new TestCase(
                        "Input",
                        scenario,
                        "Verify maxlength=" + maxlen + " for '" + label + "'",
                        "1. Enter more than " + maxlen + " chars",
                        "Field restricts input",
                        "Medium"
                ));
            }
        }

        return list;
    }

    // Button tests
    private static List<TestCase> generateButtonTests(Document doc) {
        List<TestCase> list = new ArrayList<>();
        Elements buttons = doc.select("button, input[type=button], input[type=submit], input[type=reset]");
        int idx = 1;
        for (Element b : buttons) {
            String label = !b.text().isEmpty() ? b.text() : !b.attr("value").isEmpty() ? b.attr("value") : !b.id().isEmpty() ? b.id() : "button" + idx++;
            String scenario = "Button: " + label;

            list.add(new TestCase(
                    "Button",
                    scenario,
                    "Verify '" + label + "' is visible",
                    "1. Locate button '" + label + "'",
                    "Button visible",
                    "Medium"
            ));

            list.add(new TestCase(
                    "Button",
                    scenario,
                    "Verify '" + label + "' is clickable",
                    "1. Click button '" + label + "'",
                    "Button action performed",
                    "High"
            ));
        }
        return list;
    }

    // Link tests
    private static List<TestCase> generateLinkTests(Document doc) {
        List<TestCase> list = new ArrayList<>();
        Elements links = doc.select("a[href]");
        int idx = 1;
        for (Element l : links) {
            String text = !l.text().isEmpty() ? l.text() : !l.attr("href").isEmpty() ? l.attr("href") : "link" + idx++;
            String href = l.attr("abs:href");
            String scenario = "Link: " + text;

            list.add(new TestCase(
                    "Link",
                    scenario,
                    "Verify link is visible",
                    "1. Locate link '" + text + "'",
                    "Link visible",
                    "Low"
            ));

            list.add(new TestCase(
                    "Link",
                    scenario,
                    "Verify link navigation",
                    "1. Click link '" + text + "'\n2. Verify navigation to '" + href + "'",
                    "Navigation successful",
                    "Medium"
            ));
        }
        return list;
    }

    // Form tests
    private static List<TestCase> generateFormTests(Document doc) {
        List<TestCase> list = new ArrayList<>();
        Elements forms = doc.select("form");
        int fidx = 1;
        for (Element f : forms) {
            String fid = !f.id().isEmpty() ? f.id() : "form" + fidx++;
            String action = f.hasAttr("action") ? f.attr("abs:action") : "(same)";
            String method = f.hasAttr("method") ? f.attr("method") : "GET";
            String scenario = "Form: " + fid;

            list.add(new TestCase(
                    "Form",
                    scenario,
                    "Verify form presence",
                    "1. Locate form '" + fid + "'",
                    "Form displayed",
                    "High"
            ));

            list.add(new TestCase(
                    "Form",
                    scenario,
                    "Happy path submission",
                    "1. Fill required fields\n2. Submit form",
                    "Submission successful",
                    "High"
            ));

            list.add(new TestCase(
                    "Form",
                    scenario,
                    "Negative path submission",
                    "1. Leave blank/invalid\n2. Submit",
                    "Validation error shown",
                    "High"
            ));
        }
        return list;
    }

    // Table tests
    private static List<TestCase> generateTableTests(Document doc) {
        List<TestCase> list = new ArrayList<>();
        Elements tables = doc.select("table");
        int t = 1;
        for (Element table : tables) {
            String id = !table.id().isEmpty() ? table.id() : "table" + t++;
            String scenario = "Table: " + id;

            list.add(new TestCase(
                    "Table",
                    scenario,
                    "Verify table visibility",
                    "1. Locate table '" + id + "'",
                    "Table displayed with headers",
                    "Medium"
            ));

            list.add(new TestCase(
                    "Table",
                    scenario,
                    "Verify pagination/sorting",
                    "1. Sort columns\n2. Navigate pages",
                    "Works correctly",
                    "Low"
            ));
        }
        return list;
    }
}