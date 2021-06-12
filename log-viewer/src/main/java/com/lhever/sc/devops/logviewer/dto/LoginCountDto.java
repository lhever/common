package com.lhever.sc.devops.logviewer.dto;

public class LoginCountDto {
    private int count;
    private long createTime;

    public LoginCountDto(int count, long createTime) {
        this.count = count;
        this.createTime = createTime;
    }
    public LoginCountDto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
