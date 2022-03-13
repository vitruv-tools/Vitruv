package tools.vitruv.testutils.metamodels

import tools.vitruv.testutils.activeannotations.ModelCreators
import registryoffice.RegistryofficeFactory

@ModelCreators(factory=RegistryofficeFactory)
final class RegistryOfficeCreators {
	public static val roc = new RegistryOfficeCreators
}
