package tools.vitruv.framework.change.copy.impl

import java.util.Set

import org.apache.log4j.Level
import org.apache.log4j.Logger

import tools.vitruv.framework.change.copy.ChangeCopier
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.copy.EChangeCopier

class ChangeCopierImpl implements ChangeCopier {
	// Extensions.
	static extension Logger = Logger::getLogger(ChangeCopierImpl)

	// Values.
	val EChangeCopier eChangeCopier

	new(Set<Pair<String, String>> replacePairs) {
		eChangeCopier = EChangeCopier::createEChangeCopier(replacePairs)
		level = Level::DEBUG
	}

	override copyEChanges(EChange changeToCopy) {
		val copiedEChange = eChangeCopier.copy(changeToCopy)
		return VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[copiedEChange]) as VitruviusChange
	}

	override copyEMFModelChangeToList(VitruviusChange changeToCopy) {
		val newChanges = changeToCopy.copiedEChangeIterator.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it]) as VitruviusChange
		].toList
		return newChanges
	}

	override copyEMFModelChangeToSingleChange(VitruviusChange changeToCopy) {
		val newEchanges = changeToCopy.copiedEChangeIterator.toList
		val newChange = VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(newEchanges)
		return newChange
	}

	private def getCopiedEChangeIterator(VitruviusChange changeToCopy) {
		changeToCopy.getEChanges.map[eChangeCopier.copy(it)].filterNull
	}

}
