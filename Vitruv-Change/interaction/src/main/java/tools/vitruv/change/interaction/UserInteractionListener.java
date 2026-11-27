package tools.vitruv.change.interaction;

/**
 * A listener for getting notification about user interaction events.
 *
 * @author Heiko Klare
 */
public interface UserInteractionListener {
  /**
   * Called whenever a user interaction occurred.
   *
   * @param interaction - the result of the performed interaction
   */
  void onUserInteractionReceived(UserInteractionBase interaction);
}
