package com.ddns;

import com.GetUTCTime;
import com.aliyuncs.auth.AcsURLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.util.Base64.Encoder;
import static java.util.Base64.getEncoder;

/**
 * Created by wj on 2018/3/14.
 */
public class DDNS {


    private static String SERVER_URL = "";
    private static String DOMAIN_NAME = "";
    private static String ACCESS_KEY = "";
    private static String SIG_METHOD = "";
    private static String SIG_VER = "";
    private static String ALIYUN_API_VER = "";
    private static String ACCESS_SECRT = "";
    private static String RR_KEYWORD = "";

    final  Encoder encoder = getEncoder();

    public DDNS() throws IOException {
        Properties prop = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("json.properties");
        prop.load(in);
        SERVER_URL = prop.getProperty("SERVER_URL");
        DOMAIN_NAME = prop.getProperty("DOMAIN_NAME");
        ACCESS_KEY = prop.getProperty("ACCESS_KEY");
        SIG_METHOD = prop.getProperty("SIG_METHOD");
        SIG_VER = prop.getProperty("SIG_VER");
        ALIYUN_API_VER = prop.getProperty("ALIYUN_API_VER");
        ACCESS_SECRT = prop.getProperty("ACCESS_SECRT");
        RR_KEYWORD = prop.getProperty("RR_KEYWORD");
    }

    /**
     *  通过查询API接口可以得到 Action
     * @return
     */
    public  String GetDomainRecords() {
        Map<String, String> parameters = GetPublicParams();
//        Map<String, String> parameters = new HashMap<>();
        parameters.put("Action", "DescribeDomainRecords");
        parameters.put("DomainName", DOMAIN_NAME);
        parameters.put("RRKeyWord",RR_KEYWORD );
        return GetRequestStr("GET", SERVER_URL, parameters);
    }

    /**
     * 这些是请求的固定参数
     * @return
     */
    private  Map<String, String> GetPublicParams() {
        Map<String, String> parameters = new HashMap<String, String>();
        // insert params
        parameters.put("Action", "DescribeDomainRecords");
        parameters.put("DomainName", DOMAIN_NAME);

        parameters.put("AccessKeyId", ACCESS_KEY);
        parameters.put("Format", "JSON");
        parameters.put("SignatureMethod", SIG_METHOD);
        parameters.put("SignatureVersion", SIG_VER);
        parameters.put("SignatureNonce", UUID.randomUUID().toString());
        parameters.put("Timestamp", GetUTCTime.getGMTTime());
        parameters.put("Version", ALIYUN_API_VER);
        return parameters;
    }

    /**
     *获取完整的 Http 请求 url
     * @param HttpMethod
     * @param host
     * @param params
     * @return
     */
    private  String GetRequestStr(String HttpMethod, String host,Map<String, String> params) {
        final String SEPARATOR = "&";

        // param sort, Upper and Lower not equal
        String[] sortedKeys = params.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);

        // create stringToSign str
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(HttpMethod).append(SEPARATOR);

        try {
            stringToSign.append(AcsURLEncoder.percentEncode("/")).append(SEPARATOR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder canonicalizedQueryString = new StringBuilder();
        for(String key : sortedKeys) {
            // encode key, value
            try {
                canonicalizedQueryString.append(SEPARATOR)
                        .append(AcsURLEncoder.percentEncode(key)).append("=")
                        .append(AcsURLEncoder.percentEncode(params.get(key)));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // encode canonicalizedQueryString
        try {
            stringToSign.append(AcsURLEncoder.percentEncode(canonicalizedQueryString.toString().
                    substring(1)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String http_request = new String();
        StringBuilder httpRequest = new StringBuilder();
        try {
            String signature = GetSignature(stringToSign.toString());
            httpRequest.append("http://");
            httpRequest.append(host);
            httpRequest.append("/?");
            httpRequest.append(canonicalizedQueryString.toString().substring(1));
            httpRequest.append("&Signature=");
            httpRequest.append(AcsURLEncoder.percentEncode(signature));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpRequest.toString();
    }

    /**
     * 通过API文档可以查询具体的签名机制
     * @param stringToSign
     * @return
     */
    private  String GetSignature(String stringToSign) {
        // signature test
        final String ALGORITHM = "HmacSHA1";
        final String ENCODING = "UTF-8";
        String key = ACCESS_SECRT + "&";
        Mac mac = null;
        byte[] signData = null;

        try {
            mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(
                    key.getBytes(ENCODING), ALGORITHM));
            signData = mac.doFinal(
                    stringToSign.getBytes(ENCODING));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(encoder.encode(signData));
    }

    /**
     * 更新某一条域名解析记录
     * @param RecordID
     * @param RR
     * @param Type
     * @param Value
     * @return
     */
    public String GetUpdateDomainRecord(String RecordID, String RR,String Type, String Value) {
        Map<String, String> parameters = GetPublicParams();
        // insert params
        parameters.put("Action", "UpdateDomainRecord");
        parameters.put("RecordId", RecordID);
        parameters.put("RR", RR);
        parameters.put("Type", Type);
        parameters.put("Value", Value);

        return GetRequestStr("GET", SERVER_URL, parameters);
    }
}
