/** 
 */
package tools.vitruv.framework.versioning.commit.impl

import java.util.Date
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.CommitPackage

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl#getDate <em>Date</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl#getMessage <em>Message</em>}</li>
 * <li>{@link tools.vitruv.framework.versioning.commit.impl.CommitMessageImpl#getAuthor <em>Author</em>}</li>
 * </ul>
 * @generated
 */
class CommitMessageImpl extends MinimalEObjectImpl.Container implements CommitMessage {
	/** 
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null
	/** 
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT
	/** 
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null
	/** 
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT
	/** 
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected Author author

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return CommitPackage.Literals.COMMIT_MESSAGE
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Date getDate() {
		return date
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setDate(Date newDate) {
		var Date oldDate = date
		date = newDate
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, CommitPackage.COMMIT_MESSAGE__DATE, oldDate, date))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String getMessage() {
		return message
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setMessage(String newMessage) {
		var String oldMessage = message
		message = newMessage
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, CommitPackage.COMMIT_MESSAGE__MESSAGE, oldMessage, message))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Author getAuthor() {
		if (author !== null && author.eIsProxy()) {
			var InternalEObject oldAuthor = (author as InternalEObject)
			author = eResolveProxy(oldAuthor) as Author
			if (author !== oldAuthor) {
				if (eNotificationRequired()) eNotify(
					new ENotificationImpl(this, Notification.RESOLVE, CommitPackage.COMMIT_MESSAGE__AUTHOR, oldAuthor,
						author))
			}
		}
		return author
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def Author basicGetAuthor() {
		return author
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void setAuthor(Author newAuthor) {
		var Author oldAuthor = author
		author = newAuthor
		if (eNotificationRequired()) eNotify(
			new ENotificationImpl(this, Notification.SET, CommitPackage.COMMIT_MESSAGE__AUTHOR, oldAuthor, author))
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eGet(int featureID, boolean resolve, boolean coreType) {

		switch (featureID) {
			case CommitPackage.COMMIT_MESSAGE__DATE: {
				return getDate()
			}
			case CommitPackage.COMMIT_MESSAGE__MESSAGE: {
				return getMessage()
			}
			case CommitPackage.COMMIT_MESSAGE__AUTHOR: {
				if (resolve) return getAuthor()
				return basicGetAuthor()
			}
		}
		return super.eGet(featureID, resolve, coreType)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eSet(int featureID, Object newValue) {

		switch (featureID) {
			case CommitPackage.COMMIT_MESSAGE__DATE: {
				setDate((newValue as Date))
				return
			}
			case CommitPackage.COMMIT_MESSAGE__MESSAGE: {
				setMessage((newValue as String))
				return
			}
			case CommitPackage.COMMIT_MESSAGE__AUTHOR: {
				setAuthor((newValue as Author))
				return
			}
		}
		super.eSet(featureID, newValue)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override void eUnset(int featureID) {

		switch (featureID) {
			case CommitPackage.COMMIT_MESSAGE__DATE: {
				setDate(DATE_EDEFAULT)
				return
			}
			case CommitPackage.COMMIT_MESSAGE__MESSAGE: {
				setMessage(MESSAGE_EDEFAULT)
				return
			}
			case CommitPackage.COMMIT_MESSAGE__AUTHOR: {
				setAuthor((null as Author))
				return
			}
		}
		super.eUnset(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override boolean eIsSet(int featureID) {

		switch (featureID) {
			case CommitPackage.COMMIT_MESSAGE__DATE: {
				return if (DATE_EDEFAULT === null) date !== null else !DATE_EDEFAULT.equals(date)
			}
			case CommitPackage.COMMIT_MESSAGE__MESSAGE: {
				return if (MESSAGE_EDEFAULT === null) message !== null else !MESSAGE_EDEFAULT.equals(message)
			}
			case CommitPackage.COMMIT_MESSAGE__AUTHOR: {
				return author !== null
			}
		}
		return super.eIsSet(featureID)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String toString() {
		if (eIsProxy()) return super.toString()
		var StringBuffer result = new StringBuffer(super.toString())
		result.append(" (date: ")
		result.append(date)
		result.append(", message: ")
		result.append(message)
		result.append(Character.valueOf(')').charValue)
		return result.toString()
	} // CommitMessageImpl
}
