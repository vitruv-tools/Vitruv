package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors

import java.util.ArrayList
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
import org.eclipse.jgit.diff.DiffEntry.Side
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language
import java.io.InputStreamReader
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.util.IoUtil
import java.nio.file.Files
import java.nio.file.CopyOption
import java.nio.file.StandardCopyOption
import java.io.File

class SourceCodeChangeExtractor {
	
	private static final Logger logger = Logger.getLogger(typeof(GitActionExtractor))
	
	private Repository repository
	
	new(Repository repository) {
		this.repository = repository
	}
	
	def extract(AnyObjectId newVersion, AnyObjectId oldVersion) {
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
				logger.info('''Found «diffs.size» Java files''')
				
				val changes = diffs.map[createSourceCodeChanges(it, it.getPath(Side.NEW), it.getPath(Side.OLD), newVersion, oldVersion)]
				allResults.addAll(changes)		
				fromCommit = toCommit
				toCommit = try {commitIterator.next} catch (NoSuchElementException e) {null}
			}
			
			return allResults

		} finally {
			git.close
			reader.close
		}
		
	}
	
	private def createSourceCodeChanges(DiffEntry entry, String newPath, String oldPath, AnyObjectId newVersion, AnyObjectId oldVersion) {

		logger.info("----------------------------------------------------")
		logger.info("Distilling changes for file: " + newPath + "; Old path: " + oldPath)
		logger.info('''From «oldVersion» to «newVersion»''')

		val oldObjectLoader = repository.open(entry.oldId.toObjectId)
		val newObjectLoader = repository.open(entry.newId.toObjectId)
		
		val oldTemp = Files.createTempFile("old", ".java")
		Files.copy(oldObjectLoader.openStream, oldTemp, StandardCopyOption::REPLACE_EXISTING)
		
		val newTemp = Files.createTempFile("new", ".java")
		Files.copy(newObjectLoader.openStream, newTemp, StandardCopyOption::REPLACE_EXISTING)

		val changeDistiller = ChangeDistiller.createFileDistiller(Language.JAVA)
		try {
			changeDistiller.extractClassifiedSourceCodeChanges(oldTemp.toFile, oldVersion.toString, newTemp.toFile, newVersion.toString)
		} catch (Exception e) {
			logger.fatal("failed during change distilling")
		}
		
		
		logger.info("Done!") 
		return changeDistiller
	}
	
	private def findNewToOld(RevWalk revWalk, RevCommit oldCommit) {
		val revsNewToOld = new ArrayList
		for (rev : revWalk) {
			revsNewToOld.add(rev)
			if (rev.equals(oldCommit)) {
				return revsNewToOld
			}
		}
		return revsNewToOld
	}
	

	
	
}