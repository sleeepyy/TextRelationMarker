package sleeepyy.textrelationmarker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by sleepy on 2017/11/30.
 */

public class BigBangActivity extends AppCompatActivity {
    public static boolean isShowing = false;
    private String content = null;
    private int pointer = 0;
    private String[] lines;
    private AutoExpandLinearLayout mAutoLayout = null;
    private Vector<BangWordView> wordViewvector = new Vector<>();

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
        Log.i("content: ", content);
        lines = content.split("。|\n|\\s\\s+");
        Log.i("lines", Arrays.toString(lines));
        Log.i("lines", String.valueOf(lines.length));
        for(int i=0; i<lines.length; ++i){
            lines[i] = lines[i].concat("。");
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


    }

    private void render(){
        String line  = lines[pointer];
        while(line.length()<3){
            ++pointer;
            line = lines[pointer];
        }
        for(BangWordView bang: wordViewvector){
            mAutoLayout.removeView(bang);
        }
        wordViewvector.clear();
        Log.i("line", line);
        for (char word : line.toCharArray()) {
            BangWordView bangWordView = new BangWordView(getApplication(), String.valueOf(word));
            mAutoLayout.addView(bangWordView);
            wordViewvector.add(bangWordView);
        }
    }

}
