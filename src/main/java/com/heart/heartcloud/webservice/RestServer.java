package com.ums.itms.itas.webservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
//rest的webservice接口
//与 SOAP 不同，REST 并没有 WSDL 的概念，也没有叫做“信封”的东西
//传递的数据格式可以是 JSON 格式，也可以是 XML 格式，这完全由您来决定。
//REST 全称是 Representational State Transfer（表述性状态转移）
//REST 本质上是使用 URL 来访问资源的一种方式。

//使用了 JAX-RS 提供的注解，主要包括以下三类：
//请求方式注解，包括：@GET、@POST、@PUT、@DELETE
//请求路径注解，包括：@Path，其中包括一个路径参数
//数据格式注解，包括：@Consumes（输入）、@Produces（输出），可使用 MediaType 常量
//相关参数注解，包括：@PathParam（路径参数）、@FormParam（表单参数），此外还有 @QueryParam（请求参数）
public interface RestServer {
    //post请求方式
    //请求路径为http://localhost:8989/cxf/services/cxfRestServer/sayHelloPost
    //以上cxf为工程名，services为web.xml中cxf的servlet的url-pattern
    //cxfRestServer为spring配置rest方式的webservice的address
    //传入json格式
    //输出json或xml格式的数据
    @POST
    @Path("/signaturePost")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map signaturePost(Map<String, Object> params);
    //get请求方式
    //请求路径为http://localhost:8989/cxf/services/cxfRestServer/sayHelloGet
    //以上cxf为工程名，services为web.xml中cxf的servlet的url-pattern
    //cxfRestServer为spring配置rest方式的webservice的address
    //传入json格式
    //输出json或xml格式的数据
    @GET
    @Path("/signatureGet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData signatureGet(@QueryParam("param") String param);


    @POST
    @Path("/signaturePostTest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map signaturePostTest(Map<String, Object> params);

}