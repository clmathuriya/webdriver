

# webdriver
find webdriver related examples here.


### Selenium Grid ###
1. Setup
   download selenium server from here (http://docs.seleniumhq.org/download/)
2. Starting selenium grid hub
   
   ``` java -jar selenium-server-standalone.2.53.1.jar -role hub ```
3. Register node

```java -jar selenium-server-standalone.2.53.1.jar -role node -hub http://192.168.2.125:4444/grid/register -Dwebdriver.chrome.driver=/path/to/chromedriver ```

4. Verify registered nodes by visiting http://localhost:4444/grid/console on hub machine


###Selenium Code###
```
DesiredCapabilities capabilities = new DesiredCapabilities(); capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome"); 
WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // to navigate to google
driver.navigate().to("https://www.google.co.in");
 
```

###Running headless browsers ###

- to run headless browsers we need xvfb frame buffer.
install xvfb package
sudo apt-get install xvfb

- register node with xvfb-run

```xvfb-run java -jar selenium-server-standalone.2.53.1.jar -role node -hub http://192.168.2.125:4444/grid/register -Dwebdriver.chrome.driver=/path/to/chromedriver ```

### Upload a file ###

- find an input tag with attribute type = 'file'
  "//input[@type='file']"
- if element is not visible make it visible by executing javascript

```
((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
				driver.findElement(By.id("uploadBtn")));
				
// call send keys on element with absolute path of file

driver.findElement(By.id("uploadBtn")).sendKeys("/absolute/path/to/file.png");

		```







 

