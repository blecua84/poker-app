package com.blecua84.pokerapp.api.service;

import com.blecua84.pokerapp.api.service.impl.CombinatorialImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for interface Combinatorial.
 *
 * @author josejavier.blecua
 */
public class CombinatorialTest {

    @Test
    public void testConstructor() {
        System.out.println("Combination(2,4)");
        int subItems = 2;
        int items = 4;
        long expectCombinations = 6L;
        Combinatorial instance = new CombinatorialImpl(subItems, items);
        assertEquals(expectCombinations, instance.combinations());
        assertEquals(subItems, instance.size());
    }
    @Test(expected = IllegalArgumentException.class) public void testContructorSubItemError() {
        System.out.println("Combination(0,1)");
        int subItems = 0;
        int items = 1;
        Combinatorial instance = new CombinatorialImpl(subItems, items);
    }
    @Test(expected = IllegalArgumentException.class) public void testContructorItemError() {
        System.out.println("Combination(5,1)");
        int subItems = 5;
        int items = 1;
        Combinatorial instance = new CombinatorialImpl(subItems, items);
    }
}