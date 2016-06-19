package me.chunsheng.wolf.inputview;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.chunsheng.wolf.R;
import me.chunsheng.wolf.util.StringUtil;


/**
 * @Time 2016-01-11 by wei_spring
 */

/**
 * 自定义输入框,包括输入表情,以及的其他扩展
 */
public class SpringInputBoard extends RelativeLayout implements View.OnClickListener, IEmoticonSelectedListener {

    private ImageView buttonAudioMessage;
    private ImageView buttonTextMessage;
    private FrameLayout audioTextSwitchLayout;
    private Button audioRecord;
    private EditText editTextMessage;
    private ImageView emoji_button;
    private FrameLayout sendLayout;
    private ImageView buttonMoreFuntionInText;
    private TextView buttonSendMessage;
    private me.chunsheng.wolf.inputview.EmoticonPickerView emoticon_picker_view;

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
        emoticon_picker_view = (me.chunsheng.wolf.inputview.EmoticonPickerView) view.findViewById(R.id.emoticon_picker_view);
        initTextEdit();
    }

    private void initTextEdit() {
        editTextMessage.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editTextMessage.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    switchToTextLayout(true);
                }
                return false;
            }
        });

        editTextMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextMessage.setHint("");
                checkSendButtonEnable(editTextMessage);
            }
        });

        editTextMessage.addTextChangedListener(new TextWatcher() {
            private int start;
            private int count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.count = count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkSendButtonEnable(editTextMessage);
                MoonUtil.replaceEmoticons(SpringUIKit.getContext(), s, start, count);
                int editEnd = editTextMessage.getSelectionEnd();
                editTextMessage.removeTextChangedListener(this);
                while (StringUtil.counterChars(s.toString()) > 5000 && editEnd > 0) {
                    s.delete(editEnd - 1, editEnd);
                    editEnd--;
                }
                editTextMessage.setSelection(editEnd);
                editTextMessage.addTextChangedListener(this);

                //sendTypingCommand();
            }
        });
    }

    // 点击edittext，切换键盘和更多布局
    private void switchToTextLayout(boolean needShowInput) {
//        hideEmojiLayout();
//        hideActionPanelLayout();

        //audioRecordBtn.setVisibility(View.GONE);
        editTextMessage.setVisibility(View.VISIBLE);
        //switchToTextButtonInInputBar.setVisibility(View.GONE);
        //switchToAudioButtonInInputBar.setVisibility(View.VISIBLE);

        //messageInputBar.setVisibility(View.VISIBLE);

        if (needShowInput) {
            //uiHandler.postDelayed(showTextRunnable, SHOW_LAYOUT_DELAY);
        } else {
            //hideInputMethod();
        }
    }

    /**
     * 显示发送或更多
     *
     * @param editText
     */
    private void checkSendButtonEnable(EditText editText) {
        String textMessage = editText.getText().toString();
        if (!TextUtils.isEmpty(StringUtil.removeBlanks(textMessage)) && editText.hasFocus()) {
            //moreFuntionButtonInInputBar.setVisibility(View.GONE);
            //sendMessageButtonInInputBar.setVisibility(View.VISIBLE);
        } else {
            //sendMessageButtonInInputBar.setVisibility(View.GONE);
            //moreFuntionButtonInInputBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onEmojiSelected(String key) {
        Editable mEditable = editTextMessage.getText();
        if (key.equals("/DEL")) {
            editTextMessage.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = editTextMessage.getSelectionStart();
            int end = editTextMessage.getSelectionEnd();
            start = (start < 0 ? 0 : start);
            end = (start < 0 ? 0 : end);
            mEditable.replace(start, end, key);
        }
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName) {

    }

    public interface TextChangeListener {
        void getText(String inputText);
    }

    private TextChangeListener textChangeListener;

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
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
                if (emoticon_picker_view.getVisibility() == View.VISIBLE) {
                    emoticon_picker_view.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    emoticon_picker_view.setVisibility(View.VISIBLE);
                    emoticon_picker_view.show(this);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
            case R.id.sendLayout:
                break;
            case R.id.buttonMoreFuntionInText:
                break;
            case R.id.buttonSendMessage:
                textChangeListener.getText(editTextMessage.getText().toString());
                editTextMessage.setText("");
                break;
        }
    }
}
