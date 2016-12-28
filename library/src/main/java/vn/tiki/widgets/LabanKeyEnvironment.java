package vn.tiki.widgets;

import android.os.Build;

/**
 * Created by KenZira on 12/28/16.
 */

public class LabanKeyEnvironment {

  private LabanKeyEnvironment(){

  }

  static boolean verify(){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
      return true;
    }else {
      return false;
    }
  }
}
