package p8.example.projet;

import p8.example.projet.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
public class MainSudoku extends Activity implements OnClickListener {
	private MainSudoku instance;
	public static boolean bool;
	private boolean sonActiver = false;
	Music music;
	Context context;
	String choix[] = new String[3];
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	int i;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_sudoku);

		final View ButtonQuitter = findViewById(R.id.buttonquitter);
		final View ButtonJouer = findViewById(R.id.buttonJouer);
		final View ButtonContinuer = findViewById(R.id.buttoncontinuer);
		final View ButtonScores = findViewById(R.id.buttonhighscores);
		final View ButtonApropos = findViewById(R.id.buttonApropos);
		final View ButtonSon = findViewById(R.id.buttonSon);
		choix[0] = "Grille 1";
		choix[1] = "Grille 2";
		choix[2] = "Grille 3";
		instance = this;
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		ButtonQuitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ButtonJouer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MainSudoku.this, Grille_sudoku.class);
				
				//intent.putExtra(Grille_sudoku.Grille_sauv, Grille_sudoku.NEW);
				getIntent().putExtra(Grille_sudoku.Grille_sauv, Grille_sudoku.NEW);
				Dialog();

			}
		});
		ButtonContinuer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				choix(Grille_sudoku.CONTINUE);

			}
		});

		ButtonScores.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainSudoku.this, Score.class);

				startActivity(intent);
			}
		});
		ButtonApropos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainSudoku.this, A_propos.class);
				startActivity(intent);
			}
		});
		ButtonSon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (sonActiver == true) {
					sonActiver = false;
					Music.stop();
					
					// image
					ButtonSon.setBackground(instance.getResources().getDrawable(R.drawable.sonoff));
				} else {
					sonActiver = true;
					music = new Music();
					music.gs =instance;
					music.play(R.raw.main);
					// image 
					ButtonSon.setBackground(instance.getResources().getDrawable(R.drawable.sonon));
					

				}
				

			}
		});
		sonActiver = true;
		music = new Music();
		music.gs =instance;
		music.play(R.raw.main);
	}

	@Override
	public void onResume(){
		super.onResume();
		music = new Music();
		music.play(R.raw.main);
	}

	 @Override
	    public void onPause() {
	    	
	    	super.onPause();
	    	sonActiver = false;
	Music.stop();
	 }
	

	public void Dialog() {
		new AlertDialog.Builder(this).setTitle("choix de Grille")
				.setItems(choix, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						
						choix(i);
					}
				}).show();
	}

	public void choix(int i) {
		Intent intent = new Intent(MainSudoku.this, Grille_sudoku.class);
		
		intent.putExtra(Grille_sudoku.Grille_sauv, i);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main_sudoku, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {

			startActivity(new Intent(this, Preferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}