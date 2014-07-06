package budget;

import org.openqa.selenium.WebElement;

public class Income extends StructuredMoneyObject {
    public Income(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        super(title, level, code, url, totalInThousandsOfEuros);
    }

    public static Income parseIncomeFromWebElement(WebElement webElement, Income parent) {
        return new Income("TULOARVIOT", 0, 0, "", new Long("54583994"));
    }
}
