package tools.vitruv.domains.java.builder;

import tools.vitruv.domains.emf.builder.TestBuilderEnabled;

public class JavaTestBuilderEnabled extends TestBuilderEnabled {

    public JavaTestBuilderEnabled() {
        super(new JavaAddBuilder());
    }

}
