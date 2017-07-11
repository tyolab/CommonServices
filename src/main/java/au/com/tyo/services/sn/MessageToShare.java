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

import java.io.File;

public class MessageToShare implements Message {
	
	private String title;
	
	private Status status;
	
	private String url;
	
	private String imageUrl;

	private File[] mediaFiles;
	
	private int snToShare; // social network to share to
	
	private int attempts; 
	
	public MessageToShare(String title, Status status) {
		this.title = title;
		this.status = status;
		this.url = null;
		this.imageUrl = null;
		snToShare = SocialNetwork.ANY;
		attempts = 0;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int getSocialNetworkToShare() {
		return snToShare;
	}

	public void setSocialNetworkToShare(int snToShare) {
		this.snToShare = snToShare;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Override
	public String getText() {
		return status.getText();
	}

	@Override
	public void removeImageUrl() {
		this.setImageUrl(null);
	}

	@Override
	public File[] getMediaFiles() {
		return mediaFiles;
	}

	public void setMediaFiles(File[] mediaFiles) {
		this.mediaFiles = mediaFiles;
	}
}
