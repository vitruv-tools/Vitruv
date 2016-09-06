package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Type
import java.util.ArrayList
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils
import org.emftext.language.java.commons.NamedElement

/**
 * Base class for shadow copy helpers, which convert between Java and JML or vice versa.
 * It defines important common strings, which are necessary for conversions in both
 * directions.
 */
class ShadowCopyJavaJmlHelperBase {
	
	private static val JAVA_FIELD_SPEC_METHOD_PREFIX = "JML_FIELDSPECS_";
	private static val JML_REPLACEMENT_RESULT_METHOD_NAME = "JML_REPLACEMENT_RESULT"
	private static val JML_REPLACEMENT_OLD_METHOD_NAME = "JML_REPLACEMENT_OLD"
	private static val JML_REPLACEMENT_FRESH_METHOD_NAME = "JML_REPLACEMENT_FRESH"
	private static val JAVA_ASSIGNMENT_NAME_PREFIX = "JML_TMP_"
	private static val JAVA_ASSIGNMENT_PREFIX = "java.lang.Object " + JAVA_ASSIGNMENT_NAME_PREFIX
	private static val JAVA_TYPE_SPECIFICATIONS_METHOD_NAME = "JML_TYPESPECS"
	
	public static def isAReplacementMethod(NamedElement name) {
		return name.name.startsWith(JML_REPLACEMENT_RESULT_METHOD_NAME) || name.name.equals(JML_REPLACEMENT_OLD_METHOD_NAME) || name.name.equals(JML_REPLACEMENT_FRESH_METHOD_NAME) 
	}
	
	protected static def getJmlReplacementOld() {
		return JML_REPLACEMENT_OLD_METHOD_NAME
	}
	
	protected static def getJmlReplacementFresh() {
		return JML_REPLACEMENT_FRESH_METHOD_NAME
	}
	
	protected static def getJmlReplacementPrefixResult() {
		return JML_REPLACEMENT_RESULT_METHOD_NAME
	}
	
	protected static def getJmlReplacementResult(Type type) {
		return getJmlReplacementPrefixResult() + "_" + type.typeName.replaceAll("\\[\\]", "_ARRAY").toUpperCase + "()"
	}
	
	protected static def getJavaAssignmentNamePrefix() {
		return JAVA_ASSIGNMENT_NAME_PREFIX
	}
	
	protected static def getJavaAssignmentPrefix() {
		return JAVA_ASSIGNMENT_PREFIX
	}
	
	protected static def getJavaTypeSpecificationsMethodName() {
		return JAVA_TYPE_SPECIFICATIONS_METHOD_NAME
	}
	
	protected static def getRandomString() {
		return RandomStringUtils.random(10, true, true)
	}
	
	private static def dispatch getTypeName(PrimitiveTypeWithBrackets type) {
		return type.primitivetype.literal + StringUtils.repeat("[]", type.brackets.size)
	}
	
	private static def dispatch getTypeName(ClassOrInterfaceTypeWithBrackets type) {
		val identifierList = new ArrayList<String>()
		type.type.forEach[identifierList.add(identifier)]
		return StringUtils.join(identifierList, '.') + StringUtils.repeat("[]", type.brackets.size)
	}
	
	protected static def getJmlReplacementOldMethodString()
	'''
	public static <T> T �JML_REPLACEMENT_OLD_METHOD_NAME� (T param) {return param;}
	'''
	
	protected static def getJmlReplacementFreshMethodString()
	'''
	public static <T> T �JML_REPLACEMENT_FRESH_METHOD_NAME� (T param) {return param;}
	'''
	
	protected static def getJmlReplacementResultMethodString(Type type)
	'''
	private static �type.typeName� �type.jmlReplacementResult� {return (�type.typeName�)new java.lang.Object();}
	'''
	
	protected static def getJavaFieldSpecificationMethodName(String fieldName) {
		return JAVA_FIELD_SPEC_METHOD_PREFIX + fieldName
	}
	
	protected static def getJavaFieldSpecificationMethodString(String fieldName) {
		return "private static void " + fieldName.javaFieldSpecificationMethodName + "(){}"
	}
	
}