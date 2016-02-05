package edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.function.BiFunction
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*

class TemplateGenerator {
	// simple templating mechanism to allow for addition of code
	private Map<Pair<Object, String>, List<BiFunction<ImportHelper, TemplateGenerator, CharSequence>>> templateKey2content = newHashMap
	private Collection<Pair<CharSequence, BiFunction<ImportHelper, TemplateGenerator, CharSequence>>> templates = newHashSet

	public def void addTemplateJavaFile(CharSequence fqn,
		BiFunction<ImportHelper, TemplateGenerator, CharSequence> generatorFunction) {
		templates.add(new Pair(fqn, generatorFunction))
	}

	public def void generateAllTemplates(IFileSystemAccess fsa) {
		for (template : templates) {
			val fqn = template.first
			val generatorFunction = template.second

			var content = JavaGeneratorHelper.generate(fqn.toString.classNameToPackageName, [ ih | generatorFunction.apply(ih, this) ])
			fsa.generateFile(fqn.toString.classNameToJavaPath, content)
		}
	}

	public def List<BiFunction<ImportHelper, TemplateGenerator, CharSequence>> getTemplateContent(Object parent, String id) {
		println('''requested id: «id»''')
		templateKey2content.getOrPut(new Pair(parent, id), [newArrayList])
	}

	public def void appendToTemplateExpression(Object parent, String id, BiFunction<ImportHelper, TemplateGenerator, CharSequence> content) {
		getTemplateContent(parent, id).add(content)
	}
	
	public def void prependToTemplateExpression(Object parent, String id, BiFunction<ImportHelper, TemplateGenerator, CharSequence> content) {
		getTemplateContent(parent, id).add(0, content)
	}
	
	public def CharSequence expandTemplate(ImportHelper ih, Object parent, String id) {
		templateKey2content.getOrDefault(new Pair(parent, id), #[]).map[it.apply(ih, this)].join
	}
}