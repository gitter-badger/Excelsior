package fr.vavelinkevin.excelsior.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.lang.String;
import java.util.List;

import fr.vavelinkevin.excelsior.Documentation.Documentation;
import org.apache.commons.io.FileExistsException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import fr.vavelinkevin.excelsior.Actions.Actions;


public class Excel {
  private Workbook workbook;
  private String name;
  private String project;
  private Sheet testSheet;
  private int numberOfSheet;
  private List<String> columnName = new ArrayList<String>();
  private HashMap<String, String> testSheetProcess = new HashMap<String, String>();

  private Actions actions = new Actions();
  private Documentation documentation = new Documentation();

  //TODO : Add Logger

  public Excel(String nameTest, String projectTest) {
    name = nameTest;
    project = projectTest;
  }

  public Excel() {}

  /**
   * La fonction readFile permet de lire le fichier Excel dans son intégralité.
   * @param filePath Chemin vers le fichier Excel
   * @throws IOException Exception levee si le fichier n'est pas accessible
   */

  public void readFile(String filePath) throws IOException {
    documentation.createTest(name);
    File testFile = new File(filePath);
    name = testFile.getName().split("\\.")[0];
//    project = testFile.getParent();
    try {
      FileInputStream inputStream = new FileInputStream(testFile);
      workbook = new XSSFWorkbook(inputStream);
      numberOfSheet = getNumberOfSheets(workbook);
      if(isSheetActionable(workbook))
      {
        Iterator<Row> iterator = testSheet.iterator();
        iterator.next();
        getColumnName(workbook, 0);
        while (iterator.hasNext()) {
          readTest(iterator);
          System.out.println();
        }
        documentation.writeTestReport(project, name);
        workbook.close();
        inputStream.close();
      } else {
        System.out.println("Le test ne peut pas etre lance car la feuille n'est pas une 'Action1'.");
      }
    } catch(FileExistsException exception) {
      System.out.println("Le fichier " + filePath + " n'a pas ete trouve.");
    }
  }

  /**
   * <h3>Get number of sheets</h3>
   * <p>La fonction getNumberOfSheets permet de récupéré le nombre de feuille dans un fichier Excel.</p>
   * @param workbook Fichier excel utilise
   * @return Retourne le nombre de feuille présente dans le fichier
   */

  private int getNumberOfSheets(Workbook workbook) {
    return workbook.getNumberOfSheets();
  }

  private boolean isSheetActionable(Workbook workbook) {
    Sheet sheet = workbook.getSheetAt(0);
    String nameSheet = sheet.getSheetName();
    if(nameSheet.equals("Action1"))
    {
      testSheet = sheet;
      return true;
    }
    return false;
  }

  /**
   * getColumnName permet de recuperer le libelle des premieres ligne afin de definir un tableau de definition de nos proprietes
   * @param workbook Fichier excel utilise
   * @param sheetNumber Index de la feuille excel utilise
   */
  private void getColumnName(Workbook workbook, int sheetNumber) {
    Sheet sheet = workbook.getSheetAt(sheetNumber);
    Row row = sheet.getRow(sheet.getFirstRowNum());
    Iterator<Cell> cellIterator = row.iterator();
    while(cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      columnName.add(cell.getStringCellValue());
      testSheetProcess.put(cell.getStringCellValue(), "");
    }
  }

  private void readTest(Iterator<Row> currentRow) {
    Row nextRow = currentRow.next();
    Iterator<Cell> cellIterator = nextRow.cellIterator();
    getColumnName(workbook, 0);
    while(cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      Integer indexColumn = cell.getColumnIndex();
      String key = getKeyAtIndex(indexColumn);
      switch(cell.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
          modifyKey(key, "");
          break;
        case Cell.CELL_TYPE_BOOLEAN:
          modifyKey(key, "True");
          break;
        case Cell.CELL_TYPE_NUMERIC:
          modifyKey(key, String.valueOf(cell.getNumericCellValue()));
          break;
        case Cell.CELL_TYPE_STRING:
          modifyKey(key, cell.getStringCellValue());
          break;
      }
    }
    readAction();
    documentation.addTestStep(nextRow.getRowNum(),
        testSheetProcess.get("commentaire"),
        testSheetProcess.get("Type_Action"),
        testSheetProcess.get("id"),
        testSheetProcess.get("location"),
        testSheetProcess.get("objet"),
        testSheetProcess.get("valeur"),
        actions.testIsOk);
    documentation.addTestResult(actions.request, actions.result);
  }

  private String getKeyAtIndex(int index) {
    return columnName.get(index);
  }

  private void modifyKey(String key, String value) {
    testSheetProcess.put(key, value);
  }

  private void readAction() {
    String action = testSheetProcess.get("Type_Action");
    System.out.println(Actions.DITExSelPlusActions.valueOf(action) + " is running");
    actions.sendAction(Actions.DITExSelPlusActions.valueOf(action),testSheetProcess);
    if(actions.testIsOk) {
      System.out.println("Passed");
    } else {
      System.out.println("Failed");
    }
  }
}
