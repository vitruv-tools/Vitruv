package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer.withocl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMRepositorytoJaMoPPInvariantEnforcer;

 
/**
 * Example for an invariant enforcers that uses a QVTo-refinement transformation. For details see
 * "PCMtoJaMoPPComponentInterfaceImplementsAmbiguity"
 */
public class PJIE_ComponentInterfaceImplementsAmbiguity extends PCMRepositorytoJaMoPPInvariantEnforcer {

    private String outFilePath = null; // optional path to the output file. If not set overwrite

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.FixedInvariantEnforcer#enforceInvariant
     * ()
     */
    @Override
    public void enforceInvariant() {

        final Bundle bundle = Platform
                .getBundle("edu.kit.ipd.sdq.vitruvius.integration.invariantEnforcingTransformations");
        final URL fileURL = bundle.getEntry("transforms/DeterministicInterfaceAmbiguityImplementsSolver.qvto");
        File file = null;
        try {
            String filteredFileName = FileLocator.resolve(fileURL).toString();
            filteredFileName = filteredFileName.replaceAll(" ", "%20");
            file = new File(FileLocator.resolve(new URL(filteredFileName)).toURI());
        } catch (final URISyntaxException e1) {
            e1.printStackTrace();
        } catch (final IOException e1) {
            e1.printStackTrace();
        }

        try {
            this.model = QVToTransformationExecuter.transform(this.model, this.outFilePath, file.getAbsolutePath());
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the out file path.
     *
     * @return the out file path
     */
    public String getOutFilePath() {
        return this.outFilePath;
    }

    /**
     * Sets the out file path.
     *
     * @param outFilePath
     *            the new out file path
     */
    public void setOutFilePath(final String outFilePath) {
        this.outFilePath = outFilePath;
    }

}
