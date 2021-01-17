package tools.vitruv.demo.domains.addresses

import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import edu.kit.ipd.sdq.metamodels.addresses.AddressesPackage
import static tools.vitruv.demo.domains.addresses.AddressesNamespace.*;
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain

class AddressesDomain extends AbstractTuidAwareVitruvDomain {
	public static final String METAMODEL_NAME = "Addresses"
	
	package new() {
		super(METAMODEL_NAME, ROOT_PACKAGE, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new AttributeTuidCalculatorAndResolver(METAMODEL_NAMESPACE, 
			#[AddressesPackage.Literals.IDENTIFIED_ELEMENT__ID.name]
		);
	}
	
	override boolean isUserVisible() {
		return false;
	}
	
}