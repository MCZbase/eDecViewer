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
package edu.harvard.mcz.edec.mczbase;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.gbif.ecat.model.ParsedName;
import org.gbif.ecat.parser.NameParser;
import org.gbif.ecat.parser.UnparsableException;

import edu.harvard.mcz.edec.Core;
import edu.harvard.mcz.edec.EDec;
import edu.harvard.mcz.edec.Species;

/**
 * Library class for MCZbase coldfusion to serialize transaction records
 * as USFW eDec documents used for online creation of USFWS 3-177 forms.
 * 
 * Invoke from coldfusion with the following (where getLoan and loanSpecies 
 * are coldfusion query resultsets): 
<pre>
<cfobject type="Java" class="edu.harvard.mcz.edec.mczbase.EDECBuilder" name="builder">
<cfset res = builder.init(#getLoan#,#loanSpecies#)>
<cfset returnVal = builder.getEDecFile()>
<cfoutput>#returnVal#</cfoutput>
</pre>
 * 
 * @author mole
 *
 */
public class EDecBuilder {
	
	private EDec edec;
	
	/**
	 * Given a transaction_id, construct a corresponding edec object.
	 * 
	 * @param transaction_id
	 */
	public EDecBuilder (ResultSet loanAgents, ResultSet speciesList) { 
		edec = new EDec();
		Core core = new Core();
		core.setIEBuisiness("Museum of Comparative Zoology");
		core.setIEAddress("26 Oxford Street");
		core.setIEState("MA");
		core.setIECity("Cambridge");
		core.setIEZip("02138");
		core.setPurpose("S");
		
		try {
			if (loanAgents.first()) { 
			    core.setForLastName(loanAgents.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		edec.setCoreFields(core);
		
		
		try {
			while (speciesList.next()) { 
				Species species = new Species();
				species.setWildlifeCode("SPE");  // Specimen (Scientific or museum)
				String scientificName = speciesList.getString(1);
System.out.println(scientificName);
				NameParser parser = new NameParser();
				parser.debug = false;
				ParsedName pn = null;
				try {
					pn = parser.parse(scientificName);
					species.setGenus(pn.getGenusOrAbove());
					species.setSpecies(pn.getSpecificEpithet());
					species.setSubspecies(pn.getInfraSpecificEpithet());
				} catch (UnparsableException e) {
					e.printStackTrace();
				}
				try {
				    species.setWildlifeCode("SPE");
				    species.setCountryOfOrigin(speciesList.getString(2));
				    species.setQuantity(speciesList.getFloat(3));
				} catch (SQLException e) {
				   e.printStackTrace();
				}

				edec.getSpeciesFields().add(species);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//NOTE: Don't add an init() method, it will conflict with the coldfusion call.

	/**
	 * Obtain the loaded edec serialization of the transaction.
	 * 
	 * @return a string using the edec tilde and pipe separated text 
	 * of core and species list data for a 3-177 form.
	 */
	public String getEDecFile() { 
		return edec.toEDecDocument();
	}
}
