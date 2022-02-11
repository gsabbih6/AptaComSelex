import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessingPipe implements Runnable {

    File rnaFasta;
    WebDriver driver;
//    HtmlUnitDriver driver;

    File protienFasta;
    private ExecutorService pool;
    /*
     * Selenium implementation for CatRapid
     * */


    public ProcessingPipe(File protienFasta, File rnaFasta) {
        this.protienFasta = protienFasta;
        this.rnaFasta = rnaFasta;
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
//        options.addArguments("--window-size=1920,1080");
        System.setProperty("webdriver.edge.driver", "C:\\Users\\fzp281\\Documents\\Edge\\msedgedriver.exe");
        driver = new EdgeDriver();
//        driver = new HtmlUnitDriver(BrowserVersion.CHROME,true);
        pool = Executors.newFixedThreadPool(100);
    }

    /*returns the download url*/
    private String process() {
//        driver.getCapabilities();
        driver.get("http://service.tartaglialab.com/page/catrapid_omics2_group");
        driver.findElement(By.linkText("catRAPID omics v2.1 [custom protein set VS custom transcript set]")).click();
//        driver.findElement(By.linkText("Click here")).click();
//        List<WebElement> elements = driver.findElements(By.tagName("iframe"));
//
//        driver.switchTo().frame(elements.get(0));
//        WebElement proteinInput = driver.findElement(By.id("uploadfile"));
//        proteinInput.sendKeys(protienFasta.getAbsolutePath());

        WebElement proteinTextArea = driver.findElements(By.id("prot_seq")).get(0);
        proteinTextArea.sendKeys("test");


//        driver.switchTo().defaultContent();

//        driver.switchTo().frame(elements.get(1));
//        WebElement rnaInput = driver.findElement(By.id("uploadfile"));
//        rnaInput.sendKeys(rnaFasta.getAbsolutePath());

        driver.switchTo().defaultContent();
        driver.navigate().refresh();

        WebElement submit = new WebDriverWait(driver, Duration.ofSeconds(300))
                .until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        submit.click();

        WebElement result = new WebDriverWait(driver, Duration.ofSeconds(300))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("result")));
        result.click();

        WebElement reduced = new WebDriverWait(driver, Duration.ofHours(1))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("reduced")));
        String downloadLink = reduced.getAttribute("href");
        System.out.println(downloadLink);

        driver.close();
        driver.quit();

        return downloadLink;

    }

    class DownloadResults implements Runnable {

        private String url;
        private File file;

        public DownloadResults(String url, File file) {
            this.url = url;
            this.file = file;
        }

        public void downloadResults() throws IOException {

            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();// make directory

            FileUtils.copyURLToFile(
                    new URL(url),
                    file,
                    10000,
                    10000);

        }

        @Override
        public void run() {
            try {
                downloadResults();
            } catch (IOException e) {
                e.printStackTrace();
//                pool.shutdown();
            }
        }
    }


    @Override
    public void run() {

        pool.execute(new DownloadResults(process(),
                new File("output/" + this.rnaFasta.getName() + "_out.zip")));
        pool.shutdown();

    }
}
