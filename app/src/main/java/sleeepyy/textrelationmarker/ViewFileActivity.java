package sleeepyy.textrelationmarker;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by sleepy on 2017/11/28.
 */

public class ViewFileActivity extends AppCompatActivity {

    private String file_path = null;
    private String url = null;
    private static final String gb2312 = "GB2312";
    private static final String utf8 = "UTF-8";
    private static final String defaultCode = gb2312;
    private String content = null;
    private LinearLayout adjust_page;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_view);
        Log.i("View", "here");
        tv = findViewById(R.id.view_contents);
        try {
            Bundle bundle = this.getIntent().getExtras();
            assert bundle != null;
            file_path = bundle.getString("path");
            url = bundle.getString("url");
            Log.i("path", file_path==null?"":file_path);
            Log.i("url", url==null?"":url);
            refreshGUI(defaultCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adjust_page = findViewById(R.id.adjust_page);
        adjust_page.setVisibility(View.GONE);

        Button add_size = findViewById(R.id.add_size);
        add_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize()+1f);
                Log.i("info", "size:"+tv.getTextSize());
            }
        });
        Button minus_size = findViewById(R.id.minus_size);
        minus_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize()-1f);
                Log.i("info", "size:"+tv.getTextSize());
            }
        });
        Button adjust_done = findViewById(R.id.adjust_done);
        adjust_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjust_page.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gb2312:
                refreshGUI(defaultCode);
                break;
            case R.id.utf8:
                refreshGUI(utf8);
                break;
            case R.id.about:
                doAbout();
                break;
            case R.id.adjust:
                adjust_page.setVisibility(View.VISIBLE);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doAbout() {
        Dialog dialog = new AlertDialog.Builder(ViewFileActivity.this).setTitle(
                R.string.aboutTitle).setMessage(R.string.aboutInfo)
                .setPositiveButton(R.string.aboutOK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                            }
                        }).create();
        dialog.show();
    }

    private void refreshGUI(String code)
    {
        getStringFromFile(code);
        if(content == null){
            Log.i("content", "bad");
        }
        else{
            Log.i("content", content);
        }
        tv.setText(content);
    }

    public void getStringFromFile(String code)
    {
        try {

            if(file_path != null){
                StringBuilder builder = new StringBuilder();
                BufferedReader in;
                FileInputStream fInputStream = new FileInputStream(file_path);
                InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, code);
                if(!new File(file_path).exists())
                {
                    return ;
                }
                in = new BufferedReader(inputStreamReader);
                while (in.ready()) {
                    builder.append(in.readLine()).append("\n");
                }
                in.close();
                content =  builder.toString();
            }
            else if (url != null){
                httpThread http = new httpThread();
                http.setaUrl(url);
                http.setView(this);
                http.start();

                try{
                    http.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public byte[] readFile(String fileName) throws Exception {
        byte[] result = null;
        File file = new File(fileName);
        try (FileInputStream fis = new FileInputStream(file)) {
            result = new byte[fis.available()];
            fis.read(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    class httpThread extends Thread implements Runnable{
        URL aUrl;
        ViewFileActivity activity;

        public void setaUrl(String url){
            try {
                aUrl = new URL(url);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void setView(ViewFileActivity view_activity){
            activity = view_activity;
        }

        @Override
        public void run() {
            try{
                HttpURLConnection conn = (HttpURLConnection) aUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);//设置连接超时
                conn.setReadTimeout(8000);
                conn.setRequestProperty("connection", "Keep-Alive");
                InputStream is = conn.getInputStream();
                Log.i("http code", String.valueOf(conn.getResponseCode()));

                InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
                BufferedReader in = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line=in.readLine())!=null) {
                    builder.append(line).append("\n");
//                    line = in.readLine();
//                    Toast.makeText(ViewFileActivity.this, line, Toast.LENGTH_SHORT).show();
                    Log.i("info", line);
                }
                in.close();
                activity.content = builder.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}