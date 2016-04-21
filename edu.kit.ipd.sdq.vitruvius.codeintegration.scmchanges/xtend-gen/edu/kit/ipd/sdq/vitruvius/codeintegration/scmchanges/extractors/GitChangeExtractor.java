package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ScmChangeResult;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.IScmChangeExtractor;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
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
public class GitChangeExtractor implements IScmChangeExtractor<AnyObjectId> {
  private final static Logger logger = Logger.getLogger(GitChangeExtractor.class);
  
  private Repository repository;
  
  public GitChangeExtractor(final Repository repository) {
    GitChangeExtractor.logger.setLevel(Level.ALL);
    this.repository = repository;
  }
  
  @Override
  public List<ScmChangeResult> extract(final AnyObjectId newVersion, final AnyObjectId oldVersion) {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Computing changes between git repo versions ");
      _builder.append(newVersion, "");
      _builder.append(" to ");
      _builder.append(oldVersion, "");
      GitChangeExtractor.logger.info(_builder);
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
        final ArrayList<ScmChangeResult> allResults = new ArrayList<ScmChangeResult>();
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
            final List<DiffEntry> rawDiffs = diff.call();
            final RenameDetector renameDetector = new RenameDetector(this.repository);
            renameDetector.addAll(rawDiffs);
            final List<DiffEntry> diffs = renameDetector.compute();
            final Function1<DiffEntry, ScmChangeResult> _function_1 = (DiffEntry it) -> {
              return this.createResult(it);
            };
            final List<ScmChangeResult> result = ListExtensions.<DiffEntry, ScmChangeResult>map(diffs, _function_1);
            allResults.addAll(result);
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
  
  public ArrayList<RevCommit> findNewToOld(final RevWalk revWalk, final RevCommit oldCommit) {
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
  
  private ScmChangeResult createResult(final DiffEntry entry) {
    try {
      String newContent = ((String) null);
      String oldContent = ((String) null);
      try {
        AbbreviatedObjectId _newId = entry.getNewId();
        ObjectId _objectId = _newId.toObjectId();
        final ObjectLoader newObjectLoader = this.repository.open(_objectId);
        final ByteArrayOutputStream newOutStream = new ByteArrayOutputStream();
        newObjectLoader.copyTo(newOutStream);
        String _string = newOutStream.toString("UTF-8");
        newContent = _string;
      } catch (final Throwable _t) {
        if (_t instanceof MissingObjectException) {
          final MissingObjectException e = (MissingObjectException)_t;
          String _oldPath = entry.getOldPath();
          String _plus = ("File seems to have been removed: " + _oldPath);
          GitChangeExtractor.logger.info(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      try {
        AbbreviatedObjectId _oldId = entry.getOldId();
        ObjectId _objectId_1 = _oldId.toObjectId();
        final ObjectLoader oldObjectLoader = this.repository.open(_objectId_1);
        final ByteArrayOutputStream oldOutStream = new ByteArrayOutputStream();
        oldObjectLoader.copyTo(oldOutStream);
        String _string_1 = oldOutStream.toString("UTF-8");
        oldContent = _string_1;
      } catch (final Throwable _t_1) {
        if (_t_1 instanceof MissingObjectException) {
          final MissingObjectException e_1 = (MissingObjectException)_t_1;
          String _newPath = entry.getNewPath();
          String _plus_1 = ("File seems to have been added " + _newPath);
          GitChangeExtractor.logger.info(_plus_1);
        } else {
          throw Exceptions.sneakyThrow(_t_1);
        }
      }
      String _newPath_1 = entry.getNewPath();
      IPath _fromOSString = Path.fromOSString(_newPath_1);
      String _oldPath_1 = entry.getOldPath();
      IPath _fromOSString_1 = Path.fromOSString(_oldPath_1);
      return new ScmChangeResult(_fromOSString, newContent, _fromOSString_1, oldContent);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
