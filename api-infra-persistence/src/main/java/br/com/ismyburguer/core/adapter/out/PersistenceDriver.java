package br.com.ismyburguer.core.adapter.out;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PersistenceDriver {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean out case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
