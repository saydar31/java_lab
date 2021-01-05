package ru.itis.processors;

import com.google.auto.service.AutoService;
import ru.itis.FormGenerator;
import ru.itis.annotations.FormInput;
import ru.itis.annotations.HtmlForm;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"ru.itis.annotations.HtmlForm", "ru.itis.annotations.FormInput"})
public class HtmlProcessor extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "failed 27 ");

        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            HtmlForm form = element.getAnnotation(HtmlForm.class);
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            Map<String, Object> model = new HashMap<>();
            model.put("method", form.method());
            model.put("action", form.action());
            List<Map<String, Object>> inputs = new ArrayList<>();
            List<Element> inputFields = element.getEnclosedElements().stream()
                    .filter(el -> el.getKind().isField())
                    .collect(Collectors.toList());
            inputFields.stream()
                    .map(el -> el.getAnnotation(FormInput.class))
                    .forEach(formInput -> {
                        Map<String, Object> input = new HashMap<>();
                        input.put("type", formInput.type());
                        input.put("placeholder", formInput.placeholder());
                        input.put("name", formInput.name());
                        inputs.add(input);
                    });
            model.put("inputs", inputs);
            FormGenerator formGenerator = new FormGenerator();
            formGenerator.generate(model, "form.ftl", out);
        }
        return true;
    }
}
