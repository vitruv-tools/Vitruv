package tools.vitruv.framework.userinteraction

import org.eclipse.emf.common.util.URI
import java.util.List
import java.util.Date

interface UserInteracting {
	def void showMessage(UserInteractionType type, String message)

	def int selectFromMessage(UserInteractionType type, String message, String... selectionDescriptions)

	def String getTextInput(String msg)

	/** 
	 * Ask for a URI.
	 * @param messagethe message to display to the user.
	 * @return the URI of the resource.
	 * @author Dominik Werle
	 */
	def URI selectURI(String message)

	/**
	 * Versioning method
	 * @author PS
	 */
	def List<Integer> getAllUserInteractions()

	def List<Integer> getAllUserInteractionsSince(Date date)

}
