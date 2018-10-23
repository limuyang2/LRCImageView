package top.limuyang2.rcimgviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * 自定义圆角图片，四个角圆角弧度可各自定义，也可不定义默认角度为20px.
 * <p>
 * Created by lmy
 * 2018/10/22
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint mPaint;
    private Paint mPaint2;

    private static final int CORNER_RADIUS_DEFAULT = 20;

    private int leftUpCornerRadius;
    private int rightUpCornerRadius;
    private int leftDownCornerRadius;
    private int rightDownCornerRadius;


    public RoundImageView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context) {
        super(context);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        //16种状态
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint2 = new Paint();
        mPaint2.setXfermode(null);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        int cornerRadius = array.getDimensionPixelOffset(R.styleable.RoundImageView_rc_round_corner, 0);
        leftUpCornerRadius = array.getDimensionPixelOffset(R.styleable.RoundImageView_rc_round_leftUp, 0);
        leftDownCornerRadius = array.getDimensionPixelOffset(R.styleable.RoundImageView_rc_round_leftDown, 0);
        rightUpCornerRadius = array.getDimensionPixelOffset(R.styleable.RoundImageView_rc_round_rightUp, 0);
        rightDownCornerRadius = array.getDimensionPixelOffset(R.styleable.RoundImageView_rc_round_rightDown, 0);

        if (cornerRadius == 0 && leftUpCornerRadius == 0 && leftDownCornerRadius == 0 && rightUpCornerRadius == 0 && rightDownCornerRadius == 0) {
            cornerRadius = CORNER_RADIUS_DEFAULT;
        }

        if (cornerRadius > 0) {
            leftUpCornerRadius = leftDownCornerRadius = rightUpCornerRadius = rightDownCornerRadius = cornerRadius;
        }

        array.recycle();
    }


    @Override
    public void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.onDraw(canvas2);
        if (leftUpCornerRadius > 0) {
            drawLeftUp(canvas2);
        }
        if (rightUpCornerRadius > 0) {
            drawRightUp(canvas2);
        }
        if (leftDownCornerRadius > 0) {
            drawLeftDown(canvas2);
        }
        if (rightDownCornerRadius > 0) {
            drawRightDown(canvas2);
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint2);
        bitmap.recycle();
    }

    private void drawLeftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, leftUpCornerRadius);
        path.lineTo(0, 0);
        path.lineTo(leftUpCornerRadius, 0);
        //arcTo的第二个参数是以多少度为开始点，第三个参数-90度表示逆时针画弧，正数表示顺时针
        path.arcTo(new RectF(0, 0, leftUpCornerRadius * 2, leftUpCornerRadius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - leftDownCornerRadius);
        path.lineTo(0, getHeight());
        path.lineTo(leftDownCornerRadius, getHeight());
        path.arcTo(new RectF(0, getHeight() - leftDownCornerRadius * 2, 0 + leftDownCornerRadius * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - rightDownCornerRadius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - rightDownCornerRadius);
        path.arcTo(new RectF(getWidth() - rightDownCornerRadius * 2, getHeight() - rightDownCornerRadius * 2, getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), rightUpCornerRadius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - rightUpCornerRadius, 0);
        path.arcTo(new RectF(getWidth() - rightUpCornerRadius * 2, 0, getWidth(), 0 + rightUpCornerRadius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

}
