import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessingRPISeq implements Runnable {

    //    Logger log=new Logger();
    File rnaFasta;
    WebDriver driver;
//    HtmlUnitDriver driver;

    String protienFasta;
    private ExecutorService pool;
    /*
     * Selenium implementation for CatRapid
     * */

    private static Logger logger;

    public ProcessingRPISeq(String protienFasta, File rnaFasta) {
        this.protienFasta = protienFasta;
        this.rnaFasta = rnaFasta;
        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        options.addArguments("--window-size=1920,1080");

        // location of the browsers webdriver
        System.setProperty("webdriver.edge.driver", "C:\\Users\\fzp281\\Documents\\Edge\\msedgedriver.exe");
        driver = new EdgeDriver(options);
//        driver = new HtmlUnitDriver(BrowserVersion.CHROME,true);
        pool = Executors.newFixedThreadPool(100);
        logger = LoggerFactory.getLogger(ProcessingRPISeq.class);

    }

    public String processRPISeq(String protienFastaString) {
//        driver.getCapabilities();
        driver.get("http://pridb.gdcb.iastate.edu/RPISeq/batch-prot.html");
//        driver.findElement(By.linkText("catRAPID omics v2.1 [custom protein set VS custom transcript set]")).click();
//        driver.findElement(By.linkText("Click here")).click();
//        List<WebElement> elements = driver.findElements(By.tagName("iframe"));

        WebElement protienFastaElement = driver.findElement(By.id("p_input"));
        protienFastaElement.sendKeys(protienFastaString);

//
        WebElement rnaInput = driver.findElement(By.id("r_input"));
        rnaInput.sendKeys(rnaFasta.getAbsolutePath());


//        WebElement submit = driver.findElement(By.name("submit"));
//        submit.click();
//
        WebElement submit = new WebDriverWait(driver, Duration.ofSeconds(300))
                .until(ExpectedConditions.elementToBeClickable(By.name("submit")));
        submit.click();
//
//        WebElement result = new WebDriverWait(driver, Duration.ofSeconds(300))
//                .until(ExpectedConditions.elementToBeClickable(By.linkText("result")));
//        result.click();
//
        WebElement reduced = new WebDriverWait(driver, Duration.ofHours(1))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("here")));
        String downloadLink = reduced.getAttribute("href");
//        System.out.println(downloadLink);

        driver.close();
        driver.quit();

        return downloadLink;

    }
//    class DownloadResults implements Runnable {
//
//        private String url;
//        private File file;
//
//        public DownloadResults(String url, File file) {
//            this.url = url;
//            this.file = file;
//        }
//
//        public void downloadResults() throws IOException {
//
//            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();// make directory
//
//            FileUtils.copyURLToFile(
//                    new URL(url),
//                    file,
//                    300000,
//                    300000);
//
//        }
//
//        @Override
//        public void run() {
//            try {
//                downloadResults();
//            } catch (IOException e) {
//                e.printStackTrace();
////                pool.shutdown();
//            }
//        }
//
//    }


    @Override
    public void run() {

//        pool.submit(new DownloadResults(process(),
//                new File("output/" + this.rnaFasta.getName() + "_out.zip")));
//        pool.shutdown();
        logger.info("Processing job: " + rnaFasta.getName());
        System.out.println("Processing job: " + rnaFasta.getName());
        File file = new File("output/" + this.rnaFasta.getName() + "_out.txt");
        String url = processRPISeq(this.protienFasta);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();// make directory
        try {
            FileUtils.copyURLToFile(
                    new URL(url),
                    file,
                    300000,
                    300000);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new DownloadResults(process(),
//                new File("output/" + this.rnaFasta.getName() + "_out.zip"));


    }
}
