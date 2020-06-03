package tools.vitruv.dsls.commonalities.participation

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.ToString
import tools.vitruv.dsls.commonalities.language.ParticipationClass

@EqualsHashCode
@ToString
@FinalFieldsConstructor
abstract class Containment {

	@Accessors(PUBLIC_GETTER)
	val ParticipationClass container
	@Accessors(PUBLIC_GETTER)
	val ParticipationClass contained
}
