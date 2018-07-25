package tools.vitruv.framework.userinteraction.impl;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.types.InteractionFactoryImpl;
import tools.vitruv.framework.userinteraction.types.InteractionFactory;
import tools.vitruv.framework.userinteraction.WindowModality;
import tools.vitruv.framework.userinteraction.resultprovider.DialogInteractionProvider;
import tools.vitruv.framework.userinteraction.resultprovider.InteractionResultProvider;

/**
 * Implementation of the {@link InternalUserInteractor} interface providing dialog builders for different cases of user
 * interaction.
 * 
 * @author messinger
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class UserInteractorImpl extends AbstractUserInteractor<InteractionResultProvider> {
	private static final WindowModality DEFAULT_WINDOW_MODALITY = WindowModality.MODELESS;
    private InteractionFactory interactionFactory;
    
    public UserInteractorImpl() {
    	super();
    	this.interactionFactory = new InteractionFactoryImpl(getInteractionResultProvider(), DEFAULT_WINDOW_MODALITY);
    }

	@Override
	protected InteractionFactory getInteractionFactory() {
		return interactionFactory;
	}

	@Override
	public InteractionResultProvider createInteractionResultProvider() {
		Display display = PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench().getDisplay() : PlatformUI.createDisplay();
        Shell shell = display.getActiveShell();
		return new DialogInteractionProvider(shell, display);
	}
    
}
