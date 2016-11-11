package tools.vitruv.applications.pcmjava.reconstructionintegration.transformations;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;

import tools.vitruv.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import tools.vitruv.extensions.constructionsimulation.handler.IntegrationHandler;
import tools.vitruv.extensions.constructionsimulation.util.IntegrationUtil;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class JaMoPPIntegrationHandler extends IntegrationHandler<IJavaProject> {

    public JaMoPPIntegrationHandler() {
        super(IJavaProject.class);
    }

    @Override
    protected void handleSelectedElement(final IJavaProject iJavaProject) {
        this.integrateCode(iJavaProject);

    }

    /**
     * Integrate source code into Vitruvius 1. Create JavaProject with src folder 2. Run SoMoX on
     * this project 3. Update PCM Parameter definition 4. Extract all necessary models from the
     * model folder
     *
     * @param firstElement
     *            : the project containing the source code
     */
    private void integrateCode(final Object firstElement) {

        Logger.getRootLogger().setLevel(Level.ALL);

        final IJavaProject javaProject = (IJavaProject) firstElement;
        final IProject project = javaProject.getProject();

        // IPath projectPath = project.getFullPath(); // workspace relative Path
        final IPath projectPath = project.getLocation(); // absolute path
        final IPath models = projectPath.append("model").addTrailingSeparator().append("internal_architecture_model");

        final IPath scdmPath = models.addFileExtension("sourcecodedecorator");
        final IPath pcmPath = models.addFileExtension("repository");
        final IPath srcPath = projectPath.append("src");

        final File f = scdmPath.toFile();
        if (!f.exists()) {
            throw new IllegalArgumentException("Run SoMoX first!");
        }

        final Iterable<Metamodel> metamodels = PCMJavaRepositoryCreationUtil.createPcmJamoppMetamodels();
        final InternalVirtualModel vsum = IntegrationUtil.createVSUM(metamodels);
//        vsum.getOrCreateAllCorrespondenceModelsForMM(
//                metaRepository.getMetamodel(VURI.getInstance(PcmNamespace.PCM_METAMODEL_NAMESPACE)));

        final ICreateCorrespondenceModel transformation = new PCMJaMoPPCorrespondenceModelTransformation(
                scdmPath.toString(), pcmPath.toString(), srcPath.toString(), vsum);

        transformation.createCorrespondences();
    }

}
