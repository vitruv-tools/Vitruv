package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import static edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseLanguageGeneratorConstants.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces.IResponseRealization

class SingleEmptyResponseGenerator implements ISingleResponseGenerator {
	private XtendImportHelper ih;
	
	protected new() {
		this.ih = new XtendImportHelper();	
	}
	
	public override generateResponseClass(String packageName, String className) {
		val classImplementation = '''
		public class «className» implements «ih.typeRef(IResponseRealization)» {
			public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
						ih.typeRef(Blackboard)» blackboard) {}	
		}
		'''	
		
		return generateClass(packageName, ih, classImplementation);
	}
}