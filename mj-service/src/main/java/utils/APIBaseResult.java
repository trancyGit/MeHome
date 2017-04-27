package utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by pengf on 2015/11/26.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIBaseResult {
    private int errorCode;
    private String errorDescription;
    private String requestId;
    private Object result;
    private Long totalCount;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public APIBaseResult() {
        this.requestId = UUID.randomUUID().toString();
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
