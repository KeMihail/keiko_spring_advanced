package com.epam.keikom.web.viewresolver.user;

import com.epam.keikom.web.dto.UserDTO;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class UserPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        final ArrayList<UserDTO> users = (ArrayList<UserDTO>) model.get("users");

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(Color.lightGray);

        table.addCell("firstName");
        table.addCell("lastName");
        table.addCell("email");
        table.addCell("birthday");

        for (final UserDTO dto : users) {
            table.addCell(dto.getLastName());
            table.addCell(dto.getFirstName());
            table.addCell(dto.getEmail());
            table.addCell(dto.getBirthday().toString());
        }

        document.add(table);
    }

}
