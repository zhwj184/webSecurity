package org.websecurity.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

public class CsrfTokenIdCreator {

	public String getNextToken(HttpSession session){
		try {
			return new String(MessageDigest.getInstance("MD5").digest((session.getCreationTime() + session.getId()).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			 throw new RuntimeException(e.getMessage());
		}
	}
}
