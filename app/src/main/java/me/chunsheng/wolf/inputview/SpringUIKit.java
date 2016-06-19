package me.chunsheng.wolf.inputview;

import android.content.Context;

/**
 * Created by wei_spring on 16/6/13.
 */

public final class SpringUIKit {

    // context
    private static Context context;

    public static final String LOGTAG = "Log_Tag_Spring";


    /**
     * 初始化SpringUIKit，须传入context以及用户信息提供者
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        SpringUIKit.context = context.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
