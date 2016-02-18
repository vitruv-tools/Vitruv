package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava

import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.Interface
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.core.entity.Entity

class PCMJavaHelper {
	private static def String getQualifiedName(Repository repository) {
		repository.entityName.toFirstLower;
	}
	
	public static final val interfaceToInterface = new CorrespondingInterface();
	public static final val repositoryToJavaHelper = new CorrespondingJavaHelper();
	public static final val componentToClass = new CorrespondingJavaClass();
		
	public abstract static class CorrespondingClass<E extends EObject> {
		public abstract def String getClassName(E object);
		public abstract def String getPackageName(E object);
		public def String getQualifiedClassName(E object) {
			return object.packageName + "." + object.className;
		}
		public def String getPathInProject(E object) {
			return "src/" + object.qualifiedClassName.replace(".", "/") + ".java";
		}
	}
	
	public abstract static class CorrespondingToEntityClass<E extends Entity> extends CorrespondingClass<E> {
		override getClassName(E object) {
			return object.entityName.toFirstUpper;
		}
	}
	
	public static class CorrespondingInterface extends CorrespondingToEntityClass<Interface> {
		override getPackageName(Interface object) {
			object.repository__Interface.qualifiedName;
		}
	}
	
	public static class CorrespondingJavaHelper extends CorrespondingToEntityClass<Repository> {
		override getClassName(Repository object) {
			return super.getClassName(object) + "Helper";
		}
		
		override getPackageName(Repository object) {
			object.qualifiedName;
		}
	}
	
	public static class CorrespondingJavaClass extends CorrespondingToEntityClass<RepositoryComponent> {
		override getClassName(RepositoryComponent object) {
			return super.getClassName(object) + "Impl";
		}
		
		override getPackageName(RepositoryComponent object) {
			return object.repository__RepositoryComponent.qualifiedName + "." + object.entityName;
		}
	}
	
}