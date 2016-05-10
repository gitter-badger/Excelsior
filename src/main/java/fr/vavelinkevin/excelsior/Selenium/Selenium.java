package fr.vavelinkevin.excelsior.Selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Selenium {

  private enum Browser {
    Firefox,
    InternetExplorer,
    Chrome
  }

  private Browser browser;

  public WebDriver firefoxDriver;
  public WebDriver ieDriver;
  public WebDriver chromeDriver;

  public Browser browserUse;

  /**
   * Ouvre une session dans firefox
   * @param url URL de la page web a ouvrir
   * @throws Exception Exception retourner si Firefox n'a pas pu être ouvert
   */
  public void OpenFirefoxSession(String url) throws Exception {
    firefoxDriver = new FirefoxDriver();
    firefoxDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    firefoxDriver.get(url);
    firefoxDriver.manage().window().maximize();
    browserUse = Browser.Firefox;
  }

  public void OpenChromeSession(String url) throws Exception {
    chromeDriver = new ChromeDriver();
    chromeDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    chromeDriver.get(url);
    chromeDriver.manage().window().maximize();
    browserUse = Browser.Chrome;
  }

  public void OpenIESession(String url) throws Exception {
    ieDriver = new InternetExplorerDriver();
    ieDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    ieDriver.get(url);
    ieDriver.manage().window().maximize();
    browserUse = Browser.InternetExplorer;
  }

  private WebDriver driverToUse() {
    WebDriver driver = null;
    switch(browserUse) {
      case Firefox:
        driver = firefoxDriver;
        break;
      case Chrome:
        driver = chromeDriver;
        break;
      case InternetExplorer:
        driver = ieDriver;
        break;
    }
    return driver;
  }

  /**
   * Permet d'assigner une valeur à un input selon son id
   * @param id ID de l'element input HTML
   * @param value Valeur a entrer dans l'input
   * @throws Exception Retourne une erreur si l'id n'a pas été trouvé dans la page
   */
  public void Set(String id, String value) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.id(id));
    element.sendKeys(value);
  }

  /**
   * Envoi un signal à Selenium pour simuler l'appuie sur la touche tabulation
   * @param id ID de l'élément qui doit recevoir la tabulation
   * @throws Exception Retourne une erreur si l'id n'a pas été trouvé dans la page
   */
  public void SendTab(String id) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.id(id));
    element.sendKeys(Keys.TAB);
  }

  /**
   * Attent que l'id soit visible dans la page
   * @param id ID de l'element HTML à attendre
   * @throws Exception Retourne une erreur si l'ID n'est pas trouvé au bout de 5s
   */
  public void WaitForId(String id) throws Exception {
    WebDriver driver = driverToUse();
    WebDriverWait waiter = new WebDriverWait(driver, 60);
    waiter.until(ExpectedConditions.elementToBeClickable(By.id(id)));
  }

  /**
   * Test si l'élément avec l'XPath contient bien la class désiré
   * @param XPath XPath de l'élément
   * @param objet Attribut class
   * @param msw_class Nom de la classe que l'on souhaite obtenir
   * @throws Exception Retourne une erreur si l'element n'est pas trouvé dans la page, ou si la classe n'existe pas
   */
  public void WaitForXPathHaveClass(String XPath, String objet, String msw_class) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.xpath(XPath));
    WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
    webDriverWait.until(ExpectedConditions.attributeToBe(element, objet, msw_class));
  }

  /**
   * Attente d'un élément en utilisant son XPath
   * @param XPath Chemin XPath de l'élément HTML
   * @throws Exception Retourne une erreur si l'XPath n'est pas trouvé au bout de 5s
   */
  public void WaitForXPath(String XPath) throws Exception {
    WebDriver driver = driverToUse();
    WebDriverWait waiter = new WebDriverWait(driver, 60);
    waiter.until(ExpectedConditions.elementToBeClickable(By.xpath(XPath)));
  }

  /**
   * Test si l'élément devient invisble
   * @param XPath XPath de l'element
   * @throws Exception Retourne une exception si l'élement n'est pas trouvé
   */
  public void WaitForXPathDisappear(String XPath) throws Exception {
    WebDriver driver = driverToUse();
    WebDriverWait waiter = new WebDriverWait(driver, 60);
    waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPath)));
  }

  /**
   * Clique sur un élément en utilisant son ID
   * @param id ID de l'élément HTML à cliquer
   * @throws Exception Retourne une erreur si l'élément n'est pas cliqué ou l'ID n'est pas trouvé
   */
  public void Click(String id) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.id(id));
    element.click();
  }

  /**
   * Clique sur un élément en utilisant son XPath
   * @param XPath XPath de l'élément à cliquer
   * @throws Exception Retourne une erreur si l'élément n'est pas cliquable ou si l'XPath n'est pas trouvé
   */
  public void ClickByXPath(String XPath) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.xpath(XPath));
    element.click();
  }

  /**
   * Envoie un event submit en utilisant l'XPath de l'élément
   * @param XPath XPath de l'élément qui doit envoyer l'event submit
   * @throws Exception Retourne une erreur si l'XPath n'est pas trouvé ou si le submit ne peut pas être envoyé
   */
  public void SubmitByXPath(String XPath) throws Exception {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.xpath(XPath));
    element.submit();
  }

  /**
   * Ferme la session actuelle du WebDriver Selenium
   * @throws Exception Retourne une erreur si la session n'a pas pu être fermé
   */
  public void CloseSession() throws Exception {
    WebDriver driver = driverToUse();
    driver.close();
  }

  /**
   * Modifie un cookie sur le site actuel
   * @param cookieKey Identifiant du cookie
   * @param cookieValue Nouvelle valeur du cookie
   * @throws Exception Retourne une erreur si le cookie n'a pas été trouvée ou si la valeur n'a pas pu être modifié
   */
  public void ModifyCookie(String cookieKey, String cookieValue) throws Exception {
    WebDriver driver = driverToUse();
    Set<Cookie> allCookies = driver.manage().getCookies();
    for (Cookie loadedCookie : allCookies) {
      if(loadedCookie.getName() == cookieKey) {
        driver.manage().deleteCookie(loadedCookie);
        Cookie cookie = new Cookie(cookieKey, cookieValue);
        driver.manage().addCookie(cookie);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      }
    }
  }

  /**
   * Supprime un cookie sur le site actuel
   * @param cookieKey Identifiant du cookie
   * @throws Exception Retourne une erreur si le cookie n'a pas été trouvée
   */
  public void DeleteCookie(String cookieKey) throws Exception {
    WebDriver driver = driverToUse();
    Set<Cookie> allCookies = driver.manage().getCookies();
    for (Cookie loadedCookie : allCookies) {
      if(loadedCookie.getName() == cookieKey) {
        driver.manage().deleteCookie(loadedCookie);
      }
    }
  }

  /**
   * Supprime tous les cookies sur le site actuel
   * @throws Exception Retourne une exception si aucun cookie ne peut être supprimé ou si il en existe aucun
   */
  public void DeleteAllCookies() throws Exception {
    WebDriver driver = driverToUse();
    driver.manage().deleteAllCookies();
  }

  public void TakeScreenshot(String project, String name) throws Exception {
    WebDriver driver = driverToUse();
    File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    int i = 0;
    boolean fileWrite = false;
    String nameFile = "C:\\Users\\Public\\tmp\\"+ project +"\\" + name + ".png";
    File screenshotFile = new File(nameFile);
    do {
      if(!screenshotFile.exists()) {
        try {
          FileUtils.copyFile(sourceFile, new File(nameFile));
          fileWrite = true;
        } catch(IOException exception) {
          exception.printStackTrace();
        }
      } else {
        i++;
        nameFile = "C:\\Users\\Public\\tmp\\"+ project +"\\" + name + i +".png";
        screenshotFile = new File(nameFile);
        if(!screenshotFile.exists()) {
          try {
            FileUtils.copyFile(sourceFile, new File(nameFile));
            fileWrite = true;
          } catch(IOException exception) {
            exception.printStackTrace();
          }
        }
      }
    } while(!fileWrite);
  }

  public boolean CheckCSSValue(String id, String location, String value) {
    WebDriver driver = driverToUse();
    WebElement element = driver.findElement(By.id(id));
    String cssValue = element.getCssValue(location);
    if(cssValue == value) {
      return true;
    } else {
      return false;
    }
  }
}
