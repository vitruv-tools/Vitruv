package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

public interface BasicComponentFinding {

    BasicComponent findBasicComponentForMethod(Method newMethod, CorrespondenceInstance<?> ci);

}
