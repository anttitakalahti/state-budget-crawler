package budget;

import org.openqa.selenium.WebElement;

public class Income extends StructuredMoneyObject {
    public Income(String title, int level, int code, String url, Long totalInEuros) {
        super(title, level, code, url, totalInEuros);
    }

    public String toString() {
        return String.format("Income - title: %s, level: %d, code: %d, url: %s, total: %d", getTitle(), getLevel(), getCode(), getUrl(), getTotalInEuros());
    }

    public static Income parseIncomeFromWebElement(WebElement webElement) {
        Income income = new Income("TULOARVIOT", 0, 0, "", new Long("54583994000"));
        income.setConsistsOf(parseChildren(webElement, 0, 0, true));
        return income;
    }
}
