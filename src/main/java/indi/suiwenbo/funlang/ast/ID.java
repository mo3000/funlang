package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.PrimitiveValueType;

public class ID implements AstNode {

    private boolean isConst = false;

    public void setConst() {
        isConst = true;
    }

    @Override
    public PrimitiveValueType valueType() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }


    @Override
    public AstType type() {
        return AstType.ID;
    }

    @Override
    public void eval() {

    }

    @Override
    public void parse() {

    }
}
