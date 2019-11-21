package com.project.swhackaton.model;

public class docs {
    private String _id;
    private String title;
    private String a_id;
    private String b_id;
    private Boolean a_resolve;
    private Boolean b_resolve;
    private String file_name;
    private Boolean isResolve;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Boolean getA_resolve() {
        return a_resolve;
    }

    public void setA_resolve(Boolean a_resolve) {
        this.a_resolve = a_resolve;
    }

    public Boolean getB_resolve() {
        return b_resolve;
    }

    public void setB_resolve(Boolean b_resolve) {
        this.b_resolve = b_resolve;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Boolean getResolve() {
        return isResolve;
    }

    public void setResolve(Boolean resolve) {
        isResolve = resolve;
    }
}
