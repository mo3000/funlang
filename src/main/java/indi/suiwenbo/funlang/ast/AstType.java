package indi.suiwenbo.funlang.ast;

public enum AstType {
    ID, Literal, FunctionDecl, FuncCall, Class, Interface, Expr, ClassMethod,
    ClassVisiableModifier, Type, TypeDecl, IDDecl, GenericDecl, GenericInstance,
    Paren, Operator,
    ClassInit, MethodCall, If, While, For, Choose, IDDeclWithInit, Assignment;
}
