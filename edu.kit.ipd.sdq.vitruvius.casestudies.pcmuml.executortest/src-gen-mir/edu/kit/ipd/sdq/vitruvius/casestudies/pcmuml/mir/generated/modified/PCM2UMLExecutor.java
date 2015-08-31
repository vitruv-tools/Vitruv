package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings.MappingUClassToInterface;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * {@link AbstractMIRChange2CommandTransforming} for keeping the following meta models consistent:
 * <ol>
 *   <li>http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.uml_mockup</li>
 *   <li>http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.pcm_mockup</li>
 * </ol>.
 */
public class PCM2UMLExecutor extends AbstractMIRChange2CommandTransforming {
	private static final Logger LOGGER = Logger.getLogger(PCM2UMLExecutor.class);
	
	/** The first mapped metamodel. **/
	public final String MM_ONE = "http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.uml_mockup";
	/** The second mapped metamodel. **/
	public final String MM_TWO = "http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.pcm_mockup";
	
	private final VURI VURI_ONE = VURI.getInstance(MM_ONE);
	private final VURI VURI_TWO = VURI.getInstance(MM_TWO);
	
	/* Transformable metamodels. */
	private final List<Pair<VURI, VURI>> transformableMetamodels;
	
	public PCM2UMLExecutor() {
		transformableMetamodels = new ArrayList<Pair<VURI, VURI>>();
		transformableMetamodels.add(new Pair<VURI, VURI>(VURI_ONE, VURI_TWO));
		transformableMetamodels.add(new Pair<VURI, VURI>(VURI_TWO, VURI_ONE));
	}
	
	@Override
	protected void setup() {
		addMIRMapping(edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings.MappingUPackageToRepository.INSTANCE);
		addMIRMapping(edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings.MappingUClassToInterface.INSTANCE);
	}

	@Override
	public List<Pair<VURI, VURI>> getTransformableMetamodels() {
		return transformableMetamodels;
	}
}
