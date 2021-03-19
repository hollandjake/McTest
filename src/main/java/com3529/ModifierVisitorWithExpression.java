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
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public abstract class ModifierVisitorWithExpression extends ModifierVisitor<Void>
{
    public Visitable visit(Expression n, Void arg)
    {
        if (n instanceof ArrayAccessExpr)
        {
            return super.visit((ArrayAccessExpr) n, arg);
        }
        else if (n instanceof ArrayCreationExpr)
        {
            return super.visit((ArrayCreationExpr) n, arg);
        }
        else if (n instanceof ArrayInitializerExpr)
        {
            return super.visit((ArrayInitializerExpr) n, arg);
        }
        else if (n instanceof AssignExpr)
        {
            return super.visit((AssignExpr) n, arg);
        }
        else if (n instanceof BinaryExpr)
        {
            return super.visit((BinaryExpr) n, arg);
        }
        else if (n instanceof BooleanLiteralExpr)
        {
            return super.visit((BooleanLiteralExpr) n, arg);
        }
        else if (n instanceof CastExpr)
        {
            return super.visit((CastExpr) n, arg);
        }
        else if (n instanceof CharLiteralExpr)
        {
            return super.visit((CharLiteralExpr) n, arg);
        }
        else if (n instanceof ClassExpr)
        {
            return super.visit((ClassExpr) n, arg);
        }
        else if (n instanceof ConditionalExpr)
        {
            return super.visit((ConditionalExpr) n, arg);
        }
        else if (n instanceof DoubleLiteralExpr)
        {
            return super.visit((DoubleLiteralExpr) n, arg);
        }
        else if (n instanceof EnclosedExpr)
        {
            return super.visit((EnclosedExpr) n, arg);
        }
        else if (n instanceof FieldAccessExpr)
        {
            return super.visit((FieldAccessExpr) n, arg);
        }
        else if (n instanceof InstanceOfExpr)
        {
            return super.visit((InstanceOfExpr) n, arg);
        }
        else if (n instanceof IntegerLiteralExpr)
        {
            return super.visit((IntegerLiteralExpr) n, arg);
        }
        else if (n instanceof LongLiteralExpr)
        {
            return super.visit((LongLiteralExpr) n, arg);
        }
        else if (n instanceof MarkerAnnotationExpr)
        {
            return super.visit((MarkerAnnotationExpr) n, arg);
        }
        else if (n instanceof MethodCallExpr)
        {
            return super.visit((MethodCallExpr) n, arg);
        }
        else if (n instanceof NameExpr)
        {
            return super.visit((NameExpr) n, arg);
        }
        else if (n instanceof NormalAnnotationExpr)
        {
            return super.visit((NormalAnnotationExpr) n, arg);
        }
        else if (n instanceof NullLiteralExpr)
        {
            return super.visit((NullLiteralExpr) n, arg);
        }
        else if (n instanceof ObjectCreationExpr)
        {
            return super.visit((ObjectCreationExpr) n, arg);
        }
        else if (n instanceof SingleMemberAnnotationExpr)
        {
            return super.visit((SingleMemberAnnotationExpr) n, arg);
        }
        else if (n instanceof StringLiteralExpr)
        {
            return super.visit((StringLiteralExpr) n, arg);
        }
        else if (n instanceof SuperExpr)
        {
            return super.visit((SuperExpr) n, arg);
        }
        else if (n instanceof ThisExpr)
        {
            return super.visit((ThisExpr) n, arg);
        }
        else if (n instanceof UnaryExpr)
        {
            return super.visit((UnaryExpr) n, arg);
        }
        else if (n instanceof VariableDeclarationExpr)
        {
            return super.visit((VariableDeclarationExpr) n, arg);
        }
        else if (n instanceof LambdaExpr)
        {
            return super.visit((LambdaExpr) n, arg);
        }
        else if (n instanceof MethodReferenceExpr)
        {
            return super.visit((MethodReferenceExpr) n, arg);
        }
        else if (n instanceof TypeExpr)
        {
            return super.visit((TypeExpr) n, arg);
        }
        else if (n instanceof SwitchExpr)
        {
            return super.visit((SwitchExpr) n, arg);
        }
        else if (n instanceof TextBlockLiteralExpr)
        {
            return super.visit((TextBlockLiteralExpr) n, arg);
        }
        else if (n instanceof PatternExpr)
        {
            return super.visit((PatternExpr) n, arg);
        }
        return n;
    }

    @Override
    public Visitable visit(ArrayAccessExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ArrayCreationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ArrayInitializerExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(AssignExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(BinaryExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(BooleanLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(CastExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(CharLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ClassExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ConditionalExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(DoubleLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(EnclosedExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(FieldAccessExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(InstanceOfExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(IntegerLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(LongLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(MarkerAnnotationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(MethodCallExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(NameExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(NormalAnnotationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(NullLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ObjectCreationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(SingleMemberAnnotationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(StringLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(SuperExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(ThisExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(UnaryExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(VariableDeclarationExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(LambdaExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(MethodReferenceExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(TypeExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(SwitchExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(TextBlockLiteralExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }

    @Override
    public Visitable visit(PatternExpr n, Void arg)
    {
        return visit((Expression) n, arg);
    }
}
