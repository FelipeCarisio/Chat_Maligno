/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe sala
*/

import java.util.ArrayList;
public class Sala
{
  protected ArrayList<Usuario> usuarios;
  protected String nome;
  protected int capacidade, lotacao;

  public Sala( String nom, int cap) throws Exception
  {
	  if(nom == null)
	  throw new Exception("nome nulo");
	  if(cap < 1)
	  throw new Exception("capacidade invalida");

	  this.nome = nom;
	  this.capacidade = cap;
      this.usuarios = new ArrayList(cap);
      this.lotacao = 0;
  }

  /**
  Getter da lotacao da sala
  */
  public int getLotacao()
  {
	  return this.lotacao;
  }

  /**
  Getter da capacidade
  */
  public int getCapacidade()
  {
	  return this.capacidade;
  }

  /**
  Getter do nome da sala
  */
  public String getNome()
  {
	  return this.nome;
  }


  /**
  M�todo que verifica se um usu�rio existe
  @param nom � o nome do usu�rio desejado
  @throws se o parametro for nulo
  */
  public int existeUsuario(String nom) throws Exception
  {
    if(nom == null)
       throw new Exception("parametro nulo");

    int local = -1;
    for(int i = 0; i < this.usuarios.size(); i++)
    {
        if (this.usuarios.get(i).getNome() == nom)
            local = i;
    }
    return local;
  }

  /**
  Getter de usuario que retorna um usuario numa posicao
  @param i � o indice que ele quer do vetor
  @throws se o parametro for nulo
  */
  public Usuario getUsuario(int i)throws Exception
  {
    if( i < usuarios.size())
        return usuarios.get(i);
    else
    throw new Exception("indice fora do vetor");
  }

  /**
  Getter de usuarios que retorna um vetor
  */
  public String[] getUsuarios()
  {
    String[] users = null;
    if (this.lotacao == 0)
    {
       return users;
    }
    users =new String[this.capacidade];
   for(int i = 0; i < this.usuarios.size(); i++)
           {
                   users[i] = this.usuarios.get(i).getNome();
       }
   return users;
  }

  /**
  M�todo booleano que fala se a sala est� ou n cheia
  */
  public boolean isCheia()
  {
    if(this.lotacao < this.capacidade)
       return false;
    return true;
  }

  /**
  Getter de usu�rio que retorna um usu�rio com o nome desejado
  @param nom � o nome desejado
  @thtows se o usu�rio n�o existir ou se o parametro for nulo
  */
  public Usuario getUsuario(String nom) throws Exception
  {
    if(nom == null)
       throw new Exception("parametro nulo");

    int local = existeUsuario(nom);
    if(local > -1)
       return this.usuarios.get(local);
    else
    throw new Exception("esse usuario n�o existe");
  }

  /**
  M�todo que exclui um usu�rio
  @param nom � o nome do usu�rio a ser excluido
  @throws se o usu�rio n�o existir ou se o parametro for nulo
  */
  public void excluir(String nom) throws Exception
  {
    if(nom == null)
       throw new Exception("parametro nulo");

    int local = existeUsuario(nom);
            if(local > -1)
            {
               this.usuarios.remove(local);
               this.lotacao--;
           }
            else
    throw new Exception("esse usuario n�o existe");
  }

  /**
  M�todo que inclui um usu�rio
  @param nom � o nome do usu�rio a ser incluido
  @throws se o a sala estiver cheia ou se o parametro for nulo
  */
  public void incluir(Usuario user) throws Exception
  {
    if(user == null)
       throw new Exception("parametro nulo");

    if(!isCheia())
    {
       this.usuarios.add(user);
       this.lotacao++;
    }
    else
    throw new Exception("sala cheia");
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

    Sala ret = (Sala)x;
    if(this.nome != ret.nome)
    return false;

    if(this.capacidade != ret.capacidade)
    return false;

    if(this.lotacao != ret.lotacao)
    return false;

    if (this.lotacao > 0)
    {
      for(int i = 0; i < this.usuarios.size(); i++)
      {
        if(!ret.usuarios.contains(this.usuarios.get(i)))
          return false;
      }
    }

    return true;
  }

  /**
  hashCode da classe
  */
  public int hashCode()
  {
	  int ret = 34;
	  ret = ret * 3 + this.nome.hashCode();
	  ret = ret * 3 + this.lotacao;
	  ret = ret * 3 + this.capacidade;
	  for(int i = 0; i < this.lotacao ; i++)
	  	  {
	  		  ret = ret * 3 + this.usuarios.get(i).hashCode();
	      }
	      return ret;
  }

  /**
  toString da classe
  */
  public String toString()
  {
	  String ret;
	  ret = "A sala " + this.nome + " possui " + this.lotacao + " de " + this.capacidade + " sendo os participantes:" + "\n";
	  String[] nomes = getUsuarios();
	  if (nomes == null)
	      ret += "N�o h� nenhum participante";
	  else
	  {
	   for(int i = 0; i < this.lotacao ; i++)
	   {
	 	  ret += nomes[i] + "\n";
	   }
      }
	  return ret;
  }
}