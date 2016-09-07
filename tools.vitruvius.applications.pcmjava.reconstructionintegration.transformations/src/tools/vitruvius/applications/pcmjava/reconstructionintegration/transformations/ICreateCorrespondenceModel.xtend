package tools.vitruvius.applications.pcmjava.reconstructionintegration.transformations


interface ICreateCorrespondenceModel {
	
	/**
	 * Creates correspondences between model elements from two different metamodels and adds them to the vsum.
	 * They are necessary to propagate changes.
	 */
	public def void createCorrespondences() 
}