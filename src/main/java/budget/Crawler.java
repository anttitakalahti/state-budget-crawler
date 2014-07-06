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

        driver.get(URL);
        List<WebElement> contentWrappers = driver.findElements(By.className("Taulukko_perus"));

        Budget budget = new Budget(driver.getTitle(), 2014, URL);

        budget.setIncome(Income.parseIncomeFromWebElement(contentWrappers.get(0)));
        budget.setExpenditure(Expenditure.parseExpenditureFromWebElement(contentWrappers.get(1), null));

        makeSureCalculatedSumsMatch(budget);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, budget);
        System.out.println(stringWriter.toString());

        driver.quit();
    }

    private static void makeSureCalculatedSumsMatch(Budget budget) {
        Long totalIncome = 0L;
        for (StructuredMoneyObject income : budget.getIncome().getConsistsOf()) {
              totalIncome += income.getTotalInEuros();
        }
        if (totalIncome.longValue() != budget.getIncome().getTotalInEuros().longValue()) {
            System.out.println("SUMS DON'T MATCH!");
            System.exit(-1);
        }
    }
}
