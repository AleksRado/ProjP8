package p8.example.projet;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Chrono 
{
	private Timer timer;
	long counter;
	public long begin;
	static int second=0;
	static int minute=0;
	static int heure=0;
	long end;
	

public Chrono() {
	timer = new Timer();
	
}
	
public void start(){
	long delay = 1000;
	begin = Calendar.getInstance().getTimeInMillis();
newTimer();
	timer.scheduleAtFixedRate(new TimerTask(){

		@Override
		public void run() {
end = Calendar.getInstance().getTimeInMillis();
			counter += end - begin;
			begin = end;
			
		}
		
	}, delay, delay);
	
	
}

public void pause(){	
	newTimer();	
} 

public void stop(){
	newTimer();
	counter = 0;
}

private void newTimer(){
	try{
	timer.cancel();
	timer = new Timer();
	}catch(IllegalStateException e){}
}
public int getCounter(){
	
	return (int) counter/1000;
}
public void setCounter(int c){
	
	 counter=c;
}
public String getTime(){
	second=(int) (counter/1000);
	minute=(int) (counter/60000);
	heure=(int) (counter/3600000);
	 // Dessine le temps écoulé sous forme de h:mm:ss  
	 
  String S=heure +":"+(minute/10)%6+(minute%10)+":"+(second/10)%6+second%10;
	       
	return  S;
}






	}