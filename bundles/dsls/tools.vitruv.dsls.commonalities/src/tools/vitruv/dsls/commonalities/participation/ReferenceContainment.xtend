package tools.vitruv.dsls.commonalities.participation

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute

@Data
class ReferenceContainment extends Containment {

	/**
	 * Can be <code>null</code>.
	 */
	val ParticipationAttribute reference
}
