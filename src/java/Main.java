import com.HttpRequest;
import com.JedisUtils;
import com.ddns.DDNS;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;

public class Main {
    public static String INTRANET_IP = getIntranetIp(); // 内网IP
    public static String INTERNET_IP = getInternetIp(); // 外网IP

    public static void main(String[] args) throws IOException {
        DDNS ddns = new DDNS();
        JedisUtils.initJedis();
        String getDomainRecordsUrl = ddns.GetDomainRecords();
        String response = HttpRequest.get(getDomainRecordsUrl).body();

        String curIp = getMyIP();
        String redisIp = JedisUtils.get("curDomainIp_"+JedisUtils.PREFIX);

        //如果缓存中没有记录，则直接通过接口获取当前的绑定ip值，与当前ip对比
        if(redisIp == null || "".equals(redisIp)){
            String oldIp = getRecordValue(response);
            if(curIp != null && !curIp.equals("")&& !oldIp.equals(curIp)){
                updateIp(response,ddns,curIp,"");
                System.out.println("原IP为："+oldIp);
            }else{
                System.out.println("IP没有发生变化");
            }
            JedisUtils.set("curDomainIp_"+JedisUtils.PREFIX,curIp,0);

        }else{
            if(curIp != null && !curIp.equals("")&& !curIp.equals(redisIp)){
                //表示ip有变化，需要更新域名解析的id
                updateIp(response,ddns,redisIp,"");
                System.out.println("原IP为："+redisIp);
                JedisUtils.set("curDomainIp_"+JedisUtils.PREFIX,curIp,0);
            }else{
                System.out.println("IP没有发生变化");
            }

        }

    }

    public static void updateIps(String response,DDNS ddns,String curIp) throws IOException {
        Properties prop = new Properties();
        InputStream in = Main.class.getClassLoader().getResourceAsStream("json.properties");
        prop.load(in);
        String rrKeyWords = (String) prop.get("RR_KEYWORDS");
        if(rrKeyWords != null && !"".equals(rrKeyWords)){
            for(String rrKeyword : rrKeyWords.split(",")){
                if(rrKeyword != null && ",".equals(rrKeyword) && !"".equals(rrKeyword)){
                    updateIp(response,ddns,curIp,rrKeyword);
                }
            }
        }
    }

    public static void updateIp(String response,DDNS ddns,String curIp,String rrKeyWord) throws IOException {
        if(rrKeyWord == null || "".equals(rrKeyWord)){
            Properties prop = new Properties();
            InputStream in = Main.class.getClassLoader().getResourceAsStream("json.properties");
            prop.load(in);
            rrKeyWord = (String) prop.get("RR_KEYWORD");
        }


        System.out.println("IP发生变化");
        String recordId = getValue(getRecordString(response),"RecordId");
        System.out.println("RecordId为："+recordId);
        String updateDomainRecord = ddns.GetUpdateDomainRecord(recordId, rrKeyWord,"A",curIp);
        String response1 = HttpRequest.get(updateDomainRecord).body();
        if(response1.contains("Code")){
            String error = getValue(response1,"Code");
            String message = getValue(response1,"Message");
            System.out.println("更新失败,失败原因为：\n"+error+"\n"+message);
        }else{
            System.out.println("更新成功!");
        }
        System.out.println("当前IP为："+curIp);
    }


    /**
     * 转换返回结果字符串为json对象，并返回key的值
     * @param response
     * @return
     */
    public static String getRecordValue(String response){
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonObject1 = jsonObject.getJSONObject("DomainRecords");
        JSONArray records = jsonObject1.getJSONArray("Record");
        JSONObject jj = new JSONObject(records.get(0).toString());
        return jj.get("Value").toString();
    }

    /**
     * 转换返回结果字符串为json对象，并返回key的值
     * @param response
     * @return
     */
    public static String getRecordString(String response){
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonObject1 = jsonObject.getJSONObject("DomainRecords");
        JSONArray records = jsonObject1.getJSONArray("Record");
        return records.get(0).toString();
    }

    /**
     * 获取本地ip
     * @return
     * @throws SocketException
     */
    public static String  getLocalIp() throws SocketException {
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address)
                {
                    System.out.println("本机的IP = " + ip.getHostAddress());
                    return ip.getHostAddress();
                }
            }
        }
         return "";
    }

    /**
     * 获得内网IP
     * @return 内网IP
     */
    private static String getIntranetIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * 获得外网IP
     * @return 外网IP
     */
    private static String getInternetIp(){
        try{
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements())
            {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(INTRANET_IP))
                    {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return INTRANET_IP;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过访问站长工具获取当前主机外网ip（推荐，有效）
     * @return
     * @throws IOException
     */
    public static String getMyIP() throws IOException {
        String url="http://ip.chinaz.com/getip.aspx";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText =  sb.toString();
            return getValue(jsonText,"ip");
        } finally {
            is.close();
            // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
        }
    }

    public static String getValue(String objStr, String key){
        JSONObject jsonObject = new JSONObject(objStr);
        if(jsonObject != null){
            return jsonObject.get(key).toString();
        }
        return "";
    }
}
