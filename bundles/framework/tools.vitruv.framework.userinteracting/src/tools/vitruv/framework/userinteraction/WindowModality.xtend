package tools.vitruv.framework.userinteraction

/**
 * Represents the different ways a window can behave (whether it requires interaction blocking other windows behind it
 * or it can be minimized).
 * 
 * @author Dominik Klooz
 */
enum WindowModality {
	MODAL, MODELESS
	/* TODO DK: MODAL doesn't work currently. In order for it to work, the shell passed to UserInteractor would have to be
	the one used by the workbench */
}