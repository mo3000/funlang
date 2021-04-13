import indi.suiwenbo.funlang.parser.EndOfFileException;
import indi.suiwenbo.funlang.parser.Lexer;
import indi.suiwenbo.funlang.parser.Tokenizer;
import org.junit.Test;

public class TestLexer {

    @Test
    public void testBasic() {
        Lexer lexer = new Lexer();
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.setText("int a = 123 + 3412.55;");
        try {
            while (tokenizer.notEof()) {
                lexer.add(tokenizer.readOne());
            }
            lexer.printRawTokens();
        } catch (EndOfFileException ignored) {

        }


    }
}
