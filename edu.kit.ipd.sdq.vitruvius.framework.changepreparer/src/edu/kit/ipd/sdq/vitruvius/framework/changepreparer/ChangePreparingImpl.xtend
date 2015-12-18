package edu.kit.ipd.sdq.vitruvius.framework.changepreparer

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding

/** 
 * The default implementation of change preparing prepares EMFModelChanges, FileChanges and
 * CompositeChanges. For FileChanges the according EMFModelChanges are created (root EObject created
 * or deleted). It does nothing for EMFModelChanges. Furthermore, it checks whether CompositeChanges
 * are valid.
 * @author langhamm
 */
class ChangePreparingImpl implements ChangePreparing {
	static final Logger logger = Logger::getLogger(typeof(ChangePreparingImpl).getSimpleName())
	final Map<Class<?>, ConcreteChangePreparer> changePreparerMap

	new(ModelProviding modelProviding, CorrespondenceProviding correspondenceProviding) {
		this.changePreparerMap = new HashMap<Class<?>, ConcreteChangePreparer>()
		val EMFModelPreparer emfModelPreparer = new EMFModelPreparer(modelProviding,correspondenceProviding)
		this.changePreparerMap.put(typeof(EMFModelChange), emfModelPreparer)
		this.changePreparerMap.put(typeof(FileChange), new FileChangePreparer(modelProviding))
		this.changePreparerMap.put(typeof(CompositeChange), new CompositeChangePreparer(correspondenceProviding))
	}

	override void prepareAllChanges(Blackboard blackboard) {
		val List<Change> changesForPreparation = blackboard.popChangesForPreparation()
		val List<Change> preparedChanges = new ArrayList<Change>()
		for (Change change : changesForPreparation) {
			if (!this.changePreparerMap.containsKey(change.getClass())) {
				logger.warn('''Could not find ChangeSynchronizer for change «change.getClass().getSimpleName()». Can not synchronize change «change»''')
			}
			val Change preparedChange = this.changePreparerMap.get(change.getClass()).prepareChange(change)
			if (null !== preparedChange) {
				preparedChanges.add(preparedChange)
			}

		}
		blackboard.pushChanges(preparedChanges)
	}
}
		