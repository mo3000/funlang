import indi.suiwenbo.funlang.parser.EndOfFileException;
import indi.suiwenbo.funlang.parser.Lexer;
import indi.suiwenbo.funlang.parser.Tokenizer;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLexer {

    @Test
    public void testBasic() {
        Lexer lexer = new Lexer();
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.setText("const a: int = 123 + 3412.55;");
        try {
            while (tokenizer.notEof()) {
                lexer.add(tokenizer.readOne());
            }
        } catch (EndOfFileException ignored) {

        }
        assertEquals(lexer.getRawTokens().toString(), "[(const, 0, 0), (a, 6, 0), (:, 7, 0), (int, 9, 0), (=, 13, 0), (123, 15, 0), (+, 19, 0), (3412.55, 21, 0), (;, 28, 0)]");
    }
}
