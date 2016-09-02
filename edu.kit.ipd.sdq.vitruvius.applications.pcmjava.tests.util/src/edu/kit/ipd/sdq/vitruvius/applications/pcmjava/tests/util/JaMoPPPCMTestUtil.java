package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.util;

import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory;
import org.palladiosimulator.pcm.PcmPackage;
import org.palladiosimulator.pcm.util.PcmResourceFactoryImpl;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import edu.kit.ipd.sdq.vitruvius.domains.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.tests.util.TestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

/**
 * Class for JaMoPPPCM utility testing
 *
 * @author Langhamm
 *
 */
public final class JaMoPPPCMTestUtil {
    /**
     * Utility classes should not have a public constructor
     */
    private JaMoPPPCMTestUtil() {
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
        m.put(JaMoPPNamespace.JAVA_FILE_EXTENSION, new JavaResourceFactory());
        // register PCM
        EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
        m.put(PCMNamespace.REPOSITORY_FILE_EXTENSION, new PcmResourceFactoryImpl());
        // register correspondence model for xmi files
        EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackage.eINSTANCE);
        m.put("xmi", new XMIResourceFactoryImpl());
    }

    /**
     * creates a VSUM that can deal with JaMoPP and PCM elements
     *
     * @return
     */
    public static VSUMImpl createJaMoPPPCMVSUM() {
        return TestUtil.createVSUM(JaMoPPPCMTestUtil.createJaMoPPPCMMetaRepository());
    }

    /**
     * creates a MetaRepository using JaMoPP and PCM as metamodels
     *
     * @return
     */
    public static MetaRepositoryImpl createJaMoPPPCMMetaRepository() {
        return PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository();
    }
}
