package com.heart.mavendl.mvndl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.heart.mavendl.mvndl.enums.RepoEnums;
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

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public static boolean dl(String url) {
        boolean bool = false;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            System.out.println();
            System.out.println("Http get url :" + url);
            int repoType = getRepoType(url);
            HttpRequest httpRequest = HttpRequest.get(url);
            HttpResponse response = httpRequest.execute();
//        System.out.println(response);
            String contentType = response.header("Content-Type");
            System.out.println("Http get response content-type :" + contentType);
            if (isDirectory(contentType)) {
                boolean mkDirs = mkDirs(url, repoType);
//                System.out.println(mkDirs ? "Mkdirs SUCCESS!" : "Mkdirs FAIL!");
                String textHtml = response.body();
                Document document = Jsoup.parse(textHtml);
                Elements elements = document.select("a[href]");
                List<String> hrefList = new ArrayList<>();
                for (Element element : elements) {
//                    System.out.println(url + element.attr("href"));
//                    System.out.println(element.text());

                    switch (RepoEnums.getRepoEnum(repoType)) {
                        case REPO_COM_CHINAUMS:
                            if (!"Parent Directory".equals(element.text())) {
                                hrefList.add(element.attr("href"));
                            }
                            break;
                        case REPO_COM_MVNREPOSITORY:
                            hrefList.add(url + element.attr("href"));
                            break;
                        default:
                            throw new Exception("UNKNOWN REPO TYPE!");
                    }
                }
                for (String href : hrefList) {
                    dl(href);
                }
            } else {
                System.out.println("download file :" + url);
                String filePath = getFilePath(url, repoType) + getFileName(url);
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
            System.err.println("An error occured! " + e.getMessage());
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

    /**
     * 判断是否是文件夹
     *
     * @param contentType
     * @return
     */
    private static boolean isDirectory(String contentType) {
        if (Constants.CONTENT_TYPE.HTML.equals(contentType)) {
            System.out.println("This is dir");
            return true;
        } else {
            System.out.println("This is file, download it!");
            return false;
        }
    }

    /**
     * 创建文件下载目录
     *
     * @param url
     * @param repoType
     * @return
     * @throws Exception
     */
    private static boolean mkDirs(String url, int repoType) throws Exception {
        String dirName = getDirName(url);
        String dirPath = getDirPath(url, repoType);
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

    /**
     * 获取文件下载目录名
     *
     * @param url
     * @return
     */
    private static String getDirName(String url) {
        String sub = url.substring(0, url.lastIndexOf("/"));
        String name = sub.substring(sub.lastIndexOf("/") + 1);
        System.out.println("current path name :" + name);
        return name;
    }

    /**
     * 获取文件下载目录路径
     *
     * @param url
     * @param repoType
     * @return
     * @throws Exception
     */
    private static String getDirPath(String url, int repoType) throws Exception {
        //获取根目录
        String rootUrl = getRootUrl(repoType);
        //如果是根目录
        if (url.equals(rootUrl)) {
            System.out.println("parent path :" + ARCHIVES_ROOT_PATH);
            return ARCHIVES_ROOT_PATH;
        } else {
            String sub = url.substring(0, url.lastIndexOf("/"));
            String path = ARCHIVES_ROOT_PATH + sub.substring(0, sub.lastIndexOf("/")).replace(rootUrl, "") + "/";
            System.out.println("parent path :" + path);
            return path;
        }
    }

    /**
     * 获取文件名
     *
     * @param url
     * @return
     */
    private static String getFileName(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        System.out.println("current file name :" + name);
        return name;
    }

    /**
     * 获取文件下载路径
     *
     * @param url
     * @param repoType
     * @return
     * @throws Exception
     */
    private static String getFilePath(String url, int repoType) throws Exception {
        //获取根目录
        String rootUrl = getRootUrl(repoType);
        //如果是根目录
        if (url.equals(rootUrl)) {
            System.out.println("current file path :" + ARCHIVES_ROOT_PATH);
            return ARCHIVES_ROOT_PATH;
        } else {
            String path = ARCHIVES_ROOT_PATH + url.substring(0, url.lastIndexOf("/")).replace(rootUrl, "") + "/";
            System.out.println("current file path :" + path);
            return path;
        }
    }

    /**
     * 判断是否是.lastUpdate文件
     *
     * @param path
     * @return
     */
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

    /**
     * 根据url获取远程仓库类型
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static int getRepoType(String url) throws Exception {

        Integer repoType = null;
        String domainName = getDomainName(url);
        for (RepoEnums repoEnums : RepoEnums.values()) {
            if (repoEnums.getRepoRoot().contains(domainName)) {
                repoType = repoEnums.getRepoId();
            }
        }
        if (repoType == null) {
            throw new Exception("UNKNOWN REPO TYPE!");
        }
        return repoType;
    }

    /**
     * 根据url获取域名
     *
     * @param url
     * @return
     */
    private static String getDomainName(String url) {
        String[] split = url.split(DOMAIN_NAME_SEPERATOR);
        String protocol = split[0];
        String domain;
        if (split[1].contains(DOMAIN_URL_SEPERATOR)) {
            domain = split[1].substring(0, split[1].indexOf(DOMAIN_URL_SEPERATOR));
        } else {
            domain = split[1];
        }
//        System.out.println(protocol+"  --  "+domain);
        return protocol + DOMAIN_NAME_SEPERATOR + domain;
    }


    /**
     * 根据repoType获取repo根路径
     *
     * @param repoType
     * @return
     * @throws Exception
     */
    private static String getRootUrl(int repoType) throws Exception {
        //获取根路径
        String rootUrl;
        switch (RepoEnums.getRepoEnum(repoType)) {
            case REPO_COM_CHINAUMS:
                rootUrl = RepoEnums.REPO_COM_CHINAUMS.getRepoRoot();
                break;
            case REPO_COM_MVNREPOSITORY:
                rootUrl = RepoEnums.REPO_COM_MVNREPOSITORY.getRepoRoot();
                break;
            default:
                throw new Exception("UNKNOWN REPO TYPE!");
        }
        return rootUrl;
    }

}
