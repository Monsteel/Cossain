package com.project.swhackaton.network;

import com.project.swhackaton.model.ListModel;
import com.project.swhackaton.model.user;

import java.util.List;

public class Data {
    private String accessToken;
    private List<ListModel> list;//채널인포
    private user user;


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