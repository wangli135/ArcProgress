package xingfeng.library;

import android.content.res.Resources;

/**
 * Created by Xingfeng on 2016/1/20.
 */
public class Utils {

    public static float dp2px(Resources resources,float dp){
        float scale=resources.getDisplayMetrics().density;
        return dp*scale;
    }

    public static float sp2px(Resources resources,float sp){
        float scale=resources.getDisplayMetrics().scaledDensity;
        return sp*scale;
    }

}
