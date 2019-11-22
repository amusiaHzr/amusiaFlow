package com.amusia.flowlayoutexample;

public class DataBean {
    private int logo;
    private String name;

    DataBean(int l, String n) {
        logo = l;
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
