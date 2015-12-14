package edu.kit.ipd.sdq.vitruvius.dsls.mapping.generator

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.DefaultContainExpression
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NamedEClass
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*

@Accessors(PUBLIC_GETTER)
class MappingLanguageGeneratorState {
	private List<Mapping> mappings
	private List<Import> imports
	private Map<Mapping, List<Import>> mappingToImports
	private Map<Mapping, Map<Import, List<NamedEClass>>> mappingToImportToNamedEClasses
	private Map<Mapping, Map<Import, List<ConstraintExpression>>> mappingToImportToConstraints

	public new(MappingFile file) {
		this.imports = file.imports.claim[size == 2]
		this.mappings = file.mappings.sortWith [ m1, m2 |
			if(m1.requires.contains(m2)) ( 1 ) else ( -1)
		]
		
		mappingToImports = newHashMap
		mappingToImportToNamedEClasses = newHashMap
		mappingToImportToConstraints = newHashMap
		for (mapping : mappings) {
			val importToNamedEClasses = newHashMap
			val importToConstraints = newHashMap
			for (imp : imports) {
				val namedEClasses = mapping.signatures.map[elements].flatten.filterWithPackage(imp.package).toList
				importToNamedEClasses.put(imp, namedEClasses)
				val constraints = mapping.constraints.map[expressions].flatten.filterWithPackage(imp.package).toList
				importToConstraints.put(imp, constraints)
				
				if (!(namedEClasses.empty && constraints.empty)) {
					mappingToImports.getOrPut(mapping, [newArrayList]).add(imp)
				}
			}
			mappingToImportToNamedEClasses.put(mapping, importToNamedEClasses)
			mappingToImportToConstraints.put(mapping, importToConstraints)
		}
	}
	
	public def getNamedEClasses(Mapping mapping, Import imp) {
		return mappingToImportToNamedEClasses.get(mapping)?.get(imp) ?: #[]
	}
	
	public def getConstraints(Mapping mapping, Import imp) {
		return mappingToImportToConstraints.get(mapping)?.get(imp) ?: #[]
	}
	
	public def getDefaultContainments(Mapping mapping, Import imp) {
		return mappingToImportToConstraints.get(mapping)?.get(imp)?.filter(DefaultContainExpression) ?: #[]
	}
	
	public def getImports(Mapping mapping) {
		return mappingToImports.get(mapping) ?: #[]
	}
	
	public def getImportLetter(Import imp) {
		val index = imports.indexOf(imp)
		if (index == -1) {
			return null
		} else {
			return #["A", "B"].get(index)
		}
	}
}