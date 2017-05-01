/*
 * Copyright (C) 2015 TYONLINE TECHNOLOGY PTY. LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.com.tyo.services;

public class SecretBase implements Secret {
	
	private String token; // use for storing the token, could be user id
	
	private String secret; // token secret, could be the password for the correspondent id
	
	private int typeSn;  // social network type
	
	private int typeAuth;  // for oauth, OAUTH_CONSUMER or , OAUTH_ACCESS_TOKEN
	
	public SecretBase() {
		this("", "");
	}
	
	public SecretBase(int type) {
		this(type, "", "");
	}
	
	public SecretBase(String token, String secret) {
		this.setToken(token);
		this.setSecret(secret);		
		this.typeSn = SocialNetworkConstants.ANY;
		this.typeAuth = SocialNetworkConstants.INFORMATION_ANY;
	}

	public SecretBase(int type, String token, String secret) {
		this(token, secret);
		this.typeSn = type;
	}

	public SecretBase(int type, int typeAuth) {
		this(type, "", "");
		this.typeAuth = typeAuth;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getType() {
		return typeSn;
	}

	public void setType(int type) {
		this.typeSn = type;
	}

	public int getTypeAuth() {
		return typeAuth;
	}

	public void setTypeAuth(int typeAuth) {
		this.typeAuth = typeAuth;
	}

	public String getTypeString() {
		return getTypeString(typeSn);
	}
	
	public static String getTypeString(int type) {
		return SocialNetworkConstants.SOCIAL_NETWORK[type];
	}

	@Override
	public String buildKey() {
		 return buildKey(this.getType(), this.getTypeAuth());
	}
	
	public static String buildKey(int type, int authType) {
		return type + "_" + authType;
	}

	@Override
	public boolean isBlank() {
		return token.length() == 0 || secret.length() == 0;
	}
}
