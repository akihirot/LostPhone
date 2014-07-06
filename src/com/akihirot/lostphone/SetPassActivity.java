package com.akihirot.lostphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetPassActivity extends Activity {
	
	Button okButton;
	EditText edit;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);
   
        edit = (EditText)findViewById(R.id.text_password_id);

        okButton = (Button)findViewById(R.id.ok_button_id);
        okButton.setOnClickListener(new OnClickListener(){
	     @Override
	       public void onClick(View v) {
	    	 SpannableStringBuilder st = (SpannableStringBuilder)edit.getText();
	    	 String stPass = st.toString();
	    	 stPass = toMD5.encodePassdigiest(stPass);
	    	 Intent intent = new Intent();
	    	 intent.putExtra("pass", stPass);
	    	 setResult(Activity.RESULT_OK, intent);
	    	 finish();
	      }
	    });
    }
	
}