/**
 * 
 */
package edu.harvard.mcz.edec.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.harvard.mcz.edec.Country;
import edu.harvard.mcz.edec.Species;

/**
 * @author mole
 *
 */
public class TestCountryLookup {

	/**
	 * Test method for {@link edu.harvard.mcz.edec.Country#getCodeForCountryName(java.lang.String)}.
	 */
	@Test
	public void testGetCodeForCountryName() {
		Country country = new Country();
		assertEquals("MX",country.getCodeForCountryName("Mexico"));
		assertEquals("US",country.getCodeForCountryName("United States"));		
		assertEquals("IN",country.getCodeForCountryName("India"));
		assertEquals("PH",country.getCodeForCountryName("Philippines"));
		
		assertEquals("",country.getCodeForCountryName("Axzzzzz"));
	}
	
	@Test
	public void testSettingCountryCode() { 
		Species species = new Species();
		species.setCountryOfOrigin("Mexico");
		assertEquals("MX",species.getCountryOfOrigin());
		species.setCountryOfOrigin("United States");
		assertEquals("US",species.getCountryOfOrigin());
		species.setCountryOfOrigin("India");
		assertEquals("IN",species.getCountryOfOrigin());	
		
		species.setCountryOfOrigin("Axzzzzzz");
		assertEquals("Ax",species.getCountryOfOrigin());
		species.setCountryOfOrigin("AA");
		assertEquals("AA",species.getCountryOfOrigin());
		species.setCountryOfOrigin("bb");
		assertEquals("bb",species.getCountryOfOrigin());		
	}

}
