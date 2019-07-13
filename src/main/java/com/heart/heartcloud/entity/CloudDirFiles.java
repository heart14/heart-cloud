package com.heart.heartcloud.entity;

import com.heart.heartcloud.domain.CloudDir;
import com.heart.heartcloud.domain.CloudFile;

import java.util.List;

public class CloudDirFiles {

    private List<CloudDir> cloudDirs;

    private List<CloudFile> cloudFiles;

    public CloudDirFiles() {
    }

    public CloudDirFiles(List<CloudDir> cloudDirs, List<CloudFile> cloudFiles) {
        this.cloudDirs = cloudDirs;
        this.cloudFiles = cloudFiles;
    }

    public List<CloudDir> getCloudDirs() {
        return cloudDirs;
    }

    public void setCloudDirs(List<CloudDir> cloudDirs) {
        this.cloudDirs = cloudDirs;
    }

    public List<CloudFile> getCloudFiles() {
        return cloudFiles;
    }

    public void setCloudFiles(List<CloudFile> cloudFiles) {
        this.cloudFiles = cloudFiles;
    }

    @Override
    public String toString() {
        return "CloudDirFiles{" +
                "cloudDirs=" + cloudDirs +
                ", cloudFiles=" + cloudFiles +
                '}';
    }
}
