package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import de.uka.ipd.sdq.pcm.PcmPackage;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.AdvancedFeaturesPackage;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.emfmodels.advancedfeatures.impl.AdvancedFeaturesFactoryImpl;

public final class Metamodels {
    private Metamodels() {
    }

    public static void registerAdvancedFeaturesMetamodel() {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        EPackage.Registry.INSTANCE.put(AdvancedFeaturesPackage.eNS_URI, AdvancedFeaturesPackage.eINSTANCE);
        m.put("advancedfeatures", new AdvancedFeaturesFactoryImpl());
    }

    public static void registerRepositoryMetamodel() {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        EPackage.Registry.INSTANCE.put(PcmPackage.eNS_URI, PcmPackage.eINSTANCE);
        m.put("repository", new PcmResourceFactoryImpl());
    }
}
