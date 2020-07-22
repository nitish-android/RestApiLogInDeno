package com.tanzible.restapilogindeno;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Phone {

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @SerializedName("phone")
    @Expose
    private String phone;
}
