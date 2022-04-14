package tools.vitruv.testutils.domains;

import java.util.List;

import allElementTypes.AllElementTypesPackage;

public final class AllElementTypesDomain extends VitruvTestDomain {
	public static final String METAMODEL_NAME = "AllElementTypes";
	public static final String FILE_EXTENSION = "allElementTypes";
	private boolean shouldTransitivelyPropagateChanges = false;

	AllElementTypesDomain() {
		super(METAMODEL_NAME, AllElementTypesPackage.eINSTANCE, List.of(AllElementTypesPackage.Literals.IDENTIFIED__ID),
				FILE_EXTENSION);
	}

	@Override
	public boolean shouldTransitivelyPropagateChanges() {
		return shouldTransitivelyPropagateChanges;
	}

	/**
	 * Calling this method enables the per default disabled transitive change
	 * propagation. Should only be called for test purposes!
	 */
	public void enableTransitiveChangePropagation() {
		shouldTransitivelyPropagateChanges = true;
	}

	/**
	 * Calling this method disables the transitive change propagation which may have
	 * been enabled calling {@link #enableTransitiveChangePropagation()}. Should
	 * only be called for test purposes!
	 */
	public void disableTransitiveChangePropagation() {
		shouldTransitivelyPropagateChanges = false;
	}

}
