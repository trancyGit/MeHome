package utils;

/**
 * Created by dell on 2015/11/30.
 */
public class SetResultUtil {

    //设置Object和返回错误结果
    public static void setObjectAndFail(APIBaseResult result) {
        SetAPIResultUtil.setFail(result);
    }

    //设置Object和返回正确结果
    public static void setObjectAndSuccess(Object obj, APIBaseResult result) {
        result.setResult(obj);
        SetAPIResultUtil.setSuccess(result);
    }

    //设置Object和返回正确结果
    public static void setObjectAndSuccessAndCount(Object obj, APIBaseResult result, int count) {
        result.setResult(obj);
        SetAPIResultUtil.setSuccess(result);
    }

    public static void setSuccess(APIBaseResult result) {
        SetAPIResultUtil.setSuccess(result);
    }
}
