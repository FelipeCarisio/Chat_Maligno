/**
@author Felipe Carisio, Ivan Knobel, João Augusto

Classe avisoUsuarioInexistente
*/

public class AvisoUsuarioInexistente implements Coisa
{
    private String conteudo;

    /**
    Construtor da classe
    @param nome é a ser avisado
    @throws se o nome for nulo
    */
	public AvisoUsuarioInexistente(String nome) throws Exception
	{
		if(nome != null)
		   conteudo = "O usuário " + nome + " não existe nessa sala";
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

		  AvisoUsuarioInexistente ret = (AvisoUsuarioInexistente)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}
}