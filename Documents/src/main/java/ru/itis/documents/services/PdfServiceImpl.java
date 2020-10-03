package ru.itis.documents.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;

@Component
public class PdfServiceImpl implements PdfService {

    @Qualifier("pdfFreemarkerConfigurationFactory")
    @Autowired
    private Configuration configuration;
    @Autowired
    private ITextRendererFactory iTextRendererFactory;

    @Value("${pdf.storage}")
    private String storage;

    @Override
    public void createPdf(Map<String, Object> data, String templateName, String fileName) {
        try {
            Template template = configuration.getTemplate(templateName+".ftl");
            StringWriter stringWriter = new StringWriter();
            template.process(data,stringWriter);
            stringWriter.flush();
            String html = stringWriter.toString();
            OutputStream outputStream = new FileOutputStream(storage+ File.separator+fileName);
            ITextRenderer iTextRenderer = iTextRendererFactory.getITextRenderer();
            iTextRenderer.setDocumentFromString(html);
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream,false);
            iTextRenderer.finishPDF();
            outputStream.flush();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
