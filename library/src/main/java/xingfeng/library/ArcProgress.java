package xingfeng.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Xingfeng on 2016/1/28.
 */
public class ArcProgress extends View {

    private int arcColor;
    private float strokeWidth;
    private int progressTextColor;
    private float progressTextSize;
    private int bottomTextColor;
    private float bottomTextSize;
    private String bottomText;

    private Paint arcPaint;
    private Paint textPaint;

    private int progress;//

    private static final String DEFAULT_COLOR_STR="#FF0000";
    private static final float DEFAULT_STROKE_WIDTH_F=3.0f;
    private static final String DEAULT_TEXT_COLOR_STR="#000000";
    private static final float DEFAULT_TEXT_SIZE_F=10.0f;
    private static final float DEFAULT_INSIDE_STORKE_WIDTH_F=1.5f;

    private static int DEFAULT_COLOR;
    private static float DEFAULT_STROKE_WIDTH;
    private static int DEFAULT_TEXT_COLOR;
    private static float DEFAULT_TEXT_SIZE;
    private static float DEFAULT_INSIDE_STORKE_WIDTH;

    private static final int MINIMUM_HEIGHT=80;
    private static final int MINIMUM_WIDTH=80;

    private static float START_ANGLE=120;
    private static float TOTAL_ANGLE=300;

    private static final int MAX=100;

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefaultValues(context);

        initAttrs(context, attrs);

        initPaints();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initDefaultValues(context);

        initAttrs(context,attrs);

        initPaints();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=getSuggestedMinimumWidth();
        }

        if(heightMode==MeasureSpec.AT_MOST){
            heightSize=getSuggestedMinimumHeight();
        }

        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width=getWidth();
        int height=getHeight();
        int size=Math.min(width, height);

        //Consider width>size and height>size case
        float left=size*0.2f+(width-size);
        float top=size*0.2f+(height-size);
        float right=size*0.8f-(width-size);
        float bottom=size*0.8f-(height-size);

        arcPaint.setStrokeWidth(DEFAULT_INSIDE_STORKE_WIDTH);

        canvas.drawArc(new RectF(left, top, right, bottom), START_ANGLE, TOTAL_ANGLE, false, arcPaint);

        left-=DEFAULT_INSIDE_STORKE_WIDTH;
        top-=DEFAULT_INSIDE_STORKE_WIDTH;
        right+=DEFAULT_INSIDE_STORKE_WIDTH;
        bottom+=DEFAULT_INSIDE_STORKE_WIDTH;

        arcPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        canvas.drawArc(new RectF(left, top, right, bottom), START_ANGLE, progress * 3, false, arcPaint);

        //Draw progress text

        textPaint.setTextSize(progressTextSize);
        textPaint.setColor(progressTextColor);

        float textHeight=textPaint.ascent()+textPaint.descent();
        String progressText=progress+"%";
        float textWidth=textPaint.measureText(progressText);
        canvas.drawText(progressText,(width-textWidth)/2,(height-textHeight)/2,textPaint);

        if(!TextUtils.isEmpty(bottomText)){

            textPaint.setTextSize(bottomTextSize);
            textPaint.setColor(bottomTextColor);
            textHeight=textPaint.ascent()+textPaint.descent();
            textWidth=textPaint.measureText(bottomText);
            canvas.drawText(bottomText,(width-textWidth)/2,height/2+size/2*0.8f+textHeight/2,textPaint);
        }

    }

    @Override
    public void invalidate() {
        initPaints();
        super.invalidate();
    }

    /**
     * Set bottom text
     * @param text
     */
    public void setBottomText(String text){
        bottomText=text;
        invalidate();
    }

    /**
     * Get bottom text
     * @return bottom text
     */
    public String getBottomText(){
        return bottomText;
    }

    /**
     * Set bottom text color
     * @param color
     */
    public void setBottomTextColor(int color){
        this.bottomTextColor =color;
        invalidate();
    }

    /**
     * Get bottom text color
     * @return
     */
    public int getBottomTextColor(){
        return bottomTextColor;
    }

    /**
     * Set bottom text size
     * @param bottomTextSize
     */
    public void setBottomTextSize(float bottomTextSize){
        this.bottomTextSize=bottomTextSize;
        invalidate();
    }

    /**
     * Get bottom text size
     * @return
     */
    public float getBottomTextSize(){
        return bottomTextSize;
    }

    /**
     * Set arc progress
     * @param progress
     */
    public void setProgress(int progress){
        if(this.progress==progress){
            return;
        }

        if(progress>MAX){
            progress=MAX;
        }
        this.progress=progress;
        invalidate();
    }

    /**
     * Get arc progress
     * @return
     */
    public int getProgress(){
        return progress;
    }

    /**
     * Set arc color
     * @param color
     */
    public void setArcColor(int color){
        this.arcColor=color;
        invalidate();
    }

    /**
     * Get arc color
     * @return
     */
    public int getArcColor(){
        return arcColor;
    }

    /**
     * Set progress text color
     * @param progressTextColor
     */
    public void setProgressTextColor(int progressTextColor){
        this.progressTextColor=progressTextColor;
        invalidate();
    }

    /**
     * Get progress text color
     * @return
     */
    public int getProgressTextColor(){
        return progressTextColor;
    }

    /**
     * Set progress text size
     * @param progressTextSize
     */
    public void setProgressTextSize(float progressTextSize){
        this.progressTextSize=progressTextSize;
        invalidate();
    }

    /**
     * Get progress text size
     * @return
     */
    public float getProgressTextSize(){
        return progressTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) Utils.dp2px(getResources(),MINIMUM_HEIGHT);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int)Utils.dp2px(getResources(),MINIMUM_WIDTH);
    }

    private void initDefaultValues(Context context){

        DEFAULT_COLOR= Color.parseColor(DEFAULT_COLOR_STR);
        DEFAULT_STROKE_WIDTH= Utils.dp2px(context.getResources(), DEFAULT_STROKE_WIDTH_F);
        DEFAULT_TEXT_COLOR=Color.parseColor(DEAULT_TEXT_COLOR_STR);
        DEFAULT_TEXT_SIZE=Utils.sp2px(context.getResources(), DEFAULT_TEXT_SIZE_F);

        DEFAULT_INSIDE_STORKE_WIDTH= Utils.dp2px(getResources(),DEFAULT_INSIDE_STORKE_WIDTH_F);

    }

    private void initAttrs(Context context,AttributeSet attrs){

        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ArcProgress);

        arcColor=ta.getColor(R.styleable.ArcProgress_arc_color,DEFAULT_COLOR);
        strokeWidth=ta.getDimension(R.styleable.ArcProgress_arc_stroke_width, DEFAULT_STROKE_WIDTH);
        progressTextColor=ta.getColor(R.styleable.ArcProgress_progress_text_color, DEFAULT_COLOR);
        progressTextSize=ta.getDimension(R.styleable.ArcProgress_progress_text_size,DEFAULT_TEXT_SIZE);
        bottomText=ta.getString(R.styleable.ArcProgress_arc_bottom_text);
        bottomTextColor =ta.getColor(R.styleable.ArcProgress_arc_bottom_text_color, DEFAULT_TEXT_COLOR);
        bottomTextSize=ta.getDimension(R.styleable.ArcProgress_arc_bottom_text_size, DEFAULT_TEXT_SIZE);
        progress=ta.getInt(R.styleable.ArcProgress_arc_progress, 0);

        ta.recycle();

    }

    private void initPaints(){

        arcPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(arcColor);
        arcPaint.setStyle(Paint.Style.STROKE);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    static class SavedState extends BaseSavedState{

        int progress;

        public SavedState(Parcel source) {
            super(source);
            progress=source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

    @Override
    protected Parcelable onSaveInstanceState() {
       Parcelable superState= super.onSaveInstanceState();
       SavedState ss=new SavedState(superState);
        ss.progress=progress;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss=(SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        setProgress(ss.progress);
    }
}
