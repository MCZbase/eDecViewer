/** 
 * Copyright © 2013 President and Fellows of Harvard College
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of Version 2 of the GNU General Public License
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.edec.test;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import edu.harvard.mcz.edec.Core;
import edu.harvard.mcz.edec.EDec;
import edu.harvard.mcz.edec.Species;

public class TestOfEDec {

	@Test
	public void testEDecString() {
		String testFile;
		InputStream stream = AllTests.class.getResourceAsStream("example_eDec.txt");
		testFile = convertStreamToString(stream);
		EDec eDec = new EDec(testFile);
		System.out.println(eDec.toEDecDocument());
		// Test round trip identity.
		assertEquals(testFile.replace("\n", ""), eDec.toEDecDocument().replace("\n", ""));
	}

	
	@Test
	public void testCreation() {
		EDec eDec = new EDec();
		Core core = new Core();
		Species species = new Species();
		assertEquals("",species.getGenus());
		assertEquals("S",core.getPurpose());
	}
	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
