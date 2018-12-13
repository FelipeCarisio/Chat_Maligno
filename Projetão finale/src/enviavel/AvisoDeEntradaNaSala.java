package enviavel;

/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto
*/

public class AvisoDeEntradaNaSala implements Coisa
{
	private String conteudo;

    /**
    Construtor da classe
    @param nome � o nome do usu�rio a ser avisado
    @throws se o parametro for nulo
    */
	public AvisoDeEntradaNaSala(String nome) throws Exception
	{
		if(nome != null)
		   conteudo = "O usu�rio " + nome + " entrou na sala";
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

		AvisoDeEntradaNaSala ret = (AvisoDeEntradaNaSala)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}
}