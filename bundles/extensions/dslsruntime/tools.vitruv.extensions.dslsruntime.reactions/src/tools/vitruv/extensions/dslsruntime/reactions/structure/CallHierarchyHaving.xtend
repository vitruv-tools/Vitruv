package tools.vitruv.extensions.dslsruntime.reactions.structure

class CallHierarchyHaving extends Loggable {
	private val CallHierarchyHaving calledBy;
	
	public new() {
		calledBy = null;
	}
	
	public new (CallHierarchyHaving calledBy) {
		this.calledBy = calledBy;
	}
	
	public def CallHierarchyHaving getCalledBy() {
		return calledBy;
	}
	
	public def String getCalledByString() {
		return '''(«this.class.simpleName»)«IF calledBy !== null» called by «calledBy.calledByString»«ENDIF»''';
	}
}