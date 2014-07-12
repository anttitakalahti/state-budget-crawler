package budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class Crawler {

    public static final String URL = "http://budjetti.vm.fi/indox/sisalto.jsp?year=2014&lang=fi&maindoc=/2014/aky/aky.xml&opennode=0:1:5:";

    public static void main(String[] args) throws IOException {
        HtmlUnitDriver driver = new HtmlUnitDriver();

        Budget budget = getBudgetWithIncomeAndExpenditure(driver);

        makeSureCalculatedSumsMatchReportedValues(budget);

        printJSONtoSystemOut(budget);

        driver.quit();
    }

    private static Budget getBudgetWithIncomeAndExpenditure(HtmlUnitDriver driver) {
        driver.get(URL);

        List<WebElement> contentWrappers = driver.findElements(By.className("Taulukko_perus"));

        Budget budget = new Budget(driver.getTitle(), 2014, URL);

        budget.setIncome(Income.parseIncomeFromWebElement(contentWrappers.get(0)));
        budget.setExpenditure(Expenditure.parseExpenditureFromWebElement(contentWrappers.get(1)));

        return budget;
    }

    private static void makeSureCalculatedSumsMatchReportedValues(Budget budget) {
        calculateSumOfChildren(budget.getIncome());
        calculateSumOfChildren(budget.getExpenditure());
    }

    private static void calculateSumOfChildren(StructuredMoneyObject structuredMoneyObject) {
        if (structuredMoneyObject.getConsistsOf().isEmpty()) { return; }
        Long total = 0L;
        for (StructuredMoneyObject child : structuredMoneyObject.getConsistsOf()) {
            total += child.getTotalInEuros();
            calculateSumOfChildren(child);
        }
        if (total.longValue() != structuredMoneyObject.getTotalInEuros().longValue()) {
            System.out.println(
                    "SUMS DON'T MATCH FOR " + structuredMoneyObject.getClass().getName() + " " +
                    "WITH LEVEL: " + structuredMoneyObject.getLevel() + " " +
                    "AND CODE: " + structuredMoneyObject.getCode() + " " +
                    "AND TITLE: " + structuredMoneyObject.getTitle() + "! " +
                    "Expected: " + structuredMoneyObject.getTotalInEuros() + " but calculated: " + total);

            for (StructuredMoneyObject child : structuredMoneyObject.getConsistsOf()) {
                System.out.println(String.format("level: %d, code: %d, title %s, euros: %d", child.getLevel(), child.getCode(), child.getTitle(), child.getTotalInEuros()));
            }


            System.exit(-1);
        }
    }

    private static void printJSONtoSystemOut(Budget budget) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, budget);
        System.out.println(stringWriter.toString());
    }
}
