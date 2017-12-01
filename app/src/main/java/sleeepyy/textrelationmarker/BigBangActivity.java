package sleeepyy.textrelationmarker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Vector;



/**
 * Created by sleepy on 2017/11/30.
 */

public class BigBangActivity extends AppCompatActivity {
    public static boolean isShowing = false;
    private String content = null;
    private String id = null;
    private int pointer = 0;
    private String[] lines;
    private Vector<String> sentences = new Vector<>();
    private AutoExpandLinearLayout mAutoLayout = null;
    private Vector<BangWordView> wordViewvector = new Vector<>();
    private JSONObject jsonData = new JSONObject();
    private String[] categories = {"Entity", "Relation"};
    private String[][] labels = {{"person", "country", "city"}, {"contains", "equals", "friends"}};
    private int label_i = 0;
    private int label_j = 0;

    @Override
    protected void onStart() {
        super.onStart();
        isShowing = true;
    }

    @Override
    protected void onStop() {
        isShowing = false;
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbang);
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        content = bundle.getString("content");
        id = bundle.getString("id");
        Log.i("id", id);
        Log.i("content: ", content);
        lines = content.split("。|\n|\\s\\s+");
        Log.i("lines", Arrays.toString(lines));
        Log.i("lines", String.valueOf(lines.length));
        for(String _line: lines){
            if(!_line.isEmpty()){
                sentences.add(_line+"。");
            }
        }
        try {
            jsonData.put("article_id", id);
            jsonData.put("artcle_content", content);
        }catch (Exception e){
            e.printStackTrace();
        }
        mAutoLayout = findViewById(R.id.auto_layout);

        render();

        Button next_sentence = findViewById(R.id.btn_next);
        next_sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointer++;
                render();
            }
        });

        final Button mark_button = findViewById(R.id.btn_mark);
        mark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = mAutoLayout.getChildCount();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    AppCompatCheckBox checkBox = (AppCompatCheckBox) mAutoLayout.getChildAt(i);
                    if (checkBox.isChecked()){
                        builder.append(checkBox.getText());
                    }
                }
                final AlertDialog.Builder markerDialog = new AlertDialog.Builder(BigBangActivity.this);
                markerDialog.setTitle("Please choose the category:");
                markerDialog.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        label_i = i;
                    }
                });
                markerDialog.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder nextMarkerDialog = new AlertDialog.Builder(BigBangActivity.this);
                        nextMarkerDialog.setTitle("Please choose label:");
                        nextMarkerDialog.setSingleChoiceItems(labels[label_i], 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                label_j = i;
                            }
                        });
                        nextMarkerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(BigBangActivity.this, categories[label_i]+"/"+labels[label_i][label_j], Toast.LENGTH_SHORT).show();
//                                onBackPressed();

                            }
                        });
                        nextMarkerDialog.show();
                    }
                });
//                markerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
                markerDialog.show();
//                real_dialog.
            }
        });


    }

    private void render(){
        String line  = sentences.get(pointer);
//        while(line.length()<3){
//            ++pointer;
//            line = lines[pointer];
//        }
//        for(BangWordView bang: wordViewvector){
//            mAutoLayout.removeView(bang);
//        }
        mAutoLayout.removeAllViews();
//        wordViewvector.clear();
        Log.i("line", line);
        for (char word : line.toCharArray()) {
            BangWordView bangWordView = new BangWordView(getApplication(), String.valueOf(word));
            mAutoLayout.addView(bangWordView);
//            wordViewvector.add(bangWordView);
        }
    }

}
