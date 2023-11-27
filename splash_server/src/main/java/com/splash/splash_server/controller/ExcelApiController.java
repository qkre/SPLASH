package com.splash.splash_server.controller;

import com.splash.splash_server.domain.excel.Excel;
import com.splash.splash_server.service.ExcelService;
import com.splash.splash_server.service.ScoreService;
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

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/excel")
public class ExcelApiController {


    private final ExcelService excelService;

    @GetMapping("/test")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("EXCEL");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> readExcel(@RequestParam("file") MultipartFile[] files) throws IOException {

        if (excelService.read(files)) {
            return ResponseEntity.ok("업로드 성공");

        } else {
            return ResponseEntity.badRequest().body("업로드 실패");
        }

    }
}
