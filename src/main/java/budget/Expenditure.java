package budget;

import org.openqa.selenium.WebElement;

public class Expenditure extends StructuredMoneyObject {
    public Expenditure(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        super(title, level, code, url, totalInThousandsOfEuros);
    }

    public Expenditure(Income income) {
        this(income.getTitle(), income.getLevel(), income.getCode(), income.getUrl(), income.getTotalInEuros());
    }

    public static Expenditure parseExpenditureFromWebElement(WebElement webElement) {
        Expenditure expenditure = new Expenditure("MÄÄRÄRAHAT", 0, 0, "", new Long("54583994000"));
        expenditure.setConsistsOf(parseChildren(webElement, 0, 0, false));
        return expenditure;
    }
}
