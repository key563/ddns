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
package com.aliyuncs.auth.sts;

import com.aliyuncs.transform.UnmarshallerContext;

/**
 * Created by zhangw on 2017/8/10.
 */
public class GetSessionAccessKeyResponseUnmarshaller {

    public static GetSessionAccessKeyResponse unmarshall(GetSessionAccessKeyResponse getSessionAccessKeyResponse,
                                                         UnmarshallerContext context) {

        getSessionAccessKeyResponse.setRequestId(context.stringValue("GetSessionAccessKeyResponse.RequestId"));

        GetSessionAccessKeyResponse.SessionAccesskey credentials = new GetSessionAccessKeyResponse.SessionAccesskey();
        credentials.setSessionAccessKeyId(context.stringValue("GetSessionAccessKeyResponse.SessionAccessKey.SessionAccessKeyId"));
        credentials.setSessionAccessKeySecert(context.stringValue("GetSessionAccessKeyResponse.SessionAccessKey.SessionAccessKeySecret"));
        credentials.setExpiration(context.stringValue("GetSessionAccessKeyResponse.SessionAccessKey.Expiration"));

        getSessionAccessKeyResponse.setSessionAccesskey(credentials);

        return getSessionAccessKeyResponse;
    }
}
