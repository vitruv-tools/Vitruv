package tools.vitruv.applications.pcmjava.seffstatements.code2seff;

import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;

import tools.vitruv.framework.correspondence.CorrespondenceModel;

public interface BasicComponentFinding {

    BasicComponent findBasicComponentForMethod(Method newMethod, CorrespondenceModel ci);

}
