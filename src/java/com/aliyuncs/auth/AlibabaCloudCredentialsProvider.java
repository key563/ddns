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

package com.aliyuncs.auth;

/**
 * Created by haowei.yao on 2017/9/12.
 */

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;

/**
 * Interface for providing alibaba cloud credentials.
 * Implementations will provide credentials in a particular approach.
 */
public interface AlibabaCloudCredentialsProvider {

    /**
     * @return credentials for the client to authorize a request
     */
    public AlibabaCloudCredentials getCredentials() throws ClientException, ServerException;
}
