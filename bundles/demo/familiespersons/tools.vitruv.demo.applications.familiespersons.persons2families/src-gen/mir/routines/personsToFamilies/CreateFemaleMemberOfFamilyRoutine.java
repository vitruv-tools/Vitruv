package mir.routines.personsToFamilies;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Female;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class CreateFemaleMemberOfFamilyRoutine extends AbstractRepairRoutineRealization {
  private CreateFemaleMemberOfFamilyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Female person, final FamilyRegister familyRegister, final Member member) {
      return person;
    }
    
    public void updateMemberElement(final Female person, final FamilyRegister familyRegister, final Member member) {
      member.setFirstName(person.getFullName().split(" ")[0]);
    }
    
    public EObject getElement2(final Female person, final FamilyRegister familyRegister, final Member member) {
      return member;
    }
    
    public EObject getCorrepondenceSourceFamilyRegister(final Female person) {
      EObject _eContainer = person.eContainer();
      return _eContainer;
    }
    
    public void callRoutine1(final Female person, final FamilyRegister familyRegister, final Member member, @Extension final RoutinesFacade _routinesFacade) {
      EList<Family> collectionFamilies = familyRegister.getFamilies();
      int _size = collectionFamilies.size();
      final List<String> familiesNames = new ArrayList<String>(_size);
      for (final Family f : collectionFamilies) {
        String _lastName = f.getLastName();
        familiesNames.add(_lastName);
      }
      final String selectMsg = "Please select the family to which the person belongs.";
      final int selected = (this.userInteractor.getSingleSelectionDialogBuilder().message(selectMsg).choices(familiesNames).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction()).intValue();
      final Family selectedFamily = collectionFamilies.get(selected);
      selectedFamily.setLastName(person.getFullName().split(" ")[1]);
      List<String> collectionRoles = new ArrayList<String>();
      Iterables.<String>addAll(collectionRoles, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Mother", "Daughter")));
      final String selectMsgRoles = "Please select whether the person is a mother or a daughter.";
      final int selectedRole = (this.userInteractor.getSingleSelectionDialogBuilder().message(selectMsgRoles).choices(collectionRoles).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction()).intValue();
      if ((selectedRole == 0)) {
        selectedFamily.setMother(member);
      } else {
        EList<Member> _daughters = selectedFamily.getDaughters();
        _daughters.add(member);
      }
      _routinesFacade.addCorr(person, selectedFamily);
    }
  }
  
  public CreateFemaleMemberOfFamilyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Female person) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.personsToFamilies.CreateFemaleMemberOfFamilyRoutine.ActionUserExecution(getExecutionState(), this);
    this.person = person;
  }
  
  private Female person;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateFemaleMemberOfFamilyRoutine with input:");
    getLogger().debug("   person: " + this.person);
    
    edu.kit.ipd.sdq.metamodels.families.FamilyRegister familyRegister = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceFamilyRegister(person), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.families.FamilyRegister.class,
    	(edu.kit.ipd.sdq.metamodels.families.FamilyRegister _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (familyRegister == null) {
    	return false;
    }
    registerObjectUnderModification(familyRegister);
    edu.kit.ipd.sdq.metamodels.families.Member member = edu.kit.ipd.sdq.metamodels.families.impl.FamiliesFactoryImpl.eINSTANCE.createMember();
    notifyObjectCreated(member);
    userExecution.updateMemberElement(person, familyRegister, member);
    
    addCorrespondenceBetween(userExecution.getElement1(person, familyRegister, member), userExecution.getElement2(person, familyRegister, member), "");
    
    userExecution.callRoutine1(person, familyRegister, member, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
