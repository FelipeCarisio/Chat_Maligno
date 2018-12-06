
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

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
                    Sala sala) throws Exception // se parametro nulos
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

	public Sala getSala()
	{
		return this.sala;
	}
    public void envia (Coisa x) throws Exception
    {
		if(x == null)
			throw new Exception("O objeto esta nulo");
        this.transmissor.writeObject(x);
        this.transmissor.flush();
    }

    public Coisa recebe ()  throws Exception
    {
		Object obj = this.receptor.readObject();

		if(!(obj instanceof Coisa))
			throw new Exception("O objeto não é valido pois não é enviavel");

		return (Coisa)obj;
    }

    public void fechaTudo () throws Exception
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

	public String toString()
	{
		String ret = "O usuario de nome: " + this.nome + " pertence à sala: " + this.sala.getNome();
		return ret;
	}

	public boolean equals (Object x)
	{
				if(x == this)
				  return true;
				if(x == null)
				  return false;
				if(x.getClass() != this.getClass())
				return false;

                Usuario ret = (Usuario)x;
				if(this.nome != ret.getNome())
				return false;

				if(!this.conexao.equals(ret.conexao))
				return false;

				if(!this.receptor.equals(ret.receptor))
				return false;

				if(!this.transmissor.equals(ret.transmissor))
				return false;

				if(!this.sala.equals(ret.sala))
				return false;

				return true;
	}

}