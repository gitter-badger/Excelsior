package fr.vavelinkevin.excelsior.Documentation;

import java.io.*;
import org.apache.commons.io.FileUtils;


public class Documentation {

  private String beginDocumentation;
  private StringBuilder documentationTextFile;

  /**
   * Constructeur par defaut
   */
  public Documentation() {
    beginDocumentation = "<html charset=\"utf-8\">" +
        "<head>" +
        "<meta charset=\"utf-8\">" +
        "<link rel=\"stylesheet\" href=\"./css/bootstrap.min.css\">" +
        "<link rel=\"stylesheet\" href=\"./css/bootstrap-theme.min.css\">" +
        "<script src=\"./js/jquery-2.2.3.min.js\"></script>" +
        "<script src=\"./js/bootstrap.min.js\"></script>" +
        "<title>Report</title>" +
        "</head>" +
        "<body>";
    documentationTextFile = new StringBuilder();
    documentationTextFile.append(beginDocumentation);
  }

  /**
   * Creation du header avec le nom de notre test
   * @param testName Nom du test
   */
  public void createTest(String testName) {
    String testPanel = "<div class=\"jumbotron\">" +
        "<div class=\"container\">" +
        "<h1>" + testName + "</h1>" +
        "</div>" +
        "</div>";
    documentationTextFile.append(testPanel);
    documentationTextFile.append("<div class=\"container\">");
  }

  /**
   * Ajout d'un panneau correspondant à une étape du test
   * @param stepNumber Etape du test
   * @param commentaire Commentaire sur le test
   * @param function Fonction appele pour le test
   * @param identifiant Identifiant de l'element a tester
   * @param location Localisation de l'élement a tester
   * @param objet Objet a tester
   * @param valeur Valeur a assigne
   * @param result Resultat attendu
   */
  public void addTestStep(int stepNumber, String commentaire, String function, String identifiant, String location, String objet, String valeur, Boolean result) {
    if(identifiant.equals("PASSWORD")) {
      valeur = "******";
    }
    StringBuilder functionTest = new StringBuilder("<div class=\"panel panel-default\">" +
        "<div class=\"panel-heading\">" +
        "<h3> Test step : " + stepNumber + " - " + commentaire + "</h3>" +
        "</div>" +
        "<div class=\"panel-body\">" +
        "<h3>Paramètre d'entrée : </h3>" +
        "<table class=\"table table-bordered\">" +
        "<tr class=\"info\">" +
        "<th>Action</th>" +
        "<th>Id</th>" +
        "<th>Location</th>" +
        "<th>Objet</th>" +
        "<th>Valeur</th>" +
        "<th>Result</th>" +
        "</tr>" +
        "<tr>");
    functionTest.append("<th>" + function + "</th>" +
        "<th>" + identifiant + "</th>" +
        "<th>" + location + "</th>" +
        "<th>" + objet + "</th>" +
        "<th>" + valeur + "</th>");
    if(result){
      functionTest.append("<th><span class=\"label label-success\">Passed</span></th>");
    } else {
      functionTest.append("<th><span class=\"label label-danger\">Not passed</span></th>");
    }
    functionTest.append("</tr></table>");
    documentationTextFile.append(functionTest);
  }

  /**
   * Ajout du resultat en dessous du test
   * @param request Parametre qui est envoye en entrée de notre test
   * @param result Resultat obtenu apres notre test
   */
  public void addTestResult(String request, String result) {
    StringBuilder testExpectation = new StringBuilder("<h4>Test envoyé : </h4>" +
        "<p>" + request + "</p>" +
        "<h4>Retour du test : </h4>" +
        "<p>" + result + "</p>" +
        "</div>" +
        "</div>");
    documentationTextFile.append(testExpectation);
  }

  /**
   * Sauvegarde de notre report dans un fichier .html
   * @throws IOException Exception levée si le fichier n'est pas accessible, n'existe pas ou ne peut pas être crée
   */
  public void writeTestReport(String project, String nameProject) throws IOException {
    documentationTextFile.append("</div></body></html>");
    String nameFile = "C:\\Users\\Public\\tmp\\"+ project +"\\" + nameProject + ".html";
    copyCssAndJsFiles(project);
    File documentationFile = new File(nameFile);
    if(!documentationFile.exists()) {
      try {
        File folderDocumentationFile = new File("C:\\Users\\Public\\tmp\\" + project);
        folderDocumentationFile.mkdir();
        documentationFile.createNewFile();
      } catch(IOException exception) {
        exception.printStackTrace();
      }
    }
    OutputStream outputStream = new FileOutputStream(documentationFile.getAbsoluteFile());
    Writer writer = new OutputStreamWriter(outputStream);
    writer.write(documentationTextFile.toString());
    writer.close();

  }

  private void copyCssAndJsFiles(String project) {
    File cssSourceToCopy = new File("D:\\Users\\kvavelin\\Documents\\Projets\\DITExselPlus\\src\\main\\resources\\css");
    File cssDestinationFolder = new File("C:\\Users\\Public\\tmp\\"+ project +"\\css");
    if(!cssDestinationFolder.exists()) {
      cssDestinationFolder.mkdir();
    }
    File jsSourceToCopy = new File("D:\\Users\\kvavelin\\Documents\\Projets\\DITExselPlus\\src\\main\\resources\\js");
    File jsDestinationFolder = new File("C:\\Users\\Public\\tmp\\"+ project +"\\js");
    if(!jsDestinationFolder.exists()) {
      jsDestinationFolder.mkdir();
    }
    File fontsSourceToCopy = new File("D:\\Users\\kvavelin\\Documents\\Projets\\DITExselPlus\\src\\main\\resources\\fonts");
    File fontsDestinationFolder = new File("C:\\Users\\Public\\tmp\\"+ project +"\\fonts");
    if(!fontsDestinationFolder.exists()) {
      fontsDestinationFolder.mkdir();
    }
    try {
      FileUtils.copyDirectory(cssSourceToCopy, cssDestinationFolder);
      FileUtils.copyDirectory(jsSourceToCopy, jsDestinationFolder);
      FileUtils.copyDirectory(fontsSourceToCopy, fontsDestinationFolder);
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
