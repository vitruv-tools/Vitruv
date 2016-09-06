package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.AnnotationTypeDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.DeclaredException
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.EnumDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FieldDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MethodDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PrimitiveTypeWithBrackets
import java.util.ArrayList
import org.apache.commons.lang.StringUtils
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Constructor
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Type
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl

/**
 * Converts a JML model element to a string representation.
 * This representation is not necessarily the concrete syntax
 * but might be anything appropriate to fulfil the need in the
 * place it is used.
 * Use the {@link edu.kit.ipd.sdq.vitruvius.domains.jml.language.ConcreteSyntaxHelper}
 * class for strict concrete syntax conversions.
 */
class StringOperationsJML {
	
	static def dispatch String getStringRepresentation(FieldDeclaration fd) {
		val ids = new ArrayList<String>()
		fd.variabledeclarator.forEach[ids.add(identifier)]
		return (fd.eContainer as MemberDeclaration).type.getStringRepresentation + StringUtils.join(ids, ",");
	}
	
	static def dispatch String getStringRepresentation(MethodDeclaration md) {
		val tuid = new StringBuilder();
		var Type returnType = null;
		if (md.eContainer instanceof MemberDeclaration) {
			returnType = (md.eContainer as MemberDeclaration).type
		} else if (md.eContainer instanceof GenericMethodOrConstructorDecl) {
			returnType = (md.eContainer as GenericMethodOrConstructorDecl).type
		}
		if (returnType == null) {
			tuid.append("void")
		} else {
			tuid.append(returnType.getStringRepresentation)
		}
		tuid.append(md.identifier)
		md.parameters.forEach[tuid.append(type.getStringRepresentation + varargs.varargsString)]
		return tuid.toString;
	}

	static def dispatch String getStringRepresentation(MemberDeclaration memberDecl) {
		//FIXME SS: this method obviously does not what we expect...
		if (memberDecl.type == null) {
			return "void"
		}
		return memberDecl.type.getStringRepresentation
	}
	
	static def dispatch String getStringRepresentation(Constructor jmlConstructor) {
		val representation = new StringBuilder()
		representation.append(jmlConstructor.identifier)
		representation.append('(')
		jmlConstructor.parameters.forEach[representation.append(type.getStringRepresentation + varargs.varargsString)]
		representation.append(')')
		return representation.toString
	}

	static def dispatch String getStringRepresentation(ClassOrInterfaceTypeWithBrackets coit) {
		//TODO type arguments
		val identifiers = new ArrayList<String>()
		coit.type.forEach[identifiers.add(identifier)]
		return StringUtils.join(identifiers, ".") + StringUtils.repeat("[]", coit.brackets.size)
	}

	static def dispatch String getStringRepresentation(PrimitiveTypeWithBrackets pt) {
		return pt.primitivetype.literal + StringUtils.repeat("[]", pt.brackets.size)
	}
	
	static def dispatch String getStringRepresentation(DeclaredException de) {
		return de.name
	}


	
	public static def dispatch String getIdentifier(NormalClassDeclaration decl) {
		return decl.identifier
	}
	
	public static def dispatch String getIdentifier(EnumDeclaration decl) {
		return decl.identifier
	}
	
	public static def dispatch String getIdentifier(NormalInterfaceDeclaration decl) {
		return decl.identifier
	}
	
	public static def dispatch String getIdentifier(AnnotationTypeDeclaration decl) {
		return decl.identifier
	}
	
	
	private static def String getVarargsString(boolean varargs) {
		if (varargs) {
			return "..."
		}
		return ""
	}
}
