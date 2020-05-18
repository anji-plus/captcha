package com.anji.captcha.model.vo;

import java.awt.*;

/**
 * Created by raodeming on 2020/5/16.
 */
public class PointVO extends Point {
    private String secretKey;

    public PointVO(String secretKey) {
        this.secretKey = secretKey;
    }

    public PointVO(Point p, String secretKey) {
        super(p);
        this.secretKey = secretKey;
    }

    public PointVO(int x, int y, String secretKey) {
        super(x, y);
        this.secretKey = secretKey;
    }

    public PointVO(int x, int y) {
        super(x, y);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
