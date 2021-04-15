package indi.suiwenbo.funlang.parser;

import java.util.List;
import java.util.Objects;

public class TypeValue {

    private boolean isPrimitiveType;
    private PrimitiveValueType valueType;

    private boolean isFuncType;
    private boolean isClassType;
    private boolean isGeneric;
    private boolean isArray;
    private String containerName;
    private List<TypeValue> typeArgs;
    private TypeValue arrayElement;
    private TextToken unResolvedTypename;
    private List<TypeValue> typeTemplateArgs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeValue typeValue = (TypeValue) o;

        if (isPrimitiveType != typeValue.isPrimitiveType) return false;
        if (isFuncType != typeValue.isFuncType) return false;
        if (isClassType != typeValue.isClassType) return false;
        if (isGeneric != typeValue.isGeneric) return false;
        if (valueType != typeValue.valueType) return false;
        if (isArray != typeValue.isArray) return false;
        if (!Objects.equals(unResolvedTypename, typeValue.unResolvedTypename))
            return false;
        if (!Objects.equals(arrayElement, typeValue.arrayElement))
            return false;
        if (!Objects.equals(containerName, typeValue.containerName))
            return false;
        return Objects.equals(typeArgs, typeValue.typeArgs);
    }

    public TypeValue() {
        isFuncType = false;
        isClassType = false;
        isGeneric = false;
        isArray = false;
    }

    public void setPrimitiveType(PrimitiveValueType type) {
        valueType = type;
        isPrimitiveType = true;
    }

    public void setUnResolvedTypename(TextToken unResolvedTypename) {
        this.unResolvedTypename = unResolvedTypename;
    }

    public boolean lessThan(TypeValue o) {
        //todo
        return true;
    }

    public void setArrayType(TypeValue value) {
        arrayElement = value;
        isArray = true;
    }


    public void setFuncType() {
        isFuncType = true;
    }

    public void setGenericTypeArgs(List<TypeValue> typeArgs) {
        this.typeArgs = typeArgs;
    }

    public void setGenericContainer(String name) {
        isGeneric = true;
        containerName = name;
    }

}
