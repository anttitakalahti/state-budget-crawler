package budget;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Crawler {
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver();

        driver.get("http://budjetti.vm.fi/indox/sisalto.jsp?year=2014&lang=fi&maindoc=/2014/aky/aky.xml&opennode=0:1:5:");
        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }
}
