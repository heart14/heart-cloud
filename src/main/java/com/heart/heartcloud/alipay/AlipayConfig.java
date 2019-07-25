package com.heart.heartcloud.alipay;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: AlipayConfig
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/25 14:06
 */

public class AlipayConfig {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    public static String app_id = "2016091800539131";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMKeEM8/CvhELOY1GlHbOw1zCCNm2SCxY/nDlas8DavpqYjrTeuhStKn3cRYtmQUos90AbUsVDGTV/4MmOdR7fiY2uMhlaMSznb10gfzL5qEp23bi0nojplMsgCPxo3UV7UTNFqNyMnY5YZch7wKtm2W4BQLjWlwcGYsvvEvfqjiYw422s6falcdBAC3oU0Y8saE3KnmcbR45Hx7YgK+zA3XFyGCPGTLTb15gcCXPmW0CD/IyWbi/mQlyOgUt+t120yJWLr7th4GaKBTypOyQhosImSjE0BM7n4e9D/ci0c8ZeTELzRh2zRciaT6oOz+RAHjvu2iilFg55kIkv4xVlAgMBAAECggEAG86rMXCsDYZF4sBdkE6GOc1XeF3n+xNsBI6v6kjDOCKcdSvKwm1UcM5/TdYWQ+EsICOlekY/kIJN1OL52eNujpaxnx+dXAFpkTjM1welS1aR/2cZn+vb6ct1SRO/T+amRELl0yofJ16SwQLgdXzSBIOaiXEXuqsX37J2FyZCPSxPSdaZi91h+ddlfxiMxKM44k6rbshOszTvnvA9b3AunY4iYOCrZBn2TlquzH/dSS6r6AlCu/xbQkcY6XHHIU2v/7YiBgtRNecrivrguYaQQJZJNRKQznvvZe5ZzcELlbdgpilB5IVCnBLCkWhYQmXVs4fTFT8BMZviRPB+0cZF+QKBgQC9/j7JN+JvtdqJN3gG//LyAwVaqq1VbNc/Db6j7U4NmKKFnezuC6dI5BmLjgeA0Qb5l5og2Do+NaVufCusKovdVwSKfUnJPT0AXTKV5/aAB1JxhOY1Zdh0sN/I3rwHX8SY9mnFnVb7ZoJKpoAqm4A4NSfxgBlpvuZ2Ygk4B201ewKBgQC829rg+QwjNI4dhNB9La2i65IrcmfOPEmVhRf8+uC+ywu1JsOtqTbWt38ZcpZK9KVWB+MYRnh6SBhlz6cp/6/TvhVJT2zaDw0kNoqEA98MeQIxI+VEC1pZT9JjlnadPrtCZ1XUDTf2SapG7+73xtTPGivpilDw+gLw8/57NYM6nwKBgCVLwIV9e+zT9QdUpTIykj1hzG5E7OvFBufwyciiL91JZjX2j2kXTopnhIA5rE1kOwtJ8E6cQ6qsm0H6DBSq8XPMvi51c8OWSuBEa0a0F1aAmXWBUjOPP5OAmR10Y9LKwOwKCy9ouId3UwJWdvhmOCBQhvvIWrTAbbs1eXvPMWf7AoGBAIA98nUN6FYHm/EjfMdjuyKEQC9Mcnyyp2zmgZGBchdcnUiXWAyWeOx4kc/f5XpkgdjXZjfXsnumdRq9VM/KFcT8sEulqU6xJcVPW+OlFVLM4NYpf8cixmkbMMv7IfRI4rOGo9toVnXhM8UTq/uIgn1XUjY1fB0GR/UCWe2kOsYVAoGAFlXXPgLaPXs8SNd9OioEpnMVsj6O3Ck7WYn2ysSyzRguhhQ4lYz45hPxgagpXg8ipLfm3MGYNYzytGa3eQ3bim1cNrHATv8CnJ5GdGBBn3vp7XBdsxZPo2p/gQKYcu7SgWRXcLtXi6P5+PibaEvG7EaXKudvNdnrzINELB7dlIs=";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjCnhDPPwr4RCzmNRpR2zsNcwgjZtkgsWP5w5WrPA2r6amI603roUrSp93EWLZkFKLPdAG1LFQxk1f+DJjnUe34mNrjIZWjEs529dIH8y+ahKdt24tJ6I6ZTLIAj8aN1Fe1EzRajcjJ2OWGXIe8CrZtluAUC41pcHBmLL7xL36o4mMONtrOn2pXHQQAt6FNGPLGhNyp5nG0eOR8e2ICvswN1xchgjxky029eYHAlz5ltAg/yMlm4v5kJcjoFLfrddtMiVi6+7YeBmigU8qTskIaLCJkoxNATO5+HvQ/3ItHPGXkxC80Yds0XImk+qDs/kQB477toopRYOeZCJL+MVZQIDAQAB";

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String notify_url = "http://172.16.29.115:8081/cloudpay/notify";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String return_url = "http://172.16.29.115:8081/cloudpay/return";

    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";

    /**
     * 参数传输方式
     */
    public static String format = "json";

    /**
     * 日志保存路径
     */
    public static String log_path = " C:/Users/jayhe/Documents/AliPayLogs/";


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
