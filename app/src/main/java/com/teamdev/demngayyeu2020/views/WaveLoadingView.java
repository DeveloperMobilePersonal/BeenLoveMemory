package com.teamdev.demngayyeu2020.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.github.siyamed.shapeimageview.path.SvgUtil;
import com.github.siyamed.shapeimageview.path.parser.PathInfo;
import com.teamdev.demngayyeu2020.R;
import com.teamdev.demngayyeu2020.dialog.wave.SVGModel;
import com.teamdev.demngayyeu2020.ex.ListExKt;
import com.teamdev.demngayyeu2020.ex.Pref;

public class WaveLoadingView extends View {

    public void updateColor(int color) {
        setWaveColor(color);
        setBorderColor(color);
    }

    private static final float DEFAULT_AMPLITUDE_RATIO = 0.1f;
    private static final float DEFAULT_AMPLITUDE_VALUE = 50.0f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;
    private static final int DEFAULT_WAVE_PROGRESS_VALUE = 50;
    private static final int DEFAULT_WAVE_COLOR = Color.parseColor("#212121");
    private static final int DEFAULT_WAVE_BACKGROUND_COLOR = Color.parseColor("#00000000");
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#212121");
    private static final int DEFAULT_STROKE_COLOR = Color.TRANSPARENT;
    private static final float DEFAULT_BORDER_WIDTH = 0;
    private static final float DEFAULT_TITLE_STROKE_WIDTH = 0;
    private static final int DEFAULT_WAVE_SHAPE = ShapeType.CIRCLE.ordinal();
    private static final int DEFAULT_TRIANGLE_DIRECTION = TriangleDirection.NORTH.ordinal();
    private static final int DEFAULT_ROUND_RECTANGLE_X_AND_Y = 30;
    private static final float DEFAULT_TITLE_TOP_SIZE = 18.0f;
    private static final float DEFAULT_TITLE_CENTER_SIZE = 22.0f;
    private static final float DEFAULT_TITLE_BOTTOM_SIZE = 18.0f;
    private PathInfo shapePath;
    private final float[] pathDimensions = new float[2];
    private final Matrix pathMatrix = new Matrix();

    public enum ShapeType {
        CIRCLE
    }

    public enum TriangleDirection {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }


    private int mCanvasSize;
    private int mCanvasHeight;
    private int mCanvasWidth;
    private float mAmplitudeRatio;
    private int mWaveBgColor;
    private int mWaveColor;
    private int mShapeType;
    private int mTriangleDirection;
    private int mRoundRectangleXY;


    private String mTopTitle;
    private String mCenterTitle;
    private String mBottomTitle;
    private float mDefaultWaterLevel;
    private float mWaterLevelRatio = 1f;
    private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;
    private int mProgressValue = DEFAULT_WAVE_PROGRESS_VALUE;
    private boolean mIsRoundRectangle;

    // Object used to draw.
    // Shader containing repeated waves.

    private Path path;

    private Paint paint;

    private BitmapShader mWaveShader;
    private Bitmap bitmapBuffer;
    // Shader matrix.
    private Matrix mShaderMatrix;
    // Paint to draw wave.
    private Paint mWavePaint;
    //Paint to draw waveBackground.
    private Paint mWaveBgPaint;
    // Paint to draw border.
    private Paint mBorderPaint;
    // Point to draw title.
    private Paint mTopTitlePaint;
    private Paint mBottomTitlePaint;
    private Paint mCenterTitlePaint;

    private Paint mTopTitleStrokePaint;
    private Paint mBottomTitleStrokePaint;
    private Paint mCenterTitleStrokePaint;

    // Animation.
    private ObjectAnimator waveShiftAnim;
    private AnimatorSet mAnimatorSet;

    private Context mContext;

    // Constructor & Init Method.
    public WaveLoadingView(final Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        // Init Wave.
        mShaderMatrix = new Matrix();
        mWavePaint = new Paint();
        // The ANTI_ALIAS_FLAG bit AntiAliasing smooths out the edges of what is being drawn,
        // but is has no impact on the interior of the shape.
        mWavePaint.setAntiAlias(true);
        mWaveBgPaint = new Paint();
        mWaveBgPaint.setAntiAlias(true);
        // Init Animation
        initAnimation();

        // Load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WaveLoadingView, defStyleAttr, 0);

        // Init ShapeType
        mShapeType = attributes.getInteger(R.styleable.WaveLoadingView_wlv_shapeType, DEFAULT_WAVE_SHAPE);

        // Init Wave
        mWaveColor = attributes.getColor(R.styleable.WaveLoadingView_wlv_waveColor, DEFAULT_WAVE_COLOR);
        mWaveBgColor = attributes.getColor(R.styleable.WaveLoadingView_wlv_wave_background_Color, DEFAULT_WAVE_BACKGROUND_COLOR);

        mWaveBgPaint.setColor(mWaveBgColor);

        // Init AmplitudeRatio
        float amplitudeRatioAttr = attributes.getFloat(R.styleable.WaveLoadingView_wlv_waveAmplitude, DEFAULT_AMPLITUDE_VALUE) / 1000;
        mAmplitudeRatio = Math.min(amplitudeRatioAttr, DEFAULT_AMPLITUDE_RATIO);

        // Init Progress
        mProgressValue = attributes.getInteger(R.styleable.WaveLoadingView_wlv_progressValue, DEFAULT_WAVE_PROGRESS_VALUE);
        setProgressValue(mProgressValue);

        // Init RoundRectangle
        mIsRoundRectangle = attributes.getBoolean(R.styleable.WaveLoadingView_wlv_round_rectangle, false);
        mRoundRectangleXY = attributes.getInteger(R.styleable.WaveLoadingView_wlv_round_rectangle_x_and_y, DEFAULT_ROUND_RECTANGLE_X_AND_Y);

        // Init Triangle direction
        mTriangleDirection = attributes.getInteger(R.styleable.WaveLoadingView_wlv_triangle_direction, DEFAULT_TRIANGLE_DIRECTION);

        // Init Border
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(attributes.getDimension(R.styleable.WaveLoadingView_wlv_borderWidth, dp2px(DEFAULT_BORDER_WIDTH)));
        mBorderPaint.setColor(Pref.INSTANCE.getWaveColor());

        // Init Top Title
        mTopTitlePaint = new Paint();
        mTopTitlePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleTopColor, DEFAULT_TITLE_COLOR));
        mTopTitlePaint.setStyle(Paint.Style.FILL);
        mTopTitlePaint.setAntiAlias(true);
        mTopTitlePaint.setTextSize(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleTopSize, sp2px(DEFAULT_TITLE_TOP_SIZE)));

        mTopTitleStrokePaint = new Paint();
        mTopTitleStrokePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleTopStrokeColor, DEFAULT_STROKE_COLOR));
        mTopTitleStrokePaint.setStrokeWidth(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleTopStrokeWidth, dp2px(DEFAULT_TITLE_STROKE_WIDTH)));
        mTopTitleStrokePaint.setStyle(Paint.Style.STROKE);
        mTopTitleStrokePaint.setAntiAlias(true);
        mTopTitleStrokePaint.setTextSize(mTopTitlePaint.getTextSize());

        mTopTitle = attributes.getString(R.styleable.WaveLoadingView_wlv_titleTop);

        // Init Center Title
        mCenterTitlePaint = new Paint();
        mCenterTitlePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleCenterColor, DEFAULT_TITLE_COLOR));
        mCenterTitlePaint.setStyle(Paint.Style.FILL);
        mCenterTitlePaint.setAntiAlias(true);
        mCenterTitlePaint.setTextSize(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleCenterSize, sp2px(DEFAULT_TITLE_CENTER_SIZE)));

        //setFont


        mCenterTitleStrokePaint = new Paint();
        mCenterTitleStrokePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleCenterStrokeColor, DEFAULT_STROKE_COLOR));
        mCenterTitleStrokePaint.setStrokeWidth(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleCenterStrokeWidth, dp2px(DEFAULT_TITLE_STROKE_WIDTH)));
        mCenterTitleStrokePaint.setStyle(Paint.Style.STROKE);
        mCenterTitleStrokePaint.setAntiAlias(true);
        mCenterTitleStrokePaint.setTextSize(mCenterTitlePaint.getTextSize());

        //setFont

        mCenterTitle = attributes.getString(R.styleable.WaveLoadingView_wlv_titleCenter);

        // Init Bottom Title
        mBottomTitlePaint = new Paint();
        mBottomTitlePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleBottomColor, DEFAULT_TITLE_COLOR));
        mBottomTitlePaint.setStyle(Paint.Style.FILL);
        mBottomTitlePaint.setAntiAlias(true);
        mBottomTitlePaint.setTextSize(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleBottomSize, sp2px(DEFAULT_TITLE_BOTTOM_SIZE)));

        mBottomTitleStrokePaint = new Paint();
        mBottomTitleStrokePaint.setColor(attributes.getColor(R.styleable.WaveLoadingView_wlv_titleBottomStrokeColor, DEFAULT_STROKE_COLOR));
        mBottomTitleStrokePaint.setStrokeWidth(attributes.getDimension(R.styleable.WaveLoadingView_wlv_titleBottomStrokeWidth, dp2px(DEFAULT_TITLE_STROKE_WIDTH)));
        mBottomTitleStrokePaint.setStyle(Paint.Style.STROKE);
        mBottomTitleStrokePaint.setAntiAlias(true);
        mBottomTitleStrokePaint.setTextSize(mBottomTitlePaint.getTextSize());

        mBottomTitle = attributes.getString(R.styleable.WaveLoadingView_wlv_titleBottom);

        attributes.recycle();
    }

    @SuppressLint({"DrawAllocation", "CanvasSize"})
    @Override
    public void onDraw(Canvas canvas) {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCanvasSize = canvas.getWidth();
        if (canvas.getHeight() < mCanvasSize) {
            mCanvasSize = canvas.getHeight();
        }
        if (mWaveShader != null) {
            // First call after mShowWave, assign it to our paint.
            if (mWavePaint.getShader() == null) {
                mWavePaint.setShader(mWaveShader);
            }

            // Sacle shader according to waveLengthRatio and amplitudeRatio.
            // This decides the size(waveLengthRatio for width, amplitudeRatio for height) of waves.
            mShaderMatrix.setScale(1, mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO, 0, mDefaultWaterLevel);
            // Translate shader according to waveShiftRatio and waterLevelRatio.
            // This decides the start position(waveShiftRatio for x, waterLevelRatio for y) of waves.
            mShaderMatrix.postTranslate(mWaveShiftRatio * getWidth(),
                    (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight());

            // Assign matrix to invalidate the shader.
            mWaveShader.setLocalMatrix(mShaderMatrix);

            // Get borderWidth.
            float borderWidth = mBorderPaint.getStrokeWidth();
            SVGModel waveOrDefault = ListExKt.getWaveOrDefault();
            shapePath = readSvg(waveOrDefault.getRawSvg());

            if (shapePath == null) {
                if (borderWidth > 0) {
                    canvas.drawCircle(getWidth() / 2f, getHeight() / 2f,
                            (getWidth() - borderWidth) / 2f - 1f, mBorderPaint);
                }

                float radius = getWidth() / 2f - borderWidth;
                // Draw background
                canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mWaveBgPaint);
                canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mWavePaint);
                return;
            }
            pathDimensions[0] = shapePath.getWidth();
            pathDimensions[1] = shapePath.getHeight();
            pathMatrix.reset();
            path.reset();
            float width = getWidth();
            float height = getHeight();
            float scale = Math.min((width / pathDimensions[0]) - 0.1f, height / pathDimensions[1]);
            float translateX = Math.round((width - pathDimensions[0] * scale) * 0.5f);
            float translateY = Math.round((height - pathDimensions[1] * scale) * 0.5f);
            pathMatrix.setScale(scale, scale);
            pathMatrix.postTranslate(translateX, translateY);
            shapePath.transform(pathMatrix, path);
            if (borderWidth > 0) {
                canvas.drawPath(path, mBorderPaint);
            }
            canvas.drawPath(path, mWaveBgPaint);
            canvas.drawPath(path, mWavePaint);

        } else {
            mWavePaint.setShader(null);
        }
    }

    private PathInfo readSvg(int raw) {
        return SvgUtil.readSvg(mContext, raw);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getShapeType() == 3) {
            mCanvasWidth = w;
            mCanvasHeight = h;
        } else {
            mCanvasSize = w;
            if (h < mCanvasSize)
                mCanvasSize = h;
        }
        updateWaveShader();
    }

    private void updateWaveShader() {
        mWaveColor = Pref.INSTANCE.getWaveColor();
        if (bitmapBuffer == null || haveBoundsChanged()) {
            if (bitmapBuffer != null)
                bitmapBuffer.recycle();
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0 && height > 0) {
                double defaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / width;
                float defaultAmplitude = height * DEFAULT_AMPLITUDE_RATIO;
                mDefaultWaterLevel = height * DEFAULT_WATER_LEVEL_RATIO;

                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                Paint wavePaint = new Paint();
                wavePaint.setStrokeWidth(2);
                wavePaint.setAntiAlias(true);

                // Draw default waves into the bitmap.
                // y=Asin(ωx+φ)+h
                final int endX = width + 1;
                final int endY = height + 1;

                float[] waveY = new float[endX];

                wavePaint.setColor(adjustAlpha(mWaveColor, 0.3f));
                for (int beginX = 0; beginX < endX; beginX++) {
                    double wx = beginX * defaultAngularFrequency;
                    float beginY = (float) (mDefaultWaterLevel + defaultAmplitude * Math.sin(wx));
                    canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);
                    waveY[beginX] = beginY;
                }

                wavePaint.setColor(mWaveColor);
                final int wave2Shift = (int) ((float) width / 4);
                for (int beginX = 0; beginX < endX; beginX++) {
                    canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
                }

                // Use the bitamp to create the shader.
                mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
                this.mWavePaint.setShader(mWaveShader);
            }
        }
    }

    private boolean haveBoundsChanged() {
        return getMeasuredWidth() != bitmapBuffer.getWidth() ||
                getMeasuredHeight() != bitmapBuffer.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        // If shapType is rectangle
        if (getShapeType() == 3) {
            setMeasuredDimension(width, height);
        } else {
            int imageSize = Math.min(width, height);
            setMeasuredDimension(imageSize, imageSize);
        }

    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // The parent has not imposed any constraint on the child.
            result = mCanvasWidth;
        }
        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number).
            result = mCanvasHeight;
        }
        return (result + 2);
    }


    public void setWaveBgColor(int color) {
        this.mWaveBgColor = color;
        mWaveBgPaint.setColor(this.mWaveBgColor);
        updateWaveShader();
        invalidate();
    }

    public int getWaveBgColor() {
        return mWaveBgColor;
    }

    public void setWaveColor(int color) {
        mWaveColor = color;
        // Need to recreate shader when color changed ?
//        mWaveShader = null;
        updateWaveShader();
        invalidate();
    }

    public int getWaveColor() {
        return mWaveColor;
    }

    public void setBorderWidth(float width) {
        mBorderPaint.setStrokeWidth(width);
        invalidate();
    }

    public float getBorderWidth() {
        return mBorderPaint.getStrokeWidth();
    }

    public void setBorderColor(int color) {
        mBorderPaint.setColor(color);
        updateWaveShader();
        invalidate();
    }

    public void updateAll() {
        int waveColor = Pref.INSTANCE.getWaveColor();
        setWaveColor(waveColor);
        setBorderColor(waveColor);
        updateWaveShader();
        invalidate();
    }

    public int getBorderColor() {
        return mBorderPaint.getColor();
    }

    public void setShapeType(ShapeType shapeType) {
        mShapeType = shapeType.ordinal();
        invalidate();
    }

    public int getShapeType() {
        return mShapeType;
    }

    /**
     * Set vertical size of wave according to amplitudeRatio.
     *
     * @param amplitudeRatio Default to be 0.05. Result of amplitudeRatio + waterLevelRatio should be less than 1.
     */
    public void setAmplitudeRatio(int amplitudeRatio) {
        if (this.mAmplitudeRatio != (float) amplitudeRatio / 1000) {
            this.mAmplitudeRatio = (float) amplitudeRatio / 1000;
            invalidate();
        }
    }

    public float getAmplitudeRatio() {
        return mAmplitudeRatio;
    }

    /**
     * Water level increases from 0 to the value of WaveView.
     *
     * @param progress Default to be 50.
     */
    public void setProgressValue(int progress) {
        mProgressValue = progress;
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(this, "waterLevelRatio", mWaterLevelRatio, ((float) mProgressValue / 100));
        waterLevelAnim.setDuration(1000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animatorSetProgress = new AnimatorSet();
        animatorSetProgress.play(waterLevelAnim);
        animatorSetProgress.start();
    }

    public int getProgressValue() {
        return mProgressValue;
    }

    public void setWaveShiftRatio(float waveShiftRatio) {
        if (this.mWaveShiftRatio != waveShiftRatio) {
            this.mWaveShiftRatio = waveShiftRatio;
            invalidate();
        }
    }

    public float getWaveShiftRatio() {
        return mWaveShiftRatio;
    }

    public void setWaterLevelRatio(float waterLevelRatio) {
        if (this.mWaterLevelRatio != waterLevelRatio) {
            this.mWaterLevelRatio = waterLevelRatio;
            invalidate();
        }
    }

    public float getWaterLevelRatio() {
        return mWaterLevelRatio;
    }

    /**
     * Set the title within the WaveView.
     *
     * @param topTitle Default to be null.
     */
    public void setTopTitle(String topTitle) {
        mTopTitle = topTitle;
    }

    public String getTopTitle() {
        return mTopTitle;
    }

    public void setCenterTitle(String centerTitle) {
        mCenterTitle = centerTitle;
    }

    public String getCenterTitle() {
        return mCenterTitle;
    }

    public void setBottomTitle(String bottomTitle) {
        mBottomTitle = bottomTitle;
    }

    public String getBottomTitle() {
        return mBottomTitle;
    }

    public void setTopTitleColor(int topTitleColor) {
        mTopTitlePaint.setColor(topTitleColor);
    }

    public int getTopTitleColor() {
        return mTopTitlePaint.getColor();
    }

    public void setCenterTitleColor(int centerTitleColor) {
        mCenterTitlePaint.setColor(centerTitleColor);
    }

    public int getCenterTitleColor() {
        return mCenterTitlePaint.getColor();
    }

    public void setBottomTitleColor(int bottomTitleColor) {
        mBottomTitlePaint.setColor(bottomTitleColor);
    }

    public int getBottomTitleColor() {
        return mBottomTitlePaint.getColor();
    }

    public void setTopTitleSize(float topTitleSize) {
        mTopTitlePaint.setTextSize(sp2px(topTitleSize));
    }

    public float getsetTopTitleSize() {
        return mTopTitlePaint.getTextSize();
    }

    public void setCenterTitleSize(float centerTitleSize) {
        mCenterTitlePaint.setTextSize(sp2px(centerTitleSize));
    }

    public float getCenterTitleSize() {
        return mCenterTitlePaint.getTextSize();
    }

    public void setBottomTitleSize(float bottomTitleSize) {
        mBottomTitlePaint.setTextSize(sp2px(bottomTitleSize));
    }

    public float getBottomTitleSize() {
        return mBottomTitlePaint.getTextSize();
    }

    public void setTopTitleStrokeWidth(float topTitleStrokeWidth) {
        mTopTitleStrokePaint.setStrokeWidth(dp2px(topTitleStrokeWidth));
    }

    public void setTopTitleStrokeColor(int topTitleStrokeColor) {
        mTopTitleStrokePaint.setColor(topTitleStrokeColor);
    }

    public void setBottomTitleStrokeWidth(float bottomTitleStrokeWidth) {
        mBottomTitleStrokePaint.setStrokeWidth(dp2px(bottomTitleStrokeWidth));
    }

    public void setBottomTitleStrokeColor(int bottomTitleStrokeColor) {
        mBottomTitleStrokePaint.setColor(bottomTitleStrokeColor);
    }

    public void setCenterTitleStrokeWidth(float centerTitleStrokeWidth) {
        mCenterTitleStrokePaint.setStrokeWidth(dp2px(centerTitleStrokeWidth));
    }

    public void setCenterTitleStrokeColor(int centerTitleStrokeColor) {
        mCenterTitleStrokePaint.setColor(centerTitleStrokeColor);
    }

    public void startAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }
    }

    public void endAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
        }
    }

    public void cancelAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    public void pauseAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mAnimatorSet != null) {
                mAnimatorSet.pause();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    public void resumeAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mAnimatorSet != null) {
                mAnimatorSet.resume();
            }
        }
    }

    /**
     * Sets the length of the animation. The default duration is 1000 milliseconds.
     *
     * @param duration The length of the animation, in milliseconds.
     */
    public void setAnimDuration(long duration) {
        waveShiftAnim.setDuration(duration);
    }

    private void initAnimation() {
        // Wave waves infinitely.
        waveShiftAnim = ObjectAnimator.ofFloat(this, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(900);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(waveShiftAnim);
    }

    @Override
    protected void onAttachedToWindow() {
        startAnimation();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAnimation();
        super.onDetachedFromWindow();
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Paint.setTextSize(float textSize) default unit is px.
     *
     * @param spValue The real size of text
     * @return int - A transplanted sp
     */
    private int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Draw EquilateralTriangle
     *
     * @param p1        Start point
     * @param width     The width of triangle
     * @param height    The height of triangle
     * @param direction The direction of triangle
     * @return Path
     */
    private Path getEquilateralTriangle(Point p1, int width, int height, int direction) {
        Point p2 = null, p3 = null;
        // NORTH
        if (direction == 0) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), (int) (height - Math.sqrt(3.0) / 2 * height));
        }
        // SOUTH
        else if (direction == 1) {
            p2 = new Point(p1.x, p1.y - height);
            p3 = new Point(p1.x + width, p1.y - height);
            p1.x = p1.x + (width / 2);
            p1.y = (int) (Math.sqrt(3.0) / 2 * height);
        }
        // EAST
        else if (direction == 2) {
            p2 = new Point(p1.x, p1.y - height);
            p3 = new Point((int) (Math.sqrt(3.0) / 2 * width), p1.y / 2);
        }
        // WEST
        else if (direction == 3) {
            p2 = new Point(p1.x + width, p1.y - height);
            p3 = new Point(p1.x + width, p1.y);
            p1.x = (int) (width - Math.sqrt(3.0) / 2 * width);
            p1.y = p1.y / 2;
        }

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        if (p2 != null) {
            path.lineTo(p2.x, p2.y);
        }
        if (p3 != null) {
            path.lineTo(p3.x, p3.y);
        }

        return path;
    }
}