package com.splash.splash_server.service;

import com.splash.splash_server.domain.excel.Excel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class ExcelService {
    private final UserService userService;
    private final ScoreService scoreService;

    public static int extractWeekNumbers(String fileName) {
        String replaced = fileName.replaceAll("[^0-9]", "");
        return Integer.parseInt(replaced);

    }

    public boolean read(MultipartFile[] files) throws IOException {


        for (MultipartFile file : files) {
            List<Excel> dataList = new ArrayList<>();

            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());

            String extensions = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!extensions.equals("xlsx") && !extensions.equals("xls")) {
                throw new IOException("Not Excel type.");
            }

            Workbook workbook = null;

            if (extensions.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extensions.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else return false;

            Sheet workSheet = workbook.getSheetAt(0);


            for (int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++) {
                Row row = workSheet.getRow(i);

                if (row == null){
                    System.out.println("i = " + i);
                }

                if (row.getCell(1).getStringCellValue().isBlank()) {
                    continue;
                }
                String userName = row.getCell(1).getStringCellValue();
                int firstScore = (int) row.getCell(2).getNumericCellValue();
                int secondScore = (int) row.getCell(3).getNumericCellValue();
                int thirdScore = (int) row.getCell(4).getNumericCellValue();
                int dayTotalScore = firstScore + secondScore + thirdScore;
                String semester = file.getOriginalFilename().split(" ")[0] + " " + file.getOriginalFilename().split(" ")[1];
                int week = extractWeekNumbers(file.getOriginalFilename().split(" ")[2]);
                String gender = row.getCell(10).getStringCellValue();


                if (dayTotalScore == 0) {
                    continue;
                }

                Excel data = new Excel();

                data.setName(row.getCell(1).getStringCellValue());
                data.setFirstScore(firstScore);
                data.setSecondScore(secondScore);
                data.setThirdScore(thirdScore);
                data.setDayTotalScore(dayTotalScore);
                data.setSemester(semester);
                data.setWeek(week);
                data.setGender(gender);

                dataList.add(data);
            }

            userService.saveExcelData(dataList);
            scoreService.saveExcelData(dataList);
        }
        return true;
    }

}
