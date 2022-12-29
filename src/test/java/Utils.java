import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.*;

public class Utils {
    public static void explicitWaitForTheElement(WebDriver driver, WebElement element, int SECOND) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(SECOND));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static List<String> GetLargestAndSmallestString(List<String> input) {
        if (input.size() == 0) {
            return Arrays.asList("", "");
        } else if (input.size() == 1) {
            String item = input.get(0);
            return Arrays.asList(item, item);
        } else {
            input.sort(Comparator.comparingInt(String::length));
            return Arrays.asList(input.get(input.size() - 1), input.get(0));
        }
    }

    public static void WriteExcel(int iteration, List<String> validSearchResults) {
        // Try block to check for exceptions
        try {
            File f = new File("./src/test/resources/Excel.xlsx");

            // Reading file from local directory
            FileInputStream file = new FileInputStream(f);

            // workbook
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // get day of week
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            Map<Integer, Integer> dayMap = new HashMap<>();
            dayMap.put(1, 2);
            dayMap.put(2, 3);
            dayMap.put(3, 4);
            dayMap.put(4, 5);
            dayMap.put(5, 6);
            dayMap.put(6, 7);
            dayMap.put(7, 1);

            // sheet
            XSSFSheet sheet = workbook.getSheetAt(dayMap.get(day) - 1);

            // cell placement - 1
            Row row = sheet.getRow(2 + iteration);
            Cell cell = row.getCell(3);
            cell.setCellValue(validSearchResults.get(0));

            // cell placement - 2
            Row row2 = sheet.getRow(2 + iteration);
            Cell cell2 = row2.getCell(4);
            cell2.setCellValue(validSearchResults.get(1));

            // Writing the workbook
            FileOutputStream out = new FileOutputStream(f);
            workbook.write(out);
            //close all
            out.close();
            file.close();
        }
        // Catch block to handle exceptions
        catch (Exception e) {
            // Display exceptions along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
    }

    public static List<String> ReadExcel() {
        List<String> output = new ArrayList<>();
        // Try block to check for exceptions
        try {
            File f = new File("./src/test/resources/Excel.xlsx");

            // Reading file from local directory
            FileInputStream file = new FileInputStream(f);

            // workbook
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // get day of week
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            Map<Integer, Integer> dayMap = new HashMap<>();
            dayMap.put(1, 2);
            dayMap.put(2, 3);
            dayMap.put(3, 4);
            dayMap.put(4, 5);
            dayMap.put(5, 6);
            dayMap.put(6, 7);
            dayMap.put(7, 1);

            // sheet
            XSSFSheet sheet = workbook.getSheetAt(dayMap.get(day) - 1);

            // Iterate through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();

            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() > 11) {
                    break;
                } else if (row.getRowNum() >= 2 && row.getRowNum() <= 11) {
                    // For each row, iterate through all the
                    // columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() > 2) {
                            break;
                        } else if (cell.getColumnIndex() == 2) {
                            output.add(cell.getStringCellValue());
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            }

            // Closing file output streams
            file.close();
            return output;
        }
        // Catch block to handle exceptions
        catch (Exception e) {
            // Display exceptions along with line number
            // using printStackTrace() method
            e.printStackTrace();
            return output;
        }
    }

    public static List<String> getSearchResults(WebDriver driver, String searchKeyword) {
        List<String> output = new ArrayList<>();
        try {
            driver.get("https://www.google.com");
            driver.findElement(By.xpath("//input[@name='q']")).clear();
            driver.findElement(By.xpath("//input[@name='q']")).sendKeys(searchKeyword);
            Thread.sleep(4000);
            List<WebElement> webElements = driver.findElements(By.xpath("//ul[@class='G43f7e']/li/div[@class='eIPGRd']/div[@class='pcTkSc']/div[@class='wM6W7d']/span"));
            for (WebElement webElement : webElements) {
                output.add(webElement.getText());
            }
            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
            return output;
        }
    }
}