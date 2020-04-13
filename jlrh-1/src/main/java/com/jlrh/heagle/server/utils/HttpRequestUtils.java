package com.jlrh.heagle.server.utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/** 
 * Http���󹤾��� 
 * @author zzw
 * @since 2014-8-24 13:30:56 
 * @version v1.0.1 
 */ 
public class HttpRequestUtils {
	 static boolean proxySet = false;
	    static String proxyHost = "127.0.0.1";
	    static int proxyPort = 8087;
	    /** 
	     * ���� 
	     * @param source 
	     * @return 
	     */  
	    public static String urlEncode(String source,String encode) {  
	        String result = source;  
	        try {  
	            result = java.net.URLEncoder.encode(source,encode);  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	            return "0";  
	        }  
	        return result;  
	    }
	    public static String urlEncodeGBK(String source) {  
	        String result = source;  
	        try {  
	            result = java.net.URLEncoder.encode(source,"GBK");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	            return "0";  
	        }  
	        return result;  
	    }
	    /** 
	     * ����http�����ȡ���ؽ�� 
	     * @param req_url �����ַ 
	     * @return 
	     */  
	    public static String httpRequest(String req_url) {
	        StringBuffer buffer = new StringBuffer();  
	        try {  
	            URL url = new URL(req_url);  
	            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  

	            httpUrlConn.setDoOutput(false);  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setUseCaches(false);  

	            httpUrlConn.setRequestMethod("GET");  
	            httpUrlConn.connect();  

	            // �����ص�������ת�����ַ���  
	            InputStream inputStream = httpUrlConn.getInputStream();  
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  

	            String str = null;  
	            while ((str = bufferedReader.readLine()) != null) {  
	                buffer.append(str);  
	            }  
	            bufferedReader.close();  
	            inputStreamReader.close();  
	            // �ͷ���Դ  
	            inputStream.close();  
	            inputStream = null;  
	            httpUrlConn.disconnect();  

	        } catch (Exception e) {  
	            System.out.println(e.getStackTrace());  
	        }  
	        return buffer.toString();  
	    }  

	    /** 
	     * ����http����ȡ�÷��ص������� 
	     * @param requestUrl �����ַ 
	     * @return InputStream 
	     */  
	    public static InputStream httpRequestIO(String requestUrl) {  
	        InputStream inputStream = null;  
	        try {  
	            URL url = new URL(requestUrl);  
	            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setRequestMethod("GET");  
	            httpUrlConn.connect();  
	            // ��÷��ص�������  
	            inputStream = httpUrlConn.getInputStream();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return inputStream;  
	    }


	    /**
	     * ��ָ��URL����GET����������
	     * 
	     * @param url
	     *            ���������URL
	     * @param param
	     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	     * @return URL ������Զ����Դ����Ӧ���
	     */
	    public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // �򿪺�URL֮�������
	            URLConnection connection = realUrl.openConnection();
	            // ����ͨ�õ���������
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // ����ʵ�ʵ�����
	            connection.connect();
	            // ��ȡ������Ӧͷ�ֶ�
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // �������е���Ӧͷ�ֶ�
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            // ���� BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("����GET��������쳣��" + e);
	            e.printStackTrace();
	        }
	        // ʹ��finally�����ر�������
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }

	    /**
	     * ��ָ�� URL ����POST����������
	     * 
	     * @param url
	     *            ��������� URL
	     * @param param
	     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	     * @param isproxy
	     *               �Ƿ�ʹ�ô���ģʽ
	     * @return ������Զ����Դ����Ӧ���
	     */
	    public static String sendPost(String url, String param,boolean isproxy) {
	        OutputStreamWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            HttpURLConnection conn = null;
	            if(isproxy){//ʹ�ô���ģʽ
	                @SuppressWarnings("static-access")
	                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	                conn = (HttpURLConnection) realUrl.openConnection(proxy);
	            }else{
	                conn = (HttpURLConnection) realUrl.openConnection();
	            }
	            // �򿪺�URL֮�������

	            // ����POST�������������������
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setRequestMethod("POST");    // POST����


	            // ����ͨ�õ���������

	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	            conn.connect();

	            // ��ȡURLConnection�����Ӧ�������
	            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	            // �����������
	            out.write(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }    

	   // ----------------------------------------------
	    /*public static String post(String requestUrl, String accessToken, String params)
	            throws Exception {
	        String contentType = "application/x-www-form-urlencoded";
	        return HttpUtil.post(requestUrl, accessToken, contentType, params);
	    }

	    public static String post(String requestUrl, String accessToken, String contentType, String params)
	            throws Exception {
	        String encoding = "UTF-8";
	        if (requestUrl.contains("nlp")) {
	            encoding = "GBK";
	        }
	        return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
	    }

	    public static String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
	            throws Exception {
	        String url = requestUrl + "?access_token=" + accessToken;
	        return HttpUtil.postGeneralUrl(url, contentType, params, encoding);
	    }*/

	    public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
	            throws Exception {
	        URL url = new URL(generalUrl);
	        // �򿪺�URL֮�������
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        // ����ͨ�õ���������
	        connection.setRequestProperty("Content-Type", contentType);
	        connection.setRequestProperty("Connection", "Keep-Alive");
	        connection.setUseCaches(false);
	        connection.setDoOutput(true);
	        connection.setDoInput(true);

	        // �õ���������������
	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	        out.write(params.getBytes(encoding));
	        out.flush();
	        out.close();

	        // ����ʵ�ʵ�����
	        connection.connect();
	        // ��ȡ������Ӧͷ�ֶ�
	        Map<String, List<String>> headers = connection.getHeaderFields();
	        // �������е���Ӧͷ�ֶ�
	        for (String key : headers.keySet()) {
	            System.err.println(key + "--->" + headers.get(key));
	        }
	        // ���� BufferedReader����������ȡURL����Ӧ
	        BufferedReader in = null;
	        in = new BufferedReader(
	                new InputStreamReader(connection.getInputStream(), encoding));
	        String result = "";
	        String getLine;
	        while ((getLine = in.readLine()) != null) {
	            result += getLine;
	        }
	        in.close();
	        System.err.println("result:" + result);
	        return result;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public static void main(String[] args) {
	        //demo:�������
	        String url = "http://localhost:8080/xlws_xxx/test/test";
	        String para = "";

	        String sr=HttpRequestUtils.sendPost(url,para,true);
	        System.out.println(sr);
	    }

	}