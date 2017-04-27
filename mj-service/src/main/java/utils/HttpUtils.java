package utils;

import com.alibaba.fastjson.JSONObject;

import exceptions.InfoException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * Created by Administrator on 2017/3/2.
 */
public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static String keyStr = "key=lionshare@po$jk@55293";
	private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
	static {
		httpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(8);
		httpConnectionManager.getParams().setMaxTotalConnections(48);
		httpConnectionManager.getParams().setConnectionTimeout(8000);
		httpConnectionManager.getParams().setSoTimeout(1500);
		httpConnectionManager.getParams().setTcpNoDelay(true);
		httpConnectionManager.getParams().setLinger(1000);
		httpConnectionManager.getParams().setSendBufferSize(1024 * 64);
		httpConnectionManager.getParams().setReceiveBufferSize(1024 * 64);
		httpConnectionManager.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}

	
	public static String getKeyStr() {
		return keyStr;
	}

	public static void setKeyStr(String keyStr) {
		HttpUtils.keyStr = keyStr;
	}

	public static String buildNewUrl(String urlParam) {
		int index = urlParam.indexOf("?");
		String url = urlParam.substring(0, index + 1);
		String addVersionStr = urlParam.substring(index + 1, urlParam.length());
		String[] strArr = addVersionStr.split("&");
		Arrays.sort(strArr);
		StringBuffer strBuffer = new StringBuffer();
		for (String str : strArr) {
			strBuffer.append(str + "&");
		}
		String result = strBuffer.substring(0, strBuffer.length() - 1);
		return url + result + "&token=" + md5(result + "&" + keyStr);
	}

	/**
	 * MD5加密
	 * 
	 * @param string
	 */
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static String post(String url,String json,Map<String,String> headers,boolean ssl) throws Exception{
    	return post(url,json,null,headers,ssl,null);
    }
	public static JSONObject sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		JSONObject result = new JSONObject();
		HttpURLConnection conn = null;
		Exception ee = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Close");
//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			if (StringUtils.isNotNull(param)) {
				out.print(param);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer("");
			String lines;
			while ((lines = in.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			result = JSONObject.parseObject(sb.toString());
			in.close();
		} catch (Exception e) {
			ee = e;
			logger.info("网络请求异常," + e.getMessage() + e.getCause());
			//throw new InfoException("\n网络异常：[" + e.getMessage() + "]\n");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (null != conn) {
					conn.disconnect();
				}
			} catch (IOException ex) {
				logger.error("网络关闭异常", ex);
			} finally {
				printLog(url, param, result.toJSONString(), ee);
			}
		}
		return result;
	}

	private static void printLog(String requestUrl, String param, String returnStr, Exception e) {
		if (null != e) {
			logger.info("\n\t网络请求：" + requestUrl + "\n\tparam :" + param + "\n\treturn  :" + returnStr);
		} else {
			logger.error("\n\t网络请求：" + requestUrl + "\n\tparam :" + param + "\n\treturn  :" + returnStr, e);
		}
	}

	public static String getForHeader(String url, String token) {
		String result = "";
		org.apache.http.client.HttpClient httpClient=null;
		try {
			// 根据地址获取请求
			HttpGet request = new HttpGet(url);
			request.addHeader("X-LION-TOKEN", token);
			// 获取当前客户端对象
			httpClient = new DefaultHttpClient();
			// 通过请求对象获取响应对象
			HttpResponse response = httpClient.execute(request);
			// 判断网络连接状态码是否正常(0--200都数正常)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}

		return result;
	}

	/**
	 * 徐亦迅提供的新的流的解析方式
	 *
	 * @param url
	 * @return
	 */
	public static String sendGet(String url) {
		String result = "";
		InputStream is = null;
		URLConnection connection = null;
		Exception ee = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = realUrl.openConnection();
			// 设置通用的请求属性
			// 建立实际的连接
			connection.connect();
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(1000);
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			is = connection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int available = 0;
			byte[] buffer = new byte[4096];
			while ((available = is.read(buffer)) != -1) {
				baos.write(buffer, 0, available);
			}
			result = new String(baos.toByteArray(), "UTF-8");
			return result;
		} catch (Exception e) {
			logger.error("网络请求异常", e);
			throw new InfoException(e.getMessage());
		} finally {



			try {
				if (is != null)
					is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				printLog(url, "", result, ee);
			}
		}
	}
	public static String post(String url, String json, TreeMap<String, String> params, Map<String, String> headers, boolean ssl, Integer timeOut) throws Exception {
		if (ssl)
			trustAllHttpsCertificates();

		if (timeOut == null) {
			timeOut = 30000;
		}

		String paramStr = prepareParamInOrder(params);
		if (paramStr == null || paramStr.trim().length() < 1) {
		} else {
			url += "?" + paramStr;
		}
		URL u = new URL(url);

		InputStream inS = null;
		OutputStream onS = null;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(timeOut);
			connection.setReadTimeout(timeOut);
			// connection.setRequestProperty("Content-Type",
			// "application/json");
			connection.setDoOutput(true);
			if (headers != null) {
				for (String headerKey : headers.keySet())
					connection.setRequestProperty(headerKey, headers.get(headerKey));
			}
			onS = connection.getOutputStream();
			if (json != null)
				onS.write(json.getBytes("UTF-8"));
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inS = connection.getInputStream();
				int reading;
				byte[] resp = new byte[0];
				byte[] buffer = new byte[1024];
				do {
					reading = inS.read(buffer, 0, buffer.length);
					if (reading > 0) {
						byte[] content = new byte[resp.length + reading];
						System.arraycopy(resp, 0, content, 0, resp.length);
						System.arraycopy(buffer, 0, content, resp.length, reading);
						resp = content;
					}
				} while (reading >= 0);
				String ret = new String(resp, "UTF-8");
				return ret;
			} else {
				throw new Exception(String.valueOf(connection.getResponseCode()) + " : " + url);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (inS != null)
				inS.close();
			if (onS != null)
				onS.close();
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static boolean sslInit = false;

	private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
		// Create a trust manager that does not validate certificate chains:
		if (!sslInit) {
			TrustManager[] trustAllCerts = new TrustManager[1];

			TrustManager tm = new miTM();
			trustAllCerts[0] = tm;
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, null);

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}
	}

	private static class miTM implements TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}
	}

	private static String prepareParamInOrder(TreeMap<String, String> paramMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (paramMap == null || paramMap.isEmpty()) {
			return "";
		} else {
			for (String key : paramMap.keySet()) {
				String value = (String) paramMap.get(key);
				value = URLEncoder.encode((String) value, "UTF-8");
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				} else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}

	public static String get(String url,Map<String,Object> params,boolean ssl,Integer timeout) throws Exception{
        String paramStr = prepareParam(params);
        return _get(url,paramStr,ssl,timeout);
    }
	
	public static String get(String url,Map<String,Object> params) throws Exception{
    	String paramStr = prepareParam(params);
        return _get(url,paramStr,false,null);
    }
    
	private static String prepareParam(Map<String,Object> paramMap) throws Exception{
        StringBuffer sb = new StringBuffer();
        if(paramMap==null||paramMap.isEmpty()){
            return "" ;
        }else{
            for(String key: paramMap.keySet()){
                Object value = paramMap.get(key);
                if(value == null)
                	continue;
                if(value instanceof String){
                	value = URLEncoder.encode((String)value,"UTF-8");
                }
                if(sb.length()<1){
                    sb.append(key).append("=").append(value);
                }else{
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }
	
    private static String _get(String url,String paramStr,boolean ssl,Integer timeout) throws Exception{
    	if(ssl)
    		trustAllHttpsCertificates();
    	
    	if(timeout==null){
    		timeout = 30000;
    	}
        
        if(paramStr == null || paramStr.trim().length()<1){
        }else{
            url +="?"+paramStr;
        }
        
        URL u = new URL(url);
        InputStream inS = null;
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)u.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                inS = connection.getInputStream();
                int reading;
                byte[] resp = new byte[0];
                byte[] buffer = new byte[1024];
                do{
                    reading = inS.read(buffer, 0, buffer.length);
                    if (reading>0) {
                        byte[] content = new byte[resp.length+reading];
                        System.arraycopy(resp, 0, content, 0, resp.length);
                        System.arraycopy(buffer, 0, content, resp.length, reading);
                        resp = content;
                    }
                }while(reading>=0);
                String ret =  new String(resp,"UTF-8");
                return ret;
            }
            else{
                throw new Exception(String.valueOf(connection.getResponseCode())+" : "+url);
            }
        }catch(Exception e){
            throw e;
        }finally{
            if(inS!=null)
                inS.close();
            if(connection!=null){
            	connection.disconnect();
            }
        }
    }
    
    public static void main(String[] args) {
//        System.out.println(getForHeader("http://test.api.shihou.tv/api/user/info", "IW2jcp7PQrrB2ECk0IPI5Qdrw5NJHiz1Q6yBbnqW6U7GvsGt9oy49W"));
       String from_user_id = "";
        String json = getForHeader("http://test.api.shihou.tv/api/user/info", "IW2jcp7PQrrB2ECk0IPI5Qdrw5NJHiz1Q6yBbnqW6U7GvsGt9oy49W");
        JSONObject jsonObject = JSONObject.parseObject(json);
        String  error = jsonObject.getString("error");
        if(error.equals("0")){
            JSONObject jsonData = JSONObject.parseObject(jsonObject.getString("data"));
            JSONObject jsonUser = JSONObject.parseObject(jsonData.getString("user"));
            from_user_id = jsonUser.getString("id");
        }else{
            from_user_id = "0";
        }
        System.out.println(from_user_id);
    }
}
