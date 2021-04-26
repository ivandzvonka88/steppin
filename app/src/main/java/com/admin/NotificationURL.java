package com.admin;

import android.util.Log;

import com.utils.ConvertToString;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class NotificationURL {

    public static String send(String sTitle, String sDetails, String sImageURL, String sType, String sID, String sAccessToken) {
        String Result = "";
        if (!sImageURL.contains("") && !sImageURL.contains("http") && !sImageURL.contains("https")) {
            sImageURL = "http://" + sImageURL;

        }
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://fcm.googleapis.com/fcm/send");
//                sAccessToken = "f3NTcJVfYB0:APA91bGYkuJyDhspmgtCPdystivX0mXjrwRUlQZR4kzkl5Q1M1AWxooeow3pyP-ymzxU24E1PZ7xMObOuHNeAVWQwDWrY-uisG6TxY5qgXeGTrVPqfQ1NQ75m3S3BdvSlIrSSxWtf34I";

//                sDetails = URLEncoder.encode(sDetails, "UTF-8");
//                sDetails = sDetails.replaceAll("\'", "\\\\\'");
            sTitle = sTitle.replaceAll("\"", "\\\\\"");
            sDetails = sDetails.replaceAll("\"", "\\\\\"");
            try {
                httppost.setHeader("Content-type", "application/json");
                httppost.setHeader("Authorization", "key=AAAAdcFYNiM:APA91bGUi52vqnpBUdjsuiCbDKcHBbDD1bilS3wvajsD7CoEgfRb7DUSCvR3JEGYIXD2G0ZEHyRkZiWfDKwxUUmvbjAvSjLrNoq26L6t0FKj3mHgY-o8MVLmLHXLcYw6tyT1Qid-fji8");

//                String payload = "{\"to\": \"" + sAccessToken + "\",\"data\": {\"message\": \"" + sDetails + "\",\"image\": \"" + sImageURL + "\",\"title\": \"" + sTitle + "\",\"type\":\"" + sType + "\",\"id\":\"" + sID + "\"}}";
                String payload = "{\"to\":\"/topics/" + sAccessToken + "\",\"data\":{\"title\":\""+sTitle+"\",\"message\":\""+sDetails+"\",\"type\":\""+sType+"\",\"id\":\""+sID+"\",\"image\":\""+sImageURL+"\"}}";
//                String payload = "{\"to\":\"/topics/" + sAccessToken + "\",\"content_available\":true,\"mutable_content\":true,\"data\":{\"title\":\""+sTitle+"\",\"message\":\""+sDetails+"\",\"type\":\""+sType+"\",\"id\":\""+sID+"\",\"image\":\""+sImageURL+"\"},\"notification\":{\"title\":\""+sTitle+"\",\"body\":\""+sDetails+"\",\"sound\":\"default\"}}";
                Log.e("Payload", "-" + payload);
                httppost.setEntity(new StringEntity(payload));
//                    httppost.setEntity(new StringEntity("'{\"to\": \"" + sAccessToken + "\",\"data\": {\"message\": \"" + sDetails + "\",\"image\": \"" + sImageURL + "\",\"title\": \"" + sTitle + "\",\"type\":\"" + 1 + "\"}}'"));

                HttpResponse result = httpclient.execute(httppost);

                HttpEntity resultEntity = result.getEntity();
                if (resultEntity != null) {
                    InputStream instream = resultEntity.getContent();
                    Result = ConvertToString.convertDate(instream);
                }
            } catch (ClientProtocolException e) {
                /**/
            } catch (IOException e) {
                /**/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

}
