/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aliyuncs.alidns.model.v20150109;

import com.aliyuncs.RpcAcsRequest;

/**
 * @author auto create
 * @version 
 */
public class CheckDomainRecordRequest extends RpcAcsRequest<CheckDomainRecordResponse> {
	
	public CheckDomainRecordRequest() {
		super("Alidns", "2015-01-09", "CheckDomainRecord");
	}

	private String lang;

	private String userClientIp;

	private String domainName;

	private String rR;

	private String type;

	private String value;

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
		putQueryParameter("Lang", lang);
	}

	public String getUserClientIp() {
		return this.userClientIp;
	}

	public void setUserClientIp(String userClientIp) {
		this.userClientIp = userClientIp;
		putQueryParameter("UserClientIp", userClientIp);
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
		putQueryParameter("DomainName", domainName);
	}

	public String getRR() {
		return this.rR;
	}

	public void setRR(String rR) {
		this.rR = rR;
		putQueryParameter("RR", rR);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
		putQueryParameter("Type", type);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
		putQueryParameter("Value", value);
	}

	@Override
	public Class<CheckDomainRecordResponse> getResponseClass() {
		return CheckDomainRecordResponse.class;
	}

}
