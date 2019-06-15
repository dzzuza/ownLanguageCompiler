import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class App {
    public static void main( String[] args )
    {
        ANTLRInputStream inputStream = new ANTLRInputStream("jesli [3<5] dla (1:3) dopoki { 2 < 3 } stop stop stop");
        GramatykaLexer markupLexer = new GramatykaLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(markupLexer);
        GramatykaParser markupParser = new GramatykaParser(commonTokenStream);

        GramatykaParser.StatementContext bodyContext = markupParser.statement();
        Visitor visitor = new Visitor();
        visitor.visit(bodyContext);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("src\\main\\antlr4\\program.java");
        } catch (FileNotFoundException e) {
            System.out.println("nie moge utworzyc pliku");
        }
        printWriter.print(Visitor.program);
        printWriter.close();
    }
}
