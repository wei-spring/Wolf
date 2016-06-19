package me.chunsheng.wolf.inputview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import me.chunsheng.wolf.R;


public class EmojiAdapter extends BaseAdapter {

    private Context context;

    private int startIndex;

    public EmojiAdapter(Context mContext, int startIndex) {
        this.context = mContext;
        this.startIndex = startIndex;
    }

    public int getCount() {
        Log.e(SpringUIKit.LOGTAG, "EmojiManager.getDisplayCount():" + EmojiManager.getDisplayCount());
        int count = EmojiManager.getDisplayCount() - startIndex + 1;
        Log.e(SpringUIKit.LOGTAG, "count:" + count);
        count = Math.min(count, EmoticonView.EMOJI_PER_PAGE + 1);
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return startIndex + position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.nim_emoji_item, null);
        ImageView emojiThumb = (ImageView) convertView.findViewById(R.id.imgEmoji);
        int count = EmojiManager.getDisplayCount();
        int index = startIndex + position;
        if (position == EmoticonView.EMOJI_PER_PAGE || index == count) {
            emojiThumb.setBackgroundResource(R.drawable.nim_emoji_del);
        } else if (index < count) {
            emojiThumb.setBackgroundDrawable(EmojiManager.getDisplayDrawable(context, index));
        }
        return convertView;
    }

}