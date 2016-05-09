package fr.vavelinkevin.excelsior;

import fr.vavelinkevin.excelsior.Excel.Excel;
import java.io.IOException;
import java.util.HashMap;

public class Excelsior {

  private static HashMap<String, String> arguments = new HashMap<String,String>();

  public static void main(String[] args) throws IOException {
    parseArguments(args);
    System.out.println(arguments);
    Excel excel = new Excel(arguments.get("nameTest"), arguments.get("project"));
    String projectPath = System.getProperty("excelFilePath");
    System.out.println(projectPath);
    excel.readFile(projectPath);
  }

  private static void parseArguments(String[] argument) {
    System.out.println(argument);
    for (String arg : argument) {
      System.out.println(arg);
      String[] args = arg.split("=");
      String key = args[0].substring(2);
      System.out.println(key);
      String value = args[1];
      arguments.put(key, value);
    }
    if(!arguments.containsKey("project")) {
      System.out.println("La variable 'project' n'est pas renseignée, veuillez entrer un nom de projet.");
    } else {
      System.setProperty("project", arguments.get("project"));
    }
    if(!arguments.containsKey("nameTest")) {
      System.out.println("La variable 'nameTest' n'est pas renseignée, veuillez entrer le nom de votre test.");
    } else {
      System.setProperty("nameTest", arguments.get("nameTest"));
    }
    if(!arguments.containsKey("excelFilePath")) {
      System.out.println("Aucun fichier excel renseigné, veuillez entrer le chemin de votre fichier excel.");
    } else {
      System.setProperty("excelFilePath", arguments.get("excelFilePath"));
    }
  }

}
