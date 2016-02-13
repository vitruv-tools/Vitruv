package edu.kit.ipd.sdq.vitruvius.integration.casestudies.pcmjava.seff;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.PCMJaMoPPCorrespondenceModelTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.statements.StatementListContainer;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.somox.sourcecodedecorator.AbstractActionClassMethodLink;
import org.somox.sourcecodedecorator.MethodLevelResourceDemandingInternalBehaviorLink;
import org.somox.sourcecodedecorator.SEFF2MethodMapping;
import org.somox.sourcecodedecorator.SourceCodeDecoratorRepository;

/**
 * this class extends the PCMJaMoPPCorrespondenceModelTransformation to enable the integration of SEFFs and
 *  ResourceDemandingInternalBehaviour as well as the integration of components, interfaces etc.
 */
@SuppressWarnings("all")
public class PCMJaMoPPCorrespondenceModelForSEFFTransformation {
  private final static Logger logger = Logger.getLogger(PCMJaMoPPCorrespondenceModelForSEFFTransformation.class.getSimpleName());
  
  public PCMJaMoPPCorrespondenceModelForSEFFTransformation() {
  }
  
  public void createBehaviourCorrespondences(final PCMJaMoPPCorrespondenceModelTransformation currentCorrespondences) {
    Resource _scdm = currentCorrespondences.getScdm();
    EList<EObject> _contents = _scdm.getContents();
    EObject _get = _contents.get(0);
    SourceCodeDecoratorRepository scdmRepo = ((SourceCodeDecoratorRepository) _get);
    EList<SEFF2MethodMapping> _seff2MethodMappings = scdmRepo.getSeff2MethodMappings();
    final Consumer<SEFF2MethodMapping> _function = (SEFF2MethodMapping it) -> {
      this.createSeff2MethodMappings(it, currentCorrespondences);
    };
    _seff2MethodMappings.forEach(_function);
    EList<MethodLevelResourceDemandingInternalBehaviorLink> _methodLevelResourceDemandingInternalBehaviorLink = scdmRepo.getMethodLevelResourceDemandingInternalBehaviorLink();
    final Consumer<MethodLevelResourceDemandingInternalBehaviorLink> _function_1 = (MethodLevelResourceDemandingInternalBehaviorLink it) -> {
      this.createMethod2ResourceDemandingInternalBehaviorLink(it, currentCorrespondences);
    };
    _methodLevelResourceDemandingInternalBehaviorLink.forEach(_function_1);
    EList<AbstractActionClassMethodLink> _abstractActionClassMethodLink = scdmRepo.getAbstractActionClassMethodLink();
    final Consumer<AbstractActionClassMethodLink> _function_2 = (AbstractActionClassMethodLink it) -> {
      this.createAbstractAction2ClassMethodLink(it, currentCorrespondences);
    };
    _abstractActionClassMethodLink.forEach(_function_2);
  }
  
  public Correspondence createSeff2MethodMappings(final SEFF2MethodMapping seff2MethodMapping, final PCMJaMoPPCorrespondenceModelTransformation currentCorrespondences) {
    Correspondence _xblockexpression = null;
    {
      final ServiceEffectSpecification seff = seff2MethodMapping.getSeff();
      final StatementListContainer statementListContainer = seff2MethodMapping.getStatementListContainer();
      final Correspondence parentCorrespondence = this.<BasicComponent>findParentCorrespondenceWithType(statementListContainer, currentCorrespondences, 
        BasicComponent.class);
      _xblockexpression = currentCorrespondences.addCorrespondence(seff, statementListContainer, parentCorrespondence);
    }
    return _xblockexpression;
  }
  
  private Correspondence createMethod2ResourceDemandingInternalBehaviorLink(final MethodLevelResourceDemandingInternalBehaviorLink method2InternalBehavior, final PCMJaMoPPCorrespondenceModelTransformation currentCorrespondences) {
    Correspondence _xblockexpression = null;
    {
      Member _function = method2InternalBehavior.getFunction();
      final ConcreteClassifier jaMoPPClass = _function.getContainingConcreteClassifier();
      final Correspondence parentCorrespondence = this.<BasicComponent>findParentCorrespondenceWithType(jaMoPPClass, currentCorrespondences, BasicComponent.class);
      Member _function_1 = method2InternalBehavior.getFunction();
      ResourceDemandingInternalBehaviour _resourceDemandingInternalBehaviour = method2InternalBehavior.getResourceDemandingInternalBehaviour();
      _xblockexpression = currentCorrespondences.addCorrespondence(_function_1, _resourceDemandingInternalBehaviour, parentCorrespondence);
    }
    return _xblockexpression;
  }
  
  private void createAbstractAction2ClassMethodLink(final AbstractActionClassMethodLink abstractAction2classMethod, final PCMJaMoPPCorrespondenceModelTransformation currentCorrespondences) {
    final AbstractAction abstractAction = abstractAction2classMethod.getAbstractAction();
    boolean _or = false;
    if ((abstractAction instanceof StartAction)) {
      _or = true;
    } else {
      _or = (abstractAction instanceof StopAction);
    }
    if (_or) {
      return;
    }
    final ClassMethod classMethod = abstractAction2classMethod.getClassMethod();
    final Correspondence parentCorrespondence = this.<ResourceDemandingBehaviour>findParentCorrespondenceWithType(classMethod, currentCorrespondences, 
      ResourceDemandingBehaviour.class);
    currentCorrespondences.addCorrespondence(abstractAction, classMethod, parentCorrespondence);
  }
  
  private <T extends Object> Correspondence findParentCorrespondenceWithType(final Commentable jaMoPPClass, final PCMJaMoPPCorrespondenceModelTransformation currentCorrespondences, final Class<T> type) {
    CorrespondenceInstanceDecorator _cInstance = currentCorrespondences.getCInstance();
    List<EObject> _list = CollectionBridge.<EObject>toList(jaMoPPClass);
    final Set<Correspondence> correspondences = _cInstance.getCorrespondences(_list);
    final Function1<Correspondence, Boolean> _function = (Correspondence it) -> {
      boolean _or = false;
      EList<EObject> _as = it.getAs();
      final Function1<EObject, Boolean> _function_1 = (EObject it_1) -> {
        return Boolean.valueOf(type.isInstance(it_1));
      };
      EObject _findFirst = IterableExtensions.<EObject>findFirst(_as, _function_1);
      boolean _notEquals = (!Objects.equal(null, _findFirst));
      if (_notEquals) {
        _or = true;
      } else {
        EList<EObject> _bs = it.getBs();
        final Function1<EObject, Boolean> _function_2 = (EObject it_1) -> {
          return Boolean.valueOf(type.isInstance(it_1));
        };
        EObject _findFirst_1 = IterableExtensions.<EObject>findFirst(_bs, _function_2);
        boolean _notEquals_1 = (!Objects.equal(null, _findFirst_1));
        _or = _notEquals_1;
      }
      return Boolean.valueOf(_or);
    };
    final Iterable<Correspondence> correspondencesContainingBc = IterableExtensions.<Correspondence>filter(correspondences, _function);
    Correspondence parentCorrespondence = null;
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(correspondencesContainingBc);
    boolean _not = (!_isNullOrEmpty);
    if (_not) {
      Correspondence _get = ((Correspondence[])Conversions.unwrapArray(correspondencesContainingBc, Correspondence.class))[0];
      parentCorrespondence = _get;
    } else {
      boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(correspondences);
      boolean _not_1 = (!_isNullOrEmpty_1);
      if (_not_1) {
        Correspondence _get_1 = ((Correspondence[])Conversions.unwrapArray(correspondences, Correspondence.class))[0];
        parentCorrespondence = _get_1;
      } else {
        PCMJaMoPPCorrespondenceModelForSEFFTransformation.logger.warn(("Could not find a parent correspondence for object: " + jaMoPPClass));
      }
    }
    return parentCorrespondence;
  }
}
