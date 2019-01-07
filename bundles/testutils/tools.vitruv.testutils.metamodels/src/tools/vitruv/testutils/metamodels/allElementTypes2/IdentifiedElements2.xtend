package tools.vitruv.testutils.metamodels.allElementTypes2

import allElementTypes2.impl.AllElementTypes2FactoryImpl
import allElementTypes2.impl.NonRoot2Impl
import allElementTypes2.impl.NonRootObjectContainerHelper2Impl
import allElementTypes2.impl.Root2Impl
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.util.EcoreUtil

@Utility
class IdentifierProvider2 {
	def static generateNewIdentifier() {
		EcoreUtil.generateUUID()
	}
}

class RandomIdElement2Factory extends AllElementTypes2FactoryImpl {
	override createRoot2() {
		new RandomIdRoot2()
	}

	override createNonRoot2() {
		new RandomIdNonRoot2()
	}

	override createNonRootObjectContainerHelper2() {
		new RandomIdNonRootObjectContainerHelper2()
	}
}

class RandomIdRoot2 extends Root2Impl {
	new() {
		this.id2 = IdentifierProvider2.generateNewIdentifier()
	}
}

class RandomIdNonRoot2 extends NonRoot2Impl {
	new() {
		this.id2 = IdentifierProvider2.generateNewIdentifier()
	}
}

class RandomIdNonRootObjectContainerHelper2 extends NonRootObjectContainerHelper2Impl {
	new() {
		this.id2 = IdentifierProvider2.generateNewIdentifier()
	}
}
