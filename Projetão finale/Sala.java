
public class Sala
{
  protected Arraylist<Usuario> usuarios;
  protected String nome;
  protected int capacidade, lotacao;

  public Sala( String nom, int cap) throws Exception
  {
	  if(nom == null)
	  throw new Exception("nome nulo");
	  if(cap == null)
	  throw new Exception("cap nulo");

	  this.nome = nom;
	  this.capacidade = cap;
      this.usuarios = new ArrayList(cap);
      this.lotacao = 0;
  }

  public int getLotacao()
  {
	  return this.lotacao;
  }

  public int getCapacidade()
  {
	  return this.capacidade;
  }

  public String getNome()
  {
	  return this.nome;
  }

  public int existeUsuario(String nom)
  {
	  int local = -1;
	  for(int i = 0, i < this.usuarios.size(); i++)
	  {
		  if (this.usuarios.get(i).getNome() == nom)
		      local = i;
	  }
	  return local;
  }


  public String[] getUsuarios()
  {
	  if (this.capacidade == 0)
	      throw new Exception("a sala esta vazia");
	  String[this.capacidade] users;
	  for(int i = 0, i < this.usuarios.size(); i++)
	  	  {
	  		  users[i] = this.usuarios.get(i).getNome();
	      }
	  return users;
  }

  public Usuario getUsuario(String nom) throws Exception
  {
	  int local = existeUsuario(nom);
	  if(local > -1)
	     return this.usuarios.get(local);
	  else
	  throw new Exception("esse usuario não existe");
  }
  public void excluir(String nom)
  {
	  int local = existeUsuario(nom);
	  	  if(local > -1)
	  	  {
	  	     this.usuarios.remove(local);
	  	     this.capacidade--;
		 }
	  	  else
	  throw new Exception("esse usuario não existe");
  }
  public void incluir(Usuario)
  {
    if(this.lotacao < this.capacidade)
    {
       this.Usuarios.add(Usuario)
       this.Lotacao++;
    }
    else(throw new Exception("sala cheia");
  }



  public int hashCode()
  {
	  int ret = 34;
	  ret = ret * 3 + this.nome.hashCode();
	  ret = ret * 3 + this.lotacao.hashCode();
	  ret = ret * 3 + this.capacidade.hashCode();
	  for(int i = 0; i < this.lotacao ; i++)
	  	  {
	  		  ret = ret * 3 + this.salas.get(i).hashCode();
	      }
  }
  public String toString()
  {
	  String ret;
	  ret = "A sala " + this.nome + " possui " + this.lotacao " de " + this.capacidade + " sendo os participantes:" + "\n";
	  String[] nomes = getUsuarios();
	  for(int i = 0; i < this.lotacao ; i++)
	  {
		  ret += String[i] + "\n";
	  }

	  return ret;
  }
}