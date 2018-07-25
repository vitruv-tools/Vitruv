package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.types.NotificationInteraction

/**
 * Enum for the levels of severity for notification messages (specifies the icon used in {@link NotificationInteraction}s).
 * @see NotificationInteraction
 * 
 * @uthor Dominik Klooz
 */
enum NotificationType {
	INFORMATION, WARNING, ERROR
}