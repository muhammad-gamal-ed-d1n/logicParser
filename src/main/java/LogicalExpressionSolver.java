import Interface.ExpressionInterface;
import Interface.LogicalExpressionSolverInterface;
import Exception.InvalidExpressionException;

import java.util.*;


public class LogicalExpressionSolver implements LogicalExpressionSolverInterface {
    private static Character[] operators = {'>', 'v', '^', '~'};
    private Map<Character, Boolean> map;

    public LogicalExpressionSolver(HashMap<Character, Boolean> map) {
        this.map = map;
    }


    public boolean evaluateExpression(ExpressionInterface expression) throws InvalidExpressionException {
        char[] postfix = expression.getRepresentation().toCharArray();
        Stack<Boolean> stack = new Stack<>();
        if (map == null) throw new InvalidExpressionException("No Values Given");

        try {
            for (int i = 0; i < postfix.length; i++) {
                char curr = postfix[i];

                if (isOperator(curr)) {
                    if (curr == '~') {
                        Boolean operand = stack.pop();
                        stack.push(!operand);
                    } else {
                        Boolean right = stack.pop(), left = stack.pop();
                        switch (curr) {
                            case '^':
                                stack.push(left && right);
                                break;
                            case 'v':
                                stack.push(left || right);
                                break;
                            case '>':
                                stack.push(!left || right);
                                break;
                        }
                    }
                } else {
                    stack.push(map.get(curr));
                }
            }
            Boolean result = stack.pop();
            return result;
        } catch (EmptyStackException error) {
            throw new InvalidExpressionException("Invalid Expression");
        }
    }

    public static boolean isValid(ExpressionInterface expression) throws InvalidExpressionException {
        char[] postfix = expression.getRepresentation().toCharArray();
        Stack<Boolean> stack = new Stack<>();

        try {
            for (int i = 0; i < postfix.length; i++) {
                char curr = postfix[i];

                if (isOperator(curr)) {
                    if (curr == '~') {
                        Boolean operand = stack.pop();
                        stack.push(!operand);
                    } else {
                        Boolean right = stack.pop(), left = stack.pop();
                        switch (curr) {
                            case '^':
                                stack.push(left && right);
                                break;
                            case 'v':
                                stack.push(left || right);
                                break;
                            case '>':
                                stack.push(!left || right);
                                break;
                        }
                    }
                } else {
                    stack.push(true);
                }
            }
            return true;
        } catch (EmptyStackException error) {
            throw new InvalidExpressionException("Invalid Expression");
        }
    }

     public static boolean isOperator(Character c) {
        return Arrays.asList(operators).contains(c);
    }
}
