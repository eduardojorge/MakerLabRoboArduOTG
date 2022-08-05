
#include <Robotica_Cognitiva.h>

long tempoInicial = millis();
long intervalo =60000; // 1 minuto

void loop() {
   long tempoAtual = millis();
   long tempoDiferenca = tempoAtual-tempoInicial;
               Sentimento.Feliz();
           //Acao.Toca_Buzzer(Feliz_1 );
           delay(2000);
           tempoInicial = tempoAtual;
   }else{
          Acao.Apaga_Tudo();
         //Acao.Toca_Buzzer(songName);
    }   
}
