package com.lhever.sc.devops.logviewer.dto;

import java.io.File;

/**
 * <p>类说明：</p>
 *
 * @author lihong10 2021/6/11 14:58
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2021/6/11 14:58
 * @modify by reason:{方法名}:{原因}
 */
public class FileInfo {
    private File file;
    private boolean isMatch;
    private long timestamp;
    private String namePrefix;

    public FileInfo(File file, boolean isMatch, long timestamp, String namePrefix) {
        this.file = file;
        this.isMatch = isMatch;
        this.timestamp = timestamp;
        this.namePrefix = namePrefix;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String nameSuffix) {
        this.namePrefix = nameSuffix;
    }

}
