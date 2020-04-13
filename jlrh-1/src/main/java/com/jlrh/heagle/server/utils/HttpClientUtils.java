package com.jlrh.heagle.server.utils;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/** 
 * Http���󹤾��� 
 * @author zzw 
 * @since 2014-8-24 13:30:56 
 * @version v1.0.1 
 */ 
public class HttpClientUtils {
	
	   public static String doGet(String url, Map<String, String> param) {  
		   
	        // ����Httpclient����  
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	  
	        String resultString = "";  
	        CloseableHttpResponse response = null;  
	        try {  
	            // ����uri  
	            URIBuilder builder = new URIBuilder(url);  
	            if (param != null) {  
	                for (String key : param.keySet()) {  
	                    builder.addParameter(key, param.get(key));  
	                }  
	            }  
	            URI uri = builder.build();  
	  
	            // ����http GET����  
	            HttpGet httpGet = new HttpGet(uri);  
	  
	            // ִ������  
	            response = httpclient.execute(httpGet);  
	            // �жϷ���״̬�Ƿ�Ϊ200  
	            if (response.getStatusLine().getStatusCode() == 200) {  
	                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (response != null) {  
	                    response.close();  
	                }  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return resultString;  
	    }  
	  
	    public static String doGet(String url) {  
	        return doGet(url, null);  
	    }  
	  
	    public static String doPost(String url, Map<String, String> param) {  
	        // ����Httpclient����  
	        CloseableHttpClient httpClient = HttpClients.createDefault();  
	        CloseableHttpResponse response = null;  
	        String resultString = "";  
	        try {  
	            // ����Http Post����  
	            HttpPost httpPost = new HttpPost(url);  
	            // ���������б�  
	            if (param != null) {   
	                List<NameValuePair> paramList = new ArrayList<NameValuePair>(); 
	                for (String key : param.keySet()) {  
	                    paramList.add(new BasicNameValuePair(key, param.get(key)));  
	                }  
	                // ģ���  
	                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");  
	                httpPost.setEntity(entity);  
	            }  
	            // ִ��http����  
	            response = httpClient.execute(httpPost);  
	            resultString = EntityUtils.toString(response.getEntity(), "utf-8");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                response.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  
	        return resultString;  
	    }  
	  
	    public static String doPost(String url) {  
	        return doPost(url, null);  
	    }  
	      
	    public static String doPostJson(String url, String json) {  
	        // ����Httpclient����  
	        CloseableHttpClient httpClient = HttpClients.createDefault();  
	        CloseableHttpResponse response = null;  
	        String resultString = "";  
	        try {  
	            // ����Http Post����  
	            HttpPost httpPost = new HttpPost(url);  
	            // ������������  
	            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);  
	            httpPost.setEntity(entity);  
	            // ִ��http����  
	            response = httpClient.execute(httpPost);  
	            resultString = EntityUtils.toString(response.getEntity(), "utf-8");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                response.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  
	        return resultString;  
	    }  
	    
	// public static JSONObject doPostStr(String url, String outStr) {
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// HttpPost httpPost = new HttpPost(url);
	// JSONObject jsonObject = null;
	// try {
	// httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
	// HttpResponse response = httpClient.execute(httpPost);
	// String result = EntityUtils.toString(response.getEntity(), "UTF-8");
	// jsonObject = JSONObject.fromObject(result);
	//
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return jsonObject;
	// }
	//
	// public static JSONObject doGetStr(String url) {
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// HttpGet httpGet = new HttpGet(url);
	// JSONObject jsonObject = null;
	// try {
	// HttpResponse response = httpClient.execute(httpGet);// ���������ķ��صĽ��
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// String result = EntityUtils.toString(entity, "UTF-8");
	// jsonObject = JSONObject.fromObject(result);
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return jsonObject;
	// }
	    
	    
	    
	}  