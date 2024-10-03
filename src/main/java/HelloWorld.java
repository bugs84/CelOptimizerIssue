import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.optimizer.CelOptimizationException;
import dev.cel.optimizer.CelOptimizerFactory;
import dev.cel.optimizer.optimizers.ConstantFoldingOptimizer;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

import java.util.Map;

public class HelloWorld {
    private static final CelCompiler CEL_COMPILER =
            CelCompilerFactory.standardCelCompilerBuilder().addVar("my_var", SimpleType.STRING).build();
    private static final CelRuntime CEL_RUNTIME =
            CelRuntimeFactory.standardCelRuntimeBuilder().build();

    public void run() throws CelValidationException, CelEvaluationException, CelOptimizationException {
        CelAbstractSyntaxTree ast = CEL_COMPILER.compile("my_var in ['H', 'O']").getAst();

        var celOptimizer = CelOptimizerFactory.standardCelOptimizerBuilder(CEL_COMPILER, CEL_RUNTIME)
                .addAstOptimizers(
                        /**
                           CODE WORKS WITHOUT 'ConstantFoldingOptimizer' WORKS

                          with optimizer ends with following exception
                         Exception in thread "main" java.lang.NoSuchMethodError: 'com.google.protobuf.Internal$LongList dev.cel.expr.UnknownSet.mutableCopy(com.google.protobuf.Internal$LongList)'
                            at dev.cel.expr.UnknownSet.access$600(UnknownSet.java:14)
                            at dev.cel.expr.UnknownSet$Builder.ensureExprsIsMutable(UnknownSet.java:456)
                            at dev.cel.expr.UnknownSet$Builder.addAllExprs(UnknownSet.java:539)
                            at dev.cel.runtime.InterpreterUtil.createUnknownExprValue(InterpreterUtil.java:95)
                            at dev.cel.runtime.InterpreterUtil.createUnknownExprValue(InterpreterUtil.java:84)
                            at dev.cel.runtime.InterpreterUtil.valueOrUnknown(InterpreterUtil.java:146)
                            at dev.cel.runtime.RuntimeUnknownResolver.resolveSimpleName(RuntimeUnknownResolver.java:114)
                            at dev.cel.runtime.DefaultInterpreter$ExecutionFrame.resolveSimpleName(DefaultInterpreter.java:975)
                            at dev.cel.runtime.DefaultInterpreter$ExecutionFrame.access$200(DefaultInterpreter.java:936)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.resolveIdent(DefaultInterpreter.java:274)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.evalIdent(DefaultInterpreter.java:262)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.evalInternal(DefaultInterpreter.java:193)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.evalCall(DefaultInterpreter.java:387)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.evalInternal(DefaultInterpreter.java:199)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.evalTrackingUnknowns(DefaultInterpreter.java:179)
                            at dev.cel.runtime.DefaultInterpreter$DefaultInterpretable.eval(DefaultInterpreter.java:170)
                            at dev.cel.runtime.CelRuntime$Program.evalInternal(CelRuntime.java:146)
                            at dev.cel.runtime.CelRuntime$Program.evalInternal(CelRuntime.java:121)
                            at dev.cel.runtime.CelRuntime$Program.evalInternal(CelRuntime.java:116)
                            at dev.cel.runtime.CelRuntime$Program.eval(CelRuntime.java:49)
                            at dev.cel.common.ast.CelExprUtil.evaluateExpr(CelExprUtil.java:42)
                            at dev.cel.optimizer.optimizers.ConstantFoldingOptimizer.maybeFold(ConstantFoldingOptimizer.java:251)
                            at dev.cel.optimizer.optimizers.ConstantFoldingOptimizer.optimize(ConstantFoldingOptimizer.java:113)
                            at dev.cel.optimizer.CelOptimizerImpl.optimize(CelOptimizerImpl.java:45)
                            at HelloWorld.run(HelloWorld.java:31)
                            at HelloWorld.main(HelloWorld.java:42)
                         */
                        ConstantFoldingOptimizer.getInstance()
                )
                .build();

        ast = celOptimizer.optimize(ast);

        CelRuntime.Program program = CEL_RUNTIME.createProgram(ast);

        Boolean result = (Boolean) program.eval(Map.of("my_var", "H"));
        System.out.println(result);

    }

    public static void main(String[] args) throws CelValidationException, CelEvaluationException, CelOptimizationException {
        HelloWorld app = new HelloWorld();
        app.run();
    }
}

