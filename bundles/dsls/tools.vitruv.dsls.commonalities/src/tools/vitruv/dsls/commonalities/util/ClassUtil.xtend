package tools.vitruv.dsls.commonalities.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.HashMap
import java.util.Map

@Utility
class ClassUtil {

	static val Map<String, Class<?>> primitiveClassesByName = initPrimitiveClassesByName()

	private static def Map<String, Class<?>> initPrimitiveClassesByName() {
		val primitiveClasses = #[
			typeof(boolean), typeof(byte), typeof(char), typeof(double),
			typeof(float), typeof(int), typeof(long), typeof(short)
		]
		val primitiveClassesByName = new HashMap<String, Class<?>>
		for (Class<?> primitiveClass : primitiveClasses) {
			primitiveClassesByName.put(primitiveClass.name, primitiveClass)
		}
		return primitiveClassesByName
	}

	/**
	 * Gets the {@link Class} for the given name.
	 * <p>
	 * Unlike {@link Class#forName(String)} this can parse classes for
	 * primitive types as well.
	 * 
	 * @param name
	 * 		the fully qualified class name
	 * @return the class
	 * @throws ClassNotFoundException
	 * 		if the class is not found
	 */
	static def Class<?> getClassForName(String className) throws ClassNotFoundException {
		val primitiveClass = primitiveClassesByName.get(className)
		if (primitiveClass !== null) return primitiveClass
		return Class.forName(className)
	}
}
