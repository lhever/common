package org.springframework.context.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public abstract class CustomImportSelector<A extends Annotation> implements ImportSelector, EnvironmentAware {
    Logger log = LoggerFactory.getLogger(CustomImportSelector.class);


    @Override
    public final String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), CustomImportSelector.class);
        Assert.state(annType != null, "Unresolvable type argument for AdviceModeImportSelector");

        try {
            Class cls = Class.forName(importingClassMetadata.getClassName());

            Annotation annotation = cls.getAnnotation(annType);

            A a = (A) annotation;

            return selectImports(a);

        } catch (ClassNotFoundException e) {
            log.error("class not found", e);
        }

        return null;

    }


    protected abstract String[] selectImports(A anno);

}
