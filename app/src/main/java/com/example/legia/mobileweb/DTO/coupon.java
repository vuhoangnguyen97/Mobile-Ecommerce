package com.example.legia.mobileweb.DTO;

import java.io.Serializable;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class coupon implements Serializable {

    public String coupon;

    public coupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public coupon() {
        super();
    }
}
