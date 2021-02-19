package tools.vitruv.domains.demo.families

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage
import tools.vitruv.domains.demo.VitruvDemoDomain

class FamiliesDomain extends VitruvDemoDomain {
	public static val String METAMODEL_NAME = "Families"
	public static val String FILE_EXTENSION = "families"

	package new() {
		super(METAMODEL_NAME, FamiliesPackage.eINSTANCE, FILE_EXTENSION)
	}

}
