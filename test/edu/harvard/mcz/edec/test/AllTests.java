package edu.harvard.mcz.edec.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestOfEDec.class,
	TestCountryLookup.class
})
public class AllTests {

}
