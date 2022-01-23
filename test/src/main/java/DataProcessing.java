import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataProcessing {

    public static ArrayList<CSVData> readFile(String fileName){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH);
        ArrayList<CSVData> data = new ArrayList<CSVData>();
        String[] lineInArray;

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try(CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()){
            lineInArray = reader.readNext() ;
            while (lineInArray != null && lineInArray.length == 7){
                CSVData csvData = new CSVData(lineInArray[0]+" " +lineInArray[1],lineInArray[2]+" "+lineInArray[3],lineInArray[4],lineInArray[5],lineInArray[6],dateFormatter);
                data.add(csvData);
                lineInArray = reader.readNext();
            }
            return data;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static Integer findLastRow(Sheet sheet, CSVData data) throws ParseException {
        Integer goodRow = 1;
        Integer numberRow = sheet.getLastRowNum() ;
        Date startDateEntryData = data.getStartDateTime() ;
        Date endDateEntryData = data.getEndDateTime();
        String descriptionEntryData = data.getDescription();
        String recipientEntryData = data.getRecipient();
        String actionEntryData = data.getAction().getAction();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH);

        while (goodRow <= numberRow){
            Date startDateRow = HSSFDateUtil.getJavaDate(sheet.getRow(goodRow).getCell(0).getNumericCellValue());
            Date endDateRow = HSSFDateUtil.getJavaDate(sheet.getRow(goodRow).getCell(1).getNumericCellValue());
            String descriptionRow = sheet.getRow(goodRow).getCell(2).getStringCellValue();
            String recipientRow = sheet.getRow(goodRow).getCell(3).getStringCellValue();
            String actionRow = sheet.getRow(goodRow).getCell(4).getStringCellValue();
            if (startDateRow.compareTo(startDateEntryData)==0){
                if (endDateRow.compareTo(endDateEntryData)==0){
                    if (descriptionRow.compareTo(descriptionEntryData)==0){
                        if (recipientRow.compareTo(recipientEntryData)==0){
                            System.out.println(actionRow+"a"+actionEntryData+"a");
                            System.out.println(actionRow.compareTo(actionEntryData));
                            if (actionRow.compareTo(actionEntryData)==0){
                                return  goodRow;
                            }
                        }
                    }
                }
            }
            goodRow++;
        }
        return goodRow;

    }
    private static void addLine(CSVData data, Row row,CellStyle cellStyle){

        for (int i=0;i<5;i++) {
            Cell cell = row.createCell(i);
            switch (i) {
                case 0:
                    cell.setCellValue(data.getStartDateTime());
                    cell.setCellStyle(cellStyle);
                    break ;
                case 1:
                    cell.setCellValue(data.getEndDateTime());
                    cell.setCellStyle(cellStyle);
                    break ;
                case 2:
                    cell.setCellValue(data.getDescription());
                    break ;
                case 3:
                    cell.setCellValue(data.getRecipient());
                    break ;
                case 4:
                    cell.setCellValue(data.getAction().getAction());
                    break ;
                default:
                    System.out.println("All information are not saved");
            }
        }
    }
    private static void modifyExistingSheet(String fileName, ArrayList<CSVData> data,Integer sheetNumber) throws InvalidFormatException, IOException, ParseException {
        FileInputStream inputStream = new FileInputStream(fileName);
        Workbook workbook = WorkbookFactory.create(inputStream);
        // Get Sheet at index 0
        Sheet sheet = workbook.getSheetAt(sheetNumber-1);
        Integer numberRow = findLastRow(sheet,data.get(0));
        //Date cell style
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm:ss"));

        for (Integer i=0;i<data.size();i++){
            Row row = sheet.createRow(numberRow+1);
            CSVData dataLine = data.get(i);
            addLine(dataLine,row,cellStyle);
            numberRow += 1;
        }


        inputStream.close();

        // Write the output to the file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

    public static void modifyWorkbook(String xlsxfileName){

    }

    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
        String fileNameCSV = "C:\\Users\\sophi\\Downloads\\VLS_TBOX_(Sophie) (1)\\VLS_TBOX_(Sophie)\\TBox01\\vlstbox01_alarmes_211016000000.csv";
        String fileNameExcel = "C:\\Users\\sophi\\Downloads\\VLS_TBOX_(Sophie) (1)\\test.xlsx";
        ArrayList<CSVData> data = readFile(fileNameCSV);
        modifyExistingSheet(fileNameExcel,data,1);
    }
    
}
