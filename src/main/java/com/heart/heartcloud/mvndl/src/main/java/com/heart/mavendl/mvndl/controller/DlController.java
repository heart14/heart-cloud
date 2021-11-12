package com.heart.mavendl.mvndl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.heart.mavendl.mvndl.utils.DlUtils.dl;

/**
 * About:
 * Other:
 * Created: wfli on 2021/11/11 14:15.
 * Editored:
 */
@RestController
@RequestMapping("/mvn")
public class DlController {

    @GetMapping("/download")
    public String mvnDownload(String uri) {
        String urlRoot = "http://localhost:80/";
        String url = urlRoot + uri + "/";
        return dl(url) ? "DOWNLOAD SUCCESS!" : "DOWNLOAD FAIL!";
    }

}
