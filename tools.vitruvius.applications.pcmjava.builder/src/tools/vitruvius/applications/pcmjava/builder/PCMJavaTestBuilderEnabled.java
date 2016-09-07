package tools.vitruvius.applications.pcmjava.builder;

import tools.vitruvius.domains.emf.builder.TestBuilderEnabled;

public class PCMJavaTestBuilderEnabled extends TestBuilderEnabled {

    public PCMJavaTestBuilderEnabled() {
        super(new PCMJavaAddBuilder());
    }

}
