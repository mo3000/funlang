package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.PrimitiveValueType;

public interface AstNode {
    public PrimitiveValueType valueType();
    public Object value();
    public AstType type();
    public void eval();
    public void parse();
}
