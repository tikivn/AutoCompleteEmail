package vn.tiki.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KenZira on 6/30/16.
 */
public class AutoCompleteEmailEditText extends AppCompatEditText {

  private List<String> domains;

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

    String keyboardId = Settings.Secure.getString(
        getContext().getContentResolver(),
        Settings.Secure.DEFAULT_INPUT_METHOD
    );

    InputHandler inputHandler;

    if (Constant.GENERIC_ANDROID_ID.contains(keyboardId)) {
      inputHandler = new GenericAndroidInputHandler(this, domains);
    } else if (Constant.LABANKEY_ID.equals(keyboardId)) {
      inputHandler = new LaBanKeyInputHandler(this, domains);
    } else if (Constant.ASUS_ID.equals(keyboardId)) {
      inputHandler = new AsusInputHandler(this, domains);
    }
  }

  public void setSuggestedDomains(List<String> domains) {
    this.domains = domains;
  }
}
