package utils;

import java.security.SecureRandom;
import java.util.Random;


/**
 * Created by Administrator on 2016-03-23.
 */
public class StringUtils {
    public static boolean isNull(String param) {
        if (null == param) {
            return true;
        }
        if (null == param.trim() || "".equals(param.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(String param) {
        return !isNull(param);
    }
    
    
    private static Random random = new Random(new SecureRandom().nextLong());
    
    public static String getRandomCode(int length,char[] alphabet){
        StringBuilder sb = new StringBuilder(length);
        for(int i=0;i<length;i++){
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }
    /**
     * 判断字符串是否为空！
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return com.alibaba.druid.util.StringUtils.isEmpty(string);
    }

    /**
     * 多个等级只取第一个
     *
     * @param levels
     * @return
     */
    public static String singleLevel(String levels) {
        if (!com.alibaba.druid.util.StringUtils.isEmpty(levels)) {
            try {
                String[] args = levels.split(",");
                int level = Integer.valueOf(args[0]);
                return level + "";
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
