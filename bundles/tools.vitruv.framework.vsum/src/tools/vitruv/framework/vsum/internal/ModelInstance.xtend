package tools.vitruv.framework.vsum.internal

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Collections
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.id.IdResolver
import tools.vitruv.change.composite.description.VitruviusChangeFactory
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy

class ModelInstance extends ResourceImpl {
	static val LOGGER = Logger.getLogger(ModelInstance)

	new(URI uri) {
		this.URI = uri
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
	
	private static def List<EChange> loadDeltas(URI modelUri) {
        val resSet = new ResourceSetImpl();
        val resource = resSet.getResource(modelUri, true);
        return resource.getContents().map[it as EChange].toList
	}
	
	override doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		val deltas = loadDeltas(this.URI)
		VitruviusChangeFactory.getInstance().createTransactionalChange(deltas).resolveAndApply(IdResolver.create(this.resourceSet))
	}
	
	override doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		if (!this.modified) {
			return
		}
		
		val deltaChanges = new DefaultStateBasedChangeResolutionStrategy().getChangeSequenceForCreated(this).EChanges;
		val resSet = new ResourceSetImpl();
		val resource = resSet.createResource(this.URI);
		resource.getContents().addAll(deltaChanges)
		try (val out = outputStream){
			resource.save(out, Collections.EMPTY_MAP)
		}
		
		resource.modified = false
	}
	
	override delete(Map<?, ?> options) throws IOException {
		LOGGER.debug('''Delete model instance: «this»''')
		super.delete(null)
	}
}