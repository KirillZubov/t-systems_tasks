package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param infix mathematical statement containing digits, '.' (dot) as decimal mark,
     *              parentheses, operations signs '+', '-', '*', '/'<br>
     *              Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */


    String evaluate(String infix) {
        return evalRPN(infixToPostfix(infix));
    }

    private static String evalRPN(String expr) {
        if (expr == null) {
            return null;
        }
        String cleanExpr = cleanExpr(expr);
        LinkedList<Double> stack = new LinkedList<>();
        for (String token : cleanExpr.split("\\s")) {
            Double tokenNum = null;
            try {
                tokenNum = Double.parseDouble(token);

            } catch (NumberFormatException ignored) {
            }
            if (tokenNum != null) {
                stack.push(Double.parseDouble(token + ""));
            } else if (token.equals("*")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand * secondOperand);
            } else if (token.equals("/")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                if (secondOperand == 0) return null;
                stack.push(firstOperand / secondOperand);
            } else if (token.equals("-")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand - secondOperand);
            } else if (token.equals("+")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand + secondOperand);
            } else {//just in case
                return null;
            }
        }
        Double answer = stack.pop();
        return format(answer);
    }

    private static String formatingText(String inflix) {
        if (inflix == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String last = null;
        for (String t : inflix.split("")) {
            Pattern p = Pattern.compile("\\d|[\\+\\-\\/\\*\\(\\)\\.]");
            Matcher m = p.matcher(t);
            if (!m.matches()) {
                return null;
            }

            if (t.equals("")) {
                if (t.equals(last)) {
                    return null;
                }
            }

            if (t.equals(".")) {
                if (t.equals(last)) {
                    return null;
                }
            }

            if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/") || t.equals("(") || t.equals(")")) {
                if (t.equals(last)) {
                    return null;
                }
                sb.append(" ").append(t).append(" ");
            } else {
                sb.append(t);
            }
            last = t;
        }

        return sb.toString();
    }

    private static String infixToPostfix(String infix) throws EmptyStackException, StringIndexOutOfBoundsException {

        infix = formatingText(infix);

        if (infix == null) {
            return null;
        }

        try {
            final String ops = "-+/*";
            StringBuilder sb = new StringBuilder();
            Stack<Integer> s = new Stack<>();

            for (String token : infix.split("\\s")) {
                if (token.isEmpty())
                    continue;
                char c = token.charAt(0);
                int idx = ops.indexOf(c);

                // check for operator
                if (idx != -1) {
                    if (s.isEmpty())
                        s.push(idx);

                    else {
                        while (!s.isEmpty()) {
                            int prec2 = s.peek() / 2;
                            int prec1 = idx / 2;
                            if (prec2 > prec1 || (prec2 == prec1) && c != '^')
                                sb.append(ops.charAt(s.pop())).append(' ');
                            else break;
                        }
                        s.push(idx);
                    }
                } else if (c == '(') {
                    s.push(-2); // -2 stands for '('
                } else if (c == ')') {
                    // until '(' on stack, pop operators.
                    while (s.peek() != -2)
                        sb.append(ops.charAt(s.pop())).append(' ');
                    s.pop();
                } else {
                    sb.append(token).append(' ');
                }
            }
            while (!s.isEmpty())
                sb.append(ops.charAt(s.pop())).append(' ');
            return sb.toString();
        } catch (EmptyStackException | StringIndexOutOfBoundsException ioe) {
            return null;
        }
    }

    private static String format(Double answer) {
        String res;
        if (answer % 1 == 0) {
            Long ans = Math.round(answer);
            res = ans.toString();
        } else {
            DecimalFormat df = new DecimalFormat("#.####");
            Double ans = Double.valueOf(df.format(answer));
            res = ans.toString();
        }
        return res;
    }


    private static String cleanExpr(String expr) {
        //remove all non-operators, non-whitespace, and non digit chars
        return expr.replaceAll("[^\\*\\+\\-\\d/\\s\\.]", "");
    }

}