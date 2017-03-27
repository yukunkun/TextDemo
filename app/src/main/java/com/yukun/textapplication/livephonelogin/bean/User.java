package com.yukun.textapplication.livephonelogin.bean;

/**
 * Created by haiyang-lu on 16-8-22.
 * 用户信息类
 */
public class User {
    private long id;
    private String resume;
    private String address;
    private int gender;
    private String signature;
    private String nickName;
    private String mobile;
    private int language;
    private String avatar;
    private int type;
    private String realName;
    private String domain;
    private String job;
    private int status;
    private int fansCount;
    private int relationCount;

    public User(long id, String resume, String address, int gender, String signature,
                String nickName, String mobile, int language, String avatar, int type,
                String realName, String domain, String job, int status, int fansCount,
                int relationCount) {
        this.id = id;
        this.resume = resume;
        this.address = address;
        this.gender = gender;
        this.signature = signature;
        this.nickName = nickName;
        this.mobile = mobile;
        this.language = language;
        this.avatar = avatar;
        this.type = type;
        this.realName = realName;
        this.domain = domain;
        this.job = job;
        this.status = status;
        this.fansCount = fansCount;
        this.relationCount = relationCount;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getRelationCount() {
        return relationCount;
    }

    public void setRelationCount(int relationCount) {
        this.relationCount = relationCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", resume='" + resume + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", signature='" + signature + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", language=" + language +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", realName='" + realName + '\'' +
                ", domain='" + domain + '\'' +
                ", job='" + job + '\'' +
                ", status=" + status +
                ", fansCount=" + fansCount +
                ", relationCount=" + relationCount +
                '}';
    }
}

