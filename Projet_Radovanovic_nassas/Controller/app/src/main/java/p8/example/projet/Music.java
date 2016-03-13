package p8.example.projet;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class Music {
	static int musicPaused;
	public static MainSudoku gs;
	private static MediaPlayer mPlayer = null;
	static Context context;
	  
	   
	   public static void play(int resource) {
		   Log.e("Music","enter");
		   if(mPlayer != null) {
				mPlayer.stop(); 
				mPlayer.setLooping(true);
				mPlayer.release();
			}
		   
			mPlayer = MediaPlayer.create(gs, resource);
		   mPlayer.seekTo(musicPaused);
			mPlayer.start();
		   mPlayer.setLooping(true);
	   }
	      
	      public static void stop() {
	         if (mPlayer != null) {
				 musicPaused = mPlayer.getCurrentPosition();
	        	 mPlayer.stop();
	        	 mPlayer.release();
	        	 mPlayer = null;
	         }
	      }
	      
}
