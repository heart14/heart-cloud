package com.ums.itms.itas.httpwrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 因为前端传到后端，是以post流的形式传过来，拦截器从流中读取之后，request
 * 就会关闭，所有需要在过滤器里面转化http的request流，从流中读取模块参数添加到
 * request的请求路径后面
 * ***/
public class PostBodyReaderRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    public PostBodyReaderRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
