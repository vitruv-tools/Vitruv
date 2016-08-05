package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change

/**
 * Base interface for changes that are somehow recorded (from EMF models, file system etc.)
 * They represent raw changes and have to be transformed into changes usable in Vitruvius.
 * 
 * @author Heiko Klare
 */
interface RecordedChange extends Change {
}