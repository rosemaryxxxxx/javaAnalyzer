package javaparser.visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MethodCallExprVisitor extends GenericVisitorAdapter<List<MethodCallExpr>, Void> {

    private Node targetNode;

    public MethodCallExprVisitor(Node targetNode) {
        this.targetNode = targetNode;
    }

    @Override
    public List<MethodCallExpr> visit(MethodCallExpr n, Void arg) {
        if (n.getParentNode().get() == targetNode) {
            return List.of(n);
        }
        return super.visit(n, arg);
    }

    @Override
    public List<MethodCallExpr> visit(MethodDeclaration n, Void arg) {
        // skip method bodies to avoid visiting nodes twice
        return new ArrayList<>();
    }
}
