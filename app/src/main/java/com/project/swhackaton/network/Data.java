package com.project.swhackaton.network;

import com.project.swhackaton.model.ListModel;

import java.util.List;

public class Data {
    private String accessToken;
    private List<ListModel> list;//채널인포


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
}