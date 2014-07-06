package budget;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Expenditure {
    private String title;
    private int level;
    private int code;
    private String url;
    private Long totalInThousandsOfEuros;
    private List<Expenditure> consistsOf;

    public Expenditure(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
        this.title = title;
        this.level = level;
        this.code = code;
        this.url = url;
        this.totalInThousandsOfEuros = totalInThousandsOfEuros;
        consistsOf = new ArrayList<>();
    }

    public void setConsistsOf(List<Expenditure> consistsOf) {
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

    public List<Expenditure> getConsistsOf() {
        return consistsOf;
    }

    public static Expenditure parseExpenditureFromWebElement(WebElement webElement, Expenditure parent) {
        return new Expenditure("MÄÄRÄRAHAT", 0, 0, "", new Long("54583994"));
    }
}
