import indi.suiwenbo.funlang.parser.EndOfFileException;
import indi.suiwenbo.funlang.parser.EndOfStatementException;
import indi.suiwenbo.funlang.parser.Token;
import indi.suiwenbo.funlang.parser.Tokenizer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestTokenizer {

    private Tokenizer tokenizer;

    @Test
    public void testBasic() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.setText("int a = 123 + 456;");
        List<String> tokens = new ArrayList<>();
        try {
            while (tokenizer.notEof()) {
                tokens.add(tokenizer.readOne().getText());
            }
            assertEquals(tokens, List.of("int", "a", "=", "123", "+", "456"));
        } catch (EndOfStatementException | EndOfFileException e) {

        }
    }

    @Test
    public void testParens() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.setText("int a = 123 * (456 - 653) / 2;");
        List<String> tokens = new ArrayList<>();
        try {
            while (tokenizer.notEof()) {
                tokens.add(tokenizer.readOne().getText());
            }
            assertEquals(tokens.toString(), "[int, a, =, 123, *, (, 456, -, 653, ), /, 2]");
        } catch (EndOfStatementException | EndOfFileException e) {

        }
    }
}
