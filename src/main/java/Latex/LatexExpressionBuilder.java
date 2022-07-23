package Latex;

public class LatexExpressionBuilder {

    public static String getLatex(String syntax, String... args){
        StringBuilder output = new StringBuilder("\\" + syntax);
        for (int i=0;i<args.length;i++){
            output.append("{");
            if(args[i] != null)
                output.append(args[i]);
            output.append("}");
        }
        output.append("\n");
        return String.valueOf(output);
    }
}
