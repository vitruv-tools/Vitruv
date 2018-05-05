package tools.vitruv.framework.userinteraction;

import tools.vitruv.framework.change.interaction.UserInputBase;

public interface UserInputListener {
	void onUserInputReceived(UserInputBase input);
}