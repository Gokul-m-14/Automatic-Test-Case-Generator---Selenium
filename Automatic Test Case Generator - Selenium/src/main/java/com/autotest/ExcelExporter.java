package com.autotest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    public static void export(String filePath, List<TestCase> cases) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Test Cases");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Test Case ID");
            header.createCell(1).setCellValue("Module");
            header.createCell(2).setCellValue("Scenario");
            header.createCell(3).setCellValue("Description");
            header.createCell(4).setCellValue("Steps");
            header.createCell(5).setCellValue("Expected Result");
            header.createCell(6).setCellValue("Priority");

            int r = 1;
            for (TestCase tc : cases) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(tc.id);
                row.createCell(1).setCellValue(tc.module);
                row.createCell(2).setCellValue(tc.scenario);
                row.createCell(3).setCellValue(tc.description);
                row.createCell(4).setCellValue(tc.steps);
                row.createCell(5).setCellValue(tc.expected);
                row.createCell(6).setCellValue(tc.priority);
            }


            // Auto-size columns
            for (int i = 0; i < 6; i++) sheet.autoSizeColumn(i);

            new File("output").mkdirs();
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                wb.write(out);
            }
            System.out.println("Test cases exported successfully to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
