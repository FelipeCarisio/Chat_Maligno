package enviavel;

/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe avisoDeSalaCheia
*/

public class AvisoDeSalaCheia implements Coisa
{
    private String conteudo;

    /**
    Construtor da classe
    @param nome � o nome da sala a ser avisada
    @throws se o parametro for nulo
    */
	public AvisoDeSalaCheia(String nome) throws Exception
	{
		if(nome != null)
		   conteudo = "A sala " + nome + " esta cheia";
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

		   AvisoDeSalaCheia ret = (AvisoDeSalaCheia)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}
}