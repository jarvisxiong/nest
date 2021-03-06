/**
 * bargetorCommon
 * com.bargetor.nest.common.http
 * SSLUtil.java
 * 
 * 2015年6月20日-下午9:52:47
 *  2015Bargetor-版权所有
 *
 */
package com.bargetor.nest.common.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.File;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *
 * SSLUtil
 * 
 * kin
 * kin
 * 2015年6月20日 下午9:52:47
 * 
 * @version 1.0.0
 *
 */
public class SSLUtil {
	private static HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public static CloseableHttpClient buildClientByKeyStore(File keyStore, String keyStroePassword){
        try {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(keyStore, keyStroePassword.toCharArray());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContextBuilder.build());
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
	
	private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
 
    static class miTM implements TrustManager,X509TrustManager {
 
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		@Override
		public X509Certificate[] getAcceptedIssuers() {
            return null;
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			return;
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			return;
		}
    }
     
    /**
     * ignoreSsl(忽略HTTPS请求的SSL证书，必须在openConnection之前调用)
     * (这里描述这个方法适用条件 – 可选)
     * @throws Exception
     * void
     * @exception
     * @since  1.0.0
    */
    public static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
    
    /**
     * defaultSsl(恢复默认的SSL)
     * (这里描述这个方法适用条件 – 可选)
     * void
     * @exception
     * @since  1.0.0
    */
    public static void defaultSsl(){
    	HttpsURLConnection.setDefaultHostnameVerifier(defaultHostnameVerifier);
    }
}
