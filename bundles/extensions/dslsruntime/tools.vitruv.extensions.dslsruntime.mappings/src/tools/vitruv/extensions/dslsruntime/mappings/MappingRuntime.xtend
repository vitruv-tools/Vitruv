package tools.vitruv.extensions.dslsruntime.mappings

import java.util.Set
import java.util.List

interface MappingRuntime<L extends MappingInstanceHalf, R extends MappingInstanceHalf> extends ElementRuntime {
	
	def Iterable<L> getLeftCandidates();
	def Iterable<R> getRightCandidates();
	def void addLeftCandidates(Iterable<L> candidates);
	def void addRightCandidates(Iterable<R> candidates);
	def Iterable<L> getLeftInstances();
	def Iterable<R> getRightInstances();
	def L getLeftInstanceHalf(List<Object> elements);
	def R getRightInstanceHalf(List<Object> elements);
	def Set<L> getLeftCandidatesAndInstances();
	def Set<R> getRightCandidatesAndInstances();
}