package com.heart.mavendl.mvndl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.heart.mavendl.mvndl.common.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.heart.mavendl.mvndl.common.Configs.*;

/**
 * About:
 * Other:
 * Created: wfli on 2021/11/12 11:13.
 * Editored:
 */
public class DlUtils {

    public static boolean dl(String url) {
        boolean bool = false;
        System.out.println();
        System.out.println("Http get url :" + url);
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        HttpRequest httpRequest = HttpRequest.get(url);
        HttpResponse response = httpRequest.execute();
//        System.out.println(response);
        String contentType = response.header("Content-Type");
        System.out.println("Http get response content-tpe :" + contentType);
        try {
            if (isDirectory(contentType)) {
                boolean mkDirs = mkDirs(url);
//                System.out.println(mkDirs ? "Mkdirs SUCCESS!" : "Mkdirs FAIL!");
                String textHtml = response.body();
                Document document = Jsoup.parse(textHtml);
                Elements elements = document.select("a[href]");
                List<String> hrefList = new ArrayList<>();
                for (Element element : elements) {
//                    System.out.println(element.attr("href"));
//                    System.out.println(element.text());
                    if (!"Parent Directory".equals(element.text())) {
                        hrefList.add(element.attr("href"));
                    }
                }
                for (String href : hrefList) {
                    dl(href);
                }
            } else {
                String filePath = getFilePath(url) + getFileName(url);
                if (!isLastUpdated(filePath)) {
                    inputStream = response.bodyStream();
                    byte[] bytes = new byte[1024];

                    File file = new File(filePath);
                    if (!file.exists()) {
                        fileOutputStream = new FileOutputStream(file);
                        int len;
                        while ((len = inputStream.read(bytes)) != -1) {
                            fileOutputStream.write(bytes, 0, len);
                        }
                        System.out.println(filePath + " download SUCCESS!");
                    } else {
                        System.out.println(filePath + " already existed! Do not download.");
                    }
                }
            }
            bool = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            bool = false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bool;
    }

    private static boolean isDirectory(String contentType) {
        if (Constants.CONTENT_TYPE.HTML.equals(contentType)) {
            System.out.println("This is dir");
            return true;
        } else {
            System.out.println("This is file, download it!");
            return false;
        }
    }

    private static boolean mkDirs(String url) {
        String dirName = getDirName(url);
        String dirPath = getDirPath(url);
        String targetFile = dirPath + dirName;
        File file = new File(targetFile);
        boolean mkdirs = false;
        String logInfo;
        if (!file.exists()) {
            mkdirs = file.mkdirs();
            logInfo = "Target dir[" + targetFile + "] create " + (mkdirs ? "SUCCESS" : "FAIL") + "!";
        } else {
            logInfo = "Target dir[" + targetFile + "] already existed! Do not create.";
        }
        System.out.println(logInfo);
        return mkdirs;

    }


    private static String getDirName(String url) {
        String sub = url.substring(0, url.lastIndexOf("/"));
        String name = sub.substring(sub.lastIndexOf("/") + 1);
        System.out.println("current path name :" + name);
        return name;
    }

    private static String getDirPath(String url) {
        //如果是根目录
        if (url.equals(ARCHIVES_ROOT_URL)) {
            System.out.println("parent path :" + ARCHIVES_ROOT_PATH);
            return ARCHIVES_ROOT_PATH;
        } else {
            String sub = url.substring(0, url.lastIndexOf("/"));
            String path = ARCHIVES_ROOT_PATH + sub.substring(0, sub.lastIndexOf("/")).replace(ARCHIVES_ROOT_URL, "") + "/";
            System.out.println("parent path :" + path);
            return path;
        }
    }

    private static String getFileName(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        System.out.println("current file name :" + name);
        return name;
    }

    private static String getFilePath(String url) {
        //如果是根目录
        if (url.equals(ARCHIVES_ROOT_URL)) {
            System.out.println("current file path :" + ARCHIVES_ROOT_PATH);
            return ARCHIVES_ROOT_PATH;
        } else {
            String path = ARCHIVES_ROOT_PATH + url.substring(0, url.lastIndexOf("/")).replace(ARCHIVES_ROOT_URL, "") + "/";
            System.out.println("current file path :" + path);
            return path;
        }
    }

    private static boolean isLastUpdated(String path) {
        String lastUpdated = "lastUpdated";
        String substring = path.substring(path.lastIndexOf(".") + 1);
        boolean equals = lastUpdated.equals(substring);
        System.out.println("File is .lastUpdated ? " + equals);
        if (equals) {
            System.out.println("Do not download.");
        }
        return equals;
    }

}
