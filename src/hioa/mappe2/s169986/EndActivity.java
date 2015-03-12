package hioa.mappe2.s169986;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class EndActivity extends Activity{
	private TextView score, rsvar;
	private int antallRiktig, antallFeil, intRiktig;
	private Bundle extras;
	private SharedPreferences intFil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        
        score = (TextView) findViewById(R.id.score);
        rsvar = (TextView) findViewById(R.id.textView2);
        
        intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
        
		antallRiktig = intFil.getInt("antallRiktig", 0);
		antallFeil = intFil.getInt("antallFeil", 0);
		
		String antVinn = getResources().getString(R.string.antVinn);
		String antTap = getResources().getString(R.string.antTap);

		extras = getIntent().getExtras();
		intRiktig = extras.getInt("antRiktig");
		
		//Skriver ut resultatet av hvor mange poeng du har fått totalt
		score.setText(antVinn + " " + antallRiktig + "\n" + antTap + " " + antallFeil + "\n");
		//Skriver ut hvor mange riktig du fikk ut ifra 10 spørsmål
		rsvar.setText(getResources().getString(R.string.intriktig) + " " + intRiktig + "/10");
    }
    
    //Nytt spill knapp
    public void nyttspill(View v)
    {
    	Intent tilMain = new Intent(this, MainActivity.class);
		startActivity(tilMain);
		super.finish();
    }
    
    //Til Meny knapp (StartActivity)
    public void tilMeny(View v)
    {
    	Intent tilStart = new Intent(this, StartActivity.class);
		startActivity(tilStart);
		super.finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_end, menu);
		return true;
	}
	
	//actionBar
	public boolean onOptionsItemSelected(MenuItem item){
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
}