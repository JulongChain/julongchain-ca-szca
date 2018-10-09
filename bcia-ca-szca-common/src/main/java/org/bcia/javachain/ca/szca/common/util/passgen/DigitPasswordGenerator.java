
 
package org.bcia.javachain.ca.szca.common.util.passgen;

/**
 * AllPrintablePasswordGenerator is a class generating random passwords containing  
 * DigitPasswordGenerator is a class generating random passwords containing 6-8 char 
 * digit passwords. 
 *
 * @version $Id: DigitPasswordGenerator.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
public class DigitPasswordGenerator extends BasePasswordGenerator{
    
    private static final char[] USEDCHARS = {'1','2','3','4','5','6','7','8','9','0'};
    
	protected static final String NAME = "PWGEN_DIGIT";
    
	public String getName() { return NAME; }
    
    public DigitPasswordGenerator(){
    	super(USEDCHARS);
    }
      
}
