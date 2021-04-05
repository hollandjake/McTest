package com.github.hollandjake.com3529;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.github.hollandjake.com3529.generation.CoverageReport;
import com.github.hollandjake.com3529.utils.ExpressionToString;
import com.github.hollandjake.com3529.utils.tree.IfNode;
import com.github.hollandjake.com3529.utils.tree.Tree;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import net.openhft.compiler.CompilerUtils;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class ParseConvert
{
    private final Map<Method, Tree> methodBranchTrees;
    private final Class<?> clazz;

    @SneakyThrows
    public static ParseConvert parse(String classToTest)
    {
        SourceRoot sourceRoot = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ParseConvert.class).resolve("src/main/resources")
        );
        CompilationUnit cu = sourceRoot.parse("", classToTest + ".java");

        AtomicReference<String> packageName = new AtomicReference<>("");
        cu.getPackageDeclaration().ifPresent(packageDeclaration -> packageName.set(
                String.format("%s.%s", packageDeclaration.getNameAsString(), classToTest)
        ));

        //Add import to class
        Set<Class<?>> imports = new HashSet<>();
        imports.add(CoverageReport.class);

        Map<String, Tree> methodStringIterables = new HashMap<>();

        cu = (CompilationUnit) cu.accept(new ModifierVisitor<Tree>()
        {
            private final AtomicInteger branchNum = new AtomicInteger();
            private final Deque<Boolean> truthPathStack = new ArrayDeque<>();

            @Override
            public Visitable visit(MethodDeclaration n, Tree treeParent)
            {
                if (n.isPublic())
                {
                    Parameter newParameter = StaticJavaParser.parseParameter(
                            CoverageReport.class.getSimpleName() + " coverage"
                    );
                    n.addParameter(newParameter);
                    branchNum.set(0);
                    truthPathStack.clear();
                    Tree root = new Tree();
                    Visitable newNode = super.visit(n, root);
                    methodStringIterables.put(n.getNameAsString(), root);
                    return newNode;
                }
                return n;
            }

            @Override
            public Visitable visit(IfStmt n, Tree parentNode)
            {
                Expression expression = n.getCondition();
                int branchId = this.branchNum.getAndIncrement();
                Expression newExpression = StaticJavaParser.parseExpression(String.format(
                        "coverage.cover(%d,%s)",
                        branchId,
                        ExpressionToString.toString(expression, imports)
                ));
                n.setCondition(newExpression);

                IfNode self = new IfNode(parentNode, branchId);
                if (parentNode instanceof IfNode) {
                    if (truthPathStack.peek() == Boolean.TRUE) {
                        ((IfNode)parentNode).addThenChild(self);
                    } else {
                        ((IfNode)parentNode).addElseChild(self);
                    }
                } else {
                    parentNode.addChild(self);
                }

                truthPathStack.push(true);
                n.setThenStmt((Statement) n.getThenStmt().accept(this, self));
                truthPathStack.pop();
                truthPathStack.push(false);
                n.setElseStmt(n.getElseStmt().map(s -> (Statement) s.accept(this, self)).orElse(null));
                truthPathStack.pop();
                return n;
            }
        }, null);

        //Add imports
        imports.forEach(cu::addImport);

        //Save n Compile
        Class<?> clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(packageName.get(), cu.toString());

        Map<Method, Tree> methodIterables = new HashMap<>();

        Arrays.stream(clazz.getMethods())
              .filter(method -> method.getDeclaringClass() == clazz)
              .forEach(method -> methodIterables.put(method, methodStringIterables.get(method.getName())));

        return new ParseConvert(methodIterables, clazz);
    }

    public Tree getBranchTree(Method method)
    {
        return methodBranchTrees.get(method);
    }
}