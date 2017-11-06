package tools.vitruv.demo.domains.recipients

import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import edu.kit.ipd.sdq.metamodels.recipients.RecipientsPackage
import static tools.vitruv.demo.domains.recipients.RecipientsNamespace.*;
import tools.vitruv.framework.domains.AbstractVitruvDomain
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator

class RecipientsDomain extends AbstractVitruvDomain {
	public static final String METAMODEL_NAME = "Recipients"
	
	package new() {
		super(METAMODEL_NAME, ROOT_PACKAGE, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new AttributeTuidCalculatorAndResolver(METAMODEL_NAMESPACE, 
			#[RecipientsPackage.Literals.IDENTIFIED_ELEMENT__ID.name]
		);
	}
	
	
	override getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
}