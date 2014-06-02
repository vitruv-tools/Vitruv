package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.TestBuilderEnabled;

public class PCMJavaTestBuilderEnabled extends TestBuilderEnabled {

    public PCMJavaTestBuilderEnabled() {
        super(new PCMJavaAddBuilder());
    }

}
