/**
@author Felipe Carisio, Ivan Knobel, João Augusto
*/

public class Mensagem implements Coisa
{
	private String conteudo;
	private String nome = null;


	/**
	Construtor da classe
	@param msg é o conteúdo da mensagem
	@param nom é o nome da mensagem
	@throws lança exceção se algum dos parâmetros for nulo
	*/
	public Mensagem(String msg, String nom)throws Exception
	{
		if(msg != null && nom != null)
		{
					   this.conteudo = msg;
					   this.nome = nom;
		}
					else
			throw new Exception("Mensagem mal estruturada sem conteudo");
	}

	/**
	Construtor com apenas conteudo
	@param msg é o conteúdo da mensagem
	@throws lança exceção se o parâmetro for nulo
	*/
	public Mensagem(String msg) throws Exception
	{
		if(msg != null)
		   this.conteudo = msg;
		else
			throw new Exception("Mensagem mal estruturada sem conteudo");
	}

   /**
   Getter de nome
   */
	public String getNome()
	{
		return this.nome;
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
		if(nome == null)
		return this.conteudo;
		else
		{
			return ("para:" + this.nome + " :" + this.conteudo);
		}
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

		Mensagem ret = (Mensagem)x;
		if(this.conteudo != ret.conteudo)
		return false;

		return true;
	}

}