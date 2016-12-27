package vn.tiki.widgets;

import android.text.Editable;
import android.widget.EditText;
import java.util.List;

/**
 * Created by KenZira on 12/27/16.
 */

public class GenericAndroidInputHandler extends InputHandler {

  private int previousLength;

  public GenericAndroidInputHandler(EditText editText, List<String> domains) {
    super(editText, domains);
    editText.addTextChangedListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    previousLength = start;
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {

    if (s.length() > previousLength) {

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

      if (!matchDomain.isEmpty()) {
        editText.removeTextChangedListener(this);

        String filled = matchDomain.substring(textAfterAtSymbol.length(), matchDomain.length());
        String text = textAfterChange + filled;
        int highlight = text.lastIndexOf(filled);
        editText.setText(text);
        editText.setSelection(highlight, text.length());

        editText.addTextChangedListener(this);
      }
    }

  }
}
