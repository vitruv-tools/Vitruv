package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.builder;

import edu.kit.ipd.sdq.vitruvius.domains.emf.builder.TestBuilderEnabled;

public class PCMJavaTestBuilderEnabled extends TestBuilderEnabled {

    public PCMJavaTestBuilderEnabled() {
        super(new PCMJavaAddBuilder());
    }

}
