package tools.vitruv.applications.jmljava.synchronizers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import tools.vitruv.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import tools.vitruv.applications.jmljava.extensions.UserInteractingProvider;
import tools.vitruv.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import tools.vitruv.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactoryImpl;
import tools.vitruv.applications.jmljava.models.JavaModelURIProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import tools.vitruv.applications.jmljava.vitruvius.utils.EclipseUtilities;
import tools.vitruv.applications.jmljava.synchronizers.CSSynchronizer;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.modelsynchronization.TransformationAbortCause;
import tools.vitruv.framework.userinteraction.impl.UserInteractor;

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
            public void synchronisationAborted(TransactionalChange abortedChange) {
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
