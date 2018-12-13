/**
@author Felipe Carisio, Ivan Knobel, João Augusto

Classe salasDisponiveis
*/

public class SalasDisponiveis implements Coisa
{
    private String[] conteudo;

    /**
	Construtor da classe
	@param salasDisponiveis que é um aray com todas as salas
	@throws se o parâmetro for nulo
    */
	public SalasDisponiveis(String[] salasDisponiveis) throws Exception
	{
		if(salasDisponiveis != null)
		{
		   for(String i : salasDisponiveis)
		   this.conteudo[i] = i;
		}
		else
			throw new Exception("Não há de onde se pegar salas");
	}

    /**
	hashCode da classe
    */
	public int hashCode()
	{
		int ret = 3 * 13 + conteudo.hashCode();
		return ret;
	}

	public String[] getSalas()
	{
		return this.conteudo;
	}

    /**
	toString da classe
    */
	public String toString()
	{
		String salas;
		for(String i : salasDisponiveis)
		   salas += this.conteudo[i] + "\n";
		return salas;
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

		SalasDisponiveis ret = (SalasDisponiveis)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}
}