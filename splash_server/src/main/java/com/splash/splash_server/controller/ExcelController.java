package com.splash.splash_server.controller;

import com.mysql.cj.log.Log;
import com.splash.splash_server.domain.excel.Excel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

    @GetMapping("/test")
    public ResponseEntity<String> main(){
        return ResponseEntity.ok("EXCEL");
    }

    @PostMapping("/read")
    public ResponseEntity<List<Excel>> readExcel(@RequestParam("file")MultipartFile file, Model model) throws IOException{

        List<Excel> dataList = new ArrayList<>();

        String extensions = FilenameUtils.getExtension(file.getOriginalFilename());

        if(!extensions.equals("xlsx") && !extensions.equals("xls")){
            throw new IOException("Not Excel type.");
        }

        Workbook workbook = null;

        if (extensions.equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extensions.equals("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet workSheet = workbook.getSheetAt(0);

        for (int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++) {
            Row row = workSheet.getRow(i);
            List<Integer> scores = new ArrayList<>();

            Excel data = new Excel();

            data.setName(row.getCell(1).getStringCellValue());
            scores.add((int) row.getCell(2).getNumericCellValue());
            scores.add((int) row.getCell(3).getNumericCellValue());
            scores.add((int) row.getCell(4).getNumericCellValue());
            data.setScores(scores);

            dataList.add(data);
        }

        model.addAttribute("datas", dataList);

        log.

        return ResponseEntity.ok(dataList);
    }
}
