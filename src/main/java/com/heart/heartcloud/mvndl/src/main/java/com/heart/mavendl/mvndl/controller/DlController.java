package com.heart.mavendl.mvndl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/dl")
    public String mvnDl(String uri) {
        String url = "http://172.30.252.132:8081/nexus/content/groups/security/com/" + uri;

        String resp = dl(url) ? "DOWNLOAD SUCCESS!" : "DOWNLOAD FAIL!";

        System.out.println("\nExecute complete!");
        return resp;
    }

    @PostMapping("/download")
    public String mvnDownload(String url) {
//        String urlRoot = "http://172.30.252.132:8081/nexus/content";
//        String url = urlRoot + uri + "/";
        System.out.println(url);
        String resp = dl(url) ? "DOWNLOAD SUCCESS!" : "DOWNLOAD FAIL!";
        System.out.println("\nExecute complete!");
        return resp;
    }

}
