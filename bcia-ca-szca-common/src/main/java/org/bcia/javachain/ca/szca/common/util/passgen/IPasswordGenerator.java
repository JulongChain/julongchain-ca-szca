
 
package org.bcia.javachain.ca.szca.common.util.passgen;

/**
 * IPasswordGenerator is an interface used to generate passwords used by end entities in EJBCA
 * Usage:
 * <pre>
 *  IPasswordGenerator pwdgen = PasswordGeneratorFactory.getInstance(PasswordGeneratorFactory.PASSWORDTYPE_ALLPRINTABLE);
 *  String pwd = pwdgen.getNewPassword(12, 16);
 * </pre>
 *
 * @version $Id: IPasswordGenerator.java 22117 2015-10-29 10:53:42Z mikekushner $
 */
public interface IPasswordGenerator {
    
    /**
     *  Method generating a new password for the user and returns a string representation of it.
     * 
     * @param minlength indicates the minimun length of the generated password.
     * @param maxlength indicates the maximum length of the generated password.
     * @return the generated password
     */
    String getNewPassword(int minlength, int maxlength);

	String getName();
   
	int getNumerOfDifferentChars();
}
