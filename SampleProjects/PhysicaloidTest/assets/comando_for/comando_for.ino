#include <Robotica_Cognitiva.h>
void loop() {
    if (Estado.Botao_Esquerdo()){
         for (int i=1;i<=3;i++){
                   if (i==1){
                        Acao.Imprimir(um);
                        delay(2000);   
                 }
                  if (i==2){
                        Acao.Imprimir(dois);
                         delay(2000);
                  }
                  if (i==3){
                       Acao.Imprimir(tres);
                       delay(2000);
                      Acao.Apaga_Tudo();
                  }
            }
        }    
}
