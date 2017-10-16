package com.github.app.ui.demo;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.LinearGradientUtil;
import com.github.app.widget.CircleBarView;

import java.text.DecimalFormat;

/**
 * Created by benny
 * on 2017/10/16.
 */

public class CircleBarActivity extends BaseActivity {
    private EditText etPre;
    private Button btnCanvas;
    public CircleBarView barView;
    private TextView tv_num;

    @Override
    public int bindLayout() {
        return R.layout.test_layout;
    }

    @Override
    public void bindView() {
        super.bindView();
        etPre = (EditText) this.findViewById(R.id.et_pre);
        btnCanvas = (Button) this.findViewById(R.id.btn_canvas);
        tv_num = (TextView) this.findViewById(R.id.tv_num);
        barView = (CircleBarView) this.findViewById(R.id.barView);
        btnCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String time=etPre.getText().toString();
                if (TextUtils.isEmpty(time))return;
                int i=Integer.parseInt(time);
                if (i==0)return;
                barView.setProgressNum(100,i);
            }
        });
        barView.setTextView(tv_num);
        barView.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float progressNum, float maxNum) {
                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                String s = decimalFormat.format(interpolatedTime * progressNum / maxNum * 100) + "%";
                return s;
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float progressNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil(Color.YELLOW,Color.RED);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
                tv_num.setTextColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
    }
}
