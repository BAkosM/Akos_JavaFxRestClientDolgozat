package com.example.akos_javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doge {
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String wtdd;
    @Expose
    private Integer warcrimes;
    @Expose
    private Boolean gay;

    public Doge(Integer id, String name, String wtdd, Integer warcrimes, Boolean gay) {
        this.id = id;
        this.name = name;
        this.wtdd = wtdd;
        this.warcrimes = warcrimes;
        this.gay = gay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWtdd() {
        return wtdd;
    }

    public void setWtdd(String wtdd) {
        this.wtdd = wtdd;
    }

    public Integer getWarcrimes() {
        return warcrimes;
    }

    public void setWarcrimes(Integer warcrimes) {
        this.warcrimes = warcrimes;
    }

    public Boolean getGay() {
        return gay;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
    }

}
