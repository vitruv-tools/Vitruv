package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.extensions.UserInteractingProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.models.JavaModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.utils.EclipseUtilities;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.impl.UserInteractor;

/**
 * Module for google guice dependency injection in the {@link CSSynchronizer} object hierarchy.
 */
public class CSSynchronizerModule extends AbstractModule {

    @Override
    protected void configure() {

        // Model uri provider for the shadow copy
        bind(ModelURIProvider.class).annotatedWith(Names.named("JavaModelUriProvider")).to(JavaModelURIProvider.class);
        bind(SynchronisationAbortedListener.class).toInstance(new SynchronisationAbortedListener() {
            @Override
            public void synchronisationAborted(GeneralChange abortedChange) {
                ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().synchronisationAborted(abortedChange);
            }

            @Override
            public void synchronisationAborted(TransformationAbortCause cause) {
                ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().synchronisationAborted(cause);
            }

        });

        bind(ShadowCopyFactory.class).to(ShadowCopyFactoryImpl.class);
//        // Shadow copy factory
//        install(new FactoryModuleBuilder()
//        .implement(ShadowCopy.class, ShadowCopyImpl2.class)
//        .build(
//                ShadowCopyFactory.class));

        // User interacting
        try {
        	UserInteracting ui = EclipseUtilities.getRegisteredExtensions(UserInteractingProvider.class).iterator().next().get();
        	ui.toString(); // throws NPE if ui is null
        	bind(UserInteracting.class).toInstance(ui);
        } catch (Exception e) {
        	bind(UserInteracting.class).to(UserInteractor.class);
        }
        
    }

}
