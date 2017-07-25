package com.cl.service.security;

import com.cl.common.lang.util.BeanCopier;
import com.cl.common.lang.util.StringUtil;
import com.cl.service.session.SessionLocal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;


public class ShiroSessionUtils {
	protected static final String SESSION_LOCAL_KEY = "sessionLocal";
	protected static final String SESSION_LOCAL_KEY_BAK = "sessionLocalBak";
	protected final static Logger logger = (Logger) LoggerFactory.getLogger(ShiroSessionUtils.class);
	
	public static final Object getSessionValue(String key) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					return shiroSession.getAttribute(key);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final Object removeSessionValue(String key) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				return shiroSession.removeAttribute(key);
			}
		}
		return null;
	}
	
	public static final void setSessionValue(String key, Object object) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					shiroSession.setAttribute(key, object);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
	}
	
	public static final SessionLocal getSessionLocal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					return (SessionLocal) shiroSession.getAttribute(SESSION_LOCAL_KEY_BAK);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final void setSessionLocal(SessionLocal local) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				SessionLocal sessionLocal = new SessionLocal();
				BeanCopier.staticCopy(local, sessionLocal);
				shiroSession.setAttribute(SESSION_LOCAL_KEY, local);
				shiroSession.setAttribute(SESSION_LOCAL_KEY_BAK, sessionLocal);
			}
			
		}
	}
	
	public static final void clear() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				
				shiroSession.removeAttribute(SESSION_LOCAL_KEY);
				shiroSession.removeAttribute(SESSION_LOCAL_KEY_BAK);
			}
			
		}
	}
	
	public static final void commitSessionLocalModify() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			SessionLocal newSessionLocal = (SessionLocal) shiroSession
				.getAttribute(SESSION_LOCAL_KEY_BAK);
			SessionLocal sessionLocal = new SessionLocal();
			BeanCopier.staticCopy(newSessionLocal, sessionLocal);
			shiroSession.setAttribute(SESSION_LOCAL_KEY, sessionLocal);
		}
	}
	
	public static boolean isLogin() {
		return getSessionLocal() != null && StringUtil.isNotBlank(getSessionLocal().getUserName());
	}
}
