package tools.vitruv.applications.pcmjava.builder;

import tools.vitruv.domains.emf.builder.TestBuilderEnabled;

public class PCMJavaTestBuilderEnabled extends TestBuilderEnabled {

    public PCMJavaTestBuilderEnabled() {
        super(new PCMJavaAddBuilder());
    }

}
