
 
package org.bcia.javachain.ca.szca.common.util.passgen;

/**
 * This class allows all letters and digits except those that look similar like 0O and l1I.
 *
 * @version $Id: NoLookALikeLDPasswordGenerator.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
public class NoLookALikeLDPasswordGenerator extends BasePasswordGenerator {
    
    private static final char[] USEDCHARS = {'2','3','4','5','6','7','8','9',
    	                                                      'q','Q','w','W','e','E','r',
    	                                                      'R','t','T','y','Y','u','U','i','o','p','P','a',
    	                                                      'A','s','S','d','D','f','F','g','G','h','H','j','J','k','K',
    	                                                      'L','z','Z','x','X','c','C','v','V','b','B','n','N','m',
    	                                                      'M'};
        
	protected static final String NAME = "PWGEN_NOLOOKALIKELD";
    
	public String getName() { return NAME; }
	
    public NoLookALikeLDPasswordGenerator(){
    	super(USEDCHARS);
    }
      
}
