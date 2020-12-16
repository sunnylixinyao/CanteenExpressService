package util;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/16-14:31
 **/
public enum DataResultError {

    M000000("{\"RECODE\":\"000000\",\"MSG\":\"SUCCESS\"}","000000"),//请求成功
    M000001("{\"RECODE\":\"000001\",\"MSG\":\"No such Person\"}","000001"),//请求成功，但是没有该账号

    M999999("{\"RECODE\":\"999999\",\"MSG\":\"system error\"}","999999");//系统错误

    private String MSG;
    private String CODE;

    private DataResultError(String MSG, String CODE) {
        this.MSG = MSG;
        this.CODE = CODE;
    }

    public String getMSG() {
        return MSG;
    }
    public String getCODE() {
        return CODE;
    }
}
