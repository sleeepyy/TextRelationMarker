package sleeepyy.textrelationmarker;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;


/**
 * TextRelationMarker
 * Created by sleepy on 2017/12/01.
 */


public class HTTPRequest {
    private static final String SPLIT_CHAR = " ";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    public static void getSplitChar(final String text,final IResponse response) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String result = null;
                HttpURLConnection conn = null;
                String utf8string = "";
                try {
                    utf8string = URLEncoder.encode(text,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = "https://api.ltp-cloud.com/analysis" +
                        '?' + "api_key" + '=' + "D8W3a5b4fvVngvMYrx0cfHOj5bKlrXlx6iiwsGNn" +
                        '&' + "text" + '=' + utf8string +
                        '&' + "pattern" + '=' + "ws" +
                        '&' + "format" + '=' + "plain";
                Log.i("url", url);
                try {
                    URL mURL = new URL(url);
                    conn = (HttpsURLConnection) mURL.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(10000);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream is = conn.getInputStream();
                        result = getStringFromInputStream(is);
                        if (response != null) {
                            response.finish(result == null ? null : result.split(SPLIT_CHAR));
                        }
                    } else {
                        if (response != null){
                            response.failure("访问失败" + responseCode + ':' + conn.getResponseMessage());
                        }
                    }
                } catch (Exception e) {
                    if (response != null){
                        response.failure(e.toString());
                    }
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }
}
