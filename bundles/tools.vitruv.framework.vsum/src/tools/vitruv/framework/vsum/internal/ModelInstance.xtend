package tools.vitruv.framework.vsum.internal

import java.io.IOException
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import tools.vitruv.change.changederivation.DeltaBasedResource

class ModelInstance extends DeltaBasedResource {
	static val LOGGER = Logger.getLogger(ModelInstance)

	new(URI uri) {
		super(uri)
		LOGGER.debug('''Create model instance for resource with URI: «uri»''')
	}

	def void addRoot(EObject root) {
		this.contents += root
		this.modified = true
		LOGGER.debug('''Add root to model instance: «this»''')
	}
	
	def boolean isEmpty() {
		this.contents.isEmpty
	}
	
	override delete(Map<?, ?> options) throws IOException {
		LOGGER.debug('''Delete model instance: «this»''')
		super.delete(null)
	}
}