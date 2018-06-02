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

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.transform.UnmarshallerContext;
import java.util.ArrayList;
import java.util.List;


public class DescribeDomainRecordsResponseUnmarshaller {

	public static DescribeDomainRecordsResponse unmarshall(DescribeDomainRecordsResponse describeDomainRecordsResponse, UnmarshallerContext context) {
		
		describeDomainRecordsResponse.setRequestId(context.stringValue("DescribeDomainRecordsResponse.RequestId"));
		describeDomainRecordsResponse.setTotalCount(context.longValue("DescribeDomainRecordsResponse.TotalCount"));
		describeDomainRecordsResponse.setPageNumber(context.longValue("DescribeDomainRecordsResponse.PageNumber"));
		describeDomainRecordsResponse.setPageSize(context.longValue("DescribeDomainRecordsResponse.PageSize"));

		List<DescribeDomainRecordsResponse.Record> domainRecords = new ArrayList<DescribeDomainRecordsResponse.Record>();
		for (int i = 0; i < context.lengthValue("DescribeDomainRecordsResponse.DomainRecords.Length"); i++) {
			DescribeDomainRecordsResponse.Record record = new DescribeDomainRecordsResponse.Record();
			record.setDomainName(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].DomainName"));
			record.setRecordId(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].RecordId"));
			record.setRR(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].RR"));
			record.setType(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Type"));
			record.setValue(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Value"));
			record.setTTL(context.longValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].TTL"));
			record.setPriority(context.longValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Priority"));
			record.setLine(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Line"));
			record.setStatus(context.stringValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Status"));
			record.setLocked(context.booleanValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Locked"));
			record.setWeight(context.integerValue("DescribeDomainRecordsResponse.DomainRecords["+ i +"].Weight"));

			domainRecords.add(record);
		}
		describeDomainRecordsResponse.setDomainRecords(domainRecords);
	 
	 	return describeDomainRecordsResponse;
	}
}