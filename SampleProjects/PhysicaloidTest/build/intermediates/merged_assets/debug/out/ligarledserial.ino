char data = 0;            //Variable for storing received data

void setup() {
  pinMode(12,OUTPUT);
  // put your setup code here, to run once:

   Serial.begin(9600); //Inicia a comunicação serial

}

void loop() {

   if(Serial.available() > 0)      
   {
      data = Serial.read();        
     
   
      if (data=='o'){
         Serial.print(data); 
      
          digitalWrite(12, HIGH); //Liga o led
  
      }else if(data=='f'){  
          digitalWrite(12, LOW); // Desliga o led
  
      }
   }
}
