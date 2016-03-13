
package p8.example.projet;



        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.graphics.Paint.FontMetrics;
        import android.graphics.Paint.Style;
        import android.graphics.drawable.BitmapDrawable;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.MotionEvent;
        import android.view.SurfaceHolder;
        import android.view.View;


public class SudokuViewLand extends View implements Runnable{

    Paint background;
    private Bitmap drop;
    Chrono ch;
    public boolean valid=true;
    SurfaceHolder holder;
    public final Grille_sudoku grilleS;
    private     boolean in      = true;
    private float width;
    private float height;
    private final Rect selRect=new Rect();
    private int selX;
    private int selY;
    private     Thread  cv_thread;
    public String time;
    final Bitmap num[] = new Bitmap[9];




    public SudokuViewLand(Context context)
    {
        super(context);
        this.grilleS = (Grille_sudoku) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        width  = (h / 9f);
        height = ((w*3)/4 / 9f)-3;
        getRect(selX, selY, selRect);
        super.onSizeChanged(w, h, oldh, oldw);
    }

    private void getRect(int x, int y, Rect rect) {

        rect.set((int) (x * height), (int) (y * width), (int) (x * height + height), (int) (y * width + width) );
    }

    public void select(int x, int y) {

        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);
        invalidate(selRect);
    }
    @SuppressLint("WrongCall")
    public void SelectSaisie(int touche) {
        if (grilleS.estValide(selX, selY, touche))
        {   			valid=true;
        }

        else {
            valid=false;
        }
        this.invalidate();

    }
    public void DessinerChronometre(Canvas canvas ) {

        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(
                R.color.puzzle_noir));

        foreground.setStyle(Style.FILL);
        foreground.setTextSize(height * 0.60f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        ((View) this).invalidate();

        time=grilleS.ch.getTime();

        canvas.drawText(String.valueOf(time), 60,30, foreground);

    }

    public void DessinerNombre(Canvas canvas ) {

        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setStyle(Style.FILL);
        foreground.setTextSize(width * 0.75f);
        foreground.setTextScaleX(height / width);
        foreground.setTextAlign(Paint.Align.CENTER);
        FontMetrics fm = foreground.getFontMetrics();
        float x = width / 2 - (fm.ascent + fm.descent) / 2;
        float y = height / 2;

        //inserer les nomrbre dans la grille du sudoku

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!grilleS.ControleSaisie(i, j, grilleS.getValueGrille(i, j)))
                    foreground.setColor(getResources().getColor(R.color.puzzle_rouge));

                else foreground.setColor(getResources().getColor(R.color.puzzle_noir));
                canvas.drawText(grilleS.GrilleToString(i, j), i * height + x,( j * width + y), foreground);
            }
        }
    }
    public void DessinerBouton(Canvas canvas) {
        // Dessiner les boutons

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setLinearText(true);

        float xOri=((getWidth()*3)/4)+ 20,yOri=0;
        int sizebutton=getHeight()/5;
        int yText=((sizebutton*3)/5);
        int xText=sizebutton/3;

        for (int i = 0; i < num.length; i++) {
            num[i]=((BitmapDrawable) getResources().getDrawable(R.drawable.image)).getBitmap();
            num[i]=num[i].createScaledBitmap(num[i], sizebutton, sizebutton, true);
        }

        drop= ((BitmapDrawable) getResources().getDrawable(R.drawable.pencil_disabled)).getBitmap();
        drop= drop.createScaledBitmap(drop,   sizebutton, sizebutton, true);

        p.setColor(getResources().getColor(R.color.puzzle_noir));
        p.setTextSize(40);
        xOri+=2;

        for (int i = 0; i <num.length; i++) {

            canvas.drawBitmap(num[i], xOri,    yOri, p);
            canvas.drawText(String.valueOf(i+1),xOri+ xText , yOri+yText, p);
            if(i<4) xText=sizebutton/3;

            xOri+=getHeight()/5;

            if(i==1 || i==3 || i==5 || i==7){
                yOri+=(getHeight()/5);
                xOri=((getWidth()*3)/4)+20;
            }
        }

        canvas.drawBitmap(drop, xOri, yOri, p);
    }

    public void DessinerLigneHor(Canvas canvas,Paint color,int x1,float x2, int y1, float y2){
        canvas.drawLine(x1, x2, y1, y2,color);

    }
    public void DessinerLigneVer(Canvas canvas,Paint color,float x1,int x2, float y1, int y2){
        canvas.drawLine(x1, x2, y1, y2,color);

    }
    public void DessinerGrille(Canvas canvas ) {

        background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));

        canvas.drawRect(0, 0, getWidth(), getHeight(), background);//  Rectangle(X_haut_gauche, Y_haut_gauche, X_droit_bas, Y_droit_bas, couleur)  champ prenant tous l'ecran - chrono
        // (0, 30, getWidth(), getHeight(), background)
        Paint background2 = new Paint();
        background2.setColor(getResources().getColor(R.color.puzzle_light));

        canvas.drawRect((getWidth()*3)/4-30, 0, getWidth(), getHeight(),background2);//((getWidth()*3)/4), 0, getWidth(), getHeight())  champ pour les bouttons

        // Définir les couleurs pour les lignes de la grille

        Paint ligne = new Paint();
        ligne.setColor(getResources().getColor(R.color.puzzle_gris));

        Paint grille = new Paint();
        grille.setColor(getResources().getColor(R.color.puzzle_noir));

        int w=getWidth();

        w=(w*3)/4;


        // Tracer les lignes de la grille
        for (int i = 0; i < 9; i++) {

            // ligne horizontal
            DessinerLigneHor(canvas,ligne,0,(i * width), w - 30, (i * width)); // (x1, x2, y1, Y2) //Attention Height n'est pas la hauteur, mais une variable

            // ligne vertical
            DessinerLigneVer(canvas,ligne,i * height, 0, i * height, w);

            if(i%3 ==0){
                DessinerLigneHor(canvas,ligne,0, (i * width) + 1, w - 30, (i * width)+1);
                DessinerLigneVer(canvas,ligne,i * height + 1,0, i * height + 1,w);
            }
        }
    }
    public void colorSelect(Canvas canvas){
        // colorer la selection...
        Paint select = new Paint();

        select.setColor(getResources().getColor(R.color.puzzle_selected));


        canvas.drawRect(selRect, select);
    }
    protected void onDraw(Canvas canvas){



        DessinerGrille(canvas);

        colorSelect(canvas);

        DessinerNombre(canvas);

        DessinerBouton(canvas);

        DessinerChronometre(canvas);



    }

    public boolean onTouchEvent (MotionEvent event) {


        int h1=getHeight()/5;
        int w1=(getWidth()*3)/4;
        int w2 = getWidth()/10;
        if (event.getX() < w1) {
            select((int) (event.getX() / height),
                    (int) (event.getY() / width));
        }
        else{
            if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() < h1){
                onKeyDown(KeyEvent.KEYCODE_1, null);
            }
            if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() <  h1*2 && event.getY() > h1){
                if (event.getY() <  h1*2)  onKeyDown(KeyEvent.KEYCODE_3, null);
            }
            if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() <  h1*3 && event.getY() > h1*2){
                if (event.getY() <  h1*3)  onKeyDown(KeyEvent.KEYCODE_5, null);
            }
            if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() <  h1*4 && event.getY() > h1*3){
                onKeyDown(KeyEvent.KEYCODE_7, null);
            }
            if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() <  h1*5 && event.getY() > h1*4){
                onKeyDown(KeyEvent.KEYCODE_9, null);
            }
            // **********************
            if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2 && event.getY() <  h1){
                onKeyDown(KeyEvent.KEYCODE_2, null);
            }
            if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2 && event.getY() <  h1*2 && event.getY() > h1 ){
                if (event.getY() <  h1*2)  onKeyDown(KeyEvent.KEYCODE_4, null);
            }
            if (event.getX() < w1 +w2*2 && event.getY() <  h1*3 && event.getY() > h1*2 && event.getX() > w1 +w2 ){
                if (event.getY() <  h1*3)  onKeyDown(KeyEvent.KEYCODE_6, null);
            }
            if (event.getX() < w1 +w2*2&& event.getX() > w1 +w2 && event.getY() <  h1*4 && event.getY() > h1*3){
                if (event.getY() <  h1*4)  onKeyDown(KeyEvent.KEYCODE_8, null);
            }
            if (event.getX() < w1 + w2*2 && event.getX() > w1 +w2 && event.getY() <  h1*5 && event.getY() > h1*4){
                onKeyDown(KeyEvent.KEYCODE_0, null);
            }
        }

            /*
             else{
              if (event.getX() < w1 +w2 && event.getX() > w1 && event.getY() < h1){
                    onKeyDown(KeyEvent.KEYCODE_1, null); System.out.println("iqusdqsjkhf");
              }
              else if (event.getX() < w1 +w2 && event.getY() <  h1*2 && event.getY() > h1){
                    onKeyDown(KeyEvent.KEYCODE_3, null);
              }
              else if (event.getX() < w1 +w2){
                  if (event.getY() <  h1*3 && event.getY() > h1*2)  onKeyDown(KeyEvent.KEYCODE_5, null);
              }
              else if (event.getX() < w1 +w2){
                  if (event.getY() <  h1*4 && event.getY() > h1*3)  onKeyDown(KeyEvent.KEYCODE_7, null);
              }
              else if (event.getX() < w1 +w2){
                  if (event.getY() <  h1*5 && event.getY() > h1*4)  onKeyDown(KeyEvent.KEYCODE_9, null);
              }
              // **********************
              else if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2){
                  if (event.getY() <  h1)  onKeyDown(KeyEvent.KEYCODE_2, null);
              }
              else if (event.getX() < w1 +w2*2  && event.getX() > w1 +w2 && event.getY() <  h1*2 && event.getY() > h1){
                    onKeyDown(KeyEvent.KEYCODE_4, null); System.out.println("BOUTON 4 DE MERDE");
              }
              else if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2){
                  if (event.getY() <  h1*3 && event.getY() > h1*2)  onKeyDown(KeyEvent.KEYCODE_6, null);
              }
              else  if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2){
                  if (event.getY() <  h1*4 && event.getY() > h1*3)  onKeyDown(KeyEvent.KEYCODE_8, null);
              }
              else  if (event.getX() < w1 +w2*2 && event.getX() > w1 +w2){
                  if (event.getY() <  h1*5 && event.getY() > h1*4)  onKeyDown(KeyEvent.KEYCODE_0, null);
    }
}

*/
          /*
          {

          if (event.getX() < w1+90){

          	if (event.getY() <  h1) {
          		onKeyDown(KeyEvent.KEYCODE_1, null);


          	} else if ((event.getX() <2*w1)&&(event.getX() >w1)) {
          		onKeyDown(KeyEvent.KEYCODE_2, null);

          	} else if ((event.getX() < 3*w1)&&(event.getX() >2*w1)){
          		onKeyDown(KeyEvent.KEYCODE_3, null);

          	} else if ((event.getX() < 4*w1)&&(event.getX() >3*w1)){
          		onKeyDown(KeyEvent.KEYCODE_4, null);

          	} else if ((event.getX() < 5*w1)&&(event.getX() >4*w1)){
          		onKeyDown(KeyEvent.KEYCODE_5, null);
          	}
          	}

          else{ if(event.getX() <num[5].getWidth()) {
        		  onKeyDown(KeyEvent.KEYCODE_6, null);

          		}else if((event.getX() < 2*w1)&&(event.getX() >w1)){
        		  onKeyDown(KeyEvent.KEYCODE_7, null);

          		}else if((event.getX() < 3*w1)&&(event.getX() >2*w1)){
        		  onKeyDown(KeyEvent.KEYCODE_8, null);

          		}else if((event.getX() < 4*w1)&&(event.getX() >3*w1)){
        		  onKeyDown(KeyEvent.KEYCODE_9, null);
        	    } else {
        		  onKeyDown(KeyEvent.KEYCODE_0, null);

       }}
    }
           */
        return super.onTouchEvent(event);
    }

    public boolean onKeyDown(int saisies, KeyEvent event)
    {
        int saisie = 0;
        switch (saisies) {

            case KeyEvent.KEYCODE_0:	  saisie=0;
                break;
            case KeyEvent.KEYCODE_1:	  saisie=1; break;
            case KeyEvent.KEYCODE_2:     saisie=2; break;
            case KeyEvent.KEYCODE_3:     saisie=3; break;
            case KeyEvent.KEYCODE_4:     saisie=4; break;
            case KeyEvent.KEYCODE_5:     saisie=5; break;
            case KeyEvent.KEYCODE_6:     saisie=6; break;
            case KeyEvent.KEYCODE_7:     saisie=7; break;
            case KeyEvent.KEYCODE_8:     saisie=8; break;
            case KeyEvent.KEYCODE_9:     saisie=9; break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:

                break;


            default:
                return super.onKeyDown(saisie, event);

        }
        returnResultat(saisie);

        return true;

    }
    public void returnResultat(int saisie)
    {
        SelectSaisie(saisie);

    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c = null;

        while (in) {
            try {
                cv_thread.sleep(40);

                try {
                    c = holder.lockCanvas(null);
                    onDraw(c);

                } finally {
                    if (c != null) {

                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }
        }

    }

}
