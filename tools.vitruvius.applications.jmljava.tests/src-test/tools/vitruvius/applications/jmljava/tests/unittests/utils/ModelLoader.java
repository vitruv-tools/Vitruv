package tools.vitruvius.applications.jmljava.tests.unittests.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.io.Files;

public class ModelLoader {
    
    public interface IResourceFiles {
        public String getModelFileName();
    }
    
    public static Resource serializeAndLoadRelativeResource(IResourceFiles file, Class<?> callerClass) throws IOException {
        return loadRelativeResourceInternal(file, callerClass, true);
    }
    
    public static Resource loadRelativeResource(IResourceFiles file, Class<?> callerClass) throws IOException {
        return loadRelativeResourceInternal(file, callerClass, false);
    }
    
    private static Resource loadRelativeResourceInternal(IResourceFiles file, Class<?> callerClass, boolean serializeBeforeReading) throws IOException {
        // http://blog1.vorburger.ch/2009/08/xtext-standalone-setup-parsing-dsl-from.html
        final String modelFileName = file.getModelFileName();
        int resourceDelimiter = modelFileName.lastIndexOf(".");
        final String modelFileNameClean = modelFileName.substring(0, resourceDelimiter);
        
        File tmpDir = Files.createTempDir();
        tmpDir.deleteOnExit();

        String filenameWithExtension = FilenameUtils.getBaseName(modelFileNameClean) + "." + FilenameUtils.getExtension(modelFileNameClean);
        String tmpFile = FilenameUtils.concat(tmpDir.getPath(), filenameWithExtension);
        new File(tmpFile).deleteOnExit();

        String resourceString = "resources/" + modelFileName;
        InputStream resourceStream = callerClass.getResourceAsStream(resourceString);
        
        Resource resource = new ResourceSetImpl().createResource(URI.createFileURI(tmpFile));

        if (serializeBeforeReading) {
            IOUtils.copy(resourceStream, new FileOutputStream(tmpFile));
            resource.load(Collections.EMPTY_MAP);
        } else {
            resource.load(resourceStream, Collections.EMPTY_MAP);            
        }
        
        return resource;
    }
    
    @SuppressWarnings("unchecked")
    public static <S extends EObject> S loadRelativeResourceModel(IResourceFiles file, Class<?> callerClass) throws IOException {
        Resource resource = loadRelativeResource(file, callerClass);
        return (S)resource.getAllContents().next();
    }

}
