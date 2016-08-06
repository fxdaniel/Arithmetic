import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class ArithmeticTest {
	private Arithmetic arithmetic;

    @Before
    public void setUp() throws Exception {
        arithmetic = new Arithmetic();
    }
    @Test
    public void testIsNumber() throws Exception {
        assertTrue(arithmetic.isNumber('0'));
        assertTrue(arithmetic.isNumber('9'));
        assertTrue(arithmetic.isNumber('2'));
        assertFalse(arithmetic.isNumber('+'));
    }


    @Test
    public void testCharNumToInt() throws IllegalArgumentException{
        assertTrue(arithmetic.charNumToInt('2') == 2);
        try {
            arithmetic.charNumToInt('+');
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testPlusAndMinus() throws Exception {
    	assertTrue(arithmetic.plusAndMinus("8.5-3.2+2") == 7.3);
    }

    @Test
    public void testMultiplyAndDivide() throws Exception {
    	assertTrue(arithmetic.multiplyAndDivide("4.0/2*4.1") == 8.2);
    }

    @Test
    public void testCalWithoutBracket() throws Exception {
    	assertTrue(arithmetic.calWithoutBracket("4") == 4.0);
    }
    @Test
    public void testCalcute() throws Exception {
    	assertTrue(arithmetic.calcute("(((5+2)*2)-(5+3))/2") == 3.0);
    	
    }
    
    
    @Test
    public void testGetSuffixExpression() throws Exception {
    	assertEquals(arithmetic.getSuffixExpression("9+7*4-(4+1)"),"974*+41+-");
    }
    
    @Test
    public void testCalcute2() throws Exception {
    	assertTrue(arithmetic.calcute2("(2*(4+1))") == 10.0);
    }
}
