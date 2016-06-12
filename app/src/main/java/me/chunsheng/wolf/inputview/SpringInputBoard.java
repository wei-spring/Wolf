package me.chunsheng.wolf.inputview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.chunsheng.wolf.R;


/**
 * @Time 2016-01-11 by wei_spring
 */

/**
 * 自定义输入框,包括输入表情,以及的其他扩展
 */
public class SpringInputBoard extends RelativeLayout implements View.OnClickListener {

    private ImageView buttonAudioMessage;
    private ImageView buttonTextMessage;
    private FrameLayout audioTextSwitchLayout;
    private Button audioRecord;
    private EditText editTextMessage;
    private ImageView emoji_button;
    private FrameLayout sendLayout;
    private ImageView buttonMoreFuntionInText;
    private TextView buttonSendMessage;

    private Context context;

    public SpringInputBoard(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public SpringInputBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public SpringInputBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View view = View.inflate(context, R.layout.spring_input_board, this);

        findViews(view);

    }

    public void findViews(View view) {
        buttonAudioMessage = (ImageView) view.findViewById(R.id.buttonAudioMessage);
        buttonAudioMessage.setOnClickListener(this);
        buttonTextMessage = (ImageView) view.findViewById(R.id.buttonTextMessage);
        buttonTextMessage.setOnClickListener(this);
        audioTextSwitchLayout = (FrameLayout) view.findViewById(R.id.audioTextSwitchLayout);
        audioTextSwitchLayout.setOnClickListener(this);
        audioRecord = (Button) view.findViewById(R.id.audioRecord);
        audioRecord.setOnClickListener(this);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);
        editTextMessage.setOnClickListener(this);
        emoji_button = (ImageView) view.findViewById(R.id.emoji_button);
        emoji_button.setOnClickListener(this);
        sendLayout = (FrameLayout) view.findViewById(R.id.sendLayout);
        sendLayout.setOnClickListener(this);
        buttonMoreFuntionInText = (ImageView) view.findViewById(R.id.buttonMoreFuntionInText);
        buttonMoreFuntionInText.setOnClickListener(this);
        buttonSendMessage = (TextView) view.findViewById(R.id.buttonSendMessage);
        buttonSendMessage.setOnClickListener(this);
    }

    public interface TextChangeListener {
        void getText(String inputText);
    }

    private TextChangeListener textChangeListener;

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return null;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAudioMessage:
                buttonTextMessage.setVisibility(View.VISIBLE);
                buttonAudioMessage.setVisibility(View.GONE);
                editTextMessage.setVisibility(View.GONE);
                audioRecord.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonTextMessage:
                buttonTextMessage.setVisibility(View.GONE);
                buttonAudioMessage.setVisibility(View.VISIBLE);
                editTextMessage.setVisibility(View.VISIBLE);
                audioRecord.setVisibility(View.GONE);
                break;
            case R.id.audioTextSwitchLayout:
                break;
            case R.id.audioRecord:
                break;
            case R.id.editTextMessage:
                break;
            case R.id.emoji_button:
                break;
            case R.id.sendLayout:
                break;
            case R.id.buttonMoreFuntionInText:
                break;
            case R.id.buttonSendMessage:
                textChangeListener.getText(editTextMessage.getText().toString());
                break;
        }
    }
}
