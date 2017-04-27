package utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/2.
 */
@Component
@ConfigurationProperties(prefix = "requestUrl")
public class PropertiesUtil {
    private String commonChangeBalance;
    private String userInfo;
    private String pushMessage;
    private String pushKey;
    private String pushUrl;
    private String cancelMessage;
    private String filterIp;
    

    public String getFilterIp() {
		return filterIp;
	}

	public void setFilterIp(String filterIp) {
		this.filterIp = filterIp;
	}

	public String getCancelMessage() {
		return cancelMessage;
	}

	public void setCancelMessage(String cancelMessage) {
		this.cancelMessage = cancelMessage;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getPushKey() {
		return pushKey;
	}

	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}

	public String getPushMessage() {
		return pushMessage;
	}

	public void setPushMessage(String pushMessage) {
		this.pushMessage = pushMessage;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getCommonChangeBalance() {
        return commonChangeBalance;
    }

    public void setCommonChangeBalance(String commonChangeBalance) {
        this.commonChangeBalance = commonChangeBalance;
    }
}
