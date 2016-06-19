package me.chunsheng.wolf.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import me.chunsheng.wolf.R;
import me.chunsheng.wolf.inputview.EmojiManager;
import me.chunsheng.wolf.inputview.EmoticonView;
import me.chunsheng.wolf.inputview.MoonUtil;
import me.chunsheng.wolf.inputview.SpringUIKit;
import me.chunsheng.wolf.util.StringUtil;


public class ShowEmojiTextAdapter extends BaseAdapter {

    private Context context;

    private List<String> showText;

    public ShowEmojiTextAdapter(Context mContext, List<String> showText) {
        this.context = mContext;
        this.showText = showText;
    }

    public int getCount() {
        Log.e(SpringUIKit.LOGTAG, "EmojiManager.getDisplayCount():" + EmojiManager.getDisplayCount());
        int count = EmojiManager.getDisplayCount();
        Log.e(SpringUIKit.LOGTAG, "count:" + count);
        count = Math.min(count, EmoticonView.EMOJI_PER_PAGE + 1);
        return showText.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.show_emojitext_item, null);

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        Random random = new Random();
        int resID = context.getResources().getIdentifier("level_" + (random.nextInt(120) + 1), "drawable", "me.chunsheng.wolf");
        Drawable image = context.getResources().getDrawable(resID);
        image.setBounds(0, 0, 100, 50);
        tv_name.setCompoundDrawables(image, null, null, null);

        tv_name.setText(" " + getRandomJianHan(2 + random.nextInt(3)) + ": ");

        final EditText emojiThumb = (EditText) convertView.findViewById(R.id.showEmojiText);

        emojiThumb.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
                Log.e(SpringUIKit.LOGTAG, "-97-" + "come in");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(SpringUIKit.LOGTAG, "-98-" + "come in");
                MoonUtil.replaceEmoticons(SpringUIKit.getContext(), s, start, count);
                int editEnd = emojiThumb.getSelectionEnd();
                emojiThumb.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                emojiThumb.setSelection(editEnd);
                emojiThumb.addTextChangedListener(this);

            }
        });

        emojiThumb.setText(showText.get(position));
        emojiThumb.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        return convertView;
    }

    static int getResourceId(Context context, String name, String type, String packageName) {
        Resources themeResources = null;
        PackageManager pm = context.getPackageManager();
        try {
            themeResources = pm.getResourcesForApplication(packageName);
            return themeResources.getIdentifier(name, type, packageName);
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk"); //转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

}