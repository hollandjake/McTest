package com3529;

import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.PatternExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public abstract class VoidVisitorAdapterWithExpression extends VoidVisitorAdapter<Void>
{
    public void visit(Expression n, Void arg)
    {
        if (n instanceof ArrayAccessExpr)
        {
            super.visit((ArrayAccessExpr) n, arg);
        }
        else if (n instanceof ArrayCreationExpr)
        {
            super.visit((ArrayCreationExpr) n, arg);
        }
        else if (n instanceof ArrayInitializerExpr)
        {
            super.visit((ArrayInitializerExpr) n, arg);
        }
        else if (n instanceof AssignExpr)
        {
            super.visit((AssignExpr) n, arg);
        }
        else if (n instanceof BinaryExpr)
        {
            super.visit((BinaryExpr) n, arg);
        }
        else if (n instanceof BooleanLiteralExpr)
        {
            super.visit((BooleanLiteralExpr) n, arg);
        }
        else if (n instanceof CastExpr)
        {
            super.visit((CastExpr) n, arg);
        }
        else if (n instanceof CharLiteralExpr)
        {
            super.visit((CharLiteralExpr) n, arg);
        }
        else if (n instanceof ClassExpr)
        {
            super.visit((ClassExpr) n, arg);
        }
        else if (n instanceof ConditionalExpr)
        {
            super.visit((ConditionalExpr) n, arg);
        }
        else if (n instanceof DoubleLiteralExpr)
        {
            super.visit((DoubleLiteralExpr) n, arg);
        }
        else if (n instanceof EnclosedExpr)
        {
            super.visit((EnclosedExpr) n, arg);
        }
        else if (n instanceof FieldAccessExpr)
        {
            super.visit((FieldAccessExpr) n, arg);
        }
        else if (n instanceof InstanceOfExpr)
        {
            super.visit((InstanceOfExpr) n, arg);
        }
        else if (n instanceof IntegerLiteralExpr)
        {
            super.visit((IntegerLiteralExpr) n, arg);
        }
        else if (n instanceof LongLiteralExpr)
        {
            super.visit((LongLiteralExpr) n, arg);
        }
        else if (n instanceof MarkerAnnotationExpr)
        {
            super.visit((MarkerAnnotationExpr) n, arg);
        }
        else if (n instanceof MethodCallExpr)
        {
            super.visit((MethodCallExpr) n, arg);
        }
        else if (n instanceof NameExpr)
        {
            super.visit((NameExpr) n, arg);
        }
        else if (n instanceof NormalAnnotationExpr)
        {
            super.visit((NormalAnnotationExpr) n, arg);
        }
        else if (n instanceof NullLiteralExpr)
        {
            super.visit((NullLiteralExpr) n, arg);
        }
        else if (n instanceof ObjectCreationExpr)
        {
            super.visit((ObjectCreationExpr) n, arg);
        }
        else if (n instanceof SingleMemberAnnotationExpr)
        {
            super.visit((SingleMemberAnnotationExpr) n, arg);
        }
        else if (n instanceof StringLiteralExpr)
        {
            super.visit((StringLiteralExpr) n, arg);
        }
        else if (n instanceof SuperExpr)
        {
            super.visit((SuperExpr) n, arg);
        }
        else if (n instanceof ThisExpr)
        {
            super.visit((ThisExpr) n, arg);
        }
        else if (n instanceof UnaryExpr)
        {
            super.visit((UnaryExpr) n, arg);
        }
        else if (n instanceof VariableDeclarationExpr)
        {
            super.visit((VariableDeclarationExpr) n, arg);
        }
        else if (n instanceof LambdaExpr)
        {
            super.visit((LambdaExpr) n, arg);
        }
        else if (n instanceof MethodReferenceExpr)
        {
            super.visit((MethodReferenceExpr) n, arg);
        }
        else if (n instanceof TypeExpr)
        {
            super.visit((TypeExpr) n, arg);
        }
        else if (n instanceof SwitchExpr)
        {
            super.visit((SwitchExpr) n, arg);
        }
        else if (n instanceof TextBlockLiteralExpr)
        {
            super.visit((TextBlockLiteralExpr) n, arg);
        }
        else if (n instanceof PatternExpr)
        {
            super.visit((PatternExpr) n, arg);
        }
    }

    @Override
    public void visit(ArrayAccessExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ArrayCreationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ArrayInitializerExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(AssignExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(BinaryExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(BooleanLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(CastExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(CharLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ClassExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ConditionalExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(DoubleLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(EnclosedExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(FieldAccessExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(InstanceOfExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(IntegerLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(LongLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(NameExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(NormalAnnotationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(NullLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(StringLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(SuperExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(ThisExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(UnaryExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(LambdaExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(MethodReferenceExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(TypeExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(SwitchExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(TextBlockLiteralExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }

    @Override
    public void visit(PatternExpr n, Void arg)
    {
        visit((Expression) n, arg);
    }
}
