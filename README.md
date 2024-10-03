# Description
Program used to demonstrate issue in CEL `ConstantFoldingOptimizer`.

# How to run
`gradlew runHelloWorld`

Program will fail with following exception

```
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
	at HelloWorld.run(HelloWorld.java:62)
	at HelloWorld.main(HelloWorld.java:73)
```


If you comment out `ConstantFoldingOptimizer.getInstance()` in `HelloWorld` class. Program will work.