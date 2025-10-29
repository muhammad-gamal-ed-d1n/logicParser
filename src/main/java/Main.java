import java.util.HashMap;
import java.util.Scanner;
import Exception.InvalidExpressionException;

public class Main {
    public static void main(String[] args) {
        Expression expression;
        LogicalExpressionSolver solver;
        Scanner scan = new Scanner(System.in);

        try {
            System.out.print("Logical Expression: ");
            String exp = scan.nextLine();
            expression = new Expression(exp);
            LogicalExpressionSolver.isValid(expression);
            HashMap<Character, Boolean> map = Main.getMap(expression.getRepresentation().toCharArray());
            solver = new LogicalExpressionSolver(map);
            System.out.println("Result: " + solver.evaluateExpression(expression));
        } catch (InvalidExpressionException e) {
            System.out.println("Invalid Expression");
        }
    }

    public static HashMap getMap(char[] expression) {
        HashMap<Character, Boolean> map = new HashMap<>();

        for (int i = 0; i < expression.length; i++) {
            char curr = expression[i];

            if (Character.isAlphabetic(curr) && !LogicalExpressionSolver.isOperator(curr) && !map.containsKey(curr)) {
                while (true) {
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Input Value of '" + curr + "' (true/false): ");
                    String value = scan.nextLine();
                    if (value.equals("true")) {
                        map.put(curr, true);
                        break;
                    }
                    else if (value.equals("false")) {
                        map.put(curr, false);
                        break;
                    }
                    System.out.println("Incorrect Value. Please Enter (true/false).");
                }
            }
        }
        return map;
    }
}
