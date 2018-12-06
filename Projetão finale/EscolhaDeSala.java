/**
@author Felipe Carisio, Ivan Knobel, João Augusto

Classe escolhaDeSala
*/

public class EscolhaDeSala implements Coisa
{
 private String conteudo;


    /**
    Construtor da classe
    @param salaSelecionada é a sala que o usuário escolheu
    @throws se o parametro for nulo
    */
 	public EscolhaDeSala(String salaSelecionada) throws Exception
 	{
 		if(salaSelecionada != null)
 		   conteudo = salaSelecionada;
 		else
 		    throw new Exception("nome da sala Vazio");
 	}

    /**
    hashCode da classe
    */
 	public int hashCode()
 	{
 		int ret = 3 * 13 + conteudo.hashCode();
 		return ret;
 	}

    /**
    toString da classe
    */
 	public String toString()
 	{
 		return this.conteudo;
 	}

    /**
    equals da classe
    */
 	public boolean equals(Object x)
 	{
 		if(x == this)
 		  return true;
 		if(x == null)
 		  return false;
 		if(x.getClass() != this.getClass())
 		return false;

        EscolhaDeSala ret = (EscolhaDeSala)x;
 		if(this.conteudo != ret.conteudo)
 		return false;

 		return true;
	}
}