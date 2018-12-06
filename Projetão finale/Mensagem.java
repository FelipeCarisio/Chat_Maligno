public class Mensagem implements Coisa
{
	private String conteudo;

		public Mensagem(String msg, String nome) throws Exception
		{
			if(msg != null || nome != null)
			   conteudo = nome + ": " + msg;
			else
			    throw new Exception("Mensagem mal estruturada sem conteudo");
		}

		public int hashCode()
		{
			int ret = 3 * 13 + conteudo.hashCode();
			return ret;
		}

		public String toString()
		{
			return this.conteudo;
		}

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