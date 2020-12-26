package ru.itis;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.annotations.HtmlForm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class FormGenerator {
    private final Configuration configuration;

    public FormGenerator() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(HtmlForm.class, "/");
    }

    public void generate(Map<String, Object> model, String templateName, Path out){
        try {
            Template template = configuration.getTemplate(templateName);
            template.process(model,new BufferedWriter(new FileWriter(out.toFile())));
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException();
        }
    }
}
