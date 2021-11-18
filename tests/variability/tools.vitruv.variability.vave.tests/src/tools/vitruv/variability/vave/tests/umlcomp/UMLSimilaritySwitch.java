package tools.vitruv.variability.vave.tests.umlcomp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralSpecification;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * Internal switch class to prove element similarity.
 * 
 * <p>
 * The similarity case methods do not need to check for null values. It is
 * assumed that the calling class does a null value check for the elements to
 * compare in advanced, such as done by the SimilarityChecker class.
 * </p>
 * 
 * <p>
 * Check strategy:<br>
 * First all "not-similar"-criteria are checked. If none hits, true will be
 * returned.
 * </p>
 */
public class UMLSimilaritySwitch extends ComposedSwitch<Boolean> {

	private EObject compareElement = null;

	private UMLSimilarityChecker similarityChecker = null;

	/**
	 * Constructor requiring the element to compare with.
	 * 
	 * @param compareElement                The right-side / original element to
	 *                                      check the similarity against.
	 * @param checkStatementPosition        Flag if the similarity check should
	 *                                      consider the position of a statement or
	 *                                      not.
	 * @param classifierNormalizations      A list of patterns replace any match in
	 *                                      a classifier name with the defined
	 *                                      replacement string.
	 * @param compilationUnitNormalizations A list of patterns replace any match in
	 *                                      a compilation unit name with the defined
	 *                                      replacement string.
	 * @param packageNormalizations         A list of package normalization
	 *                                      patterns.
	 */
	public UMLSimilaritySwitch(EObject compareElement, boolean checkStatementPosition,
			LinkedHashMap<Pattern, String> classifierNormalizations,
			LinkedHashMap<Pattern, String> compilationUnitNormalizations,
			LinkedHashMap<Pattern, String> packageNormalizations) {
		this.similarityChecker = new UMLSimilarityChecker(classifierNormalizations, compilationUnitNormalizations,
				packageNormalizations);
		this.compareElement = compareElement;
		addSwitch(new GeneralUMLSimilaritySwitch());
	}

	private class GeneralUMLSimilaritySwitch extends org.eclipse.uml2.uml.util.UMLSwitch<Boolean> {
		@Override
		public Boolean caseModel(Model object) {
			return object.getName().equals(((Model) compareElement).getName());
		}

		@Override
		public Boolean casePackage(org.eclipse.uml2.uml.Package object) {
			return object.getQualifiedName().equals(((org.eclipse.uml2.uml.Package) compareElement).getQualifiedName());
		}

		@Override
		public Boolean caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object) {
			return object.getValue() == ((LiteralUnlimitedNatural) compareElement).getValue();
		}
		@Override
		public Boolean caseLiteralInteger(LiteralInteger object) {
			return object.getValue() == ((LiteralInteger) compareElement).getValue();
		}

//		@Override
//		public Boolean caseLiteralBoolean(LiteralBoolean object) {
//			// TODO Auto-generated method stub
//			return super.caseLiteralBoolean(object);
//		}
//
//		@Override
//		public Boolean caseLiteralNull(LiteralNull object) {
//			// TODO Auto-generated method stub
//			return super.caseLiteralNull(object);
//		}
//		
//		@Override
//		public Boolean caseLiteralReal(LiteralReal object) {
//			// TODO Auto-generated method stub
//			return super.caseLiteralReal(object);
//		}
//		
//		@Override
//		public Boolean caseLiteralString(LiteralString object) {
//			// TODO Auto-generated method stub
//			return super.caseLiteralString(object);
//		}
//		
//		@Override
//		public Boolean caseLiteralSpecification(LiteralSpecification object) {
//			// TODO Auto-generated method stub
//			return super.caseLiteralSpecification(object);
//		}
		
		@Override
		public Boolean casePrimitiveType(PrimitiveType object) {
			if (!(compareElement instanceof PrimitiveType))
				return Boolean.FALSE;
			
			String name1 = object.getQualifiedName();
			String name2 = ((PrimitiveType) compareElement).getQualifiedName();

			Collection<String> typeNames = new ArrayList<>(2);
			typeNames.add(name1);
			typeNames.add(name2);

			if (typeNames.contains("PrimitiveTypes::Integer")
					&& (typeNames.contains("JavaLibrary::int") || typeNames.contains("JavaLibrary::Integer")
							|| typeNames.contains("JavaLibrary::long") || typeNames.contains("JavaLibrary::Long")
							|| typeNames.contains("JavaLibrary::short") || typeNames.contains("JavaLibrary::Short")
							|| typeNames.contains("JavaLibrary::byte") || typeNames.contains("JavaLibrary::Byte")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::Boolean")
					&& (typeNames.contains("JavaLibrary::boolean") || typeNames.contains("JavaLibrary::Boolean")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::Real")
					&& (typeNames.contains("JavaLibrary::double") || typeNames.contains("JavaLibrary::Double")
							|| typeNames.contains("JavaLibrary::float") || typeNames.contains("JavaLibrary::Float")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::String") && (typeNames.contains("java.lang.String")))
				return true;
			
			return object.getQualifiedName().equals(((Type) compareElement).getQualifiedName()); 
		}

		@Override
		public Boolean caseType(Type object) {
			if (!(compareElement instanceof Type))
				return Boolean.FALSE;
			
			String name1 = object.getQualifiedName();
			String name2 = ((Type) compareElement).getQualifiedName();

			Collection<String> typeNames = new ArrayList<>(2);
			typeNames.add(name1);
			typeNames.add(name2);

			if (typeNames.contains("PrimitiveTypes::Integer")
					&& (typeNames.contains("JavaLibrary::int") || typeNames.contains("JavaLibrary::Integer")
							|| typeNames.contains("JavaLibrary::long") || typeNames.contains("JavaLibrary::Long")
							|| typeNames.contains("JavaLibrary::short") || typeNames.contains("JavaLibrary::Short")
							|| typeNames.contains("JavaLibrary::byte") || typeNames.contains("JavaLibrary::Byte")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::Boolean")
					&& (typeNames.contains("JavaLibrary::boolean") || typeNames.contains("JavaLibrary::Boolean")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::Real")
					&& (typeNames.contains("JavaLibrary::double") || typeNames.contains("JavaLibrary::Double")
							|| typeNames.contains("JavaLibrary::float") || typeNames.contains("JavaLibrary::Float")))
				return true;
			else if (typeNames.contains("PrimitiveTypes::String") && (typeNames.contains("java.lang.String")))
				return true;

			return object.getQualifiedName().equals(((Type) compareElement).getQualifiedName());
		}

//		@Override
//		public Boolean caseClassifier(Classifier object) {
//			return object.getQualifiedName().equals(((Classifier) compareElement).getQualifiedName());
//		}

//		@Override
//		public Boolean caseClass(Class object) {
//			if (compareElement instanceof Class)
//				return object.getQualifiedName().equals(((Class) compareElement).getQualifiedName());
//			else if (compareElement instanceof Interface)
//				return object.getQualifiedName().equals(((Interface) compareElement).getQualifiedName());
//			else
//				return Boolean.FALSE;
//		}
//
//		@Override
//		public Boolean caseInterface(Interface object) {
//			if (compareElement instanceof Class)
//				return object.getQualifiedName().equals(((Class) compareElement).getQualifiedName());
//			else if (compareElement instanceof Interface)
//				return object.getQualifiedName().equals(((Interface) compareElement).getQualifiedName());
//			else
//				return Boolean.FALSE;
//		}

		@Override
		public Boolean caseProperty(Property object) {
			return object.getQualifiedName().equals(((Property) compareElement).getQualifiedName());
		}

		@Override
		public Boolean caseOperation(Operation object) {
			Operation compareOperation = (Operation) compareElement;

			if (!object.getQualifiedName().equals(compareOperation.getQualifiedName()))
				return false;

			List<Parameter> inputParameters1 = object.getOwnedParameters().stream()
					.filter(p -> p.getDirection() == ParameterDirectionKind.IN_LITERAL).collect(Collectors.toList());
			List<Parameter> inputParameters2 = compareOperation.getOwnedParameters().stream()
					.filter(p -> p.getDirection() == ParameterDirectionKind.IN_LITERAL).collect(Collectors.toList());

			if (inputParameters1.size() != inputParameters2.size())
				return false;

			for (int i = 0; i < inputParameters1.size(); i++) {
				Parameter param1 = inputParameters1.get(i);
				Parameter param2 = inputParameters2.get(i);

//				if (param1.getType() == null && param2.getType() != null
//						|| param1.getType() != null && param2.getType() == null)
//					return Boolean.FALSE;
//				if (param1.getType() != null && param2.getType() != null
//						&& !Objects.equals(param1.getType().getQualifiedName(), param2.getType().getQualifiedName()))
//					return Boolean.FALSE;
//
//				System.out.println(
//						"PARS: " + param1.getType().getQualifiedName() + " / " + param2.getType().getQualifiedName());

				Type type1 = param1.getType();
				Type type2 = param2.getType();
				Boolean typeSimilarity = similarityChecker.isSimilarIgnoringType(type1, type2);
				if (typeSimilarity == Boolean.FALSE) {
					return Boolean.FALSE;
				}
				// TODO: check cardinalities?
			}

			return true;
		}

		@Override
		public Boolean caseParameter(org.eclipse.uml2.uml.Parameter parameter1) {
			Parameter parameter2 = (Parameter) compareElement;

			if (!Objects.equals(parameter1.getQualifiedName(), parameter2.getQualifiedName()))
				return false;

//			if (!similarityChecker.isSimilar(parameter1.getType(), parameter2.getType()))
//				return false;

			return true;
		}

		@Override
		public Boolean caseGeneralization(Generalization gen1) {
			Generalization gen2 = (Generalization) compareElement;

			if (!similarityChecker.isSimilarIgnoringType(gen1.getSpecific(), gen2.getSpecific()))
				return false;

			if (!similarityChecker.isSimilarIgnoringType(gen1.getGeneral(), gen2.getGeneral()))
				return false;

			return true;
		}

		@Override
		public Boolean caseInterfaceRealization(InterfaceRealization ir1) {
			InterfaceRealization ir2 = (InterfaceRealization) compareElement;

			if (!similarityChecker.isSimilarIgnoringType(ir1.getImplementingClassifier(), ir2.getImplementingClassifier()))
				return false;

			if (!similarityChecker.isSimilarIgnoringType(ir1.getContract(), ir2.getContract()))
				return false;

			return true;
		}

		@Override
		public Boolean defaultCase(EObject object) {
			return Boolean.TRUE;
		}
	}

	/**
	 * The default case for not explicitly handled elements always returns null to
	 * identify the open decision.
	 * 
	 * @param object The object to compare with the compare element.
	 * @return null
	 */
	@Override
	public Boolean defaultCase(EObject object) {
		return null;
	}
}
