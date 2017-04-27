package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */
public class MD5Encrypt {
	private static String md5Key="key=youxiduo$jk@55293";
    private static MD5Encrypt instance;
    public String md5(byte[] b) {
        try {
            byte[] a = hashData("MD5", b);
            return ByteArrayToHexString(a);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    /**
     * 哈希函数
     *
     * @param algorithm
     *            String MD5/SHA
     * @param b
     *            byte[]
     * @return byte[]
     */
    public byte[] hashData(String algorithm, byte[] b)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(b);
        byte[] digest = md.digest();
        return digest;
    }
    /**
     * 将byte数组转换成16进制AscII字符串
     *
     * @param d
     *            byte[]
     * @return String
     */
    public String ByteArrayToHexString(byte[] d) {
        if (d == null)
            return "";
        if (d.length == 0)
            return "";

        int len = d.length * 2;
        byte[] strData = new byte[len];
        for (int i = 0; i < strData.length; i++)
            strData[i] = (byte) '0';
        byte[] data = new byte[d.length + 1];
        data[0] = (byte) 0x00;
        System.arraycopy(d, 0, data, 1, d.length);
        BigInteger bi = new BigInteger(data);
        byte[] src = bi.toString(16).getBytes();
        int offset = strData.length - src.length;
        len = src.length;
        System.arraycopy(src, 0, strData, offset, len);
        return new String(strData);
    }
    public static MD5Encrypt getInstance(){
        if (instance == null) {
            synchronized (MD5Encrypt.class) {
                if (instance == null) {
                    instance = new MD5Encrypt();
                }
            }
        }
        return instance;
    }

    public static Boolean validate(String guessId,String optionId,Integer amount,String uid,String hashValue){
        StringBuffer buff=new StringBuffer("guessId="+guessId+"&optionId="+optionId+"&amount="+amount+"&uid="+uid+"keyNum");
        System.out.println(buff.toString());
        String md5Value=MD5Encrypt.getInstance().md5(buff.toString().getBytes()).toUpperCase();
        if(hashValue.equals(md5Value)){
            return Boolean.TRUE;
        }
        else
            return Boolean.FALSE;
    }
    
    
    public static Boolean validate(Map<String,String> paramMap,String hashValue){
    	if(paramMap==null||(paramMap!=null&&paramMap.isEmpty())||StringUtils.isEmpty(hashValue)){
    		return Boolean.FALSE;
    	}
    	StringBuffer buff=new StringBuffer("");
    	for (String key : paramMap.keySet()) {  
    		buff.append(key+"="+paramMap.get(key)+"&");
    	}
    	buff.append("key="+md5Key);
        String originalBuff=buff.toString();
        String md5Value=MD5Encrypt.getInstance().md5(originalBuff.toString().getBytes()).toUpperCase();
        if(hashValue.equals(md5Value)){
            return Boolean.TRUE;
        }
        else
            return Boolean.FALSE;
    }

}
