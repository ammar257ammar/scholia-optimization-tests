package ul.unimaas.bigcat.ScholiaOptimizationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App 
{
    public static void main( String[] args )
    {
    	
    	System.out.println("Scholia original website performance");
    	testScholia("https://scholia.toolforge.org/organization/Q1269766", "query-result");
    	
    	System.out.println();
    	
    	//System.out.println("Scholia optimized website performance");
    	//testScholia("http://85.214.42.229:8100/organization/Q1269766", "result");
        
    }
    
    public static void testScholia(String baseUrl, String id){
    	
    	System.setProperty("webdriver.chrome.driver", "driver\\chromedriver87.exe");
        WebDriver driver = new ChromeDriver();
                
        List<Long> results = new ArrayList<Long>();
        
        boolean failedInstance = false;
        
        // 5 tries
        for(int i=0; i < 5; i++){
        	
        	results = new ArrayList<Long>();
        	
        	// measure the page load time based on rendering each of the 5 IFrames and then report the maximum time
            for(int j=1; j <= 5; j++){
            	
            	Long loadTime = measurePageLoad(driver, j, baseUrl,	id);
            	
            	if(loadTime == 300000L){
            		failedInstance = true;
            		break;
            	}else{
            		results.add(loadTime);            		
            	}
            }
        	
            if(!failedInstance){
            	System.out.println("Total execution time (try " + i + "): " + Collections.max(results));
            }else{
            	failedInstance = false;
            }
        }
    }
    
    public static long measurePageLoad(WebDriver driver, int i, String url, String id){

    	driver.get(url);

        long startTime = System.nanoTime();

    	if(i==1){ driver.switchTo().frame(driver.findElements(By.cssSelector("h2#Co-author-graph + div > iframe")).get(0)); }
    	else if(i==2) { driver.switchTo().frame(driver.findElements(By.cssSelector("h2#Co-author-graph + div > iframe")).get(1)); }
    	else if(i==3) { driver.switchTo().frame(driver.findElement(By.cssSelector("h2#Page-production + div > iframe"))); }
    	else if(i==4) { driver.switchTo().frame(driver.findElements(By.cssSelector("h3 + div > iframe")).get(0)); }
    	else if(i==5) { driver.switchTo().frame(driver.findElements(By.cssSelector("h3 + div > iframe")).get(1)); }
                
        WebDriverWait wait = new WebDriverWait(driver, 300);
        
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#"+id)));
        }catch(ScriptTimeoutException ex){
        	return 300000L;
        }
       
        long elapsedTime = System.nanoTime() - startTime;

        return elapsedTime/1000000;
    }
}
