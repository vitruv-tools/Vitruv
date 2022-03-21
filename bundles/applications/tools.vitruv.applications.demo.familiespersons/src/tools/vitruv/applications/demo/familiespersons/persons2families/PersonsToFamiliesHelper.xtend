package tools.vitruv.applications.demo.familiespersons.persons2families

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import java.util.ArrayList
import java.util.Collection
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

enum FamilyRole {
	Parent,
	Child
}

@Utility
class PersonsToFamiliesHelper {

	public final static String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A person's fullname is not allowed to be null."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A person's fullname has to contain at least one non-whitespace character."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A person's fullname cannot contain any whitespace escape sequences."

	/**Returns the eContainer of a Person casted as Personregister, if it is contained in a Personregister.
	 * @return <code>person.eContainer</code> as PersonRegister, if it actually is one; <code>null</code>, else.
	 */
	def static PersonRegister getRegister(Person person) {
		return if (person.eContainer instanceof PersonRegister)	person.eContainer as PersonRegister else null
	}

	/**Creates a string representation of a family which is used to label the different options during user interactions.
	 * Representation contains names and positions of all members. Person model informations such as birthdates are not used.
	 * @param family Family to represent as string.
	 * @return String representation.
	 */
	def static String stringifyFamily(Family family) {
		val StringBuilder builder = new StringBuilder().append(family.lastName).append(": ")
		if (family.father !== null) { builder.append("F: ").append(family.father.firstName).append(";") }
		if (family.mother !== null) { builder.append("M: ").append(family.mother.firstName).append(";") }
		if (family.sons !== null && family.sons.size > 0) { builder.append(family.sons.join("S: (", ", ", ")", [it.firstName])) }
		if (family.daughters !== null && family.daughters.size > 0) { builder.append(family.daughters.join("D: (", ", ", ")", [it.firstName])) }
		return builder.toString()
	}

	/**Extension, which returns the part of the person's fullname which represents the firstname. This is currently by convention
	 * everything except the last part which is separated by a space from the rest of the name. If the name does not contain a whitespace,
	 * the whole name is considered to be the firstname, which symbolizes, that the person has no lastname.
	 * @param person Person to retrieve the firstname from.
	 * @return As firstname interpreted part of the fullname.
	 */
	def static String getFirstname(Person person) {
		val Iterable<String> nameParts = person.fullName.split(" ")
		var String firstName = null
		if (nameParts.size() == 1) {
			firstName = person.fullName
		} else {
			firstName = nameParts.take(nameParts.size() - 1).join(" ")
		}
		return firstName
	}

	/**Extension, which returns the part of the person's fullname which represents the lastname. This is currently by convention
	 * the last part which is separated by a space from the rest of the name. If the name does not contain a whitespace,
	 * the empty string is returned, which symbolizes, that the person has only a firstname and no lastname.
	 * @param person Person to retrieve the lastname from.
	 * @return As lastname interpreted part of the fullname or <code>""</code>
	 */
	def static String getLastname(Person person) {
		if (!person.fullName.contains(" ")) {
			return ""
		} else {
			return person.fullName.split(" ").last
		}
	}

	def static FamilyRole askUserWhetherPersonIsParentOrChild(UserInteractor userInteractor, Person newPerson) {

		val StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have inserted ")
			.append(newPerson.fullName)
			.append(" into the persons register which results into the creation of a corresponding member into the family register.")
			.append(" Is this member supposed to be a parent or a child in the family register?")

		var Iterable<String> parentOrChildOptions = #["Parent", "Child"]

		val int parentOrChildSelection = userInteractor
			.singleSelectionDialogBuilder
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction()

		return if (parentOrChildSelection == parentOrChildOptions.toList.indexOf("Child")) FamilyRole.Child else FamilyRole.Parent
	}

	def static FamilyRole askUserWhetherPersonIsParentOrChildDuringRenaming(UserInteractor userInteractor, String oldFullname, String newFullname, boolean wasChildBefore) {

		val StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have renamed ")
			.append(oldFullname)
			.append(" to ")
			.append(newFullname)
			.append(", which might cause the corresponding member in the families model to change its position inside a family.")
			.append(" Which position should this member, who was a ")
			.append(if (wasChildBefore) "child" else "parent")
			.append(" before, have after the renaming?")

		var Iterable<String> parentOrChildOptions = #["Parent", "Child"]

		val int parentOrChildSelection = userInteractor
			.singleSelectionDialogBuilder
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction()

		return if (parentOrChildSelection == parentOrChildOptions.toList.indexOf("Child")) FamilyRole.Child else FamilyRole.Parent
	}

	/**Filters out families which already have a parent of the same sex as the given person.
	 * @param person Person whose last name is used for the filter.
	 * @return Lambda to filter out all families whose last name is different from the newPerson's.
	 */
	def static (Family)=>boolean sameLastname(Person person) {
		val String newPersonLastname = person.lastname
		return [family|family.lastName.equals(newPersonLastname)]
	}

	/**Filters out families which already have a parent of the same sex as the given person.
	 * @param person Person whose sex determines whether families with father or with mother are filtered out.
	 * @return Lambda to filter out all families which already have a parent with the same sex as the newPerson.
	 */
	def static (Family)=>boolean noParent(Person person) {
		return [family|if(person instanceof Male) family.father === null else family.mother === null]
	}

	/**Sets up an user interaction to ask the user if he wants to insert the corresponding member to the {@code newPerson}
	 * either into a new family or into one of the {@code selectableFamilies} and if so, in which.
	 * @return chosen family, if existing family was selected; <code>null</code>, if users wants to create and insert into a new family
	 */
	def static Family askUserWhichFamilyToInsertTheMemberIn(UserInteractor userInteractor, Person newPerson, Iterable<Family> selectableFamilies) {
		// Let user select the family
		var StringBuilder whichFamilyMessageBuilder = new StringBuilder()
			.append("Please choose whether you want to create a new family or insert ")
			.append(newPerson.fullName)
			.append(" into one of the existing families.")

		// Prepare options to select from
		val Collection<String> whichFamilyOptions = new ArrayList<String>()
		whichFamilyOptions.^add("insert in a new family")
		whichFamilyOptions.addAll(selectableFamilies.map[stringifyFamily(it)])

		// Start interaction
		val whichFamilyIndex = userInteractor
			.singleSelectionDialogBuilder
			.message(whichFamilyMessageBuilder.toString())
			.choices(whichFamilyOptions)
			.title("New or Existing Family?")
			.windowModality(WindowModality.MODAL)
			.startInteraction()

		return if (whichFamilyIndex === 0) null else selectableFamilies.get(whichFamilyIndex-1)
	}


	/**Checks if a persons fullname is <code>null</code>, empty or contains escape sequences.
	 * @param person The person of which the fullname has to be valid.
	 * @throws <code>IllegalStateException</code>, if the persons fullname in invalid.
	 */
	def static void assertValidFullname(Person person) {
		if (person.fullName === null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL)
		} else if (person.fullName.trim.empty) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE)
		} else if (person.fullName.contains("\n") || person.fullName.contains("\t") || person.fullName.contains("\r")) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		}
	}
}