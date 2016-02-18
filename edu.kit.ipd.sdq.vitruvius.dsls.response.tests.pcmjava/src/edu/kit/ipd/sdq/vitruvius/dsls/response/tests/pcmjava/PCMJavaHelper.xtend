package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava

import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Repository

class PCMJavaHelper {
	private static def String getQualifiedPath(Repository repository) {
		repository.entityName.toFirstLower;
	}
	
	public static def String getCorrespondingJavaHelperClassName(Repository repository) {
		return repository.entityName.toFirstUpper + "Helper";
	}
	
	public static def String getCorrespondingJavaHelperPackage(Repository repository) {
		return repository.qualifiedPath;
	}
	
	public static def String getCorrespondingJavaHelperQualifiedName(Repository repository) {
		return repository.qualifiedPath + "." + repository.correspondingJavaHelperClassName;
	}
	
	public static def String getCorrespondingJavaHelperPath(Repository repository) {
		return "src/" + repository.correspondingJavaHelperQualifiedName.replace(".", "/");
	}
	
	public static def String getCorrespondingJavaClassName(RepositoryComponent comp) {
		return comp.entityName.toFirstUpper + "Impl";
	}
	
	public static def String getCorrespondingJavaClassPackage(RepositoryComponent comp) {
		return comp.repository__RepositoryComponent.qualifiedPath + "." + comp.entityName;
	}
	
	public static def String getCorrespondingJavaClassQualifiedName(RepositoryComponent comp) {
		return comp.correspondingJavaClassPackage + "." + comp.correspondingJavaClassName;
	}
	
	public static def String getCorrespondingJavaClassPath(RepositoryComponent comp) {
		return "src/" + comp.correspondingJavaClassQualifiedName.replace(".", "/");
	}
	
}