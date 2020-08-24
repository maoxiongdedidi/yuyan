package com.yueyi.yuyinfanyi.bean;

public class WechatBean {

    /**
     * packageValue : Sign=WXPay
     * appid : wxf698c72de7cd8be7
     * sign : 2F4E50622E79C91232EF6C37B8E683FB
     * partnerid : AAA123456jkeijfdnedjssdjellppmcc
     * prepayid : wx26111227999314c63f134ced1564464200
     * noncestr : 1582686786018
     * timestamp : 1582686786
     */

    private String packageValue;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
