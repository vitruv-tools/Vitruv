package tools.vitruv.domains.demo.recipients

import edu.kit.ipd.sdq.metamodels.recipients.RecipientsPackage
import tools.vitruv.domains.demo.VitruvDemoDomain

class RecipientsDomain extends VitruvDemoDomain {
	public static val String METAMODEL_NAME = "Recipients"
	public static val String FILE_EXTENSION = "recipients"

	package new() {
		super(METAMODEL_NAME, RecipientsPackage.eINSTANCE, identifyingAttributes, FILE_EXTENSION)
	}

	def private static getIdentifyingAttributes() {
		#[RecipientsPackage.Literals.IDENTIFIED_ELEMENT__ID]
	}

}
