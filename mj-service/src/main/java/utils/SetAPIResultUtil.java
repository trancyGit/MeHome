package utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by pengf on 2014/9/26.
 */
public class SetAPIResultUtil {
    public static void setFail(APIBaseResult result) {
        result.setErrorCode(ReturnCode.FAIL);
        result.setErrorDescription(result.getErrorDescription());
    }

    public static void setSuccess(APIBaseResult result) {
        result.setErrorCode(ReturnCode.SUCCESS);
        result.setErrorDescription(ReturnCode.MSG_SUCCESS);
    }
}
