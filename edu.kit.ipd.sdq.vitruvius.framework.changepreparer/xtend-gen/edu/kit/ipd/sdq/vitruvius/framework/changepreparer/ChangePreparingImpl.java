package edu.kit.ipd.sdq.vitruvius.framework.changepreparer;

import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.CompositeChangePreparer;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ConcreteChangePreparer;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.EMFModelPreparer;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.FileChangePreparer;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * The default implementation of change preparing prepares EMFModelChanges, FileChanges and
 * CompositeChanges. For FileChanges the according EMFModelChanges are created (root EObject created
 * or deleted). It does nothing for EMFModelChanges. Furthermore, it checks whether CompositeChanges
 * are valid.
 * @author langhamm
 */
@SuppressWarnings("all")
public class ChangePreparingImpl implements ChangePreparing {
  private final static Logger logger = Logger.getLogger(ChangePreparingImpl.class.getSimpleName());
  
  private final Map<Class<?>, ConcreteChangePreparer> changePreparerMap;
  
  public ChangePreparingImpl(final ModelProviding modelProviding, final CorrespondenceProviding correspondenceProviding) {
    HashMap<Class<?>, ConcreteChangePreparer> _hashMap = new HashMap<Class<?>, ConcreteChangePreparer>();
    this.changePreparerMap = _hashMap;
    final EMFModelPreparer emfModelPreparer = new EMFModelPreparer(modelProviding, correspondenceProviding);
    this.changePreparerMap.put(EMFModelChange.class, emfModelPreparer);
    FileChangePreparer _fileChangePreparer = new FileChangePreparer(modelProviding);
    this.changePreparerMap.put(FileChange.class, _fileChangePreparer);
    CompositeChangePreparer _compositeChangePreparer = new CompositeChangePreparer(correspondenceProviding);
    this.changePreparerMap.put(CompositeChange.class, _compositeChangePreparer);
  }
  
  @Override
  public void prepareAllChanges(final Blackboard blackboard) {
    final List<Change> changesForPreparation = blackboard.popChangesForPreparation();
    final List<Change> preparedChanges = new ArrayList<Change>();
    for (final Change change : changesForPreparation) {
      {
        Class<? extends Change> _class = change.getClass();
        boolean _containsKey = this.changePreparerMap.containsKey(_class);
        boolean _not = (!_containsKey);
        if (_not) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Could not find ChangeSynchronizer for change ");
          Class<? extends Change> _class_1 = change.getClass();
          String _simpleName = _class_1.getSimpleName();
          _builder.append(_simpleName, "");
          _builder.append(". Can not synchronize change ");
          _builder.append(change, "");
          ChangePreparingImpl.logger.warn(_builder);
        }
        Class<? extends Change> _class_2 = change.getClass();
        ConcreteChangePreparer _get = this.changePreparerMap.get(_class_2);
        final Change preparedChange = _get.prepareChange(change);
        if ((null != preparedChange)) {
          preparedChanges.add(preparedChange);
        }
      }
    }
    blackboard.pushChanges(preparedChanges);
  }
}
