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

    public static List<StructuredMoneyObject> parseChildren(WebElement webElement, int parentLevel) {
        List<StructuredMoneyObject> children = new ArrayList<>();

        for (WebElement tr : webElement.findElements(By.tagName("tr"))) {
            List<WebElement> tdList = tr.findElements(By.tagName("td"));

            String title;
            int code;
            int level = getLevel(tdList.get(0));
            String url;
            Long totalInEuros;

            if (level == (parentLevel + 1)) {
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
