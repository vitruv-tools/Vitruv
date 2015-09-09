package edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers.pcmjamoppenforcer.withocl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.m2m.qvt.oml.util.WriterLog;

/**
 * This class allows the execution of QVTo transformations. It is used by some InvariantEnforcers.
 */
public class QVToTransformationExecuter {

    /**
     * Transform.
     *
     * @param inputModelPath
     *            : platform specific path of the input model
     * @param outputModelPath
     *            : platform specific path of the output model
     * @param transformationPath
     *            : platform specific path of the qvto transformation
     * @return Resource containing the output model
     * @throws Exception
     *             : Error during the transformation
     */

    public static Resource transform(final String inputModelPath, final String outputModelPath,
            final String transformationPath) throws Exception {

        final ResourceSet resourceSet = new ResourceSetImpl();
        final Resource inResource = resourceSet.getResource(URI.createURI(inputModelPath), true);

        return transform(inResource, outputModelPath, transformationPath);
    }

    /**
     * Transform.
     *
     * @param inResource
     *            : Resource containing the input model
     * @param outResourcePath
     *            : platform specific path to the output model, null if input = output
     * @param transformationPath
     *            : platform specific path to the transformation
     * @return Resource containing the output model
     * @throws Exception
     *             : Error during the transformation
     */

    public static Resource transform(final Resource inResource, final String outResourcePath,
            final String transformationPath) throws Exception {

        // Refer to an existing transformation via URI
        final URI transformationURI = URI.createFileURI(transformationPath);

        // create executor for the given transformation
        final TransformationExecutor executor = new TransformationExecutor(transformationURI);

        final EList<EObject> inObjects = inResource.getContents();

        // create the input extent with its initial contents
        final ModelExtent input = new BasicModelExtent(inObjects);
        // create an empty extent to catch the output
        final ModelExtent output = new BasicModelExtent();

        // setup the execution environment details ->
        // configuration properties, logger, monitor object etc.
        final ExecutionContextImpl context = new ExecutionContextImpl();
        context.setConfigProperty("keepModeling", true);
        final OutputStreamWriter outStream = new OutputStreamWriter(System.out);
        final Log log = new WriterLog(outStream);
        context.setLog(log);

        // run the transformation assigned to the executor with the given
        // input and output and execution context
        // Remark: variable arguments count is supported
        final ExecutionDiagnostic result = executor.execute(context, input, output);

        // check the result for success
        if (result.getSeverity() == Diagnostic.OK) {
            // the output objects got captured in the output extent
            final List<EObject> outObjects = output.getContents();
            // let's persist them using a resource
            final ResourceSet resourceSet2 = new ResourceSetImpl();
            Resource outResource;

            if (outResourcePath != null) {
                outResource = resourceSet2.createResource(URI.createURI(outResourcePath));
            } else {
                outResource = resourceSet2.createResource(inResource.getURI());
            }

            outResource.getContents().addAll(outObjects);
            try {
                outResource.save(Collections.emptyMap());
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return outResource;

        } else {
            // turn the result diagnostic into status and send it to error log
            final IStatus status = BasicDiagnostic.toIStatus(result);
            System.out.println(status.getMessage());

            throw new Exception("An error occured during the execution of a QVTo transformation");
        }

    }

}
