package vn.tiki.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KenZira on 6/30/16.
 */
public class AutoCompleteEmailEditText extends AppCompatEditText {

  private List<String> domains;
  private boolean isBackPressing;

  private final TextWatcher watcher = new TextWatcher() {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      isBackPressing = count > 0 && after == 0;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

      if (!isBackPressing) {

        String textAfterChanged = s.toString();
        String matchDomain = "";
        String textAfterAt = "";

        int atPosition = textAfterChanged.indexOf('@');
        if (atPosition > 1 && atPosition < (textAfterChanged.length() + 1)) {

          textAfterAt = textAfterChanged.substring(atPosition + 1, textAfterChanged.length());

          if (textAfterAt.isEmpty()) {
            return;
          }

          for (String domain : domains) {

            if (domain.startsWith(textAfterAt)) {
              matchDomain = domain;
            }
          }
        }

        if (!matchDomain.isEmpty()) {
          removeTextChangedListener(watcher);

          String filled = matchDomain.substring(textAfterAt.length(), matchDomain.length());
          String text = textAfterChanged + filled;
          int highlight = text.lastIndexOf(filled);
          setText(text);
          setSelection(highlight, text.length());

          addTextChangedListener(watcher);
        }
      }
    }
  };

  public AutoCompleteEmailEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public AutoCompleteEmailEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoCompleteEmailEditText);

    try {
      CharSequence[] predefinedDomains =
          a.getTextArray(R.styleable.AutoCompleteEmailEditText_domains);

      domains = new ArrayList<>();

      if (predefinedDomains != null) {
        for (CharSequence domain : predefinedDomains) {
          domains.add(domain.toString());
        }
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

    setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

    if (supportedEnvironment()) {
      addTextChangedListener(watcher);
    }
  }

  /**
   * For some keyboard with enable auto suggestion, the textwatcher does not work properly
   *
   * @return verified environment
   */

  private boolean supportedEnvironment() {
    String keyboardId = Settings.Secure.getString(
        getContext().getContentResolver(),
        Settings.Secure.DEFAULT_INPUT_METHOD);

    if (Constant.LABANKEY_ID.equals(keyboardId)) {
      return LabanKeyEnvironment.verify();
    } else {
      return true;
    }
  }

  public void setSuggestedDomains(List<String> domains) {
    this.domains = domains;
  }
}
