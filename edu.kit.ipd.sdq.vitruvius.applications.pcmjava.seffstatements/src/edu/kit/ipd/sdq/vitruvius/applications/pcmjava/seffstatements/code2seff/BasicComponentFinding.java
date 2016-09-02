package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.seffstatements.code2seff;

import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public interface BasicComponentFinding {

    BasicComponent findBasicComponentForMethod(Method newMethod, CorrespondenceModel ci);

}
