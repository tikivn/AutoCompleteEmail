package vn.tiki.widgets;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.List;

/**
 * Created by KenZira on 12/27/16.
 */

public class InputHandler implements TextWatcher{

  protected EditText editText;
  protected List<String> domains;

  public InputHandler(EditText editText, List<String> domains){
    this.editText = editText;
    this.domains = domains;
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override public void afterTextChanged(Editable s) {

  }

}
