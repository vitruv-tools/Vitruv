package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.domains.AbstractVitruvDomain
import org.eclipse.emf.emfstore.bowling.BowlingPackage
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

final class BowlingDomain extends AbstractVitruvDomain {
	public static final String METAMODEL_NAME = "Bowling"
	public static final String FILE_EXTENSION = "bowling"

	package new() {
		super(METAMODEL_NAME, BowlingPackage::eINSTANCE,
			new AttributeTuidCalculatorAndResolver(BowlingPackage::eNS_URI, BowlingPackage.Literals.PLAYER__NAME.name),
			FILE_EXTENSION)
		}
	}
	