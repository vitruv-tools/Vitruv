package tools.vitruvius.domains.java.monitorededitor.refactoringlistener.refactoringParticipants;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameFieldEvent;
import tools.vitruvius.domains.java.monitorededitor.javamodel2ast.CompilationUnitUtil;
import tools.vitruvius.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

public class RenameFieldParticipant extends RenameParticipantAdapter {

    @Override
    public ChangeClassifyingEvent classifyRefactoring(Object element, RenameArguments args) {
        if (!(element instanceof IField))
            return null;
        VariableDeclarationFragment originalFragment = JavaModel2AST
                .getVariableDeclarationFragmentByName((IField) element);
        CompilationUnit cu = (CompilationUnit) originalFragment.getRoot();
        CompilationUnit cuModified = (CompilationUnit) ASTNode.copySubtree(originalFragment.getAST(), cu);
        VariableDeclarationFragment renamedFragment = JavaModel2AST.getVariableDeclarationFragmentByName(
                (IField) element, cuModified);
        renamedFragment.getName().setIdentifier(args.getNewName());
        int line = CompilationUnitUtil.getLineNumberOfASTNode(originalFragment);
        return new RenameFieldEvent(originalFragment, renamedFragment, line);
    }
}
