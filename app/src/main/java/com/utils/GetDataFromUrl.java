package com.utils;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
 * Created by Raj on 4/4/2015.
 * this class use for submit data to server with HTTP Method.
 */
public class GetDataFromUrl {
    //this method through upload string data to server
    public static String GetData(String myUrl) {
        String Result = "";
        Log.e("URL", "-" + myUrl);
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            if (myUrl.contains("https")) {
                trustAllCertificates();
                URL url = new URL(myUrl);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream instream = new BufferedInputStream(urlConnection.getInputStream());
                Result = ConvertToString.convertDate(instream);
            } else {
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet(myUrl);
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    Result = ConvertToString.convertDate(instream);
                }
            }

//


            return Result;
//            } else {
//                return "";
//            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Exception", "-" + e.toString());
            return "";
        }

    }

    public static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }


    public static String PostData(HashMap<String, String> mapParameter, String mURL) {
        String data = "";

        String text = "";
        try {
            if (mapParameter != null) {
                for (String key : mapParameter.keySet()) {
                    if (data.equals("")) {
                        data = URLEncoder.encode(key, "UTF-8")
                                + "=" + URLEncoder.encode(mapParameter.get(key), "UTF-8");
                    } else {
                        data += "&" + URLEncoder.encode(key, "UTF-8") + "="
                                + URLEncoder.encode(mapParameter.get(key), "UTF-8");

                    }
                }
            }


            BufferedReader reader = null;

            // Send data


            // Defined URL  where to send data
            URL url = new URL(mURL);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();


            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }


}


