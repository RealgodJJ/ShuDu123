package com.example.realgodjj.shudu;

//import android.app.AlertDialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

//import android.view.LayoutInflater;
//import android.widget.TextView;

public class ShuDuView extends View {
    private float width;
    private float height;
    private Paint backgroundPaint, hilitePaint, darkPaint, lightPaint, numberPaint;
    private float x, y;
    private int selectedX, selectedY;
    private Game game = new Game();
    private KeyDialog keyDialog;

    public ShuDuView(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        //计算单元格的高度和宽度
        this.width = w / 9f;
        this.height = width;
        super.onSizeChanged(w, h, old_w, old_h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setPaint();
        backgroundPaint.setColor(getResources().getColor(R.color.shudu_background, null));
        hilitePaint.setColor(getResources().getColor(R.color.shudu_line, null));
        darkPaint.setColor(getResources().getColor(R.color.shudu_dark, null));
        lightPaint.setColor(getResources().getColor(R.color.shudu_light, null));
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        //绘画九宫格
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        //绘画浅线
        for (int i = 0; i < 10; i++) {
            //绘画横向的分割线
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            //绘画纵向的分割线
            canvas.drawLine(i * width, 0, i * width, getWidth(), lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getWidth(), hilitePaint);
        }

        //绘画深线
        for (int i = 0; i <= 9; i++) {
            if (i % 3 != 0) {
                continue;
            }
            //绘画横向的深色分割线
            darkPaint.setStrokeWidth(3);
            canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
//            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            //绘画纵向的深色分割线
            canvas.drawLine(i * width, 0, i * width, getWidth(), darkPaint);
//            canvas.drawLine(i * width + 1, 0, i * width + 1, getWidth(), hilitePaint);
//            System.out.println("111111111111111111111111111111111111111111111111111111111");
        }

        //绘制文字
//        x = width / 2;
//        y = width / 4;
        //绘制数字水平和垂直方向居中
        Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
        x = width / 2;
        y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;
//        canvas.drawText("1", 3 * width + x, y, numberPaint);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(game.getTileString(i, j), i * width + x, j * height + y, numberPaint);
            }
        }
        super.onDraw(canvas);
    }

    //设置画笔
    private void setPaint() {
        backgroundPaint = new Paint();
        hilitePaint = new Paint();
        darkPaint = new Paint();
        lightPaint = new Paint();
        numberPaint = new Paint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(motionEvent);
        }
        selectedX = (int) (motionEvent.getX() / width);
        selectedY = (int) (motionEvent.getY() / height);
        int used[] = game.getUsedTilesByCoor(selectedX, selectedY);

//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < used.length; i++) {
//            System.out.println(used[i]);
//            stringBuffer.append(used[i]);
//        }
//        System.out.println(stringBuffer.toString());
        //自定义对话框并显示
//        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
//        View view = layoutInflater.inflate(R.layout.dialog, null);
//
//        TextView textView = (TextView) view.findViewById(R.id.usedTextfield);
//        textView.setText(stringBuffer.toString());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setView(view);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
        keyDialog = new KeyDialog(getContext(), used, this);
        keyDialog.show();
        return true;
    }

    public void setSelectedTile(int tile) {
        if (game.setTileIfValid(selectedX, selectedY, tile)) {
            invalidate();
        }
    }
}
