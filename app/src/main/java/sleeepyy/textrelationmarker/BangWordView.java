package sleeepyy.textrelationmarker;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CheckBox;


/**
 * TextRelationMarker
 * Created by sleepy on 2017/11/28.
 */

public class BangWordView extends android.support.v7.widget.AppCompatCheckBox {
    public BangWordView(Context context, String text) {
        super(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundResource(R.drawable.checkbox_selector);
        this.setClickable(true);
        setButtonDrawable(null);
        setLayoutParams(params);
        setText(text);
        setTextColor(Color.BLACK);
        setTextSize(18);
    }
}
