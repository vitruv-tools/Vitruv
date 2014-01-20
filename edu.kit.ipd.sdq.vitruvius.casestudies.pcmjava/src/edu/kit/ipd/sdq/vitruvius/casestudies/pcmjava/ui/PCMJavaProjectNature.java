package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.ui;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.VitruviusEmfProjectNature;

/**
 * 
 * @author Langhamm
 * 
 */
class PCMJavaProjectNature extends VitruviusEmfProjectNature {

    private static final String PCMJavaProjectNatureName = "PCM Java Project Nature";
    private static final String PCM_JAVA_PROJECT_NATURE_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaProjectNature.ID";
    private static final String REPOSITORY_FILE_EXTENSION = ".repository";
    private static final String SYSTEM_FILE_EXTENSION = ".system";
    private static final String JAVA_FILE_EXTENSION = ".java";

    public PCMJavaProjectNature() {
//        super(project, PCMJavaProjectNatureName, PCMJavaProjectNatureID, Arrays.asList(REPOSITORY_FILE_EXTENSION,
//                SYSTEM_FILE_EXTENSION, JAVA_FILE_EXTENSION));
        super(PCM_JAVA_PROJECT_NATURE_ID, PCMJavaProjectNatureName);
    }

//    public PCMJavaProjectNature(IProject project, String modelPath, String repositoryFile, String systemFile) {
//        this(project);
//    }

}
