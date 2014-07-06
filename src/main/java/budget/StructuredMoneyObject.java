package budget;

import java.util.ArrayList;
import java.util.List;

public abstract class StructuredMoneyObject {

    private String title;
    private int level;
    private int code;
    private String url;
    private Long totalInThousandsOfEuros;
    private List<Expenditure> consistsOf;

    public StructuredMoneyObject(String title, int level, int code, String url, Long totalInThousandsOfEuros) {
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

}
