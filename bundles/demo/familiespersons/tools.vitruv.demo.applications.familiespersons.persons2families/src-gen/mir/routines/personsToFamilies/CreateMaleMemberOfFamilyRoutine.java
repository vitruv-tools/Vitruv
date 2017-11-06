package mir.routines.personsToFamilies;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateMaleMemberOfFamilyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateMaleMemberOfFamilyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Male person, final FamilyRegister familiesRegister, final Member member) {
      return person;
    }
    
    public EObject getCorrepondenceSourceFamiliesRegister(final Male person) {
      EObject _eContainer = person.eContainer();
      return _eContainer;
    }
    
    public void updateMemberElement(final Male person, final FamilyRegister familiesRegister, final Member member) {
      member.setFirstName(person.getFullName().split(" ")[0]);
    }
    
    public EObject getElement2(final Male person, final FamilyRegister familiesRegister, final Member member) {
      return member;
    }
    
    public void callRoutine1(final Male person, final FamilyRegister familiesRegister, final Member member, @Extension final RoutinesFacade _routinesFacade) {
      EList<Family> collectionFamilies = familiesRegister.getFamilies();
      int _size = collectionFamilies.size();
      final List<String> familiesNames = new ArrayList<String>(_size);
      for (final Family f : collectionFamilies) {
        familiesNames.add(f.getLastName());
      }
      final String selectMsg = "Please select the family to which the person belongs.";
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, selectMsg, ((String[])Conversions.unwrapArray(familiesNames, String.class)));
      final Family selectedFamily = collectionFamilies.get(selected);
      selectedFamily.setLastName(person.getFullName().split(" ")[1]);
      List<String> collectionRoles = new ArrayList<String>();
      Iterables.<String>addAll(collectionRoles, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Father", "Son")));
      final String selectMsgRoles = "Please select whether the person is a father or a son.";
      final List<String> _converted_collectionRoles = (List<String>)collectionRoles;
      final int selectedRole = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, selectMsgRoles, ((String[])Conversions.unwrapArray(_converted_collectionRoles, String.class)));
      if ((selectedRole == 0)) {
        selectedFamily.setFather(member);
      } else {
        selectedFamily.getSons().add(member);
      }
      _routinesFacade.addCorr(person, selectedFamily);
    }
  }
  
  public CreateMaleMemberOfFamilyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Male person) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.CreateMaleMemberOfFamilyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.personsToFamilies.RoutinesFacade(getExecutionState(), this);
    this.person = person;
  }
  
  private Male person;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMaleMemberOfFamilyRoutine with input:");
    getLogger().debug("   person: " + this.person);
    
    edu.kit.ipd.sdq.metamodels.families.FamilyRegister familiesRegister = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceFamiliesRegister(person), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.families.FamilyRegister.class,
    	(edu.kit.ipd.sdq.metamodels.families.FamilyRegister _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (familiesRegister == null) {
    	return false;
    }
    registerObjectUnderModification(familiesRegister);
    edu.kit.ipd.sdq.metamodels.families.Member member = edu.kit.ipd.sdq.metamodels.families.impl.FamiliesFactoryImpl.eINSTANCE.createMember();
    notifyObjectCreated(member);
    userExecution.updateMemberElement(person, familiesRegister, member);
    
    addCorrespondenceBetween(userExecution.getElement1(person, familiesRegister, member), userExecution.getElement2(person, familiesRegister, member), "");
    
    userExecution.callRoutine1(person, familiesRegister, member, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
