package sleeepyy.textrelationmarker;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * TextRelationMarker
 * Created by sleepy on 2017/11/28.
 */

public class ListFileActivity extends ListActivity {

    private List<File> fileNameList;
    private String UrlKey = "url";
    private Bundle bundle;
    private String fileNameKey = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_list);
        Log.i("info", "init file list");
        initFileList();
        Log.i("info", "init file list done");

    }

    private void initFileList()
    {
//        File path = android.os.Environment.getRootDirectory();
//        File path = new File(getString(R.string.init_dir));
        File path = getFilesDir();
        Log.i("info", path.getAbsolutePath());

        File[] f = path.listFiles();
        if(f == null || f.length==0){
            Toast.makeText(this, "This directory is empty...", Toast.LENGTH_SHORT).show();
            Log.i("info..", "eeeee");
            this.onBackPressed();
        }
        else{
            Log.i("info", Arrays.toString(f));
            fill(f);
        }
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
        Dialog dialog = new AlertDialog.Builder(ListFileActivity.this).setTitle(
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(ListFileActivity.this, ViewFileActivity.class);
        bundle = new Bundle();
        File file = fileNameList.get(position);
        if (file.isDirectory())
        {
            File[] f = file.listFiles();
            fill(f);
        }
        else {
            bundle.putString(fileNameKey, file.getAbsolutePath());
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    }


    private void fill(File[] files) {
        fileNameList = new ArrayList<>();
        for (File file : files) {
            if (isValidFileOrDir(file)) {
                fileNameList.add(file);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileToStrArr(fileNameList));
        setListAdapter(adapter);
    }


    private boolean isValidFileOrDir(File file)
    {
        if (file.isDirectory()) {
            return true;
        }
        else {
            String fileName = file.getName().toLowerCase();
            if (fileName.endsWith(".txt") || fileName.endsWith(".json")) {
                return true;
            }
        }
        return false;
    }


    private String[] fileToStrArr(List<File> fl)
    {

        ArrayList<String> fnList = new ArrayList<>();
        for (int i = 0; i < fl.size(); i++) {
            String nameString = fl.get(i).getName();
            fnList.add(nameString);
        }
        return fnList.toArray(new String[0]);
    }

}
