package com.szca.common.interceptor;

import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cesecore.authentication.AuthenticationFailedException;
import org.cesecore.authentication.tokens.AuthenticationSubject;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.X509CertificateAuthenticationToken;
import org.cesecore.certificates.util.DNFieldExtractor;
import org.cesecore.util.CertTools;
import org.springframework.beans.factory.annotation.Autowired;

import com.szca.common.LoginUser;

import org.bcia.javachain.ca.szca.common.bcca.core.ejb.authentication.web.WebAuthenticationProviderSessionLocal;

public class CertificateInterceptor extends BaseInterceptor {

	@Autowired
	WebAuthenticationProviderSessionLocal authenticationSession;

	public CertificateInterceptor() {
		logger.debug("CertificateInterceptor ......");

	}
 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object obj = request.getSession().getAttribute(LoginUser.LOGIN_USER);
		LoginUser loginUser = null;
		if (obj == null || !(obj instanceof LoginUser)) {
			final X509Certificate[] certificates = (X509Certificate[]) request
					.getAttribute("javax.servlet.request.X509Certificate");
			if (certificates == null || certificates.length == 0) {
				logger.error("Client certificate required.");
				throw new Exception("无法获得客户端证，请指定客户端登录证书。");
			} else {
				loginUser = new LoginUser();
				final Set<X509Certificate> credentials = new HashSet<X509Certificate>();
				// TODO: 在此增加根据验证证书和根据证书验证权限的代码
				credentials.add(certificates[0]);
				final AuthenticationSubject subject = new AuthenticationSubject(null, credentials);

				AuthenticationToken authenticationToken = authenticationSession.authenticate(subject);
				if (authenticationToken == null) {
					logger.error("Authentication failed for certificate: " + CertTools.getSubjectDN(certificates[0]));
					throw new Exception(String.format("指定的客户端证[证书序号:%s,证书颁发者:%s,证书使用者:%s]无法获得授权。",
							certificates[0].getSerialNumber().toString(16), certificates[0].getIssuerDN().getName(),
							certificates[0].getSubjectDN().getName()));
				}

				final String userdn = CertTools.getSubjectDN(certificates[0]);
				final DNFieldExtractor dn = new DNFieldExtractor(userdn, DNFieldExtractor.TYPE_SUBJECTDN);
				String usercommonname = dn.getField(DNFieldExtractor.CN, 0);
				loginUser.setCert(certificates[0]);
				loginUser.setDn(certificates[0].getSubjectDN().getName());
				loginUser.setIssuerDn(certificates[0].getIssuerDN().getName());
				loginUser.setFullName(usercommonname);
				loginUser.setSn(certificates[0].getSerialNumber().toString(16));
				loginUser.setLoginClientIp(request.getRemoteAddr());
				loginUser.setLoginTime(System.currentTimeMillis());
				loginUser.setUsername(usercommonname);
				loginUser.setAuthenticationToken(authenticationToken);
				
				//loginUser.setAuthenticationToken_2(authenticationToken);
				request.getSession().setAttribute(LoginUser.LOGIN_USER, loginUser);
				request.getSession().setAttribute("userauthenticationToken", authenticationToken);
				 
				//开发过渡时期，暂时两个
//				if(authenticationToken!=null){
//					final Set<X500Principal> principals2 = new HashSet<X500Principal>();
//			        principals2.add(certificates[0].getSubjectX500Principal());
//			        final Set<X509Certificate> credentials2 = new HashSet<X509Certificate>();
//			        credentials2.add(certificates[0]);
////			        return new X509CertificateAuthenticationToken(principals, credentials);
//			        
//					cn.net.bcia.cesecore.audit.log.tokens.AuthenticationToken authToken=new cn.net.bcia.cesecore.audit.log.tokens.X509CertificateAuthenticationToken(principals2, credentials2);
//					loginUser.setAuthenticationToken(authToken);
//				}
			}
		} else{
			loginUser = (LoginUser)obj;
		}
		
		 
		if(loginUser==null ||  loginUser.getAuthenticationToken()==null  ) {
			request.getSession().removeAttribute(LoginUser.LOGIN_USER);
			request.getSession().removeAttribute("userauthenticationToken");
			return false;
		}

		return true;

	}

}
