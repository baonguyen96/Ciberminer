import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLog {
    private String reportFileName;
    private Workbook workbook;
    private Sheet sheet;
    enum TestResult {PASS, FAIL}


    public TestLog(String browser) throws Exception {
        createWorkbook(browser);
        sheet = workbook.getSheetAt(0);
    }


    private void createWorkbook(String browser) throws Exception {
        final String REPORT_FOLDER_PATH = "src/test/report/";
        cleanUpFolder(REPORT_FOLDER_PATH);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        reportFileName = REPORT_FOLDER_PATH + browser + " at " + dateFormat.format(date) + ".xlsx";
        File template = new File(REPORT_FOLDER_PATH + "TestLog.xlsx");
        workbook = WorkbookFactory.create(OPCPackage.openOrCreate(template));
    }


    private void cleanUpFolder(String folderPath) {
        File reportFolder = new File(folderPath.substring(0, folderPath.length() - 1));
        File[] oldReports = reportFolder.listFiles();
        if(oldReports != null && oldReports.length > 10) {
            for(File oldReport: oldReports) {
                if (!oldReport.getName().contains("TestLog")) {
                    oldReport.delete();
                }
            }
        }
    }


    public void save() throws IOException {
        FileOutputStream report = new FileOutputStream(reportFileName);
        workbook.write(report);
        report.close();
    }


    public void pass(int testCase) {
        setCurrentTestCaseResult(testCase, TestResult.PASS, null);
    }


    public void fail(int testCase, String message) {
        setCurrentTestCaseResult(testCase, TestResult.FAIL, message);
    }

    private void setCurrentTestCaseResult(int testCase, TestResult result, String failMessage) {
        Row row = sheet.getRow(testCase);
        row.getCell(3).setCellValue(result.name());

        if(result == TestResult.FAIL) {
            row.getCell(4).setCellValue(failMessage);
        }
    }


}
