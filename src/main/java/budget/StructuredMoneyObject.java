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

    public void setTotalInEuros(Long totalInEuros) {
        this.totalInEuros = totalInEuros;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static int getLevel(WebElement td) {
        if (td.findElements(By.tagName("a")).isEmpty()) { return 0; }
        if (td.getAttribute("colspan") != null) { return 1; }
        if (!td.findElements(By.tagName("span")).isEmpty()) { return 2; }
        return 3;
    }

    public static List<StructuredMoneyObject> parseChildren(WebElement webElement, int parentLevel, int parentCode, boolean isIncome) {
        List<StructuredMoneyObject> children = new ArrayList<>();

        int currentParent = -1;

        for (WebElement tr : webElement.findElements(By.tagName("tr"))) {
            List<WebElement> tdList = tr.findElements(By.tagName("td"));

            int level = getLevel(tdList.get(0));


            if (level == parentLevel) {
                currentParent = getIncome(tdList, level).getCode();
            }

            if (level == (parentLevel + 1) && currentParent == parentCode) {
                Income income = getIncome(tdList, level);

                if (isIncome) {
                    if (level == 1) { // we need to know more parents to judge if particular income is relevant
                                      // ([11.]01. Tulon ja varallisuuden perusteella kannettavat verot and [13.]01. Korkotulot
                        income.setConsistsOf(parseChildren(webElement, level, income.getCode(), isIncome));
                    }
                    children.add(income);
                } else {
                    Expenditure expenditure = new Expenditure(income);
                    if (level == 1) {
                        expenditure.setConsistsOf(parseChildren(webElement, level, expenditure.getCode(), isIncome));
                    }
                    children.add(expenditure);
                }
            }
        }

        return children;
    }

    private static Income getIncome(List<WebElement> tdList, int level) {
        Income income = new Income("", level, 0, "", 0L);

        if (level == 0) { return income; }

        WebElement linkElement = tdList.get(0).findElement(By.tagName("a"));
        income.setUrl(linkElement.getAttribute("href"));

        List<WebElement> spans = linkElement.findElements(By.tagName("span"));

        if (spans.isEmpty()) {
            income.setCode(new Integer(linkElement.getText().substring(0, 2)));
            income.setTitle(linkElement.getText().substring(4));
        } else if (spans.size() == 2) {
            income.setCode(new Integer(linkElement.getText().substring(0, 2)));
            income.setTitle(linkElement.getText().substring(4));
        } else {
            income.setCode(new Integer(spans.get(0).getText().replace(".", "").replaceFirst("^0+(?!$)", "")));
            income.setTitle(spans.get(1).getText());
        }

        WebElement sumTd = tdList.get(tdList.size() - 1);
        if (sumTd.getAttribute("total") != null) {
            income.setTotalInEuros(new Long(sumTd.getAttribute("total")));
        } else if (sumTd.getAttribute("type") != null) {
            income.setTotalInEuros(new Long(sumTd.getText().replace(" ", "") + "000"));
        } else {
            income.setTotalInEuros(new Long(sumTd.findElements(By.tagName("span")).get(0).getText().replace(" ", "")));
        }
        return income;
    }
}
