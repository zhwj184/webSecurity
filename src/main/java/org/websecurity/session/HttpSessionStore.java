package org.websecurity.session;

import javax.servlet.http.HttpSession;

public interface HttpSessionStore {

	public void store(HttpSession session);
}
