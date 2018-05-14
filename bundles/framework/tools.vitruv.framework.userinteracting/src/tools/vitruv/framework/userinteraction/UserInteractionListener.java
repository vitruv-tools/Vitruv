package tools.vitruv.framework.userinteraction;

import tools.vitruv.framework.change.interaction.UserInteractionBase;

public interface UserInteractionListener {
	void onUserInteractionReceived(UserInteractionBase interaction);
}