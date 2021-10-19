package com.ums.itms.itas.webservice;
import com.colobu.fastjson.FastJsonProvider;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.*;

import com.ums.itms.itas.util.PropertUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RestClient {
    private static Logger log = LoggerFactory.getLogger(RestClient.class);

    private static String baseAddress = "http://198.17.1.52:18001/tpm/service/notice";
    static {
        Properties property = new Properties();
        InputStream in = PropertUtils.class.getResourceAsStream("/application.properties");
        try {
            property.load(in);
            baseAddress = property.getProperty("baseAddress").trim();
        } catch (Exception e) { }
        finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {}
            }
        }
    }

    public static Map sendAppInfoPost ( Map<String, Object> params ) {
        log.info("--------sendAppInfoPost begin--------"+params.toString());
        FastJsonProvider jsonProvider = new FastJsonProvider();
        Map map = ClientBuilder.newClient()
                .register(jsonProvider)
                .target(baseAddress)
                //.path("/sayHelloPost/")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(params,MediaType.APPLICATION_JSON), Map.class);
        log.info("--------sendAppInfoPost end--------" + map.toString());
        return map;
    }

    public static Map sendAppInfoGet ( String  params ) {
        //请求基本地址
        String baseAddress = "http://192.168.107.1:8080/itas/services/cxfRestServer/";
        FastJsonProvider jsonProvider = new FastJsonProvider();
        Map map = ClientBuilder.newClient()
                .register(jsonProvider)
                .target(baseAddress)
                .path("/sayHelloGet/")
                .queryParam("param",params)
                .request(MediaType.APPLICATION_JSON)
                .get( Map.class);
        System.out.println(map);
        return map;
    }

    public static Map sendAppInfo (Map map) {
        String url = "http://198.17.1.52:18001/tpm/service/notice";
       // String url = "http://127.0.0.1:18001/tpm/service/notice";
        String jsonString = JSON.toJSONString(map);
        if(log.isInfoEnabled()){
            log.info("--------jsonString--------"+jsonString);
        }
        String ret = doPost(url, jsonString)+"";
        return JSON.parseObject(ret, Map.class);
    }


    /**
     * httpclient的post请求方式
     */
    public static JSONObject  doPost(String url, String json) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            byte[] b = json.getBytes("ISO-8859-1");
            json = new String(b,"UTF-8");
            StringEntity s = new StringEntity(json);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            System.out.println("status="+res.getStatusLine().getStatusCode());
            System.out.println("SC_OK="+HttpStatus.SC_OK);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            System.out.println("qweytyfgh23");
            e.printStackTrace();
        }
        System.out.println(response);
        return response;

    }

    //JAX-RS 2.0 时代的客户端
    //在 JAX-RS 2.0 中提供了一个名为 javax.ws.rs.client.ClientBuilder 的工具类，
    //可用于创建客户端并调用 REST 服务，显然这种方式比前一种要先进，因为在代码中不再依赖 CXF API 了。
    public static void main(String[] args) {
        //请求基本地址t  http://144.131.254.198:18001/tpm/pmPortal/notice";"http://127.0.0.1:8080/itas/services/cxfRestServer";
        String baseAddress = "http://144.131.254.198:18001/tpm/service/notice";
        //设置JsonProvider
        //JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        FastJsonProvider jsonProvider = new FastJsonProvider();
        //构建测试数据
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId","111");
        params.put("requestId","111");

        params.put("appName","系i嘻嘻嘻11333311");
        params.put("appVersion","111");
        params.put("description","111");
        params.put("fileDir","111");
      //  Map m = sendAppInfo(params);
        //测试Post
        Map map = sendAppInfoPost(params);
       /* String resultData1 = ClientBuilder.newClient()
                .register(jsonProvider)
                .target(baseAddress)
               // .path("/notice")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(params,MediaType.APPLICATION_JSON), String.class);*/
       /* String a = "{\"appVersion\":{\"apvLogoPath\":\"bigPic.png|small.png\",\"apvFileName\":\"网易云音乐\",\"apvParaFile\":\"网易云音乐\",\"apvUpdateNote\":\"第一版\",\"apvDownTimes\":0,\"apvCompOsVersion\":\"V5.0\",\"apvSize\":\"10241024\",\"apvFilePath\":\"网易云音乐\",\"apvLogicVer\":\"v1.0\",\"apvCommitDate\":\"2016-08-01 20:16:55\",\"apvDate\":\"2016-08-01 20:16:55\",\"apvMark\":1,\"userId\":\"\",\"apvParameter\":[{\"name\":\"参数1\",\"note\":\"参数1\",\"key\":\"1\",\"value\":\"1\"},{\"name\":\"参数2\",\"note\":\"参数2\",\"key\":\"2\",\"value\":\"2\"},{\"name\":\"参数3\",\"note\":\"参数3\",\"key\":\"3\",\"value\":\"3\"}],\"apvParaModule\":\"\",\"apvPackVer\":\"\",\"apvPackName\":\"网易云音乐\"},\"appResources\":[{\"aprType\":1,\"aprPath\":\"1.png\",\"aprSize\":\"132\",\"aprName\":\"封面.png\"},{\"aprType\":2,\"aprPath\":\"2.png\",\"aprSize\":\"56\",\"aprName\":\"宣传1.png\"},{\"aprType\":2,\"aprPath\":\"3.png\",\"aprSize\":\"58\",\"aprName\":\"宣传2.png\"},{\"aprType\":3,\"aprPath\":\"4.png\",\"aprSize\":\"45\",\"aprName\":\"截图1.png\"},{\"aprType\":3,\"aprPath\":\"5.png\",\"aprSize\":\"55\",\"aprName\":\"截图2.png\"},{\"aprType\":3,\"aprPath\":\"6.png\",\"aprSize\":\"48\",\"aprName\":\"截图3.png\"},{\"aprType\":3,\"aprPath\":\"7.png\",\"aprSize\":\"65\",\"aprName\":\"截图4.png\"}],\"appCompModelMid\":[{\"modId\":\"2f2a724f49c74808bb8ab8eed74ac941\"},{\"modId\":\"8aa19ac4af414e39b9082ee787d8a890\"},{\"modId\":\"07739cdbbae048a0bfece46aa35afd45\"},{\"modId\":\"dcdb9e33adff40449e638a7ce47fbbd8\"}],\"apvId\":\"2dbbed2cc0304894b3cd1cc05079e22d\"}";
        JSONObject json = JSON.parseObject(a);
        JSONObject appVersion = json.getJSONObject("appVersion");
        String ss = appVersion.getString("apvLogoPath");
*/
        System.out.println(map.toString());
  //      System.out.println(m.toString());
        //测试Get

    }
}
