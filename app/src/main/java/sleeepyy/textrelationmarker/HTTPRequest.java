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
    /**
     * 使用get方式请求分词服务
     *
     * @param text 原始中文字符串
     * @return 分词词组
     */
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
                    // 利用string url构建URL对象
                    URL mURL = new URL(url);
                    conn = (HttpsURLConnection) mURL.openConnection();
//                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(10000);

                    int responseCode = conn.getResponseCode();
                    InputStream is = conn.getInputStream();
                    result = getStringFromInputStream(is);
                    response.finish(result==null?null:result.split(SPLIT_CHAR));
//                    if (responseCode == 200) {
//                        InputStream is = conn.getInputStream();
//                        result = getStringFromInputStream(is);
//                        if (response != null) {
//                            response.finish(result == null ? null : result.split(SPLIT_CHAR));
//                        }
//                    } else {
//                        if (response != null){
//                            response.failure("访问失败" + responseCode + ':' + conn.getResponseMessage());
//                        }
//                    }
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

    /**
     * 读取流,返回字符串
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}
