package budget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class StructuredMoneyObject {

    private String title;
    private int level;
    private int code;
    private String url;
    private Long totalInEuros;
    private List<StructuredMoneyObject> consistsOf;

    public StructuredMoneyObject(String title, int level, int code, String url, Long totalInEuros) {
        this.title = title;
        this.level = level;
        this.code = code;
        this.url = url;
        this.totalInEuros = totalInEuros;
        consistsOf = new ArrayList<>();
    }

    public void setConsistsOf(List<StructuredMoneyObject> consistsOf) {
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

    public Long getTotalInEuros() {
        return totalInEuros;
    }

    public List<StructuredMoneyObject> getConsistsOf() {
        return consistsOf;
    }

    public static int getLevel(WebElement td) {
        if (td.findElements(By.tagName("a")).isEmpty()) { return 0; }
        if (td.getAttribute("colspan") != null) { return 1; }


        return 2;
    }

}
