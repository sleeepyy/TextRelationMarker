package sleeepyy.textrelationmarker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button fileOpenBtn = (Button)findViewById(R.id.open_file_button);
        fileOpenBtn.setOnClickListener(new OpenFileAction());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.gb2312);
        menu.removeItem(R.id.utf8);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                doAbout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doAbout() {
        Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle(
                R.string.aboutTitle).setMessage(R.string.aboutInfo)
                .setPositiveButton(R.string.aboutOK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                // ��ť�¼�
                            }
                        }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    class OpenFileAction implements View.OnClickListener
    {

        public void onClick(View v) {

            Intent in = new Intent(MainActivity.this, ListFileActivity.class);
            startActivityForResult(in, 0);
        }

    }
}
