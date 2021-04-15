package indi.suiwenbo.funlang.ast;

import indi.suiwenbo.funlang.parser.ParseError;
import indi.suiwenbo.funlang.parser.TextToken;
import indi.suiwenbo.funlang.parser.PrimitiveValueType;
import indi.suiwenbo.funlang.parser.TypeValue;
import static indi.suiwenbo.funlang.parser.Util.matchPair;

import java.util.*;

public class Expr implements AstNode {

    private boolean paren = false;
    private AstType type;
    private PrimitiveValueType primitiveValueType;
    private List<Expr> children;
    private List<TextToken> rawTokens;
    private List<Integer> priority = null;
    private List<AstNode> exprs = null;
    private Map<String, AstNode> localSymbolTable;
    private Map<String, AstNode> __this ;
    private String image;
    private boolean isIdConst = false;

    public Expr(List<TextToken> rawTokens) {
        this.rawTokens = rawTokens;
        children = new ArrayList<>();
        priority = new ArrayList<>();
    }

    public Expr() {
        children = new ArrayList<>();
    }

    public void setRawTokens(List<TextToken> rawTokens) {
        this.rawTokens = rawTokens;
    }

    public void setValueType(PrimitiveValueType primitiveValueType) {
        this.primitiveValueType = primitiveValueType;
    }

    public void setType(AstType type) {
        this.type = type;
    }

    public void setParen() {
        paren = true;
    }

    public boolean isParen() {
        return paren;
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
        return type;
    }

    @Override
    public void eval() {

    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void parse() {
        exprs = new ArrayList<>();
        for (int i = 0; i < rawTokens.size();) {
            var t = rawTokens.get(i);
            var text = t.getText();
            switch (text) {
                case "const", "let" -> {
                    int to = firstSemiColon(i);
                    var expr = new Expr(subNodes(i, to));
                    initExprs();
                    exprs.add(expr);
                    expr.parseIdDecl();
                    String idname = expr.getDeclName();
                    if (localSymbolTable.containsKey(idname)) {
                        throw new ParseError("id with name: " + idname + " exists", t.getLinePos(), t.getLine());
                    }
                    localSymbolTable.put(idname, expr.getAssignmentExpr());
                    i = to + 1;
                }
                default -> throwError(t, "invalid token " + t.getText());
            }
        }
    }

    private void parseIdDecl() {
        int i = 0;
        var t = rawTokenAt(i);
        var text = t.getText();
        if (text.equals("const")) {
            isIdConst = true;
        }
        i++;
        assertInRange(i);
        t = rawTokenAt(i);
        text = t.getText();
        // asure text is a valid id name
        // todo stronger test may be add
        if (!Character.isAlphabetic(text.charAt(0))) {
            throwError(t, "invalid id name: " + text);
        }
        image = text;
        i++;
        if (i < rawTokens.size()) {
            t = rawTokenAt(i);
            text = t.getText();
            if (text.equals(":")) {
                setType(AstType.IDDeclWithInit);
            } else {
                throwError(t, "invalid token: " + text);
            }
        } else {
            setType(AstType.IDDecl);
        }
    }

    private void parseRval(int from) {

    }

    private TypeValue parseTypeText(List<TextToken> typeText) {
        return parseTypeText(typeText, 0, typeText.size());
    }

    private TypeValue parseTypeText(List<TextToken> typeText, int from, int to) {
        assert to > from;
        if (to - from == 1) {
            //is primitive | class | function
            return parseTypeText(typeText.get(from));
        } else if (isSetType(typeText, from, to)) {
            //type text contains '&' || '|'
            return parseSetType(typeText, from, to);
        } else if (isArrayType(typeText, from, to)) {
            return parseArrayType(typeText, from, to);
        } else {
            // is generic
            String containerName = typeText.get(from).getText();
            var type = new TypeValue();
            type.setGenericContainer(containerName);
            assert typeText.get(from + 1).getText().equals("<");
            assert typeText.get(to - 1).getText().equals(">");
            List<TypeValue> args = parseGenericTypeArgs(typeText, from + 2, to - 1);
            type.setGenericTypeArgs(args);
            return type;
        }
    }

    private boolean isArrayType(List<TextToken> typeText, int from, int to) {
        assert from + 2 < to;
        return typeText.get(to - 1).getText().equals("]")
            && typeText.get(to - 2).getText().equals("[");
    }

    private TypeValue parseArrayType(List<TextToken> typeText, int from, int to) {
        var arrayElement = parseTypeText(typeText, from, to - 2);
        TypeValue value = new TypeValue();
        value.setArrayType(arrayElement);
        return value;
    }

    private List<TypeValue> parseGenericTypeArgs(List<TextToken> typeText, int from, int to) {
        List<TypeValue> types = new ArrayList<>();
        int i = from;
        while (i < to) {
            int typeEnd = findTypeEnd(typeText, i, to);
            types.add(parseTypeText(typeText, i, typeEnd));
            i = typeEnd + 1;
        }
        assert !types.isEmpty();
        return types;
    }

    private int findTypeEnd(List<TextToken> typeText, int from, int to) {
        int i = from;
        while (i < to) {
            var text = typeText.get(i).getText();
            if (text.equals("<")) {
                return matchPair(typeText, i) + 1;
            } else if (text.equals(",")) {
                return i;
            } else {
                i++;
            }
        }
        return to;
    }

    private TypeValue parseSetType(List<TextToken> typeText, int from, int to) {
        throw new RuntimeException("not implemented");
    }

    private boolean isSetType(List<TextToken> typeText, int from , int to) {
        for (int i = from; i < to;) {
            var text = typeText.get(i).getText();
            if (text.equals("<")) {
                i = matchPair(typeText, from);
            } else if (text.equals("|") || text.equals("&")) {
                return true;
            }
            i++;
        }
        return false;
    }

    private List<List<TextToken>> split(List<TextToken> list) {
        List<List<TextToken>> split = new ArrayList<>();
        int i = 0;
        while (i < list.size()) {
            int to = i;
            while (to < list.size() && !list.get(to).getText().equals(",")) {
                to++;
            }
            split.add(list.subList(i, to));
            i = to + 1;
        }
        return split;
    }

    private TypeValue parseTypeText(TextToken tt) {
        TypeValue t = new TypeValue();
        var text = tt.getText();
        switch (text) {
            case "int", "string", "bool", "float", "void" -> {
                switch (text)  {
                    case "int" ->
                        t.setPrimitiveType(PrimitiveValueType.Int);
                    case "string" ->
                        t.setPrimitiveType(PrimitiveValueType.String);
                    case "bool" ->
                        t.setPrimitiveType(PrimitiveValueType.Bool);
                    case "float" ->
                        t.setPrimitiveType(PrimitiveValueType.Float);
                    case "void" ->
                        t.setPrimitiveType(PrimitiveValueType.Void);
                }
            }
            default -> t.setUnResolvedTypename(tt);
        }
        return t;
    }

    private void assertInRange(int i) {
        assert i < rawTokens.size();
    }

    public void throwError(TextToken t, String msg) {
        throw new ParseError(msg, t.getLinePos(), t.getLine());
    }

    private TextToken rawTokenAt(int i) {
        return rawTokens.get(i);
    }

    private AstNode getAssignmentExpr() {
        return null;
    }

    public String getDeclName() {
        switch (type) {
            case IDDeclWithInit, IDDecl -> {}
            default -> throw new RuntimeException("not implemented");
        }
        return image;
    }

    private void initExprs() {
        if (exprs == null) {
            exprs = new ArrayList<>();
        }
    }

    private int firstSemiColon(int from) {
        for (int i = from; i < rawTokens.size(); i++) {
            if (rawTokens.get(i).getText().equals(";")) {
                return i;
            }
        }
        throw new RuntimeException("shouldn't reach here");
    }

    private List<TextToken> subNodes(int from, int to) {
        return rawTokens.subList(from, to);
    }

}
