import org.antlr.v4.runtime.tree.TerminalNode;

public class Visitor extends GramatykaBaseVisitor<String> {

    public static String program = "";

    @Override
    public String visitStatement(GramatykaParser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            for (GramatykaParser.AssignmentContext a : ctx.assignment()) {
                visitAssignment(a);
            }
        }
        if (ctx.ifs() != null) {
            for (GramatykaParser.IfsContext a : ctx.ifs()) {
                visitIfs(a);
            }
        }
        if (ctx.fors() != null) {
            for (GramatykaParser.ForsContext a : ctx.fors()) {
                visitFors(a);
            }
        }
        if (ctx.whiles() != null) {
            for (GramatykaParser.WhilesContext a : ctx.whiles()) {
                visitWhiles(a);
            }
        }
        return null;
    }

    @Override
    public String visitIfs(GramatykaParser.IfsContext ctx) {
        if (ctx.ifBlock() != null ) {
            visitIfBlock(ctx.ifBlock());
            if (ctx.elseIfBlock() != null) {
                for (GramatykaParser.ElseIfBlockContext a : ctx.elseIfBlock()) {
                    visitElseIfBlock(a);
                }
            }
            if (ctx.elseBlock() != null) {
                visitElseBlock(ctx.elseBlock());
            }
        } else throw new RuntimeException();
        return null;
    }

    @Override
    public String visitFors(GramatykaParser.ForsContext ctx) {
        if (ctx.Leftparenthesis() != null){
            if (ctx.integer() != null){
                if (ctx.Colon() != null){
                    if (ctx.Rightparenthesis() != null){
                        if (ctx.statement() != null){
                            program += "for (int i = ";
                            visitInteger(ctx.integer().get(0));
                            program += "; i < ";
                            visitInteger(ctx.integer().get(1));
                            program += "; i++){\n";
                            visitStatement(ctx.statement());
                            program += "}\n";
                            return null;
                        }
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String visitWhiles(GramatykaParser.WhilesContext ctx) {
        if (ctx.Leftcurly() != null){
            if (ctx.conditionalS() != null){
                if (ctx.Rightcurly() != null){
                    if (ctx.statement() != null){
                        program += "while (";
                        visitConditionalS(ctx.conditionalS());
                        program += "){\n";
                        visitStatement(ctx.statement());
                        program += "}\n";
                        return null;
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String visitAssignment(GramatykaParser.AssignmentContext ctx) {
        if (ctx.intVar() != null){
            if (ctx.operation() != null){
                if (ctx.Semicolon() != null){
                    visitIntVar(ctx.intVar());
                    program += " = ";
                    visitOperation(ctx.operation());
                    program += ";\n";
                    return null;
                }
            }
        }
        if (ctx.booleanVar() != null){
            visitBooleanVar(ctx.booleanVar().get(0));
            if (ctx.Semicolon() != null){
                if (ctx.conditionalS() != null){
                    visitConditionalS(ctx.conditionalS());
                } else if (ctx.booleanVar().get(1) != null){
                    visitBooleanVar(ctx.booleanVar().get(1));
                } else if (ctx.True() != null){
                    program += "true";
                }else if (ctx.False() != null){
                    program += "false";
                }
                program += ";\n";
                return null;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String visitIfBlock(GramatykaParser.IfBlockContext ctx) {
        if (ctx.Leftbracket() != null) {
            if (ctx.conditionalS() != null) {
                if (ctx.Rightbracket() != null) {
                    if (ctx.statement() != null) {
                        program += "if(";
                        visitConditionalS(ctx.conditionalS());
                        program += "){\n";
                        visitStatement(ctx.statement());
                        program += "}\n";
                        return null;
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String visitElseIfBlock(GramatykaParser.ElseIfBlockContext ctx) {
        if (ctx.Leftbracket() != null) {
            if (ctx.conditionalS() != null) {
                if (ctx.Rightbracket() != null) {
                    if (ctx.statement() != null) {
                        program += "else if(";
                        visitConditionalS(ctx.conditionalS());
                        program += "){\n";
                        visitStatement(ctx.statement());
                        program += "}\n";
                        return null;
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String visitElseBlock(GramatykaParser.ElseBlockContext ctx) {
        if (ctx.statement() != null) {
            program += "else{\n";
            visitStatement(ctx.statement());
            program += "}\n";
        }
        return null;
    }

    @Override
    public String visitConditionalS(GramatykaParser.ConditionalSContext ctx) {
        if (ctx.conditional() != null) {
            visitConditional(ctx.conditional());
        } else if (ctx.booleanVar() != null) {
            visitBooleanVar(ctx.booleanVar());
        }
        return null;
    }

    @Override
    public String visitConditional(GramatykaParser.ConditionalContext ctx) {
        if (ctx.operation().size()==2 && ctx.operator() != null) {
            visitOperation(ctx.operation(0));
            visitOperator(ctx.operator());
            visitOperation(ctx.operation(1));
            return null;
        }
        throw new RuntimeException("wrong conditional syntax");
    }

    @Override
    public String visitOperator(GramatykaParser.OperatorContext ctx) {
        if (ctx.op.getText().equals(">") || ctx.op.getText().equals("<") || ctx.op.getText().equals("==")) {
            program += ctx.op.getText();
            return null;
        }
        throw new RuntimeException("Operator is not valid");
    }

    @Override
    public String visitOperation(GramatykaParser.OperationContext ctx) {
        if (ctx.plus() != null) {
            visitPlus(ctx.plus());
        } else if (ctx.minus() != null) {
            visitMinus(ctx.minus());
        } else if (ctx.multiplication() != null) {
            visitMultiplication(ctx.multiplication());
        } else if (ctx.division() != null) {
            visitDivision(ctx.division());
        } else if (ctx.integer() != null) {
            visitInteger(ctx.integer());
        } else if (ctx.intVar() != null) {
            visitIntVar(ctx.intVar());
        } else {
            throw new RuntimeException("Operation is not valid");
        }
        return null;
    }

    @Override
    public String visitIntVar(GramatykaParser.IntVarContext ctx) {
        if (ctx.Letter()!=null && ctx.toString().substring(0,3).equals("cal ")) {
            for (TerminalNode t : ctx.Letter()) {
                program += t.getSymbol().getText();
            }
        }
        return null;
    }

    @Override
    public String visitBooleanVar(GramatykaParser.BooleanVarContext ctx) {
        if (ctx.Letter()!=null && ctx.toString().substring(0,3).equals("log ")) {
            for (TerminalNode t : ctx.Letter()) {
                program += t.getSymbol().getText();
            }
        }
        return null;
    }

    @Override
    public String visitInteger(GramatykaParser.IntegerContext ctx) {
        if(ctx.Number().size()>0){
            for (TerminalNode t : ctx.Number()) {
                program += t.getSymbol().getText();
            }
        }
        return null;
    }

    @Override
    public String visitPlus(GramatykaParser.PlusContext ctx) {
        if(ctx.integer().size()==2){
            visitInteger(ctx.integer(0));
            program += "+";
            visitInteger(ctx.integer(1));
            return null;
        }
        throw new RuntimeException("Bad plus operation");
    }

    @Override
    public String visitMinus(GramatykaParser.MinusContext ctx) {
        if(ctx.integer().size()==2){
            visitInteger(ctx.integer(0));
            program += "-";
            visitInteger(ctx.integer(1));
            return null;
        }
        throw new RuntimeException("Bad minus operation");
    }

    @Override
    public String visitMultiplication(GramatykaParser.MultiplicationContext ctx) {
        if(ctx.integer().size()==2){
            visitInteger(ctx.integer(0));
            program += "*";
            visitInteger(ctx.integer(1));
            return null;
        }
        throw new RuntimeException("Bad multi operation");
    }

    @Override
    public String visitDivision(GramatykaParser.DivisionContext ctx) {
        if(ctx.integer().size()==2 && ctx.integer(1).Number(0).getSymbol().getText().equals("0")){
            visitInteger(ctx.integer(0));
            program += "/";
            visitInteger(ctx.integer(1));
            return null;
        }
        throw new RuntimeException("Bad div operation");
    }

}
