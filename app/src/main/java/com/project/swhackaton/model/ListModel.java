package com.project.swhackaton.model;

public class ListModel {
    private String title;
    private String _id;
    private String a_id;
    private String b_id;
    private boolean isResolve;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public boolean isResolve() {
        return isResolve;
    }

    public void setResolve(boolean resolve) {
        isResolve = resolve;
    }
}
