package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.ClassMethodBodyChangedTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Code2SEFFFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

@SuppressWarnings("all")
public class Java2PcmMethodBodyChangePreprocessor implements Change2CommandTransformingPreprocessor {
  private final Code2SEFFFactory code2SEFFfactory;
  
  public Java2PcmMethodBodyChangePreprocessor(final Code2SEFFFactory code2SEFFfactory) {
    this.code2SEFFfactory = code2SEFFfactory;
  }
  
  @Override
  public boolean doesProcess(final Change change) {
    if ((change instanceof CompositeChange)) {
      final JavaMethodBodyChangedChangeRefiner refiner = new JavaMethodBodyChangedChangeRefiner(null);
      return refiner.match(((CompositeChange)change));
    }
    return false;
  }
  
  @Override
  public Iterable<Command> processChange(final Change change, final UserInteracting userInteracting, final Blackboard blackboard) {
    final CompositeChange compositeChange = ((CompositeChange) change);
    VitruviusTransformationRecordingCommand _createVitruviusTransformationRecordingCommand = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
      new Callable<TransformationResult>() {
        @Override
        public TransformationResult call() {
          return Java2PcmMethodBodyChangePreprocessor.this.executeClassMethodBodyChangeRefiner(blackboard, userInteracting, compositeChange);
        }
      });
    final Command command = ((Command) _createVitruviusTransformationRecordingCommand);
    return Collections.<Command>unmodifiableList(CollectionLiterals.<Command>newArrayList(command));
  }
  
  private TransformationResult executeClassMethodBodyChangeRefiner(final Blackboard blackboard, final UserInteracting userInteracting, final CompositeChange compositeChange) {
    final CorrespondenceInstance<Correspondence> correspondenceInstance = blackboard.getCorrespondenceInstance();
    List<Change> _changes = compositeChange.getChanges();
    Change _get = _changes.get(0);
    final EMFModelChange emfChange = ((EMFModelChange) _get);
    EChange _eChange = emfChange.getEChange();
    final EFeatureChange<?> eFeatureChange = ((EFeatureChange<?>) _eChange);
    EObject _oldAffectedEObject = eFeatureChange.getOldAffectedEObject();
    final ClassMethod oldMethod = ((ClassMethod) _oldAffectedEObject);
    EObject _newAffectedEObject = eFeatureChange.getNewAffectedEObject();
    final ClassMethod newMethod = ((ClassMethod) _newAffectedEObject);
    final BasicComponentFinding basicComponentFinding = this.code2SEFFfactory.createBasicComponentFinding();
    final BasicComponent myBasicComponent = basicComponentFinding.findBasicComponentForMethod(newMethod, correspondenceInstance);
    final AbstractFunctionClassificationStrategy classification = this.code2SEFFfactory.createAbstractFunctionClassificationStrategy(basicComponentFinding, correspondenceInstance, myBasicComponent);
    final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = this.code2SEFFfactory.createInterfaceOfExternalCallFinding(correspondenceInstance, myBasicComponent);
    final ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding = this.code2SEFFfactory.createResourceDemandingBehaviourForClassMethodFinding(correspondenceInstance);
    final ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(oldMethod, newMethod, basicComponentFinding, classification, interfaceOfExternalCallFinder, resourceDemandingBehaviourForClassMethodFinding);
    return methodBodyChanged.execute(blackboard, userInteracting, null);
  }
}
