package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.IScmChangeExtractor
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ScmChangeResult
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.NoSuchElementException
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.core.runtime.Path
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.RenameDetector
import org.eclipse.jgit.errors.MissingObjectException
import org.eclipse.jgit.lib.AnyObjectId
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevSort
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.CanonicalTreeParser

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
				val rawDiffs = diff.call
				val renameDetector = new RenameDetector(repository)
				renameDetector.addAll(rawDiffs)
				val diffs = renameDetector.compute
			
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
		var newContent = null as String
		var oldContent = null as String
		
		try {
			val newObjectLoader = repository.open(entry.newId.toObjectId)
			val newOutStream = new ByteArrayOutputStream()	
			newObjectLoader.copyTo(newOutStream)
			newContent = newOutStream.toString("UTF-8")
		} catch (MissingObjectException e) {
			logger.info("File seems to have been removed: " + entry.oldPath)
		}
		
		try {
			val oldObjectLoader = repository.open(entry.oldId.toObjectId)
			val oldOutStream = new ByteArrayOutputStream()	
			oldObjectLoader.copyTo(oldOutStream)
			oldContent = oldOutStream.toString("UTF-8")
		} catch (MissingObjectException e) {
			logger.info("File seems to have been added " + entry.newPath)
		}

		
		return new ScmChangeResult(Path.fromOSString(entry.newPath), newContent, Path.fromOSString(entry.oldPath), oldContent)
	}
	
}