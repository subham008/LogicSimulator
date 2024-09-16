package com.logic.logicsimulator.Gates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.logic.logicsimulator.parsers.TextLabelElement;

public class TextLabel extends View {

	public static final String GATE_TYPE="TextLabel";
	private TextLabelElement element;
	private String text;
	private int fontSize;
	private int color;
    int width,height;
	public TextLabel(Context context, String t, int f, int c  ){
		 super(context);
		 this.text=t;
		 this.fontSize=f;
		 this.color=c;

		 this.width=getExpectedWidth(text,fontSize);
		 this.height=getExpectedHeight(fontSize);

		 setLayoutParams(new ViewGroup.LayoutParams(this.width , this.height));
				  	 
		}

	public void setElement(@NonNull TextLabelElement element){
		this.element=element;
	}
	public static int getExpectedWidth(String text, int fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        return (int) paint.measureText(text);
    }

    public static int getExpectedHeight(int fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.bottom - fm.top);
    }



	public TextLabelElement getElement(){
		return element;
	}

	@Override
	public void setX(float x){
		 super.setX(x);

	}


	@Override
	public void setY(float y){
		super.setY(y);

	}


	public void updateElementLocation(){
		try{
			element.setXloc(String.valueOf((int)getX()));
			element.setYloc(String.valueOf((int)getY()));
		}catch (Exception e){
			System.out.println("TextLabel->updateElementLocation:ERROR :"+e.toString());
		}
	}

	private Paint paint=null;
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		if(paint==null)
				paint=new Paint();

	  paint.setColor(color);
	  paint.setTextSize(fontSize);
	  canvas.drawText(this.text,0,(getHeight()/2)+10,paint);
		
		}



	public void setTextColor(int color){
		this.color=color;
		invalidate();
	}
   public int getTextColor(){
		return color;
   }


   public int getFontSize(){
		return fontSize;
   }

   public String getLabelText(){
		return  this.text;
   }


	float dx=0.0f,dy=0.0f;
	@Override
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		 super.bringToFront();

		 int action=event.getAction();
		 
		 if(action == MotionEvent.ACTION_MOVE){
			 float px=super.getX(); float py=super.getY();						  			 
			  super.animate().x(event.getRawX()+dx)
					         .y(event.getRawY()+dy)
							 .setDuration(0)
							 .start();													
			     						  			 
			}
		 else if(action == MotionEvent.ACTION_DOWN){
			 dx=super.getX()-event.getRawX();
			 dy=super.getY()-event.getRawY();
		 }
        else if(action==MotionEvent.ACTION_UP){
			 this.updateElementLocation();
		 }
		 return true;
		}
}