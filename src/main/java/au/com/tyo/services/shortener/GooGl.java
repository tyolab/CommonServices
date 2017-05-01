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

package au.com.tyo.services.shortener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class GooGl {
	
	public static final String GOO_GL_REQUEST_URL_TEMPLATE = "";
	
	private static final String API_KEY = "AIzaSyALjSgLRg2kgMXVT2Q_RE_i2ZLt1OB6xdI";
	
	private static final String GOO_GL_REQUEST_URL = "https://www.googleapis.com/urlshortener/v1/url?key=" + API_KEY;

//	public static String shorten(String url) {
//		String jsonResult = HttpPool.getInstance().getConnection().get(url);
//		String shortenedUrl = parse(jsonResult);
//		return shortenedUrl;
//	}
	
	private static String post(String url) {
		HttpURLConnection httpcon = null;
		
		try {
			httpcon = (HttpURLConnection) ((new URL(GOO_GL_REQUEST_URL).openConnection()));
			
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] outputBytes = {0};
		OutputStream os = null;
		InputStream is = null;
		BufferedReader in = null;
        StringBuilder text = new StringBuilder();
        
		try {
			outputBytes = ("{'longUrl': \"" + url + "\"}").getBytes("UTF-8");
			httpcon.setRequestProperty("Content-Length", Integer.toString(outputBytes.length));
			httpcon.connect();
			os = httpcon.getOutputStream();
			os.write(outputBytes);
			is = httpcon.getInputStream();
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				
			if (in != null) {
		        // get page text
		        String line;

				while ((line = in.readLine()) != null)
				{
				    text.append(line);
				    text.append("\n");
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
		
		return text.toString();
	}
	
	public static String getShortenedUrl(String url) {
		String jsonText = post(url);
		String shortUrl = parse(jsonText);
		return shortUrl;
	}
	
	private static String parse(String jsonText) {
		String url = null;
		if (jsonText != null || jsonText.length() > 0) {
			JSONObject array = null;
			
			try {
				array = new JSONObject(jsonText);
				
				if (array.has("id"))
					url = array.getString("id");
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return url;
	}
	
	/*
	 * Look up a short URL's analytics

		To look up a short URL's analytics, issue an expand request, adding a parameter to ask for additional details. Add &projection=FULL to the API URL, like this:
		
		curl 'https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&projection=FULL'
		
		If successful, the response will look like:
		
		{
		 "kind": "urlshortener#url",
		 "id": "http://goo.gl/fbsS",
		 "longUrl": "http://www.google.com/",
		 "status": "OK",
		 "created": "2009-12-13T07:22:55.000+00:00",
		 "analytics": {
		  "allTime": {
		   "shortUrlClicks": "3227",
		   "longUrlClicks": "9358",
		   "referrers": [ { "count": "2160", "id": "Unknown/empty" }],
		   "countries": [ { "count": "1022", "id": "US" }],
		   "browsers": [ { "count": "1025", "id": "Firefox" }],
		   "platforms": [ { "count": "2278", "id": "Windows" }]
		  },
		  "month": {  },
		  "week": {  },
		  "day": {  },
		  "twoHours": {  }
		 }
		}
	 */
}
