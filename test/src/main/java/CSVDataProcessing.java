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

public class CSVDataProcessing {

    //Concatenate Date and time
    public static String concatenateDateAndTime(String date, String time){
        return (date + " " + time);
    }

    //Read data in CSV File and saves its in ArrayList with CSVData objects.
    public static ArrayList<CSVData> readFile(String fileName){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH);
        ArrayList<CSVData> data = new ArrayList<CSVData>();
        String[] lineInArray;

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try(CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
                .withCSVParser(csvParser)   // custom CSV parser
                .build()){
            lineInArray = reader.readNext() ;
            boolean hasGoodNumberOfElement = lineInArray.length == 7;
            while (lineInArray != null && hasGoodNumberOfElement){
                String startDateTime = concatenateDateAndTime(lineInArray[0],lineInArray[1]);
                String endDateTime = concatenateDateAndTime(lineInArray[2],lineInArray[3]);
                String description = lineInArray[4];
                String recipient = lineInArray[5];
                String action = lineInArray[6];
                CSVData csvData = new CSVData(startDateTime,endDateTime,description,recipient,action,dateFormatter);
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
        }
        return data;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
        String fileNameCSV = "C:\\Users\\sophi\\Downloads\\VLS_TBOX_(Sophie) (1)\\VLS_TBOX_(Sophie)\\TBox01\\vlstbox01_alarmes_211016000000.csv";
        String fileNameExcel = "C:\\Users\\sophi\\Downloads\\VLS_TBOX_(Sophie) (1)\\test.xlsx";
        ArrayList<CSVData> data = readFile(fileNameCSV);
        XLSXDataProcessing.modifyExistingSheet(fileNameExcel,data,1);
    }
    
}
