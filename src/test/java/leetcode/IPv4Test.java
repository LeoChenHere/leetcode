/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leo
 */
public class IPv4Test {
    
    public IPv4Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ipv4 method, of class IPv4.
     */
    @Test
    public void testIpv4() {
        System.out.println("ipv4");
        IPv4 instance = new IPv4();
        String result = null;
        //Test 1
        result = ""+instance.ipv4("172.168.5.1");
        assertEquals("2896692481", result);
        
        //Test 2
        result = ""+instance.ipv4("172 . 168.5.1");
        assertEquals("2896692481", result);
        
        //Test 3
        result = ""+instance.ipv4("17 2 . 168.5.1");
        assertEquals("0", result);
        
        //Test 4
        result = ""+instance.ipv4("172 168.5.1");
        assertEquals("0", result);
        
        //Test 4
        result = ""+instance.ipv4("256.168.5.1");
        assertEquals("0", result);
        
    }
    
}
