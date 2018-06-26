
 
package org.bcia.javachain.ca.szca.common.util.passgen;

/**
 * This class allows all letters and digits except those that look similar like O0 and I1l
 * or sound similar in english like aj and eg.
 *
 * @version $Id: NoLookOrSoundALikeENLDPasswordGenerator.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
public class NoLookOrSoundALikeENLDPasswordGenerator extends BasePasswordGenerator {
    
    private static final char[] USEDCHARS = {'2','3','4','5','6','7','8','9',
    																		'q','Q','w','W','r','R','t','T',
    																		'y','Y','u','U','i','o','p','P',
    																		's','S','d','D','f','F','h','H',
    																		'k','K','L','z','Z','x','X','c','C',
    																		'v','V','b','B','n','N','m','M'};
        
	protected static final String NAME = "PWGEN_NOLOSALIKEENLD";
    
	public String getName() { return NAME; }
	
    public NoLookOrSoundALikeENLDPasswordGenerator(){
    	super(USEDCHARS);
    }
      
}
