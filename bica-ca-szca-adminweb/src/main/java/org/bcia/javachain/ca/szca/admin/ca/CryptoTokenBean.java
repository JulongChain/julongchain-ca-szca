/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bcia.javachain.ca.szca.admin.ca;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.authorization.control.CryptoTokenRules;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.util.AlgorithmConstants;
import org.cesecore.keybind.InternalKeyBindingInfo;
import org.cesecore.keys.token.BaseCryptoToken;
import org.cesecore.keys.token.CryptoToken;
import org.cesecore.keys.token.CryptoTokenAuthenticationFailedException;
import org.cesecore.keys.token.CryptoTokenInfo;
import org.cesecore.keys.token.CryptoTokenOfflineException;
import org.cesecore.keys.token.KeyPairInfo;
import org.cesecore.keys.token.SoftCryptoToken;
import org.ejbca.config.WebConfiguration;
//import org.ejbca.ui.web.admin.cryptotoken.CryptoTokenMBean.CryptoTokenGuiInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.bcia.javachain.ca.szca.common.cesecore.authorization.control.AccessControlSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.keybind.InternalKeyBindingMgmtSessionLocal;
import org.bcia.javachain.ca.szca.common.cesecore.keys.token.CryptoTokenManagementSessionLocal;

@Repository
public class CryptoTokenBean {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AccessControlSessionLocal accessControlSession;
	@Autowired
	org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal caSession;
	@Autowired
	CryptoTokenManagementSessionLocal cryptoTokenManagementSession;
	@Autowired
	InternalKeyBindingMgmtSessionLocal internalKeyBindingMgmtSession;

	private String newKeyPairAlias = "signKey";
	private String newKeyPairSpec = AlgorithmConstants.KEYALGORITHM_RSA + "4096";

	public List<CryptoTokenGuiInfo> getCryptoTokenGuiList(AuthenticationToken authenticationToken)
			throws AuthorizationDeniedException {
		// List<CryptoTokenGuiInfo> cryptoTokenGuiInfos = new
		// ArrayList<CryptoTokenGuiInfo>();
		// ListDataModel cryptoTokenGuiList = null;
		// if (cryptoTokenGuiList==null) {
		final List<Integer> referencedCryptoTokenIds = getReferencedCryptoTokenIds();
		final List<CryptoTokenGuiInfo> list = new ArrayList<CryptoTokenGuiInfo>();
		for (final CryptoTokenInfo cryptoTokenInfo : cryptoTokenManagementSession
				.getCryptoTokenInfos(authenticationToken)) {
			final String p11LibraryAlias = getP11LibraryAlias(cryptoTokenInfo.getP11Library());
			final boolean allowedActivation = accessControlSession.isAuthorizedNoLogging(authenticationToken,
					CryptoTokenRules.ACTIVATE + "/" + cryptoTokenInfo.getCryptoTokenId().toString());
			final boolean allowedDeactivation = accessControlSession.isAuthorizedNoLogging(authenticationToken,
					CryptoTokenRules.DEACTIVATE + "/" + cryptoTokenInfo.getCryptoTokenId().toString());
			final boolean referenced = referencedCryptoTokenIds
					.contains(Integer.valueOf(cryptoTokenInfo.getCryptoTokenId()));
			list.add(new CryptoTokenGuiInfo(cryptoTokenInfo, p11LibraryAlias, allowedActivation, allowedDeactivation,
					referenced));
			Collections.sort(list, new Comparator<CryptoTokenGuiInfo>() {
				@Override
				public int compare(CryptoTokenGuiInfo cryptoTokenInfo1, CryptoTokenGuiInfo cryptoTokenInfo2) {
					return cryptoTokenInfo1.getTokenName().compareToIgnoreCase(cryptoTokenInfo2.getTokenName());
				}
			});
		}
		// cryptoTokenGuiInfos = list;
		// cryptoTokenGuiList = new ListDataModel(cryptoTokenGuiInfos);
		// }
		// If show the list, then we are on the main page and want to flush the two
		// caches
		// flushCurrent();
		// setCurrentCryptoTokenEditMode(false);
		return list;

	}

	public CryptoTokenGuiInfo getCryptoTokenById(AuthenticationToken authenticationToken, int cryptoTokenId)
			throws AuthorizationDeniedException {

		CryptoTokenInfo cryptoTokenInfo = cryptoTokenManagementSession.getCryptoTokenInfo(authenticationToken,
				cryptoTokenId);
		// Integer cryptoTokenId, String name, boolean active, boolean autoActivation,
		// Class<? extends CryptoToken> type, Properties cryptoTokenProperties

		final List<Integer> referencedCryptoTokenIds = getReferencedCryptoTokenIds();
		final String p11LibraryAlias = getP11LibraryAlias(cryptoTokenInfo.getP11Library());
		final boolean referenced = referencedCryptoTokenIds.contains(Integer.valueOf(cryptoTokenId));
		final boolean allowedActivation = accessControlSession.isAuthorizedNoLogging(authenticationToken,
				CryptoTokenRules.ACTIVATE + "/" + cryptoTokenId);
		final boolean allowedDeactivation = accessControlSession.isAuthorizedNoLogging(authenticationToken,
				CryptoTokenRules.DEACTIVATE + "/" + cryptoTokenId);

		CryptoTokenGuiInfo cryptoTokenGuiInfo = new CryptoTokenGuiInfo(cryptoTokenInfo, p11LibraryAlias,
				allowedActivation, allowedDeactivation, referenced);

		return cryptoTokenGuiInfo;
		// final List<CryptoTokenGuiInfo> list = new ArrayList<CryptoTokenGuiInfo>();
		// for (final CryptoTokenInfo cryptoTokenInfo : cryptoTokenManagementSession
		// .getCryptoTokenInfos(authenticationToken)) {
		// final String p11LibraryAlias =
		// getP11LibraryAlias(cryptoTokenInfo.getP11Library());
		// final boolean allowedActivation =
		// accessControlSession.isAuthorizedNoLogging(authenticationToken,
		// CryptoTokenRules.ACTIVATE + "/" +
		// cryptoTokenInfo.getCryptoTokenId().toString());
		// final boolean allowedDeactivation =
		// accessControlSession.isAuthorizedNoLogging(authenticationToken,
		// CryptoTokenRules.DEACTIVATE + "/" +
		// cryptoTokenInfo.getCryptoTokenId().toString());
		// final boolean referenced = referencedCryptoTokenIds
		// .contains(Integer.valueOf(cryptoTokenInfo.getCryptoTokenId()));
		// list.add(new CryptoTokenGuiInfo(cryptoTokenInfo, p11LibraryAlias,
		// allowedActivation, allowedDeactivation,
		// referenced));
		// Collections.sort(list, new Comparator<CryptoTokenGuiInfo>() {
		// @Override
		// public int compare(CryptoTokenGuiInfo cryptoTokenInfo1, CryptoTokenGuiInfo
		// cryptoTokenInfo2) {
		// return
		// cryptoTokenInfo1.getTokenName().compareToIgnoreCase(cryptoTokenInfo2.getTokenName());
		// }
		// });
		// }
		// cryptoTokenGuiInfos = list;
		// cryptoTokenGuiList = new ListDataModel(cryptoTokenGuiInfos);
		// }
		// If show the list, then we are on the main page and want to flush the two
		// caches
		// flushCurrent();
		// setCurrentCryptoTokenEditMode(false);
		// return list;

	}

	public List<KeyPairGuiInfo> getKeyPairGuiList(AuthenticationToken authenticationToken,
			CryptoTokenGuiInfo cryptoTokenGuiInfo) throws AuthorizationDeniedException {
		// if (keyPairGuiList==null) {
		final List<KeyPairGuiInfo> ret = new ArrayList<KeyPairGuiInfo>();
		if (cryptoTokenGuiInfo.isActive()) {
			// Add existing key pairs
			try {
				for (KeyPairInfo keyPairInfo : cryptoTokenManagementSession.getKeyPairInfos(authenticationToken,
						cryptoTokenGuiInfo.getCryptoTokenId())) {
					ret.add(new KeyPairGuiInfo(keyPairInfo));
				}
			} catch (CryptoTokenOfflineException ctoe) {
				log.error("Failed to load key pairs from CryptoToken: " + ctoe.getMessage());
			}
			// Add placeholders for key pairs
			// String keyPlaceholders = getCurrentCryptoToken().getKeyPlaceholders();
			String keyPlaceholders = cryptoTokenGuiInfo.getCryptoTokenInfo().getCryptoTokenProperties()
					.getProperty(CryptoToken.KEYPLACEHOLDERS_PROPERTY, "");
			for (String template : keyPlaceholders.split("[" + CryptoToken.KEYPLACEHOLDERS_OUTER_SEPARATOR + "]")) {
				if (!template.trim().isEmpty()) {
					ret.add(new KeyPairGuiInfo(template));
				}
			}
		}
		Collections.sort(ret, new Comparator<KeyPairGuiInfo>() {
			@Override
			public int compare(KeyPairGuiInfo keyPairInfo1, KeyPairGuiInfo keyPairInfo2) {
				return keyPairInfo1.getAlias().compareTo(keyPairInfo2.getAlias());
			}
		});
		// keyPairGuiInfos = ret;
		// keyPairGuiList = new ListDataModel(keyPairGuiInfos);
		// }
		return ret;
	}

	/** Invoked when admin requests a CryptoToken creation. */
	public void saveCurrentCryptoToken(AuthenticationToken authenticationToken, String cryptoTokenName,
			String secretCode, boolean isAllowExportPrivateKey, boolean isAutoActivate)
			throws AuthorizationDeniedException {
		String msg = null;
		// if
		// (!getCurrentCryptoToken().getSecret1().equals(getCurrentCryptoToken().getSecret2()))
		// {
		// msg = "Authentication codes do not match!";
		// } else {
		try {
			final Properties properties = new Properties();
			String className = null;
			/*
			 * if (PKCS11CryptoToken.class.getSimpleName().equals(getCurrentCryptoToken().
			 * getType())) { className = PKCS11CryptoToken.class.getName(); String library =
			 * getCurrentCryptoToken().getP11Library();
			 * properties.setProperty(PKCS11CryptoToken.SHLIB_LABEL_KEY, library); String
			 * slotTextValue = getCurrentCryptoToken().getP11Slot().trim(); String
			 * slotLabelType = getCurrentCryptoToken().getP11SlotLabelType(); //Perform some
			 * name validation
			 * if(slotLabelType.equals(Pkcs11SlotLabelType.SLOT_NUMBER.getKey())) {
			 * if(!Pkcs11SlotLabelType.SLOT_NUMBER.validate(slotTextValue)) { msg =
			 * "Slot must be an absolute number"; } } else
			 * if(slotLabelType.equals(Pkcs11SlotLabelType.SLOT_INDEX.getKey())) {
			 * if(slotTextValue.charAt(0) != 'i') { slotTextValue = "i" + slotTextValue; }
			 * if(!Pkcs11SlotLabelType.SLOT_INDEX.validate(slotTextValue)) { msg =
			 * "Slot must be an absolute number or use prefix 'i' for indexed slots."; } }
			 * 
			 * // Verify that it is allowed SlotList allowedSlots = getP11SlotList(); if
			 * (allowedSlots != null && !allowedSlots.contains(slotTextValue)) { throw new
			 * IllegalArgumentException("Slot number "
			 * +slotTextValue+" is not allowed. Allowed slots are: "+allowedSlots); }
			 * 
			 * properties.setProperty(PKCS11CryptoToken.SLOT_LABEL_VALUE, slotTextValue);
			 * properties.setProperty(PKCS11CryptoToken.SLOT_LABEL_TYPE, slotLabelType); //
			 * The default should be null, but we will get a value "default" from the GUI
			 * code in this case.. final String p11AttributeFile =
			 * getCurrentCryptoToken().getP11AttributeFile(); if
			 * (!"default".equals(p11AttributeFile)) {
			 * properties.setProperty(PKCS11CryptoToken.ATTRIB_LABEL_KEY, p11AttributeFile);
			 * } } else if
			 * (SoftCryptoToken.class.getSimpleName().equals(getCurrentCryptoToken().getType
			 * ())) { className = SoftCryptoToken.class.getName();
			 * properties.setProperty(SoftCryptoToken.NODEFAULTPWD, "true"); }
			 */

			className = SoftCryptoToken.class.getName();
			properties.setProperty(SoftCryptoToken.NODEFAULTPWD, "true");
			/*
			 * if (getCurrentCryptoToken().isAllowExportPrivateKey()) {
			 * properties.setProperty(CryptoToken.ALLOW_EXTRACTABLE_PRIVATE_KEY,
			 * String.valueOf(getCurrentCryptoToken().isAllowExportPrivateKey())); } if
			 * (getCurrentCryptoToken().getKeyPlaceholders() != null) {
			 * properties.setProperty(CryptoToken.KEYPLACEHOLDERS_PROPERTY,
			 * getCurrentCryptoToken().getKeyPlaceholders()); }
			 */
			if (isAllowExportPrivateKey)
				properties.setProperty(CryptoToken.ALLOW_EXTRACTABLE_PRIVATE_KEY,
						String.valueOf(isAllowExportPrivateKey));

			final char[] secret = secretCode.toCharArray();// getCurrentCryptoToken().getSecret1().toCharArray();
			final String name = cryptoTokenName;// getCurrentCryptoToken().getName();
			// if (getCurrentCryptoTokenId() == 0) {
			// if (secret.length>0) {
			// if (getCurrentCryptoToken().isAutoActivate()) {
			// BaseCryptoToken.setAutoActivatePin(properties, new String(secret), true);
			// }
			if (isAutoActivate)
				BaseCryptoToken.setAutoActivatePin(properties, new String(secret), true);
			int currentCryptoTokenId = cryptoTokenManagementSession.createCryptoToken(authenticationToken, name,
					className, properties, null, secret);
			msg = "CryptoToken created successfully.";
			// } else {
			// super.addNonTranslatedErrorMessage("You must provide an authentication code
			// to create a CryptoToken.");
			// }
			/*
			 * } else { if (getCurrentCryptoToken().isAutoActivate()) { if (secret.length>0)
			 * { BaseCryptoToken.setAutoActivatePin(properties, new String(secret), true); }
			 * else { // Indicate that we want to reuse current auto-pin if present
			 * properties.put(CryptoTokenManagementSessionLocal.KEEP_AUTO_ACTIVATION_PIN,
			 * Boolean.TRUE.toString()); } }
			 * cryptoTokenManagementSession.saveCryptoToken(authenticationToken,
			 * getCurrentCryptoTokenId(), name, properties, secret); msg =
			 * "CryptoToken saved successfully."; }
			 */
			// flushCaches();
			// setCurrentCryptoTokenEditMode(false);
		} catch (CryptoTokenOfflineException e) {
			msg = e.getMessage();
		} catch (CryptoTokenAuthenticationFailedException e) {
			msg = e.getMessage();
		} catch (AuthorizationDeniedException e) {
			msg = e.getMessage();
		} catch (IllegalArgumentException e) {
			msg = e.getMessage();
		} catch (Throwable e) {
			msg = e.getMessage();
			log.info("", e);
		}
		// }
		/*
		 * if (msg != null) { if (log.isDebugEnabled()) {
		 * log.debug("Message displayed to user: " + msg); }
		 * super.addNonTranslatedErrorMessage(msg); }
		 */
	}

	public void generateNewKeyPair(AuthenticationToken authenticationToken, int cryptoTokenId, String alias,
			String keyPairSpec) {
		log.trace(">generateNewKeyPair");
		try {
			cryptoTokenManagementSession.createKeyPair(authenticationToken, cryptoTokenId, alias, keyPairSpec);
		} catch (CryptoTokenOfflineException e) {
			log.error("Token is off-line. KeyPair cannot be generated.");
		} catch (Exception e) {
			final String logMsg = authenticationToken.toString() + " failed to generate a keypair:";
			log.error(logMsg + e.getMessage());
		}

		log.trace("<generateNewKeyPair");

	}
	
	
	public void testKeyPair(AuthenticationToken authenticationToken, int cryptoTokenId, String alias) throws Exception{
       
            cryptoTokenManagementSession.testKeyPair(authenticationToken,cryptoTokenId, alias);
                 
    }
	
	
	public void removeKeyPair(AuthenticationToken authenticationToken, int cryptoTokenId, String alias) throws Exception{
      
		cryptoTokenManagementSession.removeKeyPair(authenticationToken, cryptoTokenId, alias);
                   
    }
	
	
	public PublicKey getPublicKey(AuthenticationToken authenticationToken, int cryptoTokenId, String alias) throws Exception{
	      
	//	cryptoTokenManagementSession.removeKeyPair(authenticationToken, cryptoTokenId, alias);
	return	cryptoTokenManagementSession.getPublicKey(authenticationToken, cryptoTokenId, alias).getPublicKey();
                   
    }

	/** @return a List of all CryptoToken Identifiers referenced by CAs. */
	private List<Integer> getReferencedCryptoTokenIds() {
		final List<Integer> ret = new ArrayList<Integer>();
		// Add all CryptoToken ids referenced by CAs
		for (int caId : caSession.getAllCaIds()) {
			try {
				ret.add(Integer.valueOf(caSession.getCAInfoInternal(caId).getCAToken().getCryptoTokenId()));
			} catch (CADoesntExistsException e) {
				log.warn("Referenced CA has suddenly disappearded unexpectedly. caid=" + caId);
			}
		}
		// Add all CryptoToken ids referenced by InternalKeyBindings
		for (final String internalKeyBindingType : internalKeyBindingMgmtSession.getAvailableTypesAndProperties()
				.keySet()) {
			for (final InternalKeyBindingInfo internalKeyBindingInfo : internalKeyBindingMgmtSession
					.getAllInternalKeyBindingInfos(internalKeyBindingType)) {
				ret.add(Integer.valueOf(internalKeyBindingInfo.getCryptoTokenId()));
			}
		}
		// In the future other components that use CryptoTokens should be checked here
		// as well!
		return ret;
	}

	
	
	/** @return alias if present otherwise the filename */
	private String getP11LibraryAlias(String library) {
		if (library == null) {
			return "";
		}

		WebConfiguration.P11LibraryInfo libinfo = WebConfiguration.getAvailableP11LibraryToAliasMap().get(library);
		if (libinfo == null)
			return library;
		String alias = libinfo.getAlias();
		if (alias == null || alias.isEmpty())
			return library;
		return alias;
	}
}
