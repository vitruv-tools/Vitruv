package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.refactoringParticipants;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util.JavaModel2AST;

public class RenameTypeParticipant extends RenameParticipantAdapter {

	@Override
	public ChangeClassifyingEvent classifyRefactoring(Object element,
			RenameArguments args) {
		if (!(element instanceof IType))
			return null;
		IType itype = (IType) element;
		TypeDeclaration original = JavaModel2AST.getTypeDeclaration(itype,
				itype.getCompilationUnit());
		CompilationUnit cu = (CompilationUnit) original.getRoot();
		CompilationUnit cuModified = (CompilationUnit) ASTNode.copySubtree(original.getAST(), cu);
		TypeDeclaration renamed = JavaModel2AST.getTypeDeclaration(itype, cuModified);
		renamed.getName().setIdentifier(args.getNewName());
		if (original.isInterface())
			return new RenameInterfaceEvent(original, renamed);
		else
			return new RenameClassEvent(original, renamed);
	}
}
