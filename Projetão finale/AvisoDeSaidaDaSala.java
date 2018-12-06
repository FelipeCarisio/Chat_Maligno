/**
@author Felipe Carisio, Ivan Knobel, João Augusto

Classe avisoDeSaidaDaSala
*/

public class AvisoDeSaidaDaSala implements Coisa
{
	private String conteudo;

    /**
    Construtor da classe
    @param nome é o nome do usuário a ser avisado
    @throws se o parametro for nulo
    */
	public AvisoDeSaidaDaSala(String nome) throws Exception
	{
		if(nome != null)
		   conteudo = "O usuário " + nome + " saiu da sala";
		else
			throw new Exception("nome Vazio");
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

		AvisoDeSaidaDaSala ret = (AvisoDeSaidaDaSala)x;

		if(this.conteudo != ret.toString())
		return false;

		return true;
	}
}