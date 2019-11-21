package com.project.swhackaton.network;

import com.project.swhackaton.model.ListModel;
import com.project.swhackaton.model.docs;
import com.project.swhackaton.model.user;

import java.util.List;

public class Data {
    private String accessToken;
    private List<ListModel> list;//채널인포
    private user user;
    private docs data;
    private String url;
    private String a_url;
    private String b_url;

    public String getA_url() {
        return a_url;
    }

    public void setA_url(String a_url) {
        this.a_url = a_url;
    }

    public String getB_url() {
        return b_url;
    }

    public void setB_url(String b_url) {
        this.b_url = b_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public docs getData() {
        return data;
    }

    public void setData(docs data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<ListModel> getList() {
        return list;
    }

    public void setList(List<ListModel> list) {
        this.list = list;
    }

    public com.project.swhackaton.model.user getUser() {
        return user;
    }

    public void setUser(com.project.swhackaton.model.user user) {
        this.user = user;
    }
}