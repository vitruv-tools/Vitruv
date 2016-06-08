package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class GitChangeExtractor implements IScmChangeExtractor<ObjectId> {
  private final static Logger logger = Logger.getLogger(GitChangeExtractor.class);
  
  private Repository repository;
  
  private IPath projectRepoOffset;
  
  public GitChangeExtractor(final Repository repository, final IPath projectRepoOffset) {
    GitChangeExtractor.logger.setLevel(Level.ALL);
    this.repository = repository;
    this.projectRepoOffset = projectRepoOffset;
  }
  
  public <T extends Object> Iterable<T> reverse(final Iterable<T> iterable) {
    final ArrayList<T> toReverse = new ArrayList<T>();
    for (final T element : iterable) {
      toReverse.add(element);
    }
    final List<T> reversed = Lists.<T>reverse(toReverse);
    return ((Iterable<T>) reversed);
  }
  
  public boolean pathExists(final ObjectId from, final ObjectId to) {
    try {
      final RevWalk checkWalk = new RevWalk(this.repository);
      RevCommit _parseCommit = checkWalk.parseCommit(from);
      RevCommit _parseCommit_1 = checkWalk.parseCommit(to);
      return checkWalk.isMergedInto(_parseCommit, _parseCommit_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public RevCommit getNextCommit(final RevCommit toCommit, final ObjectId oldVersion) {
    RevCommit[] _parents = toCommit.getParents();
    for (final RevCommit child : _parents) {
      ObjectId _id = child.getId();
      boolean _pathExists = this.pathExists(oldVersion, _id);
      if (_pathExists) {
        int _parentCount = toCommit.getParentCount();
        boolean _greaterThan = (_parentCount > 1);
        if (_greaterThan) {
          GitChangeExtractor.logger.warn((((("Commit " + toCommit) + " has multiple parents. Choosing random path to parent: ") + child) + 
            " Changes in other path will only be applied during merge. Not as atomic as possible..."));
        }
        return child;
      }
    }
    return null;
  }
  
  @Override
  public List<ScmChangeResult> extract(final ObjectId newVersion, final ObjectId oldVersion) {
    final ObjectReader reader = this.repository.newObjectReader();
    final Git git = new Git(this.repository);
    try {
      final RevWalk walk = new RevWalk(this.repository);
      boolean reachedOldVersion = false;
      final RevCommit newestCommit = walk.parseCommit(newVersion);
      final RevCommit oldestCommit = walk.parseCommit(oldVersion);
      final ArrayList<RevCommit> commitList = new ArrayList<RevCommit>();
      commitList.add(newestCommit);
      RevCommit currentTo = newestCommit;
      while ((!reachedOldVersion)) {
        {
          RevCommit _nextCommit = this.getNextCommit(currentTo, oldVersion);
          final ObjectId nextCommitId = _nextCommit.getId();
          final RevCommit nextCommit = walk.parseCommit(nextCommitId);
          commitList.add(nextCommit);
          boolean _equals = Objects.equal(nextCommit, oldestCommit);
          if (_equals) {
            reachedOldVersion = true;
          }
          currentTo = nextCommit;
        }
      }
      Iterable<RevCommit> _reverse = this.<RevCommit>reverse(commitList);
      final Iterator<RevCommit> commitIterator = _reverse.iterator();
      RevCommit fromCommit = commitIterator.next();
      RevCommit toCommit = commitIterator.next();
      final ArrayList<ScmChangeResult> allResults = new ArrayList<ScmChangeResult>();
      while ((!Objects.equal(toCommit, null))) {
        {
          final ObjectId toCommitId = toCommit.getId();
          final ObjectId fromCommitId = fromCommit.getId();
          GitChangeExtractor.logger.info(((("Git diffing from " + fromCommitId) + " to ") + toCommitId));
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
          final Function1<DiffEntry, ScmChangeResult> _function = (DiffEntry it) -> {
            return this.createResult(it, fromCommitId, toCommitId);
          };
          final List<ScmChangeResult> result = ListExtensions.<DiffEntry, ScmChangeResult>map(diffs, _function);
          final Function1<ScmChangeResult, Boolean> _function_1 = (ScmChangeResult r) -> {
            return Boolean.valueOf((!Objects.equal(r, null)));
          };
          final Iterable<ScmChangeResult> filteredResult = IterableExtensions.<ScmChangeResult>filter(result, _function_1);
          Iterables.<ScmChangeResult>addAll(allResults, filteredResult);
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
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        final Throwable e = (Throwable)_t;
        GitChangeExtractor.logger.fatal("Error during git extraction", e);
        throw new RuntimeException(e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    } finally {
      git.close();
      reader.close();
    }
  }
  
  private ScmChangeResult createResult(final DiffEntry entry, final ObjectId oldVersion, final ObjectId newVersion) {
    try {
      String newContent = null;
      String oldContent = null;
      String _newPath = entry.getNewPath();
      IPath newPath = Path.fromOSString(_newPath);
      String _oldPath = entry.getOldPath();
      IPath oldPath = Path.fromOSString(_oldPath);
      try {
        boolean _isPrefixOf = this.projectRepoOffset.isPrefixOf(newPath);
        if (_isPrefixOf) {
          AbbreviatedObjectId _newId = entry.getNewId();
          ObjectId _objectId = _newId.toObjectId();
          final ObjectLoader newObjectLoader = this.repository.open(_objectId);
          final ByteArrayOutputStream newOutStream = new ByteArrayOutputStream();
          newObjectLoader.copyTo(newOutStream);
          String _string = newOutStream.toString("UTF-8");
          newContent = _string;
        }
      } catch (final Throwable _t) {
        if (_t instanceof MissingObjectException) {
          final MissingObjectException e = (MissingObjectException)_t;
          String _oldPath_1 = entry.getOldPath();
          String _plus = ("File seems to have been removed: " + _oldPath_1);
          GitChangeExtractor.logger.info(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      try {
        boolean _isPrefixOf_1 = this.projectRepoOffset.isPrefixOf(oldPath);
        if (_isPrefixOf_1) {
          AbbreviatedObjectId _oldId = entry.getOldId();
          ObjectId _objectId_1 = _oldId.toObjectId();
          final ObjectLoader oldObjectLoader = this.repository.open(_objectId_1);
          final ByteArrayOutputStream oldOutStream = new ByteArrayOutputStream();
          oldObjectLoader.copyTo(oldOutStream);
          String _string_1 = oldOutStream.toString("UTF-8");
          oldContent = _string_1;
        }
      } catch (final Throwable _t_1) {
        if (_t_1 instanceof MissingObjectException) {
          final MissingObjectException e_1 = (MissingObjectException)_t_1;
          String _newPath_1 = entry.getNewPath();
          String _plus_1 = ("File seems to have been added " + _newPath_1);
          GitChangeExtractor.logger.info(_plus_1);
        } else {
          throw Exceptions.sneakyThrow(_t_1);
        }
      }
      boolean _and = false;
      boolean _equals = Objects.equal(newContent, null);
      if (!_equals) {
        _and = false;
      } else {
        boolean _equals_1 = Objects.equal(oldContent, null);
        _and = _equals_1;
      }
      if (_and) {
        return null;
      }
      boolean _equals_2 = Objects.equal(newContent, null);
      if (_equals_2) {
        newPath = null;
      }
      boolean _equals_3 = Objects.equal(oldContent, null);
      if (_equals_3) {
        oldPath = null;
      }
      String _name = newVersion.getName();
      String _name_1 = oldVersion.getName();
      return new ScmChangeResult(this.projectRepoOffset, newPath, newContent, _name, oldPath, oldContent, _name_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
