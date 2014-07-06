package budget;

public class Budget {

    private String title;
    private int year;
    private String url;

    private Income income;
    private Expenditure expenditure;

    public Budget(String title, int year, String url) {
        this.title = title;
        this.year = year;
        this.url = url;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public void setExpenditure(Expenditure expenditure) {
        this.expenditure = expenditure;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public Income getIncome() {
        return income;
    }

    public Expenditure getExpenditure() {
        return expenditure;
    }
}
