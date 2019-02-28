package com.example.ermolaenkoalex.admodule_compiler;
 
import com.example.ermolaenkoalex.admodule_annotations.AddAdvertisement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

// Partially taken from https://habr.com/ru/company/e-legion/blog/206208/
public class Processor extends AbstractProcessor {
    private final Map<TypeElement, Visitor> visitors = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        for (TypeElement typeElement : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(AddAdvertisement.class))) {
            Visitor visitor = visitors.get(typeElement);
            if (visitor == null) {
                visitor = new Visitor(processingEnv, typeElement);
                visitors.put(typeElement, visitor);
            }
        }

        for (final Visitor visitor : visitors.values()) {
            visitor.brewJava();
        }

        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(AddAdvertisement.class.getCanonicalName());
        }};
    }
}
