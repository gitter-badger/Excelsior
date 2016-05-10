package fr.vavelinkevin.excelsior.Actions;

import fr.vavelinkevin.excelsior.Selenium.Selenium;
import fr.vavelinkevin.excelsior.Webservices.SOAPServices;
import java.util.HashMap;


public class Actions {

  Selenium selenium = new Selenium();
  SOAPServices soapServices = new SOAPServices();
  public Boolean testIsOk = false;

  public String request;
  public String result;

  /**
   * Liste des actions possible dans DITExSelPlus
   */
  public enum DITExSelPlusActions {
    // Selenium Fonction
    OpenFirefoxSession,
    OpenChromeSession,
    OpenIESession,
    Set,
    Click,
    ClickByXPath,
    SubmitByXPath,
    SendTab,
    WaitForId,
    WaitForXPath,
    WaitForXPathDisappear,
    WaitForXPathHaveClass,
    CloseSession,
    ModifyCookie,
    DeleteCookie,
    DeleteAllCookies,
    TakeScreenshot,
    // SOAP Fonction
    SetSOAPParameter,
    SendSOAP
  }

  /**
   * Envoi l'action au module adéquate
   * @param action Action a éxécuté
   * @param testStep Step de notre test
   */
  public void sendAction(DITExSelPlusActions action, HashMap<String, String> testStep) {

    if(testStep.get("Actif").equals("O") && (!testStep.get("Bloqué").equals(0) || testStep.get("Bloqué_Dev").equals("O")))
    {
      testIsOk = true;
      switch(action) {
        case OpenFirefoxSession:
          try {
            selenium.OpenFirefoxSession(testStep.get("valeur"));
            request = "Ouverture de la session Firefox à l'url [" + testStep.get("valeur") + "]";
            result = "Firefox ouvert";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case OpenChromeSession:
          try {
            selenium.OpenChromeSession(testStep.get("valeur"));
            request = "Ouverture de la session Google Chrome à l'url [" + testStep.get("valeur") + "]";
            result = "Google Chrome ouvert";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case OpenIESession:
          try {
            selenium.OpenIESession(testStep.get("valeur"));
            request = "Ouverture de la session Internet Explorer à l'url [" + testStep.get("valeur") + "]";
            result = "Internet Explorer ouvert";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case Set:
          try {
            selenium.Set(testStep.get("id"), testStep.get("valeur"));
            request = "Ajout du texte pour l'id [" + testStep.get("id") + "]";
            result = "";
          } catch(Exception exception) {
            System.out.println("Eerreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case Click:
          try {
            request = "Clique sur l'id [" + testStep.get("id") + "]";
            selenium.Click(testStep.get("id"));
            result = "";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case ClickByXPath:
          try {
            request = "Clique sur l'element avec XPath [" + testStep.get("id") + "]";
            selenium.ClickByXPath(testStep.get("id"));
            result = "Clique sur l'element réussi";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case SubmitByXPath:
          try {
            request = "Submit du formulaire par le bouton avec XPath [" + testStep.get("id") + "]";
            selenium.SubmitByXPath(testStep.get("id"));
            result = "Formulaire envoyé";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case SendTab:
          try {
            request = "Appuie sur la touche tabulation pour l'autocompletion";
            selenium.SendTab(testStep.get("id"));
            result = "Autocompletion termine";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case WaitForId:
          try {
            request = "Attente de l'element [" + testStep.get("id")+ "]";
            selenium.WaitForId(testStep.get("id"));
            result = "L'element est visible";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case WaitForXPath:
          try {
            request = "Attente de l'element par son XPath [" + testStep.get("id") +"]";
            selenium.WaitForXPath(testStep.get("id"));
            result = "L'element est visible";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case WaitForXPathDisappear:
          try {
            request = "Attente de l'element [" + testStep.get("id") + "]";
            selenium.WaitForXPathDisappear(testStep.get("id"));
            result = "L'element a disparu";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case WaitForXPathHaveClass:
          try {
            request = "Atttente de l'element [" + testStep.get("id") + "] possede la classe [" + testStep.get("MSW_class") + "]";
            selenium.WaitForXPathHaveClass(testStep.get("id"), testStep.get("objet"), testStep.get("MSW_class"));
          } catch (Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case CloseSession:
          try {
            selenium.CloseSession();
            request = "Fermeture de la session Firefox";
            result = "Firefox fermé";
          } catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;
        case ModifyCookie:
          try {
            selenium.ModifyCookie(testStep.get("objet"), testStep.get("valeur"));
            result = "Cookie [" + testStep.get("objet") + "] modifie";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case DeleteCookie:
          try {
            selenium.DeleteCookie(testStep.get("objet"));
            result = "Cookie [" + testStep.get("objet") + "] supprime";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case DeleteAllCookies:
          try {
            selenium.DeleteAllCookies();
            result = "Tous les cookies ont été supprimé";
          } catch(Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case TakeScreenshot:
          try {
            request = "Screenshot";
            selenium.TakeScreenshot(System.getProperty("project"), System.getProperty("nameTest"));
            result = "Voir screenshot dans le dossier";
          } catch (Exception exception) {
            System.out.println(exception.getMessage());
            result = exception.getMessage();
          }
          break;
        case SetSOAPParameter:
          soapServices.createParameter(testStep.get("objet"), testStep.get("location"), testStep.get("valeur"));
          request = "Création du de la variable [" + testStep.get("objet") +"] avec pour valeur [" + testStep.get("valeur") + "] pour le parametre [" + testStep.get("location") + "]";
          result = "";
          break;
        case SendSOAP:
          try {
            soapServices.sendSoap(testStep.get("location"), testStep.get("objet"));
            request = soapServices.request;
            result = soapServices.result;
          }catch(Exception exception) {
            System.out.println("Erreur : " + exception.getMessage());
            testIsOk = false;
            result = exception.getMessage();
          }
          break;

      }
    } else {
      System.out.println("Test " + action + " est en etat bloque. Le test ne sera donc pas lance.");
    }
  }
}
