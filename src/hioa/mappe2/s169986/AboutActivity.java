package hioa.mappe2.s169986;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AboutActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
	}
    
    @Override
   	protected void onPause() {
   		super.onPause();
   	} 
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}
	
	//actionBar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mk_start:
			Intent tilMain = new Intent(this, MainActivity.class);
			startActivity(tilMain);
			super.finish();
			return true;  
	    case R.id.mk_avslutt:
	    	super.finish();
	        return true; 
	    }
		return false;
	}
	
	//Tilbake knapp til StartActivity
	public void tilbakeKnapp(View v) {
		Intent tilStart = new Intent(this, StartActivity.class);
		startActivity(tilStart);
		super.finish();
	}
	
	public void StartSpill(View v) {
		Intent tilMain = new Intent(this, MainActivity.class);
		startActivity(tilMain);  
		super.finish();
    }
}
