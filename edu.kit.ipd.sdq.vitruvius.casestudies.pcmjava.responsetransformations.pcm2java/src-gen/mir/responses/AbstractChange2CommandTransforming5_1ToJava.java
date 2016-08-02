package mir.responses;

import java.util.List;
import java.util.Collections;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class AbstractChange2CommandTransforming5_1ToJava extends AbstractResponseChange2CommandTransforming {
	public List<Pair<VURI, VURI>> getTransformableMetamodels() {
		VURI sourceVURI = VURI.getInstance("http://palladiosimulator.org/PalladioComponentModel/5.1");
		VURI targetVURI = VURI.getInstance("http://www.emftext.org/java");
		Pair<VURI, VURI> pair = new Pair<VURI, VURI>(sourceVURI, targetVURI);
		return Collections.singletonList(pair);
	}
	
	protected void setup() {
		this.addResponseExecutor(new mir.responses.responses5_1ToJava.pcm2java.Executor5_1ToJava(userInteracting));
	}
	
}
