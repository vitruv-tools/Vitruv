package tools.vitruv.applications.pcmjava.reconstructionintegration.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

 
/**
 * The Class PCMMetaModelConverter.
 *
 * @author Johannes Hoor Temporarily needed to convert PCM-Models (Repository) to a newer PCM-MM.
 *         Change: Old Definition:
 *
 *         <parameters__OperationSignature parameterName="NumberOfFiles"> <datatype__Parameter
 *         xsi:type="PrimitiveDataType"
 *         href="pathmap://PCM_MODELS/PrimitiveTypes.repository#//@datatypes_Repository.0"/>
 *         </parameters__OperationSignature>
 *
 *         New Definition:
 *
 *         <parameters__OperationSignature entityName="NumberOfFiles"> <dataType__Parameter
 *         xsi:type="repository:PrimitiveDataType"
 *         href="pathmap://PCM_MODELS/PrimitiveTypes.repository#//@dataTypes__Repository.0"/>
 *         </parameters__OperationSignature>
 */
public class PCMMetaModelConverter {

    private static boolean modelUpdated = false;

    /**
     * Checks if is model updated.
     *
     * @return true, if is model updated
     */
    public static boolean isModelUpdated() {
        return modelUpdated;
    }

    /**
     * Replaces <parameters__OperationSignature parameterName="NumberOfFiles"> with
     * <parameters__OperationSignature entityName="NumberOfFiles">.
     *
     * @param path
     *            of the pcm file
     * @param newPath
     *            if the updated model should be saved to a new location
     * @return pcm model instance as resource
     */
    public static File convertPCM(final IPath path, final boolean newPath) {

        modelUpdated = false;

        // get file for path
        final File pcmFile = path.toFile();

        // create new file
        final String fileName = path.lastSegment().toString().replace(".repository", "").concat("_new.repository");
        final File newFile = path.removeLastSegments(1).append(fileName).toFile();

        BufferedReader bfr = null;
        PrintWriter pwr = null;

        try {
            bfr = new BufferedReader(new FileReader(pcmFile));
            pwr = new PrintWriter(new FileWriter(newFile));
        } catch (final Exception e) {
            e.printStackTrace();
        }

        try {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                if (line.contains("parameterName") && line.contains("parameters__OperationSignature ")) {
                    line = line.replace("parameterName", "entityName");
                    modelUpdated = true;
                }
                pwr.write(line);
                pwr.write("\r\n");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bfr != null) {
                    bfr.close();
                }
                if (pwr != null) {
                    pwr.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }

        if (modelUpdated) {
            if (newPath) {
                return newFile;
            } else {
                final String oldFilePath = pcmFile.getAbsolutePath().toString();
                pcmFile.delete();

                final File f = new File(oldFilePath);
                try {
                    FileUtils.moveFile(newFile, f);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                return f;
            }
        } else {
            newFile.delete();
            return pcmFile;
        }
    }

    /**
     * Testmethod.
     *
     * @param args
     *            null
     */
    public static void main(final String[] args) {
        // String path = "Testmodels/invariantTests/beginchar.repository";
        final String path = "Testmodels/structConflictInterfaces.repository";
        final Path p = new Path(path);
        // convertPCM((IPath) Paths.get(path), true);
        convertPCM((p), false);

    }

}
