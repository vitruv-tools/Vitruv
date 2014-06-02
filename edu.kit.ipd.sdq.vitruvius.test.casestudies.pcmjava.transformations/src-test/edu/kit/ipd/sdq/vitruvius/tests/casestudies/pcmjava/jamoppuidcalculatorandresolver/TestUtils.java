package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.jamoppuidcalculatorandresolver;

import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;

import de.uka.ipd.sdq.pcm.PcmPackage;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage;

public abstract class TestUtils {

    // private constructor for Util class
    private TestUtils() {
    }

    /**
     * Static initialization of Log4J
     */
    static {
        BasicConfigurator.configure(new ConsoleAppender(new SimpleLayout()));
    }

    /**
     * Register JaMoPP, PCM and Correspondence packages to use these models in a non-Plugin test.
     */
    public static void registerMetamodels() {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();

        // register JaMoPP package and factory globally
        EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
        m.put("java", new JavaResourceFactory());
        // register PCM
        EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
        m.put("repository", new PcmResourceFactoryImpl());
        // register correspondence model for xmi files
        EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackage.eINSTANCE);
        m.put("xmi", new XMIResourceFactoryImpl());
    }

}
