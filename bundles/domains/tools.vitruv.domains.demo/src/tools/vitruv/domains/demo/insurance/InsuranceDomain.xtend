package tools.vitruv.domains.demo.insurance

import edu.kit.ipd.sdq.metamodels.insurance.InsurancePackage
import tools.vitruv.domains.demo.VitruvDemoDomain

class InsuranceDomain extends VitruvDemoDomain {
	public static val String METAMODEL_NAME = "Insurance"
	public static val String FILE_EXTENSION = "insurance"

	package new() {
		super(METAMODEL_NAME, InsurancePackage.eINSTANCE, FILE_EXTENSION)
	}

}
