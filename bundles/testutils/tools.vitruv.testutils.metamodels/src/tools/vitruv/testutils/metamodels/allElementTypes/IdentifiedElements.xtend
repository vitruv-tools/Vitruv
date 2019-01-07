package tools.vitruv.testutils.metamodels.allElementTypes

import allElementTypes.impl.AllElementTypesFactoryImpl
import allElementTypes.impl.NonRootImpl
import allElementTypes.impl.NonRootObjectContainerHelperImpl
import allElementTypes.impl.RootImpl
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.util.EcoreUtil

@Utility
class IdentifierProvider {
	def static generateNewIdentifier() {
		EcoreUtil.generateUUID()
	}
}

class RandomIdElementFactory extends AllElementTypesFactoryImpl {
	override createRoot() {
		new RandomIdRoot()
	}

	override createNonRoot() {
		new RandomIdNonRoot()
	}

	override createNonRootObjectContainerHelper() {
		new RandomIdNonRootObjectContainerHelper()
	}
}

class RandomIdRoot extends RootImpl {
	new() {
		this.id = IdentifierProvider.generateNewIdentifier()
	}
}

class RandomIdNonRoot extends NonRootImpl {
	new() {
		this.id = IdentifierProvider.generateNewIdentifier()
	}
}

class RandomIdNonRootObjectContainerHelper extends NonRootObjectContainerHelperImpl {
	new() {
		this.id = IdentifierProvider.generateNewIdentifier()
	}
}
