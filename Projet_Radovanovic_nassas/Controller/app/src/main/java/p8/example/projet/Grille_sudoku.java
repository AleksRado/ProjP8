package p8.example.projet;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;


@SuppressLint("NewApi")
public class Grille_sudoku extends Activity  {
	 SudokuView sudokuView;
    SudokuViewLand sudokuViewLand;
     public static final String Grille_sauv = "continue";
    public static int enregister=0;
    Music music;
    static int init=1;
    static int grille3[][] =  
        {
     // Grille fini
    	{0,2,1,0,7,6,0,5,3},
    	{9,0,0,0,0,1,8,0,0},
    	{0,4,0,0,0,3,0,9,0},
    	{4,0,0,3,6,0,5,0,0},
    	{0,9,0,0,1,0,3,2,0},
    	{1,5,0,0,0,0,0,0,4},
    	{3,0,0,0,0,4,2,0,6},
    	{0,0,0,0,0,7,0,0,0},
    	{0,0,5,2,0,0,1,0,0}
    	};
  
   /* static int grille3[][] =  
        {
     // Grille fini
    	{0,8,4,9,7,2,3,5,1},
    	{5,9,1,6,3,4,7,8,2},
    	{7,3,2,5,8,1,4,6,9},
    	{1,4,8,2,9,7,5,3,6},
    	{3,7,6,4,1,5,2,9,8},
    	{2,5,9,8,6,3,1,7,4},
    	{4,6,3,7,2,8,9,1,5},
    	{8,1,5,3,4,9,6,2,7},
    	{9,2,7,1,5,6,8,4,3}
    	};*/
    final int grille3init[][] =  
        {
     
    		{0,2,1,0,7,6,0,5,3},
        	{9,0,0,0,0,1,8,0,0},
        	{0,4,0,0,0,3,0,9,0},
        	{4,0,0,3,6,0,5,0,0},
        	{0,9,0,0,1,0,3,2,0},
        	{1,5,0,0,0,0,0,0,4},
        	{3,0,0,0,0,4,2,0,6},
        	{0,0,0,0,0,7,0,0,0},
        	{0,0,5,2,0,0,1,0,0}
        	};
    static int grille[][]= new int[9][9];
    int grilleInitial[][]= new int[9][9];
    Chrono ch;     
    String time;
    public static final int choix_grille1= 0;
    public static final int choix_grille2= 1;
    public static final int choix_grille3= 2;
    public static final int CONTINUE = -1;
    public static final int NEW = -2;
    public static  int compte;    
    static final String GRILLE = "sudoku";    
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static int choix_grille;
    
    static int grille1[][]=  
        {
                {6,9,7,3,2,5,1,8,0},
                {8,3,2,7,1,4,5,6,9},
                {1,4,5,8,9,6,3,7,2},
                {5,1,4,2,8,3,6,9,7},
                {7,6,8,9,5,1,2,4,3},
                {9,2,3,4,6,7,8,1,5},
                {3,8,6,5,4,9,7,2,1},
                {2,5,9,1,7,8,4,3,6},
                {4,7,1,6,3,2,9,5,8}
        };
  
    
    final int grille1init[][] =  
        {
                {6,0,0,3,0,0,1,8,0},
                {0,0,2,7,0,0,5,0,0},
                {0,4,0,0,9,6,3,0,2},
                {5,0,4,0,0,0,0,9,7},
                {0,6,0,0,0,0,0,4,0},
                {9,2,0,0,0,0,8,0,5},
                {3,0,6,5,4,0,0,2,0},
                {0,0,9,0,0,8,4,0,0},
                {0,7,1,0,0,2,0,0,8}
        };
    
    
    final int grille2init[][] =
    	{	
    {0,0,4,9,0,0,0,5,0},
    {0,0,0,0,3,0,0,8,2},
    {7,0,0,5,0,1,4,0,0},
    {1,0,8,2,0,0,5,0,0},
    {0,7,0,0,0,0,0,9,0},
    {2,0,9,0,0,3,1,0,4},
    {4,0,3,7,0,8,0,0,5},
    {8,1,0,0,4,0,0,0,0},
    {0,2,0,0,0,6,8,0,0}
    };
    static int grille2[][] =
    	{	
    {0,0,4,9,0,0,0,5,0},
    {0,0,0,0,3,0,0,8,2},
    {7,0,0,5,0,1,4,0,0},
    {1,0,8,2,0,0,5,0,0},
    {0,7,0,0,0,0,0,9,0},
    {2,0,9,0,0,3,1,0,4},
    {4,0,3,7,0,8,0,0,5},
    {8,1,0,0,4,0,0,0,0},
    {0,2,0,0,0,6,8,0,0}
    };
    
    
     int intent;
     
     @Override
     public void onResume(){
    	 super.onResume();
    	 music = new Music();
         music.play(R.raw.grille);
     }
    @Override
    public void onPause() {
    	
    	super.onPause();
    	Music.stop();
    	  getPreferences(MODE_PRIVATE).edit().putInt(GRILLE,CONTINUE ).commit();
	
    	  preferences = PreferenceManager.getDefaultSharedPreferences(this);
          editor = preferences.edit();
 
   
	        //Sauvgarde de la grille
          for (int i = 0; i < 9; i++) {
  	         for (int j = 0; j < 9; j++) {  	        	 		        	 
  	        	 String indice=String.valueOf(i)+String.valueOf(j);  	     
  	        	editor.putInt(indice,grille[i][j]);  	            
  	         	}
  	         }                    
          compte=(int) ch.counter;
      	  editor.putInt("cpt", compte);
      	editor.putInt("choixGrille",choix_grille);
          editor.apply();
    }
   /* public int getScreenOrientation()
    {
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
            if(getWidth() < getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }
            else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        return orientation;
    }*/
    @Override 
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
         intent = getIntent().getIntExtra(Grille_sauv, choix_grille1);
        int orientationActuelle = getResources().getConfiguration().getLayoutDirection();
        int portraitPotentiel = Configuration.ORIENTATION_PORTRAIT;
        Display getOrient = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        getOrient.getSize(size);
        int orientation;
        if (size.x < size.y)
        {
            sudokuView = new SudokuView(this);
            setContentView(sudokuView);
            sudokuView.requestFocus();
        }
        else
        {
            sudokuViewLand = new SudokuViewLand(this);
            setContentView(sudokuViewLand);
            sudokuViewLand.requestFocus();
        }

        //if(orientationActuelle == portraitPotentiel) {

        //}
        /*if(getResources().getConfiguration().getLayoutDirection()== Configuration.SCREENLAYOUT_LAYOUTDIR_RTL) {
            sudokuViewLand = new SudokuViewLand(this);
            setContentView(sudokuViewLand);
            sudokuViewLand.requestFocus();
        }*/

        music = new Music();
		music.play(R.raw.grille);


        getIntent().putExtra(Grille_sauv, CONTINUE);       
     
        
        if(intent!=CONTINUE)
        {remplirGrilleChoisi(intent);
        ch = new Chrono();  
        
    }
     
        else{
        	//// recuperer la grille initial
        	 getPreferences(MODE_PRIVATE).getInt(GRILLE,CONTINUE);
			 preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        choix_grille=preferences.getInt("choixGrille",choix_grille);
    	if (choix_grille==1){
        	
    		grille=grille1;
			grilleInitial=grille1init;
			choix_grille=1;
		
    	}
		else if(choix_grille==2) {
							grille=grille2;
							grilleInitial=grille2init;
							choix_grille=2;
							} 
		else if(choix_grille==3) {
				grille=grille3;
				grilleInitial=grille3init;
				choix_grille=3;
				}
        // Recuperer la grille sauvegarder		  

        for (int i = 0; i < 9; i++) {
	         for (int j = 0; j < 9; j++) {
	        	 String indice=String.valueOf(i)+String.valueOf(j);
	        	grille[i][j] = preferences.getInt(indice,grille[i][j]);

             }
	         }
        	/////
        		int i=preferences.getInt("cpt",compte);    	
        		ch = new Chrono();	
        		ch.setCounter(i);         	     
     	}
     	ch.start();     
        
    }
       
    private void remplirGrilleChoisi(int choix) {
    	
    
    	if (choix==0){
    	
    		grille=grille1init;
			grilleInitial=grille1init;
			choix_grille=1;
		
    	}
		else if(choix==1) {
							grille=grille2init;
							grilleInitial=grille2init;
							choix_grille=2;
							} 
		else if(choix==2) {
				grille=grille3init;
				grilleInitial=grille3init;
				choix_grille=3;
				}
   
    }
	             
  protected int getValueGrille(int x,int y){
	  
	 return grille[x][y];
  }
    protected String GrilleToString(int x, int y) {
    	 
       int v = grille[x][y];
       if (v == 0)
       {
          return "";
          }
       else
          return String.valueOf(v);
    }
    private void RemplirGrille(int x, int y, int value) {
    if (grilleInitial[x][y]==0)
    	
    	grille[x][y]= value;
    
         }   
 protected boolean  absentSurColonne (int x, int y, int value)
        {
            for (int j=0; j < 9; j++)
                if (grille[x][j] == value)
                    return false;
            return true;
        }
 protected boolean  absentSurLigne (int x, int y, int value)
 {
	  for (int i=0; i < 9; i++)
	        if ((grille[i][y] == value))
	            return false;
	    return true;
 }
 protected boolean  absentSurBloc (int x, int y, int value)
 {
	  int i = x-(x%3), j = y-(y%3);  // ou encore : i = 3*(x/3), j = 3*(y/3);
	    for (x=i; x < i+3; x++)
	        for (y=j; y < j+3; y++)
	            if (grille[x][y] == value)
	                return false;
	    return true;
 }
 protected boolean verifijeuxfini(){
  
	 for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
       
        	 if (!ControleSaisie(i,j,grille[i][j])){
        		 return false;
      	   	}
        	 
         }
      }
	 return true; 
	 
 }
 protected boolean ControleSaisie(int x, int y, int value) {
   	 
		 grille[x][y]=0;
	 if ((!absentSurLigne(x,y,value))||(!absentSurColonne(x,y,value))|| // si non valid
			  (!absentSurBloc(x,y,value))){
		 
		 grille[x][y]=value;
		 return false;
	 }
	 grille[x][y]=value;
	 return true;
 }
    protected boolean estValide(int x, int y, int value) {
    	  
    	if (value==grille[x][y]) grille[x][y]=0;
    	
   if (value != 0) {
	
	   if ((!absentSurLigne(x,y,value))||(!absentSurColonne(x,y,value))|| // si non valid
		  (!absentSurBloc(x,y,value))){
		   								
		   								RemplirGrille(x, y, value);
		   								
		   								
		   								return false;
	   }
	   else{// cas valid		
		   
		   RemplirGrille(x, y, value);
	
		   if(verifijeuxfini()){				  
			   
			  enregister=1;
	              scoreIntent();
				ch.pause();
		finish();		
	}
	   }
   }
    	RemplirGrille(x, y, value);
    	 return true;    
     }
    public void scoreIntent(){
    	Intent intent = new Intent(this, Score.class);
        startActivity(intent);
    }
    
    
}