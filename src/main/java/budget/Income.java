package budget;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Income {
    private String title;
    private int level;
    private int code;
    private String url;
    private Long totalInThousandsOfEuros;
    private List<Income> consistsOf;

    public Income(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        this.title = title;
        this.level = level;
        this.code = code;
        this.url = url;
        this.totalInThousandsOfEuros = totalInThousandsOfEuros;
        consistsOf = new ArrayList<>();
    }

    public void setConsistsOf(List<Income> consistsOf) {
        this.consistsOf = consistsOf;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public int getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public Long getTotalInThousandsOfEuros() {
        return totalInThousandsOfEuros;
    }

    public List<Income> getConsistsOf() {
        return consistsOf;
    }

    public static Income parseIncomeFromWebElement(WebElement webElement, Income parent) {
        return new Income("TULOARVIOT", 0, 0, "", new Long("54583994"));
    }
}
