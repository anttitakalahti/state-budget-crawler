package budget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Income extends StructuredMoneyObject {
    public Income(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        super(title, level, code, url, totalInThousandsOfEuros);
    }

    public static Income parseIncomeFromWebElement(WebElement webElement) {
        Income income = new Income("TULOARVIOT", 0, 0, "", new Long("54583994000"));
        income.setConsistsOf(parseChildren(webElement, income));
        return income;
    }

    private static List<StructuredMoneyObject> parseChildren(WebElement webElement, Income parent) {
        List<StructuredMoneyObject> children = new ArrayList<>();

        for (WebElement tr : webElement.findElements(By.tagName("tr"))) {
            List<WebElement> tdList = tr.findElements(By.tagName("td"));

            String title = "";
            int code = 0;
            int level = getLevel(tdList.get(0));
            String url = "";
            Long totalInEuros = 0L;

            if (level == (parent.getLevel() + 1)) {
                WebElement linkElement = tdList.get(0).findElement(By.tagName("a"));
                url = linkElement.getAttribute("href");

                List<WebElement> spans = linkElement.findElements(By.tagName("span"));

                if (spans.isEmpty()) {
                    code = new Integer(linkElement.getText().substring(0, 2));
                    title = linkElement.getText().substring(4);
                } else if (spans.size() == 2) {
                    code = new Integer(linkElement.getText().substring(0, 2));
                    title = linkElement.getText().substring(4);
                } else {
                    code = new Integer(spans.get(0).getText().replace(".", "").replaceFirst("^0+(?!$)", ""));
                    title = spans.get(1).getText();
                }

                WebElement sumTd = tdList.get(tdList.size() - 1);
                if (sumTd.getAttribute("total") != null) {
                    totalInEuros = new Long(sumTd.getAttribute("total"));
                } else {
                    totalInEuros = new Long(sumTd.findElements(By.tagName("span")).get(0).getText().replace(" ", ""));
                }

                Income income = new Income(title, level, code, url, totalInEuros);
                children.add(income);
            }
        }

        return children;
    }
}
