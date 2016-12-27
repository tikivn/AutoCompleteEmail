package vn.tiki.widgets;

import android.text.Editable;
import android.widget.EditText;
import java.util.List;

/**
 * Created by KenZira on 12/27/16.
 */

public class AsusInputHandler extends InputHandler {

  public AsusInputHandler(final EditText editText, List<String> domains) {
    super(editText, domains);
    editText.addTextChangedListener(this);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}
