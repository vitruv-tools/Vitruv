package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import java.util.NoSuchElementException
import org.apache.log4j.Logger
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevSort
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.util.ArrayList
import org.eclipse.jgit.treewalk.filter.PathFilter
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.IScmChangeExtractor
import java.io.ByteArrayOutputStream
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ScmChangeResult
import org.eclipse.core.runtime.Path
import org.apache.log4j.Level

class GitChangeExtractor implements IScmChangeExtractor<AnyObjectId> {
	
	private static final Logger logger = Logger.getLogger(typeof(GitChangeExtractor))
	
	private Repository repository
	
	new(Repository repository) {
		logger.level = Level.ALL
		this.repository = repository
	}
	
	override extract(AnyObjectId newVersion, AnyObjectId oldVersion) {
		//TODO if commits are not neighbors iterate over each commit-pair between
		logger.info('''Computing changes between git repo versions «newVersion» to «oldVersion»''')
		val reader = repository.newObjectReader()
		val git = new Git(repository)
		val revWalk = new RevWalk(repository)
		try {
			revWalk.markStart(revWalk.parseCommit(newVersion))
			val oldCommit = revWalk.parseCommit(oldVersion)
			
			revWalk.sort(RevSort.COMMIT_TIME_DESC)
			val revsNewToOld = findNewToOld(revWalk, oldCommit)
			val oldToNew = revsNewToOld.sortBy[it.commitTime]
			val commitIterator = oldToNew.iterator
			var fromCommit = commitIterator.next
			var toCommit = commitIterator.next
			val allResults = new ArrayList()
			while (toCommit != null) {
				val newTree = new CanonicalTreeParser
				newTree.reset(reader, toCommit.tree.id)
				val oldTree = new CanonicalTreeParser
				oldTree.reset(reader, fromCommit.tree.id)
				val diff = git.diff
				diff.newTree = newTree
				diff.oldTree = oldTree
				val diffs = diff.call
			
				val result = diffs.map[createResult(it)]
				allResults.addAll(result)
				
				fromCommit = toCommit
				toCommit = try {commitIterator.next} catch (NoSuchElementException e) {null}
			}
			
			return allResults

		} finally {
			git.close
			reader.close
		}
		
	}
	
	def findNewToOld(RevWalk revWalk, RevCommit oldCommit) {
		val revsNewToOld = new ArrayList
		for (rev : revWalk) {
			revsNewToOld.add(rev)
			if (rev.equals(oldCommit)) {
				return revsNewToOld
			}
		}
		return revsNewToOld
	}
	
	private def createResult(DiffEntry entry) {
		val newObjectLoader = repository.open(entry.newId.toObjectId)
		val oldObjectLoader = repository.open(entry.oldId.toObjectId)
		logger.info("Path: " + entry.newPath)
		
		val newOutStream = new ByteArrayOutputStream()	
		newObjectLoader.copyTo(newOutStream)
		val newContent = newOutStream.toString("UTF-8")
		
		val oldOutStream = new ByteArrayOutputStream()	
		oldObjectLoader.copyTo(oldOutStream)
		val oldContent = oldOutStream.toString("UTF-8")
		
		return new ScmChangeResult(Path.fromOSString(entry.newPath), newContent, oldContent)
	}
	
}