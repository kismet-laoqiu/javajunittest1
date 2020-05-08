package com.example.yhy.justfortest.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yhy.justfortest.R;

public class HeadPart extends RelativeLayout {
    public HeadPart(Context context) {
        super(context);
    }
    private ImageView top_left;
    // 标题Tv
    private TextView top_title;
    public HeadPart(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_head, this);
        top_left=findViewById(R.id.title_back);
        top_title=findViewById(R.id.title_item);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BaseHead,0,0);
        top_title.setText(a.getText(R.styleable.BaseHead_title));
    }
    public void setTop_title(String title) {
        top_title.setText(title);
    }

    public void setTop_left(View.OnClickListener listener) {
        top_left.setOnClickListener(listener);
    }

    public HeadPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeadPart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
