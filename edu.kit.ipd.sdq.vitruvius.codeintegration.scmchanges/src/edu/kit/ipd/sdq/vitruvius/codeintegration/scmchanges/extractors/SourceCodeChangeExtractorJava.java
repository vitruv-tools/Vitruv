package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

	import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
	import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
	import com.google.common.base.Objects;
	import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.GitActionExtractor;
	import java.io.File;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.StandardCopyOption;
	import java.util.ArrayList;
	import java.util.Iterator;
	import java.util.List;
	import java.util.NoSuchElementException;
	import org.apache.log4j.Logger;
	import org.eclipse.jgit.api.DiffCommand;
	import org.eclipse.jgit.api.Git;
	import org.eclipse.jgit.diff.DiffEntry;
	import org.eclipse.jgit.lib.AbbreviatedObjectId;
	import org.eclipse.jgit.lib.AnyObjectId;
	import org.eclipse.jgit.lib.ObjectId;
	import org.eclipse.jgit.lib.ObjectLoader;
	import org.eclipse.jgit.lib.ObjectReader;
	import org.eclipse.jgit.lib.ObjectStream;
	import org.eclipse.jgit.lib.Repository;
	import org.eclipse.jgit.revwalk.RevCommit;
	import org.eclipse.jgit.revwalk.RevSort;
	import org.eclipse.jgit.revwalk.RevTree;
	import org.eclipse.jgit.revwalk.RevWalk;
	import org.eclipse.jgit.treewalk.CanonicalTreeParser;
	import org.eclipse.xtend2.lib.StringConcatenation;
	import org.eclipse.xtext.xbase.lib.Exceptions;
	import org.eclipse.xtext.xbase.lib.Functions.Function1;
	import org.eclipse.xtext.xbase.lib.IterableExtensions;
	import org.eclipse.xtext.xbase.lib.ListExtensions;

	@SuppressWarnings("all")
public class SourceCodeChangeExtractorJava {
	  private final static Logger logger = Logger.getLogger(GitActionExtractor.class);
	  
	  private Repository repository;
	  
	  public SourceCodeChangeExtractorJava(final Repository repository) {
	    this.repository = repository;
	  }
	  
	  public ArrayList<FileDistiller> extract(final AnyObjectId newVersion, final AnyObjectId oldVersion) {
	    try {
	      StringConcatenation _builder = new StringConcatenation();
	      _builder.append("Computing changes between git repo versions ");
	      _builder.append(newVersion, "");
	      _builder.append(" to ");
	      _builder.append(oldVersion, "");
	      SourceCodeChangeExtractorJava.logger.info(_builder);
	      final ObjectReader reader = this.repository.newObjectReader();
	      final Git git = new Git(this.repository);
	      final RevWalk revWalk = new RevWalk(this.repository);
	      try {
	        RevCommit _parseCommit = revWalk.parseCommit(newVersion);
	        revWalk.markStart(_parseCommit);
	        final RevCommit oldCommit = revWalk.parseCommit(oldVersion);
	        revWalk.sort(RevSort.COMMIT_TIME_DESC);
	        final ArrayList<RevCommit> revsNewToOld = this.findNewToOld(revWalk, oldCommit);
	        final Function1<RevCommit, Integer> _function = (RevCommit it) -> {
	          return Integer.valueOf(it.getCommitTime());
	        };
	        final List<RevCommit> oldToNew = IterableExtensions.<RevCommit, Integer>sortBy(revsNewToOld, _function);
	        final Iterator<RevCommit> commitIterator = oldToNew.iterator();
	        RevCommit fromCommit = commitIterator.next();
	        RevCommit toCommit = commitIterator.next();
	        final ArrayList<FileDistiller> allResults = new ArrayList<FileDistiller>();
	        while ((!Objects.equal(toCommit, null))) {
	          {
	            final CanonicalTreeParser newTree = new CanonicalTreeParser();
	            RevTree _tree = toCommit.getTree();
	            ObjectId _id = _tree.getId();
	            newTree.reset(reader, _id);
	            final CanonicalTreeParser oldTree = new CanonicalTreeParser();
	            RevTree _tree_1 = fromCommit.getTree();
	            ObjectId _id_1 = _tree_1.getId();
	            oldTree.reset(reader, _id_1);
	            final DiffCommand diff = git.diff();
	            diff.setNewTree(newTree);
	            diff.setOldTree(oldTree);
	            final List<DiffEntry> diffs = diff.call();
	            StringConcatenation _builder_1 = new StringConcatenation();
	            _builder_1.append("Found ");
	            int _size = diffs.size();
	            _builder_1.append(_size, "");
	            _builder_1.append(" Java files");
	            SourceCodeChangeExtractorJava.logger.info(_builder_1);
	            final Function1<DiffEntry, FileDistiller> _function_1 = (DiffEntry it) -> {
	              String _path = it.getPath(DiffEntry.Side.NEW);
	              String _path_1 = it.getPath(DiffEntry.Side.OLD);
	              return this.createSourceCodeChanges(it, _path, _path_1, newVersion, oldVersion);
	            };
	            final List<FileDistiller> changes = ListExtensions.<DiffEntry, FileDistiller>map(diffs, _function_1);
	            allResults.addAll(changes);
	            fromCommit = toCommit;
	            RevCommit _xtrycatchfinallyexpression = null;
	            try {
	              _xtrycatchfinallyexpression = commitIterator.next();
	            } catch (final Throwable _t) {
	              if (_t instanceof NoSuchElementException) {
	                final NoSuchElementException e = (NoSuchElementException)_t;
	                _xtrycatchfinallyexpression = null;
	              } else {
	                throw Exceptions.sneakyThrow(_t);
	              }
	            }
	            toCommit = _xtrycatchfinallyexpression;
	          }
	        }
	        return allResults;
	      } finally {
	        git.close();
	        reader.close();
	      }
	    } catch (Throwable _e) {
	      throw Exceptions.sneakyThrow(_e);
	    }
	  }
	  
	  private FileDistiller createSourceCodeChanges(final DiffEntry entry, final String newPath, final String oldPath, final AnyObjectId newVersion, final AnyObjectId oldVersion) {
	    try {
	      SourceCodeChangeExtractorJava.logger.info("----------------------------------------------------");
	      SourceCodeChangeExtractorJava.logger.info(((("Distilling changes for file: " + newPath) + "; Old path: ") + oldPath));
	      StringConcatenation _builder = new StringConcatenation();
	      _builder.append("From ");
	      _builder.append(oldVersion, "");
	      _builder.append(" to ");
	      _builder.append(newVersion, "");
	      SourceCodeChangeExtractorJava.logger.info(_builder);
	      AbbreviatedObjectId _oldId = entry.getOldId();
	      ObjectId _objectId = _oldId.toObjectId();
	      final ObjectLoader oldObjectLoader = this.repository.open(_objectId);
	      AbbreviatedObjectId _newId = entry.getNewId();
	      ObjectId _objectId_1 = _newId.toObjectId();
	      final ObjectLoader newObjectLoader = this.repository.open(_objectId_1);
	      final Path oldTemp = Files.createTempFile("old", ".java");
	      ObjectStream _openStream = oldObjectLoader.openStream();
	      Files.copy(_openStream, oldTemp, StandardCopyOption.REPLACE_EXISTING);
	      final Path newTemp = Files.createTempFile("new", ".java");
	      ObjectStream _openStream_1 = newObjectLoader.openStream();
	      Files.copy(_openStream_1, newTemp, StandardCopyOption.REPLACE_EXISTING);
	      final FileDistiller changeDistiller = ChangeDistiller.createFileDistiller(ChangeDistiller.Language.JAVA);
	      try {
	        File _file = oldTemp.toFile();
	        String _string = oldVersion.toString();
	        File _file_1 = newTemp.toFile();
	        String _string_1 = newVersion.toString();
	        changeDistiller.extractClassifiedSourceCodeChanges(_file, _string, _file_1, _string_1);
	      } catch (final Throwable _t) {
	        if (_t instanceof Exception) {
	          final Exception e = (Exception)_t;
	          SourceCodeChangeExtractorJava.logger.fatal("failed during change distilling");
	        } else {
	          throw Exceptions.sneakyThrow(_t);
	        }
	      }
	      SourceCodeChangeExtractorJava.logger.info("Done!");
	      return changeDistiller;
	    } catch (Throwable _e) {
	      throw Exceptions.sneakyThrow(_e);
	    }
	  }
	  
	  private ArrayList<RevCommit> findNewToOld(final RevWalk revWalk, final RevCommit oldCommit) {
	    final ArrayList<RevCommit> revsNewToOld = new ArrayList<RevCommit>();
	    for (final RevCommit rev : revWalk) {
	      {
	        revsNewToOld.add(rev);
	        boolean _equals = rev.equals(oldCommit);
	        if (_equals) {
	          return revsNewToOld;
	        }
	      }
	    }
	    return revsNewToOld;
	  }
	


}
