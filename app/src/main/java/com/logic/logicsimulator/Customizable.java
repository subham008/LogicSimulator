package com.logic.logicsimulator;

//version 1
public class Customizable {
	String tag="tag";
	int annotType=MAKE_CHANGEABLE;

	public Customizable(int type, String custom_tag){
		 annotType=type;
		 tag=custom_tag;
	}


	public void onChangeButton(boolean toggle,boolean increase,int seek, String text){
		   //on add button clicked		   
		  
		}

	public DataGroup retrieveData(){
		 //return a DataGroup filled with default data
		  return null;
		}

	public int getAnnotType() {
		return annotType;
	}


	public static int MAKE_CHANGEABLE=78;
	public static int MAKE_NUMBER_EDITABLE=79;
	public static int MAKE_SEEKABLE=80;
	public static int MAKE_TEXT_EDITABLE=81;
	public static int MAKE_TOGGLEABLE=82;



	public static class DataGroup{
		   public int number,max;
		   public String text;
			public boolean state;
		}
}