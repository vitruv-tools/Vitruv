package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.statements.Statement;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLExpressionHaving;
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement;

public interface ShadowCopyCorrespondences {

    public Statement get(JMLExpressionHaving jml);

    public JMLExpressionHaving get(Statement stmt);

    public JMLSpecifiedElement get(Member javaMember);

    public Member getMember(JMLSpecifiedElement jml);

    public <T extends NamedElement> T getShadow(T original);
    
    public <T extends NamedElement> T getOriginal(T shadow);
    
    public <T extends EObject> Set<T> getCorrespondingEObjectsByType(EObject eObject, Class<T> type);
    
    public <T extends EObject> T getJMLElement(T original);
    
    public Collection<Setting> findReferencesToJavaObject(EObject obj);
    
    public ClassMethod getSpecContainingMethod(Member shadowMember);
    
    public Set<Entry<Member, ClassMethod>> getSpecContainingMethods(); 

}