package com.yueyi.yuyinfanyi.bean;

public class CountryBean {
    private String name;
    private int national_flag_resource;
    private boolean isLock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNational_flag_resource() {
        return national_flag_resource;
    }

    public void setNational_flag_resource(int national_flag_resource) {
        this.national_flag_resource = national_flag_resource;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
