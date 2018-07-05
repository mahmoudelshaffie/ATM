package org.atm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescendingOrderUnitTest {

	@Test
	public void testCompareWithS1GreaterThanS2ShouldReturnNegative() {
		DescendingOrder target = new DescendingOrder();
		short s1 = 10;
		short s2 = 5;
		int actual = target.compare(s1, s2);
		assertTrue(actual < 0);
	}
	
	@Test
	public void testCompareWithS1LessThanS2ShouldReturnPositive() {
		DescendingOrder target = new DescendingOrder();
		short s1 = 110;
		short s2 = 147;
		int actual = target.compare(s1, s2);
		assertTrue(actual > 0);
	}
	
	@Test
	public void testCompareWithS1EqualsS2ShouldReturnZero() {
		DescendingOrder target = new DescendingOrder();
		short s1 = 110;
		short s2 = 110;
		int actual = target.compare(s1, s2);
		assertTrue(actual == 0);
	}
}
