import Interface.ExpressionInterface;
import Exception.InvalidExpressionException;

import java.util.Arrays;
import java.util.Stack;

public class Expression implements ExpressionInterface {
    private String representation;
    private Character[] operators = {'>', 'v', '^', '~'};

    public Expression(String expression) throws InvalidExpressionException {
        setRepresentation(expression);
    }

    public String getRepresentation() {
        return representation;
    };
    public void setRepresentation(String representation) throws InvalidExpressionException {
        Stack<Character> stack = new Stack<>();
        representation = representation.replaceAll("[\s]+", "");
        char[] expression = representation.toCharArray();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length; i++) {
            char curr = expression[i];

            if (curr == '(') {stack.push(curr);}
            else if (curr == ')') {
                if (stack.empty()) throw new InvalidExpressionException("Invalid Expression");
                while (stack.peek() != '(') {
                    result.append(stack.pop());
                    if (stack.empty()) throw new InvalidExpressionException("Invalid Expression");
                }
                stack.pop();
            }
            else if (isOperator(curr)) {
                if(stack.empty()) {
                    stack.push(curr);
                }
                else {
                    char top = stack.peek();
                    if (getPrecedence(curr) > getPrecedence(top)) {
                        stack.push(curr);
                    }
                    else {
                        result.append(stack.pop());
                        while(!stack.empty()) {
                            top = stack.peek();
                            if (top == '(') {
                                break;
                            }
                            else if (getPrecedence(curr) > getPrecedence(top)) {
                                break;
                            }
                            result.append(stack.pop());
                        }
                        stack.push(curr);
                    }
                }
            }
            else if (Character.isAlphabetic(curr)) {
                result.append(curr);
            }
        }
        if(!stack.empty()) {
            while(!stack.empty()) {
                result.append(stack.pop());
            }
        }
        this.representation = result.toString();
    };
    private boolean isOperator(Character c) {return Arrays.asList(operators).contains(c);}
    private int getPrecedence(Character operator) {
        return Arrays.asList(operators).indexOf(operator);
    }
}
