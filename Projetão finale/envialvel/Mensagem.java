/**
@author Felipe Carisio, Ivan Knobel, João Augusto
*/
package enviavel;
public class Mensagem implements Coisa
{
	private String conteudo;
	private String emissor, remetente = null;



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
					   this.emissor = nom;
		}
					else
			throw new Exception("Mensagem mal estruturada sem conteudo");
	}

	/**
	Construtor com apenas conteudo
	@param msg é o conteúdo da mensagem
	@throws lança exceção se o parâmetro for nulo
	*/
	public Mensagem(String msg, String nom, String reme)throws Exception
		{
			if(msg != null || nom != null || reme != null)
			{
						   this.conteudo = msg;
						   this.emissor = nom;
						   this.remetente = reme;
			}
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
			String reme = "";
			if(this.remetente != null)
			   reme = "para: " + this.remetente;
			return (this.emissor + ": " reme + " :" + this.conteudo);
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