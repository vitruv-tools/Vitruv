package tools.vitruv.change.testutils.changevisualization.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Base class for all visualization-dependent change-information. Each different visualization type
 * implements its own specific {@link ChangeDataSet}. This class stores all information useful for
 * all different implementations.
 *
 * @author Andreas Loeffler
 */
public abstract class ChangeDataSet implements Serializable {

  /** The serial version id for java object serialization. */
  private static final long serialVersionUID = -6142833170227828098L;

  /**
   * The different types of changes.
   *
   * @author Andreas Loeffler
   */
  public enum ChangeType {
    ORIGINAL_CHANGE,
    CONSEQUENTIAL_CHANGE
  }

  /**
   * User infos are additional information stored as key/value pairs. The ChangeDataSet acts as the
   * storage point, but does not use these information directly. It only provides a common usable
   * access to it.
   */
  private final Map<String, Object> userInfos = new HashMap<>();

  /** The time this changeDataSet is created. */
  private final Date creationTime;

  /** The ID of this changeDataSet. */
  private final String id;

  /** The number of propagated changes in this changeDataSet. */
  private int nrPropagatedChanges = 0;

  /** The number of original changes in this changeDataSet (sum over all propagated changes). */
  private int nrOriginalChanges = 0;

  /**
   * The number of consequential changes in this changeDataSet (sum over all propagated changes).
   */
  private int nrConsequentialChanges = 0;

  /**
   * This constructor is called by subclasses to initialize the general ChangeDataSet.
   *
   * @param id The ID of the changeDataSet
   */
  protected ChangeDataSet(String id) {
    this.id = id;
    this.creationTime = new Date();
  }

  /**
   * Returns the visualization-dependent object that is needed to visualize the changeDataSet.
   *
   * @return The visualization-dependent object needed to visualize this changeDataSet
   */
  public abstract Object getData();

  /**
   * Returns the ID of this changeDataSet.
   *
   * @return The ID of this changeDataSet
   */
  public String getChangeDataSetID() {
    return id;
  }

  @Override
  public String toString() {
    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
    return id + " (" + df.format(creationTime) + ")";
  }

  /**
   * Returns the creation time of this changeDataSet.
   *
   * @return The creation time of this changeDataSet
   */
  public Date getCreationTime() {
    return creationTime;
  }

  /**
   * Sets the number of propagated changes.
   *
   * @param nrPropagatedChanges The number of propagated changes
   */
  public void setNrPropagatedChanges(int nrPropagatedChanges) {
    this.nrPropagatedChanges = nrPropagatedChanges;
  }

  /**
   * Sets the number of original changes.
   *
   * @param nrOriginalChanges The number of original changes
   */
  public void setNrOriginalChanges(int nrOriginalChanges) {
    this.nrOriginalChanges = nrOriginalChanges;
  }

  /**
   * Sets the number of consequential changes.
   *
   * @param nrConsequentialChanges The number of consequential changes
   */
  public void setNrConsequentialChanges(int nrConsequentialChanges) {
    this.nrConsequentialChanges = nrConsequentialChanges;
  }

  /**
   * Gets the number of propagated changes.
   *
   * @return The number of propagated changes
   */
  public int getNrPropagatedChanges() {
    return nrPropagatedChanges;
  }

  /**
   * Gets the number of original changes.
   *
   * @return The number of original changes
   */
  public int getNrOriginalChanges() {
    return nrOriginalChanges;
  }

  /**
   * Gets the number of consequential changes.
   *
   * @return The number of consequential changes
   */
  public int getNrConsequentialChanges() {
    return nrConsequentialChanges;
  }

  /** Resets the stored user infos. */
  public void clearUserInfos() {
    userInfos.clear();
  }

  /**
   * Stores the given value under the given key in the local key/value storage.
   *
   * @param key The key
   * @param value The value
   */
  public void setUserInfo(String key, Object value) {
    userInfos.put(key, value);
  }

  /**
   * Gets the stored user info for the given key. Null if none exists.
   *
   * @param key The key
   * @return The value stored for this key, may be null
   */
  public Object getUserInfo(String key) {
    return userInfos.get(key);
  }

  /**
   * Checks whether a stored values exists for a given key.
   *
   * @param key The key
   * @return true if a value exists for this key, false otherwise
   */
  public boolean hasUserInfo(String key) {
    return userInfos.containsKey(key);
  }
}
