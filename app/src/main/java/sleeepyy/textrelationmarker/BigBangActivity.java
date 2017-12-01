package sleeepyy.textrelationmarker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Vector;


/**
 * TextRelationMarker
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
    private JSONArray jsonEntities = new JSONArray();
    private JSONArray jsonRelations = new JSONArray();
    private String[] categories = {"Entity", "Relation"};
    private String[][] labels = {{"person", "country", "city"}, {"contains", "equals", "friends"}};
    private int label_i = 0;
    private int label_j = 0;
    //    private String self_label = null;
    private String full_label = null;
    private String text_1 = null;
    private String text_2 = null;
    private int start_1 = 0;
    private int start_2 = 0;

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
    protected void onCreate(Bundle savedInstanceState) {

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
        for (String _line : lines) {
            if (!_line.isEmpty()) {
                sentences.add(_line + "。");
            }
        }
//        try {
//            jsonData.put("article_id", id);
//            jsonData.put("artcle_content", content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mAutoLayout = findViewById(R.id.auto_layout);

        render();

        Button mark_finished = findViewById(R.id.btn_finish);
        mark_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonData.put("Entities", jsonEntities);
                    jsonData.put("Relations", jsonRelations);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {

                    Log.i("jsonjsonjson", jsonData.toString(4));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File dir = new File(getFilesDir() + "/json");
                if (!dir.exists()) {
                    boolean success = dir.mkdir();
                    if (!success) {
                        Log.e("error", "create dir not successful.");
                    }
                }
                try {
                    String[] names = id.split("/|\\.");
                    String part_name = names[names.length - 2];
                    String file_name = "sentence_" + String.valueOf(pointer) + "_" + part_name + ".json";
                    Log.i("filesdir", getFilesDir().toString());
                    FileOutputStream outputStream = new FileOutputStream(new File(getFilesDir().toString() + "/json", file_name));
                    outputStream.write(jsonData.toString(4).getBytes());
                    outputStream.close();

                    Toast.makeText(BigBangActivity.this, "Json Saved to " + file_name, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Button next_sentence = findViewById(R.id.btn_next);
        next_sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointer++;
                if (pointer >= sentences.size()) {
                    Toast.makeText(BigBangActivity.this, "Already come to the last Sentence!", Toast.LENGTH_SHORT).show();
//                    pointer = 0;
                } else {
                    render();
                }
//                Log.i("json", jsonData.toString(4));
            }
        });

        final Button mark_button = findViewById(R.id.btn_mark);
        mark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_1 = "";
                text_2 = "";
                start_1 = 0;
                start_2 = 0;
                int count = mAutoLayout.getChildCount();
                StringBuilder firstBuilder = new StringBuilder();
                StringBuilder secondBuilder = new StringBuilder();
                StringBuilder builder = firstBuilder;
                AppCompatCheckBox lastCheckBox = null;
                for (int i = 0; i < count; i++) {
                    AppCompatCheckBox checkBox = (AppCompatCheckBox) mAutoLayout.getChildAt(i);
                    Log.i("check", String.valueOf(checkBox.isChecked()));
                    if (lastCheckBox != null && !lastCheckBox.isChecked() && checkBox.isChecked()) {
                        builder = secondBuilder;
                        if (start_2 == 0) start_2 = i;
                    }
                    if (checkBox.isChecked()) {
                        if (start_1 == 0 && start_2 == 0) start_1 = i;
                        builder.append(checkBox.getText().toString());
                    }
                    lastCheckBox = checkBox;
                }
                text_1 = firstBuilder.toString();
                text_2 = secondBuilder.toString();
                final AlertDialog.Builder markerDialog = new AlertDialog.Builder(BigBangActivity.this);
                markerDialog.setTitle("Please Choose The Category:");
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
                        nextMarkerDialog.setTitle("Please Choose Label:");
                        nextMarkerDialog.setSingleChoiceItems(labels[label_i], 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                label_j = i;
                            }
                        });

                        nextMarkerDialog.setPositiveButton("Add Self-defined Label", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(BigBangActivity.this, "fff", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder selfMarkerDialog = new AlertDialog.Builder(BigBangActivity.this);
                                selfMarkerDialog.setTitle("Append Self Defined Label:");
//                                final TextView textView = new TextView(BigBangActivity.this);
                                String _text = categories[label_i] + "/" + labels[label_i][label_j] + "/";
//                                textView.setText(_text);

//                                selfMarkerDialog.setView(textView);
                                final EditText editText = new EditText(BigBangActivity.this);
                                editText.setText(_text);

                                selfMarkerDialog.setView(editText);

                                selfMarkerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        full_label = editText.getText().toString();
                                        Log.i("text1", text_1);
                                        Log.i("text2", text_2);
//                                        full_label = categories[label_i] + "/" + labels[label_i][label_j] +"/"+self_label;
                                        Toast.makeText(BigBangActivity.this, full_label, Toast.LENGTH_SHORT).show();
                                        updateJson();

                                        int count = mAutoLayout.getChildCount();
                                        for (int _i = 0; _i < count; _i++) {
                                            AppCompatCheckBox checkBox = (AppCompatCheckBox) mAutoLayout.getChildAt(_i);
                                            checkBox.setChecked(false);

                                        }


                                    }
                                });
                                selfMarkerDialog.show();
                            }
                        });
                        nextMarkerDialog.show();
                    }
                });
                markerDialog.show();

//                JSONObject marker = new JSONObject();
//                try {
//                    marker.put("sentenceId", pointer);
//                    marker.put("sentence", sentences.get(pointer));
//                    marker.put("em1Text", text_1);
//                    marker.put("em2Text", text_2);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }


        });

    }

    private void updateJson() {
        JSONObject mark = new JSONObject();
        if (text_2.isEmpty()) {
            Log.i("text2", "isempty");
            Log.i("info", "entity");
            try {
                mark.put("text", text_1);
                mark.put("start", start_1);
                mark.put("label", full_label);
                Log.i("lalalalabel", full_label);
                jsonEntities.put(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                mark.put("em1Text", text_1);
                mark.put("em2Text", text_2);
                mark.put("start_1", start_1);
                mark.put("start_2", start_2);
                mark.put("label", full_label);
                jsonRelations.put(mark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void render() {
        jsonData = new JSONObject();
        jsonEntities = new JSONArray();
        jsonRelations = new JSONArray();
        String line = sentences.get(pointer);
        mAutoLayout.removeAllViews();
//        wordViewvector.clear();
        try {
            jsonData.put("articleId", id);
            jsonData.put("sentenceId", pointer);
            jsonData.put("sentence", line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("line", line);
//        for (char word : line.toCharArray()) {
//            BangWordView bangWordView = new BangWordView(getApplication(), String.valueOf(word));
//            mAutoLayout.addView(bangWordView);
////            wordViewvector.add(bangWordView);
//        }
        Toast.makeText(BigBangActivity.this, "Waiting for Split Words...", Toast.LENGTH_LONG).show();
        requestServe(line);
    }

    protected void requestServe(String text) {
        HTTPRequest.getSplitChar(text, new IResponse() {
            @Override
            public void finish(final String[] words) {
                if (words != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (String word : words) {
                                BangWordView bangWordView = new BangWordView(getApplication(),word);
                                mAutoLayout.addView(bangWordView);
                            }
                        }
                    });
                }
            }

            @Override
            public void failure(final String errorMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
