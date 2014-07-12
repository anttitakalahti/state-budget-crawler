package budget;

import org.openqa.selenium.WebElement;

public class Expenditure extends StructuredMoneyObject {
    public Expenditure(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        super(title, level, code, url, totalInThousandsOfEuros);
    }

    public static Expenditure parseExpenditureFromWebElement(WebElement webElement) {
        Expenditure expenditure = new Expenditure("MÄÄRÄRAHAT", 0, 0, "", new Long("54583994000"));
        expenditure.setConsistsOf(parseChildren(webElement, 0));
        return expenditure;
    }
}
