import AST.RootNode;
import FrontEnd.*;
import Parser.*;
import Util.error.IsaiahErrorListener;
import Util.error.error;
import Util.scope.GlobalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception{
        String name = "test.mx";
        InputStream input = new FileInputStream(name);
//        InputStream input = System.in;
        try {
//            int value = Integer.parseInt("-2147483648");
            RootNode ASTRoot;
            GlobalScope gScope = new GlobalScope(null);
            IsaiahLexer lexer = new IsaiahLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new IsaiahErrorListener());
            IsaiahParser parser = new IsaiahParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new IsaiahErrorListener());
            ParseTree parseTreeRoot = parser.program();
            // Semantic Part.
            ASTBuilder astBuilder = new ASTBuilder(gScope);
            ASTRoot = (RootNode) astBuilder.visit(parseTreeRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
            System.out.println("Semantic Check passed.");
            // IR generate & Print
//            IRBuilder irBuilder = new IRBuilder();
//            ASTRoot.accept(irBuilder);
//            IRRoot irRoot = irBuilder.BuiltRoot();;


        } catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}