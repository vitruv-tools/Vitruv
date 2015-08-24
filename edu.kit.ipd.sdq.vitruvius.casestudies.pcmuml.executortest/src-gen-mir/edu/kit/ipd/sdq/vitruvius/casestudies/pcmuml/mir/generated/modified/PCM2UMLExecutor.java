package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * {@link AbstractMIRChange2CommandTransforming} for keeping the following meta models consistent:
 * <ol>
 *   <li>http://palladiosimulator.org/PalladioComponentModel/Repository/5.1</li>
 *   <li>http://www.eclipse.org/uml2/5.0.0/UML</li>
 * </ol>.
 */
public class PCM2UMLExecutor extends AbstractMIRChange2CommandTransforming {
	private static final Logger LOGGER = Logger.getLogger(PCM2UMLExecutor.class);
	
	/** The first mapped metamodel. **/
	public final String MM_ONE = "http://palladiosimulator.org/PalladioComponentModel/Repository/5.1";
	/** The second mapped metamodel. **/
	public final String MM_TWO = "http://www.eclipse.org/uml2/5.0.0/UML";
	
	private final VURI VURI_ONE = VURI.getInstance(MM_ONE);
	private final VURI VURI_TWO = VURI.getInstance(MM_TWO);
	
	/* Transformable metamodels. */
	private final List<Pair<VURI, VURI>> transformableMetamodels;
	
	public PCM2UMLExecutor() {
		transformableMetamodels = new ArrayList<Pair<VURI, VURI>>();
//		transformableMetamodels.add(new Pair<VURI, VURI>(VURI_ONE, VURI_TWO));
		transformableMetamodels.add(new Pair<VURI, VURI>(VURI_TWO, VURI_ONE));
	}
	
	@Override
	protected void setup() {
		addMIRMapping(edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings.Mapping0.INSTANCE);
//		addMIRMapping(edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.mappings.Mapping1.INSTANCE);
	}

	@Override
	public List<Pair<VURI, VURI>> getTransformableMetamodels() {
		return transformableMetamodels;
	}
	
	/*
	@Override
	protected PCM2UMLExecutorCorrespondence getMappedCorrespondenceInstance() {
		return this.mappedCorrespondenceInstance;
	}

	@Override
	protected void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.mappedCorrespondenceInstance.setCorrespondenceInstance(correspondenceInstance);
	}*/
}
