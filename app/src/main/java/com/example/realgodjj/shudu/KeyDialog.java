package com.example.realgodjj.shudu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//该类用于实现Dialog，实现自定义的对话框功能
public class KeyDialog extends Dialog {
    //用来存放对话框中按钮的对象
    private final Button buttons[] = new Button[9];
    private final int used[];
    private ShuDuView shuDuView;

    public KeyDialog(Context context, int[] used, ShuDuView shuDuView) {
        super(context);
        this.used = used;
        this.shuDuView = shuDuView;
    }

    //当一个Dialog第一次显示的时候，会使用onCreate的方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.no_used_number);
        setContentView(R.layout.dialog_key);
        findViews();
        //为对话框中所有按钮设置监听器
        setListeners();
    }

    private void findViews() {
        //View类型的Button不需要转换形式，可以写成buttons[0] = findViewById(R.id.first_button);
        buttons[0] = (Button) findViewById(R.id.first_button);
        buttons[1] = (Button) findViewById(R.id.second_button);
        buttons[2] = (Button) findViewById(R.id.third_button);
        buttons[3] = (Button) findViewById(R.id.fourth_button);
        buttons[4] = (Button) findViewById(R.id.fifth_button);
        buttons[5] = (Button) findViewById(R.id.sixth_button);
        buttons[6] = (Button) findViewById(R.id.seventh_button);
        buttons[7] = (Button) findViewById(R.id.eighth_button);
        buttons[8] = (Button) findViewById(R.id.ninth_button);

        for (int i = 0; i < used.length; i++) {
            if (used[i] != 0) {
                buttons[used[i] - 1].setEnabled(false);
            }
        }
        for (int i = 0; i < 9; i++) {
            buttons[i].setText(String.valueOf(i + 1));
        }
    }

    private void setListeners() {
        for (int i = 0; i < buttons.length; i++) {
            final int t = i + 1;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnResult(t);
                }
            });
        }
    }

    //通知ShuDuView对象，刷新整个九宫格显示的数据
    private void returnResult(int tile) {
        shuDuView.setSelectedTile(tile);
        dismiss();
    }
}
