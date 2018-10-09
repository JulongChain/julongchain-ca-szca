

package org.bcia.javachain.ca.szca.common.util.passgen;

import java.security.SecureRandom;
import java.util.Random;


public abstract class BasePasswordGenerator implements IPasswordGenerator {

    private final char[] usedchars;
    // Declare the random here so that the seed only have to be generated once. This will save time.
	final private static Random ran = new SecureRandom();

    protected BasePasswordGenerator(char[] usedchars){
       this.usedchars = usedchars;
    }

	/**
	 * @see org.ejbca.util.passgen.IPasswordGenerator
	 */
	@Override
	public String getNewPassword(int minlength, int maxlength){
		final int difference = maxlength - minlength;
		// Calculate the length of password
		int passlen = maxlength;
		if(minlength != maxlength) {
			passlen = minlength + BasePasswordGenerator.ran.nextInt(difference);
		}
		final char[] password = new char[passlen];
		for (int i=0; i < passlen; i++) {
			password[i] = this.usedchars[BasePasswordGenerator.ran.nextInt(this.usedchars.length)];
		}
		return new String(password);
	}

    @Override
	public int getNumerOfDifferentChars() { return usedchars.length; }
}
