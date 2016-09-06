package edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.List
import org.emftext.language.java.containers.CompilationUnit

/**
 * Correspondence adder for file correspondences between Java and JML.
 * This class performs the necessary model loading and passes the
 * corresponding models to a correspondence adder specialised for
 * model correspondences.
 */
class Java2JMLFileCorrespondenceAdder {

	val ModelProviding modelProviding
	val CorrespondenceModel corrInst

	public new(ModelProviding modelProviding, CorrespondenceModel correspondenceInstance) {
		this.modelProviding = modelProviding
		this.corrInst = correspondenceInstance
	}

	def void addCorrespondences(List<Pair<VURI, VURI>> matchedFiles) {
		for (Pair<VURI, VURI> correspondence : matchedFiles) {
			addFileCorrespondences(correspondence)
		}
	}

	private def addFileCorrespondences(Pair<VURI, VURI> correspondence) {
		val javaModel = modelProviding.getAndLoadModelInstanceOriginal(correspondence.first)
		val jmlModel = modelProviding.getAndLoadModelInstanceOriginal(correspondence.second)

		val javaRoot = javaModel.getUniqueTypedRootEObject(CompilationUnit)
		val jmlRoot = jmlModel.getUniqueTypedRootEObject(edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit)

		Java2JMLCorrespondenceAdder.addCorrespondencesForCompilationUnit(javaRoot, jmlRoot, corrInst)
	}

}
