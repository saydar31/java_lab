package ru.itis.documents.services;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;

@Component
public class ITextRendererFactory {
    public ITextRenderer getITextRenderer(){
        ITextRenderer renderer = new ITextRenderer();
        try {
            renderer.getFontResolver().addFont("/Verdana.ttf", BaseFont.IDENTITY_H,false);
        } catch (DocumentException | IOException e) {
            throw new IllegalStateException(e);
        }
        return renderer;
    }
}
