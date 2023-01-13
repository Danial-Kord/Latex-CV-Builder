package Latex;

public class LatexExpressionBuilder {

    public static String getLatex(String syntax, String... args){

        StringBuilder output = new StringBuilder("\\" + syntax);
        for (int i=0;i<args.length;i++){
            output.append("{");
            if(args[i] != null) {
                output.append(LatexExpressionBuilder.HandleSpecialCharacters(args[i]));
            }
            output.append("}");
        }
        output.append("\n");
        return String.valueOf(output);
    }


    private static String HandleSpecialCharacters(String input){
        input = input.replaceAll("[^a-zA-Z0-9%&@$!?]", " ");
        input = input.replaceAll("&", "\\\\&");
        input = input.replaceAll("%", "\\\\%");
        return input;
    }
}
