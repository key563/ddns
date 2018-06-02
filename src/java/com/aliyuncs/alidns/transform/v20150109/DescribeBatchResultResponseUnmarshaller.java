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
package com.aliyuncs.alidns.transform.v20150109;

import java.util.ArrayList;
import java.util.List;

import com.aliyuncs.alidns.model.v20150109.DescribeBatchResultResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeBatchResultResponse.FailResult;
import com.aliyuncs.transform.UnmarshallerContext;


public class DescribeBatchResultResponseUnmarshaller {

	public static DescribeBatchResultResponse unmarshall(DescribeBatchResultResponse describeBatchResultResponse, UnmarshallerContext context) {
		
		describeBatchResultResponse.setRequestId(context.stringValue("DescribeBatchResultResponse.RequestId"));
		describeBatchResultResponse.setTraceId(context.stringValue("DescribeBatchResultResponse.TraceId"));
		describeBatchResultResponse.setStatus(context.longValue("DescribeBatchResultResponse.Status"));
		describeBatchResultResponse.setBatchCount(context.longValue("DescribeBatchResultResponse.BatchCount"));
		describeBatchResultResponse.setSuccessNumber(context.longValue("DescribeBatchResultResponse.SuccessNumber"));

		List<FailResult> failResults = new ArrayList<FailResult>();
		for (int i = 0; i < context.lengthValue("DescribeBatchResultResponse.FailResults.Length"); i++) {
			FailResult failResult = new FailResult();
			failResult.setBatchIndex(context.stringValue("DescribeBatchResultResponse.FailResults["+ i +"].BatchIndex"));
			failResult.setErrorCode(context.stringValue("DescribeBatchResultResponse.FailResults["+ i +"].ErrorCode"));

			failResults.add(failResult);
		}
		describeBatchResultResponse.setFailResults(failResults);
	 
	 	return describeBatchResultResponse;
	}
}