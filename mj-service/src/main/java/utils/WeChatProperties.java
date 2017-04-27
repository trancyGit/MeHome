package utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016-03-18.
 */
@Component
@ConfigurationProperties(prefix = "send.weixin")
public class WeChatProperties {
    private String customsend;
    private String templatesend;
    private String sendurl;
    private String codeurl;
    private String accesstokenurl;
    private String appid;
    private String secret;
    private String grant_type;
    private String basetokenurl;
    private String granttype;
    private String ticketurl;


    public String getCustomsend() {
        return customsend;
    }

    public void setCustomsend(String customsend) {
        this.customsend = customsend;
    }


    public void setTemplatesend(String templatesend) {
        this.templatesend = templatesend;
    }

    public String getGranttype() {
        return granttype;
    }

    public void setGranttype(String granttype) {
        this.granttype = granttype;
    }

    public String getTicketurl() {
        return ticketurl;
    }

    public void setTicketurl(String ticketurl) {
        this.ticketurl = ticketurl;
    }

    public String getBasetokenurl() {
        return basetokenurl;
    }

    public void setBasetokenurl(String basetokenurl) {
        this.basetokenurl = basetokenurl;
    }


    public String getCodeurl() {
        return codeurl;
    }

    public void setCodeurl(String codeurl) {
        this.codeurl = codeurl;
    }

    public String getTemplatesend() {
        return templatesend;
    }

    public String getAccesstokenurl() {
        return accesstokenurl;
    }

    public void setAccesstokenurl(String accesstokenurl) {
        this.accesstokenurl = accesstokenurl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getSendurl() {
        return sendurl;
    }

    public void setSendurl(String sendurl) {
        this.sendurl = sendurl;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
