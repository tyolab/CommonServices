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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Secrets {
	
	private Map<String, Secret> secrets;
	
	private static Secrets instance;

	public static void setInstance(Secrets ins) {
		instance = ins;
	}
	
	public static Secrets getInstance() {
		return instance;
	}

	public Secrets() {
		setSecrets(new HashMap<String, Secret>());
	}
	
//	public void add(int type, String token, String secretStr) {
//		add(new Secret(type, token, secretStr));
//	}
	
	public void add(Secret secret) {
		getSecrets().put(secret.buildKey(), secret);
	}
	
	public Secret get(int type, int authType) {
		return getSecrets().get(SecretBase.buildKey(type, authType));
	}
	
	public void addAll(ArrayList<Secret> list) {
		for (Secret secret : list)
			add(secret);
	}

	public Map<String, Secret> getSecrets() {
		return secrets;
	}

	public void setSecrets(Map<String, Secret> secrets) {
		this.secrets = secrets;
	}
	
	public abstract void load(Secret secret);
	
//	public abstract void save();
	
	public abstract void save(Secret secret);

}
