#include "Robotica_Cognitiva.h"


void loop() {

  if((Estado.Botao_Direito() )&&(Estado.Botao_Esquerdo())){
     Acao.Imprimir(coracao);
    
  }else{
     Acao.Liga_Tudo();
  }


}
