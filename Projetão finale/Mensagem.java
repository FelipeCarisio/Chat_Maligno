public class Mensagem implements Coisa
{
	private String conteudo;
	private String nome = null;

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
		public Mensagem(String msg) throws Exception
		{
			if(msg != null)
			   conteudo = msg;
			else
			    throw new Exception("Mensagem mal estruturada sem conteudo");
		}

		public String getNome()
		{
			return this.nome;
		}

		public int hashCode()
		{
			int ret = 3 * 13 + conteudo.hashCode();
			return ret;
		}

		public String toString()
		{
			if(nome == null)
			return this.conteudo;
			else
			{
				return ("para:" + this.nome + " :" + this.conteudo);
			}
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