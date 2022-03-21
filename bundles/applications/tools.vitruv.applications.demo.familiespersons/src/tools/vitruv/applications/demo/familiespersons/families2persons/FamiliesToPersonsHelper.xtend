package tools.vitruv.applications.demo.familiespersons.families2persons

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.Member
import static extension tools.vitruv.domains.demo.families.FamiliesUtil.getFamily
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.Female

@Utility
class FamiliesToPersonsHelper {

	public final static String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A member's firstname is not allowed to be null."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A member's firstname has to contain at least one non-whitespace character."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A member's firstname cannot contain any whitespace escape sequences."

	/** Returns the name of a Person corresponding to a Member. By convention, Persons whose fullname
	 *  do not contain a space (" ") to separate firstname and lastname are inserted into a Family with
	 *  an empty lastname. Therefore, Persons have to be named accordingly to keep both models consistent.
	 *
	 *  @param member Member from which a corresponding Person should be given a correct name.
	 *  @return Name that the corresponding Person from the persons model should have.
	 */
	def static String getPersonName(Member member) {
		val Family family = member.family
		val StringBuilder personNameBuilder = new StringBuilder(member.firstName)
		if (!family.lastName.equals("")) {
			personNameBuilder.append(" ").append(family.lastName)
		}
		return personNameBuilder.toString()
	}

	/**Checks if a members firstname is <code>null</code>, empty or contains escape sequences.
	 * These first names are not valid for members, since single names for persons are always interpreted
	 * as firstnames and if needed, lastnames are set to <code>""</code>. Since empty fullnames for persons
	 * are not allowed but empty lastnames for families are, a member's firstname can not be allowed to be
	 * empty to avoid conversions between the models which lead to invalid models.
	 * @param member The member whose firstname is checked
	 * @throws <code>IllegalArgumentException</code> if firstname is not valid
	 */
	def static void assertValidFirstname(Member member) {
		if (member.firstName === null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL)
		} else if (member.firstName.trim.empty) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE)
		} else if (member.firstName.contains("\n") || member.firstName.contains("\t") || member.firstName.contains("\r")){
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		}
	}

	/**Checks if a person is a Male and throws an exception if not to indicate an unsupported operation
	 * when the user tries to assign a member with a female corresponding person to a position inside a family
	 * which requires no or a male corresponding person. For example is the user tries to assign a former
	 * mother to be a father from now on, this is considered an unsupported operation.
	 * @param person The person which is supposed to be a <code>Male</code>.
	 * @throws <code>UnsupportedOperationException</code>, if the person is not a <code>Male</code>.
	 */
	def static void assertMale(Person person) {
		if (!(person instanceof Male)) {
			throw new UnsupportedOperationException(
				"The position of a male family member can only be assigned to members with no or a male corresponding person."
			)
		}
	}

	/**Checks if a person is a Female and throws an exception if not to indicate an unsupported operation
	 * when the user tries to assign a member with a male corresponding person to a position inside a family
	 * which requires no or a female corresponding person. For example is the user tries to assign a former
	 * father to be a mother from now on, this is considered an unsupported operation.
	 * @param person The person which is supposed to be a <code>Female</code>.
	 * @throws <code>UnsupportedOperationException</code>, if the person is not a <code>Female</code>.
	 */
	def static void assertFemale(Person person) {
		if (!(person instanceof Female)) {
			throw new UnsupportedOperationException(
				"The position of a female family member can only be assigned to members with no or a female corresponding person."
			)
		}
	}
}