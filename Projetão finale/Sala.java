/**
@author Felipe Carisio, Ivan Knobel, João Augusto

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
  Método que verifica se um usuário existe
  @param nom é o nome do usuário desejado
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
  @param i é o indice que ele quer do vetor
  @throws se o parametro for nulo
  */
  public Usuario getUsuario(int i)throws Exception
  {
	  if(i == null)
         throw new Exception("parametro nulo")

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

	  String[] users = new String[this.capacidade];
	   if (this.lotacao == 0)
	   {
	      return users;
	   }
	  for(int i = 0; i < this.usuarios.size(); i++)
	  	  {
	  		  users[i] = this.usuarios.get(i).getNome();
	      }
	  return users;
  }

  /**
  Método booleano que fala se a sala está ou n cheia
  */
  public boolean isCheia()
  {
	  if(this.lotacao < this.capacidade)
	     return false;
	  return true;
  }

  /**
  Getter de usuário que retorna um usuário com o nome desejado
  @param nom é o nome desejado
  @thtows se o usuário não existir ou se o parametro for nulo
  */
  public Usuario getUsuario(String nom) throws Exception
  {
	  if(nom == null)
	     throw new Exception("parametro nulo");

	  int local = existeUsuario(nom);
	  if(local > -1)
	     return this.usuarios.get(local);
	  else
	  throw new Exception("esse usuario não existe");
  }

  /**
  Método que exclui um usuário
  @param nom é o nome do usuário a ser excluido
  @throws se o usuário não existir ou se o parametro for nulo
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
	  throw new Exception("esse usuario não existe");
  }

  /**
  Método que inclui um usuário
  @param nom é o nome do usuário a ser incluido
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
	      ret += "Não há nenhum participante";
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