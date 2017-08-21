package tools.vitruv.extensions.dslruntime.commonalities

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility package class CommonalitiesConstants {
	// must be in sync with GeneratorConstants!
	package static val INTERMEDIATE_MODEL_ROOT_CLASS = 'IntermediateModelRoot'
	package static val INTERMEDIATE_MODEL_NONROOT_CLASS = 'IntermediateModelNonRootObject'
	package static val INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME = 'children'
	package static val INTERMEDIATE_MODEL_ID_ATTRIBUTE = 'intermediateModelUuid'
	package static val INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER = 'uuidCounter'
}