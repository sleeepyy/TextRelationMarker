package sleeepyy.textrelationmarker;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;



/**
 * Created by sleepy on 2017/11/28.
 */

public class ViewFileActivity extends AppCompatActivity {

    private String filenameString;
    private static final String gb2312 = "GB2312";
    private static final String utf8 = "UTF-8";
    private static final String defaultCode = gb2312;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_view);
        try {
            Bundle bunde = this.getIntent().getExtras();
            assert bunde != null;
            filenameString = bunde.getString("fileName");
            refreshGUI(defaultCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        TextView tv = (TextView) findViewById(R.id.view_contents);
        String fileContent = getStringFromFile(code);
        tv.setText(fileContent);
    }

    public String getStringFromFile(String code)
    {
        try {
            StringBuilder sBuffer = new StringBuilder();
            FileInputStream fInputStream = new FileInputStream(filenameString);
            InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, code);
            BufferedReader in = new BufferedReader(inputStreamReader);
            if(!new File(filenameString).exists())
            {
                return null;
            }
            while (in.ready()) {
                sBuffer.append(in.readLine()).append("\n");
            }
            in.close();
            return sBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

}
