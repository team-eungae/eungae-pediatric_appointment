package com.playdata.eungae.security;

import java.util.Map;

public interface OAuthUserInfo {

	Map<String, Object> getAttributes();
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
