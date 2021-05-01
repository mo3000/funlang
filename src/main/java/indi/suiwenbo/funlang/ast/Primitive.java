package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.PrimitiveValueType;
import indi.suiwenbo.funlang.parser.TypeValue;


public class Primitive implements AstNode {

    private PrimitiveValueType primitiveValueType;
    private Object value;

    @Override
    public PrimitiveValueType valueType() {
        return primitiveValueType;
    }

    public void setValue(PrimitiveValueType type, Object value) {
        this.primitiveValueType = type;
        this.value = value;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public AstType type() {
        return AstType.Literal;
    }

    @Override
    public void eval() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void parse() {
        throw new RuntimeException("not implemented");
    }
}
