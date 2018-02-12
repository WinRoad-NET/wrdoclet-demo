package net.winroad.Models;

public enum LoginAuthType {

    /**
     * 不做登录验证
     */
    NO_AUTH(0),

    /**
     * 请求cookie登录验证
     */
    REQUEST_COOKIE_AUTH(1),

    /**
     * 请求body登录验证(body包含memberNo&mobile)
     */
    REQUEST_BODY_AUTH(2),

    /**
     * 选择性登录验证(可验可不验证)
     */
    OPTIONAL_AUTH(3);

    private Integer type;

    LoginAuthType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}