package org.websecurity.session;

import javax.servlet.http.HttpSession;

public interface HttpSessionStore {

	public void seriable(HttpSession session);
	
	public void deseriable(HttpSession session);
}
