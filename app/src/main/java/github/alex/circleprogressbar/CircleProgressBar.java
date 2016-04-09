package github.alex.circleprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/4/8.
 */
public class CircleProgressBar extends View {
    private final int maxProgress = 100;
    private float firstProgress;
    private float secondProgress;
    private Paint maxProgressPaint;
    private Paint firstProgressPaint;
    private Paint secondProgressPaint;
    private int maxProgressColor;
    private int firstProgressColor;
    private int secondProgressColor;
    private int width;
    private int height;
    private int maxProgressWidth;
    private int firstProgressWidth;
    private int secondProgressWidth;
    private RectF firstRectF;
    private RectF secondRectF;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        maxProgressWidth = (int) dp2Px(20);
        maxProgressColor = Color.parseColor("#F3F3F3");
        maxProgressPaint = new Paint();
        maxProgressPaint.setAntiAlias(true);
        maxProgressPaint.setStyle(Paint.Style.STROKE);
        maxProgressPaint.setStrokeWidth(maxProgressWidth);
        maxProgressPaint.setColor(maxProgressColor);

        firstProgressWidth = (int) dp2Px(20);
        firstProgressColor = Color.parseColor("#FD8957");
        firstProgressPaint = new Paint();
        firstProgressPaint.setAntiAlias(true);
        firstProgressPaint.setStyle(Paint.Style.STROKE);
        firstProgressPaint.setStrokeWidth(firstProgressWidth);
        firstProgressPaint.setColor(firstProgressColor);

        secondProgressWidth = (int) dp2Px(20);
        secondProgressColor = Color.parseColor("#6AA84F");
        secondProgressPaint = new Paint();
        secondProgressPaint.setAntiAlias(true);
        secondProgressPaint.setStyle(Paint.Style.STROKE);
        secondProgressPaint.setStrokeWidth(secondProgressWidth);
        secondProgressPaint.setColor(secondProgressColor);

        firstRectF = new RectF(0, 0, 0, 0);
        secondRectF = new RectF(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int padding = getPaddingLeft() + getPaddingRight();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.max(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.max(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1.圆心（x,y）坐标值
        float centerX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f;
        float centerY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2.0f;
        // 2.圆环半径
        float radius;
        int progressWidth = Math.max(Math.max(maxProgressWidth, firstProgressWidth), secondProgressWidth);
        if (getWidth() >= getHeight()) {
            radius = (centerY - progressWidth / 2);
        } else {
            radius = (centerX - progressWidth / 2);
        }
        canvas.drawCircle(centerX, centerY, radius , maxProgressPaint);

        firstRectF.left = centerX - radius;
        firstRectF.right = centerX + radius;
        firstRectF.top = centerY - radius;
        firstRectF.bottom = centerY + radius;

        secondRectF.left = centerX - radius;
        secondRectF.right = centerX + radius;
        secondRectF.top = centerY - radius;
        secondRectF.bottom = centerY + radius;

        canvas.drawArc(firstRectF, 0 - 90, (float) 360 * firstProgress / (float) maxProgress, false, firstProgressPaint);
        canvas.drawArc(secondRectF, 0 - 90, ((float) 360 * secondProgress / (float) maxProgress) , false, secondProgressPaint);

    }

    /**
     * 给  maxProgress 设置颜色
     */
    public void setMaxProgressColor(int maxProgressColor) {
        this.maxProgressColor = maxProgressColor;
        maxProgressPaint.setColor(maxProgressColor);
    }

    /**
     * 给  firstProgress 设置颜色
     */
    public void setFirstProgressColor(int firstProgressColor) {
        this.firstProgressColor = firstProgressColor;
        firstProgressPaint.setColor(firstProgressColor);
    }

    public int getFirstProgressColor() {
        return firstProgressColor;
    }

    /**
     * 给  firstProgress 设置颜色
     */
    public void setSecondProgressColor(int secondProgressColor) {
        this.secondProgressColor = secondProgressColor;
        secondProgressPaint.setColor(secondProgressColor);
    }

    public int getSecondProgressColor() {
        return secondProgressColor;
    }

    /**
     * 获取 firstProgress  的进度值
     */
    public float getFirstProgress() {
        return firstProgress;
    }

    /**
     * 给  firstProgress 设置进度， [0,100 ]
     */
    public void setFirstProgress(float firstProgress) {
        if (firstProgress > maxProgress) {
            firstProgress = maxProgress;
        } else if (firstProgress < 0) {
            firstProgress = 0;
        }
        if (secondProgress > firstProgress) {
            secondProgress = firstProgress;
        } else if (secondProgress < 0) {
            secondProgress = 0;
        }
        this.firstProgress = firstProgress;
        invalidate();
    }

    /**
     * 获取 secondProgress  的进度值
     */
    public float getSecondProgress() {
        return secondProgress;
    }

    /**
     * 给  secondProgress 设置进度, [0,100 ]
     */
    public void setSecondProgress(float secondProgress) {
        if (secondProgress > maxProgress) {
            secondProgress = maxProgress;
        } else if (secondProgress < 0) {
            secondProgress = 0;
        }
        if (secondProgress > firstProgress) {
            firstProgress = secondProgress;
        }
        this.secondProgress = secondProgress;
        invalidate();
    }

    public int getMaxProgressWidth() {
        return maxProgressWidth;
    }

    /**
     * 设置 Max 的宽度，单位 dp
     */
    public void setMaxProgressWidth(int maxProgressWidth) {
        this.maxProgressWidth = (int) dp2Px(maxProgressWidth);
        maxProgressPaint.setStrokeWidth(this.maxProgressWidth);
        invalidate();
    }

    public int getFirstProgressWidth() {
        return firstProgressWidth;
    }

    /**
     * 设置 第一个进度条 的宽度，单位 dp
     */
    public void setFirstProgressWidth(int firstProgressWidth) {
        this.firstProgressWidth = (int) dp2Px(firstProgressWidth);
        firstProgressPaint.setStrokeWidth(this.firstProgressWidth);
        invalidate();
    }

    public int getSecondProgressWidth() {
        return secondProgressWidth;
    }

    /**
     * 设置 第二个进度条 的宽度，单位 dp
     */
    public void setSecondProgressWidth(int secondProgressWidth) {
        this.secondProgressWidth = (int) dp2Px(secondProgressWidth);
        secondProgressPaint.setStrokeWidth(this.secondProgressWidth);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2Px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

}
