/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs.alidns.transform.v20150109;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse;
import com.aliyuncs.transform.UnmarshallerContext;
import java.util.ArrayList;
import java.util.List;


public class DescribeDomainsResponseUnmarshaller {

	public static DescribeDomainsResponse unmarshall(DescribeDomainsResponse describeDomainsResponse, UnmarshallerContext context) {
		
		describeDomainsResponse.setRequestId(context.stringValue("DescribeDomainsResponse.RequestId"));
		describeDomainsResponse.setTotalCount(context.longValue("DescribeDomainsResponse.TotalCount"));
		describeDomainsResponse.setPageNumber(context.longValue("DescribeDomainsResponse.PageNumber"));
		describeDomainsResponse.setPageSize(context.longValue("DescribeDomainsResponse.PageSize"));

		List<DescribeDomainsResponse.Domain> domains = new ArrayList<DescribeDomainsResponse.Domain>();
		for (int i = 0; i < context.lengthValue("DescribeDomainsResponse.Domains.Length"); i++) {
			DescribeDomainsResponse.Domain domain = new DescribeDomainsResponse.Domain();
			domain.setDomainId(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].DomainId"));
			domain.setDomainName(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].DomainName"));
			domain.setPunyCode(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].PunyCode"));
			domain.setAliDomain(context.booleanValue("DescribeDomainsResponse.Domains["+ i +"].AliDomain"));
			domain.setRegistrantEmail(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].RegistrantEmail"));
			domain.setGroupId(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].GroupId"));
			domain.setGroupName(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].GroupName"));
			domain.setInstanceId(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].InstanceId"));
			domain.setVersionCode(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].VersionCode"));
			domain.setVersionName(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].VersionName"));

			List<String> dnsServers = new ArrayList<String>();
			for (int j = 0; j < context.lengthValue("DescribeDomainsResponse.Domains["+ i +"].DnsServers.Length"); j++) {
				dnsServers.add(context.stringValue("DescribeDomainsResponse.Domains["+ i +"].DnsServers["+ j +"]"));
			}
			domain.setDnsServers(dnsServers);

			domains.add(domain);
		}
		describeDomainsResponse.setDomains(domains);
	 
	 	return describeDomainsResponse;
	}
}