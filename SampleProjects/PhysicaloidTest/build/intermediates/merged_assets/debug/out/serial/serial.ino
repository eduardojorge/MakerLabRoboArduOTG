#include "Robotica_Cognitiva.h"


void loop() {
  
 char data = 0;            //Variable for storing received data

 if(Serial.available() > 0)      
   {
      data = Serial.read();        
      Serial.print(data); 
   
      if (data=='L'){
         Acao.Liga_Tudo();
  
  
      }else if(data=='D'){  
          Acao.Apaga_Tudo();
  
      }else if (data=='F'){
          Sentimento.Feliz();
      }
   }



  




}
