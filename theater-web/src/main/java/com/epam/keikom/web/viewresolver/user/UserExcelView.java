package com.epam.keikom.web.viewresolver.user;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.keikom.web.dto.UserDTO;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class UserExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        final ArrayList<UserDTO> users = (ArrayList<UserDTO>) model.get("users");

        Sheet sheet = workbook.createSheet("sheet 1");
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        Row row = null;
        Cell cell = null;

        int rowCount = 0;
        int colCount = 0;

        // Create header cells
        row = sheet.createRow(rowCount++);

        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("firstName");

        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("lastName");

        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("email");

        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("birthday");

        Date date = new Date();

        for (final UserDTO dto : users) {

            date = Date.from(dto.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant());

            row = sheet.createRow(rowCount++);
            colCount = 0;
            row.createCell(colCount++).setCellValue(dto.getLastName());
            row.createCell(colCount++).setCellValue(dto.getFirstName());
            row.createCell(colCount++).setCellValue(dto.getEmail());
            row.createCell(colCount++).setCellValue(date);
        }

        for (int i = 0; i < 4; i++)
            sheet.autoSizeColumn(i, true);
    }

}
