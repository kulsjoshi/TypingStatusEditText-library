package com.kuldeepjoshi.chatedittextlibrary;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypingStatusEditText extends AppCompatEditText implements TextWatcher {

    private static final String TAG = TypingStatusEditText.class.getSimpleName();

    //*********************************************************** //
    //******************* VARIABLE DECLARATION ****************** //
    //*********************************************************** //

    private static final int TypingInterval = 800;

    private boolean selectorShown;
    private boolean currentTypingState = false;

    private int mentionStart;
    private int mentionEnd;


    private Handler handler = new Handler();

    //*********************************************************** //
    //******************* LISTENER DECLARATION ****************** //
    //*********************************************************** //

    private OnMentionListener mentionListener;
    private OnTypingModified typingChangedListener;


    //your listener interface that you implement anonymously from the Activity

    //*********************************************************** //
    //********************** INTERFACE  ************************* //
    //*********************************************************** //

    public interface OnTypingModified {
        void onIsTypingModified(EditText view, boolean isTyping);
    }

    public interface OnMentionListener extends TextWatcher {
        void OnMentionStarted(String sequence);

        void OnMentionFinished();
    }

    //*********************************************************** //
    //********************** CONSTRUCTOR  *********************** //
    //*********************************************************** //

    public TypingStatusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }


    public TypingStatusEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEditText();

    }

    public TypingStatusEditText(Context context) {
        super(context);
        initEditText();
    }

    //*********************************************************** //
    //******************** INITIALIZATION  ********************** //
    //*********************************************************** //

    private void initEditText() {

        this.selectorShown = false;
        this.mentionStart = 0;
        this.mentionEnd = 0;
        this.addTextChangedListener(this);

    }

    //*********************************************************** //
    //**************** EDITTEXT OVERRIDE METHODS  *************** //
    //*********************************************************** //

    @Override
    public void afterTextChanged(Editable s) {

        if (null != typingChangedListener) {
            if (!currentTypingState) {
                typingChangedListener.onIsTypingModified(this, true);
                currentTypingState = true;
            }

            handler.removeCallbacks(stoppedTypingNotifier);
            handler.postDelayed(stoppedTypingNotifier, TypingInterval);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }


    @Override
    public void onTextChanged(CharSequence text, int start, int before, int after) {
        checkIfMentioning(getMentioningSequence(text, start, after));
    }

    private Runnable stoppedTypingNotifier = new Runnable() {
        @Override
        public void run() {
            //part A of the magic...
            if (null != typingChangedListener) {
                typingChangedListener.onIsTypingModified(TypingStatusEditText.this, false);
                currentTypingState = false;
            }
        }
    };


    public void setOnTypingModified(OnTypingModified typingChangedListener) {
        this.typingChangedListener = typingChangedListener;
    }

    public void setSelectedMention(SpannableStringBuilder mention) {

        try {
            this.getText().replace(mentionStart - 1, mentionEnd, mention);
        } catch (IndexOutOfBoundsException exception) {
            Log.e(TAG, "Not valid start-end values for the mention");
        }

    }

    private void checkIfMentioning(String mentionSequence) {
        if (mentionSequence != null) {
            if (!selectorShown && mentionListener != null) {
                mentionListener.OnMentionStarted(mentionSequence);
            }
        }
        if (mentionSequence == null && mentionListener != null) {
            mentionListener.OnMentionFinished();
        }
    }

    public int getCurrentCursorLine() {
        int selectionStart = Selection.getSelectionStart(this.getText());
        Layout layout = this.getLayout();

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }

    public int getCurrentLineTop() {
        int y = 0;
        int currentLine = getCurrentCursorLine();
        Rect r = new Rect();
        this.getLineBounds(currentLine, r);
        y = r.top - this.getPaddingTop();
        return y;
    }


    private String getMentioningSequence(CharSequence s, int start, int count) {

        Pattern pattern = Pattern.compile("(?<=\\s|^)@([a-z|A-Z|\\.|\\-|\\_|0-9]*)(?=\\s|$)");
        Matcher matcher = pattern.matcher(s.toString());
        String mention = null;
        while (matcher.find()) {
            if (matcher.start(1) <= start + count &&
                    start + count <= matcher.end(1)
            ) {
                mentionStart = matcher.start(1);
                mentionEnd = matcher.end(1);
                mention = matcher.group(1);
                break;
            }
        }
        return mention;
    }

    public void setMentionListener(OnMentionListener mentionListener) {
        this.mentionListener = mentionListener;
    }


}
