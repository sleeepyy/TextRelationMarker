package sleeepyy.textrelationmarker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button openFileButton = findViewById(R.id.open_file_button);
        openFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent,1);
//            }

            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ListFileActivity.class);
                startActivityForResult(in, 0);
            }
        });

        Button openUrlButton = findViewById(R.id.open_url_button);
        openUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(MainActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
                inputDialog.setTitle("Input Text Url: ");
                inputDialog.setView(editText);
                inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = editText.getText().toString();
                        Toast.makeText(MainActivity.this, "Trying to open "+ url, Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(MainActivity.this, ViewFileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                });
                inputDialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.gb2312);
        menu.removeItem(R.id.utf8);
        menu.removeItem(R.id.adjust);
        menu.removeItem(R.id.mark);
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
                            }
                        }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



//    class OpenFileAction implements View.OnClickListener
//    {
//
////        public void onClick(View v) {
////
////            Intent in = new Intent(MainActivity.this, ListFileActivity.class);
////            startActivityForResult(in, 0);
////        }
//
//        public void onClick(View v) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(intent,1);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == AppCompatActivity.RESULT_OK) {//是否选择，没选择就不会继续
//            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
////                String[] proj = {MediaStore.Images.Media.DATA};
////                Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
////                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////                actualimagecursor.moveToFirst();
////                String img_path = actualimagecursor.getString(actual_image_column_index);
////                File file = new File(img_path);
////                Toast.makeText(MainActivity.this, file.toString(), Toast.LENGTH_SHORT).show();
//
//            assert uri != null;
//            String path = uri.getPath();
//            Log.i("info", "uri path   "+path);
//            Intent intent = new Intent(MainActivity.this, ViewFileActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("fileName", path);
//            intent.putExtras(bundle);
//            startActivityForResult(intent, 0);
//        }
//    }
}
