package com.heart.heartcloud.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/fileupload")
public class FileUploadServlet extends HttpServlet {

    /**
     * 听说用servlet进行图片上传可以保留图片EXIF信息
     * 用SpringMVC+MultipartFile接收图片上传的话会丢失
     * 待测试
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
