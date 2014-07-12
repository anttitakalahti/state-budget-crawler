package budget;

import org.openqa.selenium.WebElement;

public class Expenditure extends StructuredMoneyObject {
    public Expenditure(String title, int level, int code, String url, Long totalInEuros) {
        super(title, level, code, url, totalInEuros);
    }

    public Expenditure(Income income) {
        this(income.getTitle(), income.getLevel(), income.getCode(), income.getUrl(), income.getTotalInEuros());
    }

    public String toString() {
        return String.format("Expenditure - title: %s, level: %d, code: %d, url: %s, total: %d", getTitle(), getLevel(), getCode(), getUrl(), getTotalInEuros());
    }

    public static Expenditure parseExpenditureFromWebElement(WebElement webElement) {
        Expenditure expenditure = new Expenditure("MÄÄRÄRAHAT", 0, 0, "", new Long("54583994000"));
        expenditure.setConsistsOf(parseChildren(webElement, 0, 0, false));
        return expenditure;
    }
}
