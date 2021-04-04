package com.github.hollandjake.com3529.utils;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
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
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.PatternExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.GenericVisitor;

public class ExpressionStringifier implements GenericVisitor<String, Set<Class<?>>>
{
    public static String toString(Expression expression)
    {
        return toString(expression, new HashSet<>());
    }

    public static String toString(Expression expression, Set<Class<?>> imports)
    {
        return expression.clone().accept(new ExpressionStringifier(), imports);
    }

    private String asExpressionConstructor(String expressionString, Set<Class<?>> imports)
    {
        imports.add(StaticJavaParser.class);
        return String.format("StaticJavaParser.parseExpression(String.valueOf(%s))", expressionString);
    }

    @Override
    public String visit(CompilationUnit n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(PackageDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(TypeParameter n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(LineComment n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(BlockComment n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ClassOrInterfaceDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(EnumDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(EnumConstantDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(AnnotationDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(AnnotationMemberDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(FieldDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(VariableDeclarator n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ConstructorDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(MethodDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(Parameter n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(InitializerDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(JavadocComment n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ClassOrInterfaceType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(PrimitiveType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ArrayType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ArrayCreationLevel n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(IntersectionType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(UnionType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(VoidType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(WildcardType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(UnknownType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ArrayAccessExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ArrayCreationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ArrayInitializerExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(AssignExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(BinaryExpr n, Set<Class<?>> arg)
    {
        arg.add(BinaryExpr.class);
        arg.add(BinaryExpr.Operator.class);

        String left = n.getLeft().accept(this, arg);
        String right = n.getRight().accept(this, arg);
        String operator = String.format("BinaryExpr.Operator.%s", n.getOperator().name());

        return String.format("new BinaryExpr(%s,%s,%s)", left, right, operator);
    }

    @Override
    public String visit(CastExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ClassExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ConditionalExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(EnclosedExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(FieldAccessExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(InstanceOfExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(StringLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(IntegerLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(LongLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(CharLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(DoubleLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(BooleanLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(NullLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(MethodCallExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(NameExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.getNameAsString(), arg);
    }

    @Override
    public String visit(ObjectCreationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ThisExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SuperExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(UnaryExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(VariableDeclarationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(MarkerAnnotationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SingleMemberAnnotationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(NormalAnnotationExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(MemberValuePair n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ExplicitConstructorInvocationStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(LocalClassDeclarationStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(AssertStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(BlockStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(LabeledStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(EmptyStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ExpressionStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SwitchStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SwitchEntry n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(BreakStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ReturnStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(IfStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(WhileStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ContinueStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(DoStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ForEachStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ForStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ThrowStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SynchronizedStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(TryStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(CatchClause n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(LambdaExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(MethodReferenceExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(TypeExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(NodeList n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(Name n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SimpleName n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ImportDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleDeclaration n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleRequiresDirective n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleExportsDirective n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleProvidesDirective n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleUsesDirective n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ModuleOpensDirective n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(UnparsableStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(ReceiverParameter n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(VarType n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(Modifier n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(SwitchExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(YieldStmt n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(TextBlockLiteralExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }

    @Override
    public String visit(PatternExpr n, Set<Class<?>> arg)
    {
        return asExpressionConstructor(n.toString(), arg);
    }
}
