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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Callback {
	
	public static final String CALLBACK_DEFAULT_SCHEME = "callback";
	
	public static final String CALLBACK_DEFAULT_HOST = "sn4j";
	
	protected String scheme;
	
	protected String host;

	protected int port;
	
	protected String path; // to show what social network for
	
	private static Callback callback;

	private Map<String, String> parameters;
	
	static {
		callback = new Callback();
	}
	
	public Callback() {
		this(CALLBACK_DEFAULT_SCHEME, CALLBACK_DEFAULT_HOST, -1);
	}
	
	public Callback(String path) {
		this();
		this.path = path;
	}
	
	public Callback(String scheme, String host, int port) {
		this(scheme, host, port, "");
	}
	
	public Callback(String scheme, String host, int port, String path) {
		this.scheme = scheme;
		this.host = host;
		this.port = port;
		this.setPath(path);
	}

    public Callback(String scheme, String host, String path) {
        this(scheme, host, -1, path);
    }
	
	public static Callback getDefaultCallback() {
		return callback;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return getHomeUrl() + path;
	}
	
	@Override
	public String toString() {
		return getUrl();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLastPathSegment() {
		return getLastPathSegment(getUrl());
	}

	public String getLastPathSegment(String url) {
		return url.substring(url.lastIndexOf('/'));
	}

	public String getQueryParameter(String query) {
		if (parameters == null) {
			try {
				parameters = Http.toQueryParameters(new URL(getUrl()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (null != parameters)
			return parameters.get(query);

		return null;
	}

	public String getHomeUrl() {
		return scheme + "://" + host + (port > -1 ? (":" + String.valueOf(port)) : "") + "/";
	}
}
