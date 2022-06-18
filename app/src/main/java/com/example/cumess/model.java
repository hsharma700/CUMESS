package com.example.cumess;

public class model
{
    String name,pri,purl;

    model()
    {

    }

    public model(String name, String pri, String purl) {
        this.name = name;
        this.pri = pri;
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
