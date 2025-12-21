
package school.madcalc;
import java.util.Scanner;

/**
 * MadCalc - A command-line calculator supporting basic arithmetic operations,
 * parentheses, and special functions (square root, power of two, and cube).
 * 
 * <p>This calculator accepts mathematical expressions and evaluates them using
 * recursive descent parsing with proper operator precedence.</p>
 * 
 * @author Mad Calc Team
 * @version 1.0
 */
public class MadCalc {
    /**
     * Main entry point for the Mad Calc application.
     * Provides an interactive command-line interface for evaluating mathematical expressions.
     * 
     * <p>Supported operations:</p>
     * <ul>
     *   <li>Basic arithmetic: +, -, *, /</li>
     *   <li>Parentheses for grouping</li>
     *   <li>sqrt &lt;number&gt; - Square root function</li>
     *   <li>pow2 &lt;number&gt; - Power of two function</li>
     *   <li>cube &lt;number&gt; - Cube function</li>
     * </ul>
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Mad Calc!");
        System.out.println("Enter a mathematical expression (with parentheses), 'sqrt <number>', 'pow2 <number>', 'cube <number>', or type 'exit' to quit:");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
                        } else if (input.trim().toLowerCase().startsWith("sqrt ")) {
                            try {
                                double result = calculateSquareRoot(input.trim().substring(5));
                                System.out.println("🎉 The answer is: " + result);
                            } catch (NumberFormatException e) {
                                System.out.println("Oops! 'sqrt' needs a number. Please try again with 'sqrt <number>'.");
                            } catch (IllegalArgumentException e) {
                                System.out.println("I can only calculate the square root of positive numbers!");
                            }
                        } else if (input.trim().toLowerCase().startsWith("pow2 ")) {
                            try {
                                double result = calculatePowerOfTwo(input.trim().substring(5));
                                System.out.println("🎉 The answer is: " + result);
                            } catch (NumberFormatException e) {
                                System.out.println("Oops! 'pow2' needs a number. Please try again with 'pow2 <number>'.");
                            }
                        } else if (input.trim().toLowerCase().startsWith("cube ")) {
                            try {
                                double result = calculateCube(input.trim().substring(5));
                                System.out.println("🎉 The answer is: " + result);
                            } catch (NumberFormatException e) {
                                System.out.println("Oops! 'cube' needs a number. Please try again with 'cube <number>'.");
                            }
                        }
                        else {
                            try {
                                double result = evaluate(input);
                                System.out.println("🎉 The answer is: " + result);
                            } catch (RuntimeException e) {
                                String msg = e.getMessage();
                                if (msg != null && msg.contains("Mismatched parentheses")) {
                                    System.out.println("Oops! Your parentheses don't match. Please check and try again.");
                                } else if (msg != null && msg.contains("Unexpected")) {
                                    System.out.println("Hmm, I see something I don't understand. Please use only numbers, +, -, *, /, parentheses, square root (sqrt), power of two (pow2), and cube (cube) operations.");
                                } else {
                                    System.out.println("Oops! That doesn't look like a valid expression. Please try again.");
                                }
                            } catch (Exception e) {
                                System.out.println("Oops! That doesn't look like a valid expression. Please try again.");
                            }
                        }
                    }
        scanner.close();
    }

    /**
     * Evaluates a mathematical expression supporting +, -, *, /, and parentheses.
     * Uses recursive descent parsing to handle operator precedence correctly.
     * 
     * @param expr the mathematical expression to evaluate
     * @return the result of the evaluation
     * @throws RuntimeException if the expression is invalid or contains mismatched parentheses
     */
    private static double evaluate(String expr) {
        return new Parser(expr).parse();
    }

    /**
     * Inner parser class that implements recursive descent parsing
     * for mathematical expressions.
     * 
     * <p>This parser handles operator precedence and parentheses
     * using the following grammar:</p>
     * <ul>
     *   <li>expression = term | expression `+` term | expression `-` term</li>
     *   <li>term = factor | term `*` factor | term `/` factor</li>
     *   <li>factor = `+` factor | `-` factor | number | `(` expression `)`</li>
     * </ul>
     */
    private static class Parser {
        private final String input;
        private int pos = -1, ch;

        /**
         * Constructs a new Parser for the given input expression.
         * Removes all whitespace from the input during initialization.
         * 
         * @param input the mathematical expression to parse
         */
        Parser(String input) {
            this.input = input.replaceAll("\\s+", "");
            nextChar();
        }

        /**
         * Advances to the next character in the input string.
         * Sets ch to -1 when the end of input is reached.
         */
        void nextChar() {
            ch = (++pos < input.length()) ? input.charAt(pos) : -1;
        }

        /**
         * Attempts to consume a specific character from the input.
         * Skips any leading spaces before checking.
         * 
         * @param charToEat the character to consume
         * @return true if the character was consumed, false otherwise
         */
        boolean eat(int charToEat) {
            while (ch == ' ') nextChar();
            if (ch == charToEat) {
                nextChar();
                return true;
            }
            return false;
        }

        /**
         * Parses and evaluates the entire expression.
         * 
         * @return the result of the expression
         * @throws RuntimeException if there are unexpected characters after parsing
         */
        double parse() {
            double x = parseExpression();
            if (pos < input.length()) throw new RuntimeException("Unexpected: " + (char)ch);
            return x;
        }

        /**
         * Parses an expression (addition and subtraction operations).
         * Handles left-to-right evaluation of terms connected by + or - operators.
         * 
         * @return the result of the expression
         */
        double parseExpression() {
            double x = parseTerm();
            while (true) {
                if (eat('+')) x += parseTerm();
                else if (eat('-')) x -= parseTerm();
                else return x;
            }
        }

        /**
         * Parses a term (multiplication and division operations).
         * Handles left-to-right evaluation of factors connected by * or / operators.
         * 
         * @return the result of the term
         */
        double parseTerm() {
            double x = parseFactor();
            while (true) {
                if (eat('*')) x *= parseFactor();
                else if (eat('/')) x /= parseFactor();
                else return x;
            }
        }

        /**
         * Parses a factor (numbers, parenthesized expressions, and unary operators).
         * Handles:
         * <ul>
         *   <li>Unary plus and minus</li>
         *   <li>Parenthesized expressions</li>
         *   <li>Numeric literals (including decimals)</li>
         * </ul>
         * 
         * @return the result of the factor
         * @throws RuntimeException if parentheses are mismatched or unexpected characters are found
         */
        double parseFactor() {
            if (eat('+')) return parseFactor(); // unary plus
            if (eat('-')) return -parseFactor(); // unary minus

            double x;
            int startPos = this.pos;
            if (eat('(')) { // parentheses
                x = parseExpression();
                if (!eat(')')) throw new RuntimeException("Mismatched parentheses");
            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                x = Double.parseDouble(input.substring(startPos, this.pos));
            } else {
                throw new RuntimeException("Unexpected: " + (char)ch);
            }
            return x;
        }
    }

    /**
     * Calculates the square root of a number.
     * 
     * @param numberStr the string representation of the number
     * @return the square root of the number
     * @throws NumberFormatException if the string cannot be parsed as a number
     * @throws IllegalArgumentException if the number is negative
     */
    private static double calculateSquareRoot(String numberStr) {
        double num = Double.parseDouble(numberStr.trim());
        if (num < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number");
        }
        return Math.sqrt(num);
    }

    /**
     * Calculates the square (power of two) of a number.
     * 
     * @param numberStr the string representation of the number
     * @return the square of the number (number²)
     * @throws NumberFormatException if the string cannot be parsed as a number
     */
    private static double calculatePowerOfTwo(String numberStr) {
        double num = Double.parseDouble(numberStr.trim());
        return num * num;
    }

    /**
     * Calculates the cube (power of three) of a number.
     * 
     * @param numberStr the string representation of the number
     * @return the cube of the number (number³)
     * @throws NumberFormatException if the string cannot be parsed as a number
     */
    private static double calculateCube(String numberStr) {
        double num = Double.parseDouble(numberStr.trim());
        return num * num * num;
    }
}