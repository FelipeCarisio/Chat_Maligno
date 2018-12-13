package enviavel;

/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe escolhaDeNome
*/

public class EscolhaDeNome implements Coisa
{
	private String conteudo;


    /**
    Construtor da classe
    @param nomeSelecionado � o nome que o usu�rio escolheu
    @throws se o parametro for nulo
    */
	public EscolhaDeNome(String nomeSelecionado) throws Exception
	{
		if(nomeSelecionado != null)
		   conteudo = nomeSelecionado;
		else
		    throw new Exception("Nome Vazio");
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

        EscolhaDeNome ret = (EscolhaDeNome)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}
}