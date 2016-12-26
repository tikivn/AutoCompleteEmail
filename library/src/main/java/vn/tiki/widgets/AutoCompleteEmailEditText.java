package vn.tiki.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KenZira on 6/30/16.
 */
public class AutoCompleteEmailEditText extends AppCompatEditText {

  private static final String TAG = "AutoCompleteEmailEdit";
  private List<String> domains;
  private boolean isBackPressing;
  private final TextWatcher watcher = new TextWatcher() {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      Log.d(
          TAG,
          "beforeTextChanged() called with: s = ["
              + s
              + "], start = ["
              + start
              + "], count = ["
              + count
              + "], after = ["
              + after
              + "]");
      isBackPressing = count > 0 && after == 0;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

      if (!isBackPressing) {

        String textAfterChange = s.toString();
        String matchDomain = "";
        String textAfterAtSymbol = "";

        int atSignPosition = textAfterChange.indexOf('@');
        if (atSignPosition > 1
            && atSignPosition < (textAfterChange.length() + 1)) {

          textAfterAtSymbol = textAfterChange.substring(
              atSignPosition + 1,
              textAfterChange.length());

          if (textAfterAtSymbol.isEmpty()) {
            return;
          }

          for (String domain : domains) {

            if (domain.startsWith(textAfterAtSymbol)) {
              matchDomain = domain;
            }
          }
        }

        removeTextChangedListener(watcher);

        if (!matchDomain.isEmpty()) {
          String filled = matchDomain.substring(textAfterAtSymbol.length(), matchDomain.length());
          String text = textAfterChange + filled;
          int highlight = text.lastIndexOf(filled);
          setText(text);
          setSelection(highlight, text.length());
        }
        addTextChangedListener(watcher);
      }
    }
  };

  public AutoCompleteEmailEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public AutoCompleteEmailEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(
        attrs,
        R.styleable.AutoCompleteEmailEditText);

    try {
      CharSequence[] domainsFromResource =
          a.getTextArray(R.styleable.AutoCompleteEmailEditText_domains);

      if (domainsFromResource != null) {

        String[] referencedDomains = new String[domainsFromResource.length];
        int i = 0;

        for (CharSequence domain : domainsFromResource) {
          referencedDomains[i++] = domain.toString();
        }

        domains = Arrays.asList(referencedDomains);
      } else {
        domains = new ArrayList<>();
      }
    } finally {
      a.recycle();
    }

    init();
  }

  public AutoCompleteEmailEditText(Context context) {
    super(context);
    init();
  }

  public void init() {
    setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    addTextChangedListener(watcher);
    setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
          if (getSelectionStart() != getSelectionEnd()) {
            setSelection(getText().length());
          }
        }
      }
    });
  }

  public void setSuggestedDomains(List<String> domains) {
    this.domains = domains;
  }
}
