package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

/**
 * The default implementation of change preparing prepares EMFModelChanges, FileChanges and
 * CompositeChanges. For FileChanges the according EMFModelChanges are created (root EObject created
 * or deleted). It does nothing for EMFModelChanges. Furthermore, it checks whether CompositeChanges
 * are valid.
 *
 * @author langhamm
 *
 */
public class ChangePreparingImpl implements ChangePreparing {

    private static final Logger logger = Logger.getLogger(ChangePreparingImpl.class.getSimpleName());

    private final Map<Class<?>, ConcreteChangePreparer> changePreparerMap;

    public ChangePreparingImpl(final ModelProviding modelProviding,
            final CorrespondenceProviding correspondenceProviding) {
        this.changePreparerMap = new HashMap<Class<?>, ConcreteChangePreparer>();
        final EMFModelPreparer emfModelPreparer = new EMFModelPreparer();
        this.changePreparerMap.put(EMFModelChange.class, emfModelPreparer);
        this.changePreparerMap.put(FileChange.class, new FileChangePreparer(modelProviding));
        this.changePreparerMap.put(CompositeChange.class, new CompositeChangePreparer(correspondenceProviding));
    }

    @Override
    public void prepareAllChanges(final Blackboard blackboard) {
        final List<Change> changesForPreparation = blackboard.popChangesForPreparation();
        final List<Change> preparedChanges = new ArrayList<Change>();
        for (final Change change : changesForPreparation) {
            if (!this.changePreparerMap.containsKey(change.getClass())) {
                logger.warn("Could not find ConcreteChangePreparer for change " + change.getClass().getSimpleName()
                        + ". Can not prepare change " + change);
            }
            final Change preparedChange = this.changePreparerMap.get(change.getClass()).prepareChange(change);
            if (null != preparedChange) {
                preparedChanges.add(preparedChange);
            }
        }
        blackboard.pushChanges(preparedChanges);
    }

}
