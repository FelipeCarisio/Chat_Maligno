public class Usuario
{
    private String             nome;
    private Socket             conexao;
    private ObjectOutputStream transmissor;
    private ObjectInputStream  receptor;
    private Sala               sala;

    public Usuario (Socket conexao,
                    ObjectOutputStream transmissor,
                    ObjectInputStream  receptor,
                    String nome,
                    Sala sala) throws Exeception // se parametro nulos
    {
		if(conexao == null)
		   throw new Exception("O parametro de socket esta nulo.");
		if(transmissor == null)
		   throw new Exception("O parametro de ObjectOutputStream esta nulo.");
		if(receptor == null)
		   throw new Exception("O parametro de ObjectInputStream esta nulo.");
		if(nome == null)
		   throw new Exception("O parametro de nome esta nulo");
		if(sala == null)
		   throw new Exception("O parametro de sala esta nulo");
        // validar parametros
        this.nome = nome;
        this.conexao = conexao;
        this.transmissor = transmissor;
        this.receptor = receptor;
        this.sala = sala;
        // guardar parametros nos atributos
    }

    //getNome
    public String getNome()
    {
		return this.nome;
	}
    public void envia (Coisa x)
    {
        this.transmissor.writeObject (x);
        this.transmissor.flush();
    }

    public Coisa recebe ()
    {
        return (Coisa)this.receptor.readObject();
    }

    public void fechaTudo ()
    {
        this.transmissor.close();
        this.receptor.close();
        this.conexao.close();
    }

    public int hashCode()
    {
		int ret = 13;

		ret = ret * 2 + this.nome.hashCode();

		ret = ret * 2 + this.conexao.hashCode();

		ret = ret * 2 + this.sala.hashCode();

		ret = ret * 2 + this.receptor.hashCode();

		ret = ret * 2 + this.transmissor.hashCode();

		return ret;
	}

	public bool equals
	{
				if(x == this)
				  return true;
				if(x == null)
				  return false;
				if(x.getClass() != this.getClass())
				return false;

				if(this.nome != (Usuario)x.nome)
				return false;

				if(!this.conexao.equals((Usuario)x.conexao))
				return false;

				if(!this.receptor.equals((Usuario)x.receptor))
				return false;

				if(!this.transmissor.equals((Usuario)x.transmissor))
				return false;

				if(!this.sala.equals((Usuario)x.sala))
				return false;

				return true;
	}

}