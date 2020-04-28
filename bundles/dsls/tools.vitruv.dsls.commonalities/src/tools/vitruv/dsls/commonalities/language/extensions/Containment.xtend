package tools.vitruv.dsls.commonalities.language.extensions

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass

@Data
class Containment {

	val ParticipationClass contained
	val ParticipationClass container
	/**
	 * Can be <code>null</code>.
	 */
	val ParticipationAttribute reference
}
