package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ExtractionResult;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.IScmActionExtractor;
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
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class GitActionExtractor implements IScmActionExtractor<AnyObjectId> {
  private final static Logger logger = Logger.getLogger(GitActionExtractor.class);
  
  private Repository repository;
  
  private JdtTreeGenerator treeGenerator;
  
  public GitActionExtractor(final Repository repository) {
    this.repository = repository;
    JdtTreeGenerator _jdtTreeGenerator = new JdtTreeGenerator();
    this.treeGenerator = _jdtTreeGenerator;
  }
  
  @Override
  public Iterable<ExtractionResult> extract(final AnyObjectId newVersion, final AnyObjectId oldVersion) {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Computing changes between git repo versions ");
      _builder.append(newVersion, "");
      _builder.append(" to ");
      _builder.append(oldVersion, "");
      GitActionExtractor.logger.info(_builder);
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
        final ArrayList<List<ExtractionResult>> allResults = new ArrayList<List<ExtractionResult>>();
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
            PathFilter _create = PathFilter.create("*.java");
            diff.setPathFilter(_create);
            final List<DiffEntry> diffs = diff.call();
            final Function1<DiffEntry, ExtractionResult> _function_1 = (DiffEntry it) -> {
              return this.extractActions(it);
            };
            final List<ExtractionResult> result = ListExtensions.<DiffEntry, ExtractionResult>map(diffs, _function_1);
            allResults.add(result);
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
        return Iterables.<ExtractionResult>concat(allResults);
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
  
  private ExtractionResult extractActions(final DiffEntry entry) {
    try {
      AbbreviatedObjectId _oldId = entry.getOldId();
      ObjectId _objectId = _oldId.toObjectId();
      final ObjectLoader oldObjectLoader = this.repository.open(_objectId);
      AbbreviatedObjectId _newId = entry.getNewId();
      ObjectId _objectId_1 = _newId.toObjectId();
      final ObjectLoader newObjectLoder = this.repository.open(_objectId_1);
      ObjectStream _openStream = oldObjectLoader.openStream();
      final TreeContext oldTreeContext = this.treeGenerator.generateFromStream(_openStream);
      ObjectStream _openStream_1 = newObjectLoder.openStream();
      final TreeContext newTreeContext = this.treeGenerator.generateFromStream(_openStream_1);
      final ITree oldTree = oldTreeContext.getRoot();
      final ITree newTree = newTreeContext.getRoot();
      Matchers _instance = Matchers.getInstance();
      final Matcher matcher = _instance.getMatcher(oldTree, newTree);
      matcher.match();
      final MappingStore mappings = matcher.getMappings();
      final ActionGenerator actionGenerator = new ActionGenerator(oldTree, newTree, mappings);
      actionGenerator.generate();
      List<Action> _actions = actionGenerator.getActions();
      final ExtractionResult result = new ExtractionResult(oldTreeContext, newTreeContext, mappings, _actions);
      return result;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
