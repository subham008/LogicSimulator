package com.logic.logicsimulator.parsers;;

import java.util.HashSet;
import java.util.Set;

public class IdentityManager {
      
    private Set<Integer> idList=new HashSet<>();

   public String createNewIdfor( ) {
       int newIntegerID=0;
       do{
            newIntegerID=(int)(Math.random()*999)+1;
       }while (idList.contains(newIntegerID));

       idList.add(newIntegerID);
      return String.valueOf(newIntegerID);
   }


   public void addID(String gateid){
     idList.add(Integer.valueOf(gateid));
   }


   public void removeID(String gateID){
       idList.remove(Integer.getInteger(gateID));
   }
   

}




