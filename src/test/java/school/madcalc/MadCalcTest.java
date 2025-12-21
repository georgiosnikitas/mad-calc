
package school.madcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Test class for MadCalc calculator application.
 * 
 * <p>This class contains unit tests for all calculator functionality including:
 * basic arithmetic operations, parentheses handling, and special functions
 * (square root, power of two, and cube).</p>
 * 
 * @author Mad Calc Team
 * @version 1.0
 */
class MadCalcTest {

    /**
     * Tests simple addition operation.
     * Verifies that 2+3 equals 5.
     */
    @Test
    void testSimpleAddition() {
        assertEquals(5.0, MadCalcTestHelper.evaluate("2+3"), 0.0001);
    }

    /**
     * Tests expressions with parentheses.
     * Verifies correct evaluation of expressions like 2*(3+4), (2+3)*4, etc.
     */
    @Test
    void testParentheses() {
        assertEquals(14.0, MadCalcTestHelper.evaluate("2*(3+4)"), 0.0001);
        assertEquals(20.0, MadCalcTestHelper.evaluate("(2+3)*4"), 0.0001);
        assertEquals(2.0, MadCalcTestHelper.evaluate("10/(2+3)"), 0.0001);
        assertEquals(9.0, MadCalcTestHelper.evaluate("(2+(3*4))-5"), 0.0001);
    }

    /**
     * Tests handling of mismatched parentheses.
     * Verifies that appropriate exceptions are thrown for invalid parentheses.
     */
    @Test
    void testMismatchedParentheses() {
        Exception ex1 = assertThrows(RuntimeException.class, () -> MadCalcTestHelper.evaluate("(2+3"));
        assertTrue(ex1.getMessage().contains("Mismatched parentheses"));
        Exception ex2 = assertThrows(RuntimeException.class, () -> MadCalcTestHelper.evaluate("2+)"));
        assertTrue(ex2.getMessage().contains("Unexpected"));
    }

    /**
     * Tests square root calculation with positive integers.
     * Verifies sqrt(16)=4 and sqrt(25)=5.
     */
    @Test
    void testSquareRootPositiveInteger() throws Exception {
        assertEquals(4.0, MadCalcTestHelper.calculateSquareRoot("16"), 0.0001);
        assertEquals(5.0, MadCalcTestHelper.calculateSquareRoot("25"), 0.0001);
    }

    /**
     * Tests square root calculation with positive decimal numbers.
     * Verifies sqrt(2.0)≈1.414 and sqrt(5.0)≈2.236.
     */
    @Test
    void testSquareRootPositiveDecimal() throws Exception {
        assertEquals(1.41421356, MadCalcTestHelper.calculateSquareRoot("2.0"), 0.0001);
        assertEquals(2.23606798, MadCalcTestHelper.calculateSquareRoot("5.0"), 0.0001);
    }

    /**
     * Tests square root calculation with zero.
     * Verifies sqrt(0)=0.
     */
    @Test
    void testSquareRootZero() throws Exception {
        assertEquals(0.0, MadCalcTestHelper.calculateSquareRoot("0"), 0.0001);
    }

    /**
     * Helper class to access private methods of MadCalc using reflection.
     * 
     * <p>This class provides static methods that use Java reflection to invoke
     * private methods in the MadCalc class for testing purposes.</p>
     */
    static class MadCalcTestHelper {
        /**
         * Evaluates a mathematical expression using reflection to access
         * the private evaluate method in MadCalc.
         * 
         * @param expr the mathematical expression to evaluate
         * @return the result of the evaluation
         * @throws RuntimeException if the expression is invalid or evaluation fails
         */
        static double evaluate(String expr) {
            // Use reflection to call private static method
            try {
                java.lang.reflect.Method m = MadCalc.class.getDeclaredMethod("evaluate", String.class);
                m.setAccessible(true);
                return (double) m.invoke(null, expr);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
            }
        }

        /**
         * Calculates the square root of a number using reflection to access
         * the private calculateSquareRoot method in MadCalc.
         * 
         * @param numberStr the string representation of the number
         * @return the square root of the number
         * @throws Exception if the string cannot be parsed or number is negative
         */
        static double calculateSquareRoot(String numberStr) throws Exception {
            try {
                java.lang.reflect.Method m = MadCalc.class.getDeclaredMethod("calculateSquareRoot", String.class);
                m.setAccessible(true);
                return (double) m.invoke(null, numberStr);
            } catch (java.lang.reflect.InvocationTargetException e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) e.getCause();
                }
                throw e;
            }
        }

        /**
         * Calculates the power of two of a number using reflection to access
         * the private calculatePowerOfTwo method in MadCalc.
         * 
         * @param numberStr the string representation of the number
         * @return the square of the number
         * @throws Exception if the string cannot be parsed as a number
         */
        static double calculatePowerOfTwo(String numberStr) throws Exception {
            try {
                java.lang.reflect.Method m = MadCalc.class.getDeclaredMethod("calculatePowerOfTwo", String.class);
                m.setAccessible(true);
                return (double) m.invoke(null, numberStr);
            } catch (java.lang.reflect.InvocationTargetException e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) e.getCause();
                }
                throw e;
            }
        }

        /**
         * Calculates the cube of a number using reflection to access
         * the private calculateCube method in MadCalc.
         * 
         * @param numberStr the string representation of the number
         * @return the cube of the number
         * @throws Exception if the string cannot be parsed as a number
         */
        static double calculateCube(String numberStr) throws Exception {
            try {
                java.lang.reflect.Method m = MadCalc.class.getDeclaredMethod("calculateCube", String.class);
                m.setAccessible(true);
                return (double) m.invoke(null, numberStr);
            } catch (java.lang.reflect.InvocationTargetException e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) e.getCause();
                }
                throw e;
            }
        }
    }

    /**
     * Tests square root calculation with a negative number.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void testSquareRootNegative() throws Exception {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> MadCalcTestHelper.calculateSquareRoot("-4"));
        assertTrue(ex.getMessage().contains("Cannot calculate square root of a negative number"));
    }

    /**
     * Tests square root calculation with non-numeric input.
     * Verifies that a NumberFormatException is thrown for invalid input.
     */
    @Test
    void testSquareRootNonNumeric() throws Exception {
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculateSquareRoot("abc"));
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculateSquareRoot(""));
    }

    /**
     * Tests power of two calculation with positive integers.
     * Verifies pow2(3)=9 and pow2(4)=16.
     */
    @Test
    void testPowerOfTwoPositiveInteger() throws Exception {
        assertEquals(9.0, MadCalcTestHelper.calculatePowerOfTwo("3"), 0.0001);
        assertEquals(16.0, MadCalcTestHelper.calculatePowerOfTwo("4"), 0.0001);
    }

    /**
     * Tests power of two calculation with positive decimal numbers.
     * Verifies pow2(1.5)=2.25 and pow2(2.5)=6.25.
     */
    @Test
    void testPowerOfTwoPositiveDecimal() throws Exception {
        assertEquals(2.25, MadCalcTestHelper.calculatePowerOfTwo("1.5"), 0.0001);
        assertEquals(6.25, MadCalcTestHelper.calculatePowerOfTwo("2.5"), 0.0001);
    }

    /**
     * Tests power of two calculation with zero.
     * Verifies pow2(0)=0.
     */
    @Test
    void testPowerOfTwoZero() throws Exception {
        assertEquals(0.0, MadCalcTestHelper.calculatePowerOfTwo("0"), 0.0001);
    }

    /**
     * Tests power of two calculation with one.
     * Verifies pow2(1)=1.
     */
    @Test
    void testPowerOfTwoOne() throws Exception {
        assertEquals(1.0, MadCalcTestHelper.calculatePowerOfTwo("1"), 0.0001);
    }

    /**
     * Tests power of two calculation with negative numbers.
     * Verifies that squaring negative numbers returns positive results.
     */
    @Test
    void testPowerOfTwoNegative() throws Exception {
        assertEquals(9.0, MadCalcTestHelper.calculatePowerOfTwo("-3"), 0.0001);
        assertEquals(16.0, MadCalcTestHelper.calculatePowerOfTwo("-4"), 0.0001);
    }

    /**
     * Tests power of two calculation with non-numeric input.
     * Verifies that a NumberFormatException is thrown for invalid input.
     */
    @Test
    void testPowerOfTwoNonNumeric() throws Exception {
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculatePowerOfTwo("xyz"));
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculatePowerOfTwo(""));
    }

    /**
     * Tests cube calculation with positive integers.
     * Verifies cube(3)=27 and cube(4)=64.
     */
    @Test
    void testCubePositiveInteger() throws Exception {
        assertEquals(27.0, MadCalcTestHelper.calculateCube("3"), 0.0001);
        assertEquals(64.0, MadCalcTestHelper.calculateCube("4"), 0.0001);
    }

    /**
     * Tests cube calculation with positive decimal numbers.
     * Verifies cube(1.5)=3.375 and cube(2.5)=15.625.
     */
    @Test
    void testCubePositiveDecimal() throws Exception {
        assertEquals(3.375, MadCalcTestHelper.calculateCube("1.5"), 0.0001);
        assertEquals(15.625, MadCalcTestHelper.calculateCube("2.5"), 0.0001);
    }

    /**
     * Tests cube calculation with zero.
     * Verifies cube(0)=0.
     */
    @Test
    void testCubeZero() throws Exception {
        assertEquals(0.0, MadCalcTestHelper.calculateCube("0"), 0.0001);
    }

    /**
     * Tests cube calculation with one.
     * Verifies cube(1)=1.
     */
    @Test
    void testCubeOne() throws Exception {
        assertEquals(1.0, MadCalcTestHelper.calculateCube("1"), 0.0001);
    }

    /**
     * Tests cube calculation with negative numbers.
     * Verifies that cubing negative numbers returns negative results.
     */
    @Test
    void testCubeNegative() throws Exception {
        assertEquals(-27.0, MadCalcTestHelper.calculateCube("-3"), 0.0001);
        assertEquals(-64.0, MadCalcTestHelper.calculateCube("-4"), 0.0001);
    }

    /**
     * Tests cube calculation with non-numeric input.
     * Verifies that a NumberFormatException is thrown for invalid input.
     */
    @Test
    void testCubeNonNumeric() throws Exception {
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculateCube("abc"));
        assertThrows(NumberFormatException.class, () -> MadCalcTestHelper.calculateCube(""));
    }
}