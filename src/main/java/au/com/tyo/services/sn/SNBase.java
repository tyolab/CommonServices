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

package au.com.tyo.services.sn;

public abstract class SNBase {
	
	private static String appId = "sn4j";
	
	protected Secrets secrets;
	
	protected int type;
	
	protected Callback callback;
	
	protected SecretOAuth secretOAuth;
	
	protected UserInfo userInfo;
	
	protected UserInfo alias;
	
	protected String userProfileImageUrl;
	
	protected String consumerKey;
	
	protected String consumerKeySecret;
	
	private SocialNetworkListener listener;
	
	protected boolean authenticated;
	
	public SNBase() {
		this(SocialNetwork.ANY, null);
	}

	public SNBase(int type) {
		this(type, null);
	}
	
	public SNBase(int type, Callback callback) {
		this.type = type;
		
		setCallback(callback);
		
		secretOAuth = new SecretOAuth(SocialNetwork.ANY);
		userInfo = new UserInfo(SocialNetwork.ANY, SocialNetwork.INFORMATION_USER_PROFILE);
		alias = new UserInfo(SocialNetwork.ANY, SocialNetwork.INFORMATION_USER_ALIAS);
		
		consumerKey = "";
		consumerKeySecret = "";
		listener = null;
		
		this.authenticated = false;
	}
	
	public static void setAppId(String name) {
		appId = name;
	}
	
	public static String getAppId() {
		return appId;
	}

	public String getTypeString() {
		return String.valueOf(this.getType());
	}

	public synchronized int getType() {
		return type;
	}

	public synchronized void setType(int type) {
		this.type = type;
	}
	
	public String getSocialNetworkName() {
		return SocialNetwork.SOCIAL_NETWORKS.get(getType());
	}

	public Secrets getSecretSafe() {
		return secrets;
	}

	public void setSecretSafe(Secrets secrets) {
		this.secrets = secrets;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public String getCachedId() {
		return secretOAuth.getId().getToken();
	}
	
	/**
	 * for Twitter, the alias is the name returned by twitter4j getName
	 */
	public String getCachedAlias() {
		return alias.getName();
	}
	
	/**
	 * for Twitter, the name will be the screen name which can be used for logging in with a password
	 */
	public String getCachedName() {
		return userInfo.getToken();
	}

	public String getCachedImage() {
		return userInfo.getBase64EncodedImage();
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void loadSecretsFromSafe() {
		if (secrets != null) {
			secrets.load(secretOAuth.getId());
			secrets.load(secretOAuth.getToken());
			secrets.load(userInfo);
		}
	}

	public void saveAuthSecrets() {
		secrets.save(secretOAuth.getToken());
		secrets.save(secretOAuth.getId());
	}
	
	public void saveUserInfo() {
		secrets.save(userInfo);
	}
	
	public void saveAlias() {
		secrets.save(alias);
	}
	
	public boolean hasCachedInfo() {
		return secretOAuth.getId().getToken().length() > 0 && userInfo.getToken().length() > 0;
	}
	
	public boolean hasConsumerKeyPair() {
		return consumerKey.length() > 0 && consumerKeySecret.length() > 0;
	}
	
	public abstract String getUserAlias();
	
	public abstract String getUserName();
	
	public abstract String getUserAvatarUrl();

	public SocialNetworkListener getListener() {
		return listener;
	}

	public void setListener(SocialNetworkListener listener) {
		this.listener = listener;
	}

	public abstract void retrieveAccessToken()
			throws Exception;

	public abstract boolean isAuthenticated();

	public abstract void authenticate() throws Exception;

	public abstract void postStatus(Message msg) throws Exception;

	public boolean hasSecret() {
		return false;
	}
	
	public abstract void addPeopleInNetwork() throws Exception;

	public abstract void createInstance();

	public void logout() {
		userInfo.setToken("");
		userInfo.setSecret("");
		alias.setToken("");
		alias.setSecret("");
		secretOAuth.getId().setToken("");
		secretOAuth.getId().setSecret("");
		secretOAuth.getToken().setToken("");
		secretOAuth.getToken().setSecret("");
		
		saveAuthSecrets();
		saveUserInfo();
		saveAlias();
		
		authenticated = false;
		
		if (listener != null)
			listener.onLogoutFinished(this.getType());
	}
}
