package mir.responses;

import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * The {@link interface tools.vitruvius.framework.change.processing.Change2CommandTransforming} for transformations between the metamodels http://palladiosimulator.org/PalladioComponentModel/5.1 and http://www.emftext.org/java.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChange2CommandTransforming5_1ToJava extends AbstractChange2CommandTransforming {
	public AbstractChange2CommandTransforming5_1ToJava() {
		super (VURI.getInstance("http://palladiosimulator.org/PalladioComponentModel/5.1"),
			VURI.getInstance("http://www.emftext.org/java"));
	}
	
	/**
	 * Adds the response change processors to this {@link AbstractChange2CommandTransforming5_1ToJava}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeProcessor(new mir.responses.responses5_1ToJava.pcm2java.Executor5_1ToJava(getUserInteracting()));
	}
	
}
