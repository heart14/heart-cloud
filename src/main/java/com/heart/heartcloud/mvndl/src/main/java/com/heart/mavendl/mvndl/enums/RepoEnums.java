package com.heart.mavendl.mvndl.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * About:
 * Other:
 * Created: wfli on 2021/12/16 17:18.
 * Editored:
 */
public enum RepoEnums {

    REPO_COM_CHINAUMS(1, "chinaums.com", "http://172.30.252.132:8081/nexus/content/groups/security/"),
    REPO_COM_MVNREPOSITORY(2, "mvnrepository.com", "https://repo1.maven.org/maven2/");

    private int repoId;

    private String repoDesc;

    private String repoRoot;

    RepoEnums(int repoId, String repoDesc, String repoRoot) {
        this.repoId = repoId;
        this.repoDesc = repoDesc;
        this.repoRoot = repoRoot;
    }

    public int getRepoId() {
        return repoId;
    }

    public RepoEnums setRepoId(int repoId) {
        this.repoId = repoId;
        return this;
    }

    public String getRepoDesc() {
        return repoDesc;
    }

    public RepoEnums setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
        return this;
    }

    public String getRepoRoot() {
        return repoRoot;
    }

    public RepoEnums setRepoRoot(String repoRoot) {
        this.repoRoot = repoRoot;
        return this;
    }

    /**
     * repoId 与 RepoEnums映射Map
     */
    private static final Map<Integer, RepoEnums> REPO_TYPE_MAPPING = new HashMap<>();

    static {
        for (RepoEnums repoEnums : RepoEnums.values()) {
            REPO_TYPE_MAPPING.put(repoEnums.repoId, repoEnums);
        }
    }

    /**
     * 根据repoId获取RepoEnums对象
     *
     * @param repoId
     * @return
     */
    public static RepoEnums getRepoEnum(int repoId) throws Exception {
        RepoEnums repoEnums = REPO_TYPE_MAPPING.get(repoId);
        if (repoEnums != null) {
            return repoEnums;
        }
        throw new Exception("UNKNOWN REPO TYPE!");
    }

    @Override
    public String toString() {
        return "RepoEnums{" +
                "repoId=" + repoId +
                ", repoDesc='" + repoDesc + '\'' +
                ", repoRoot='" + repoRoot + '\'' +
                '}';
    }
}
