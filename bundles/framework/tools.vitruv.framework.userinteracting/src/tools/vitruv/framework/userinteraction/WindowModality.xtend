package tools.vitruv.framework.userinteraction

enum WindowModality {
	MODAL, MODELESS
	/* TODO: MODAL doesn't work currently. In order for it to work, the shell passed to UserInteractor would have to be
	the one used by the workbench */
}