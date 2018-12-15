/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe cuidadoraDeUsuario
*/
import enviavel.AvisoDeSaidaDaSala;
import enviavel.AvisoUsuarioInexistente;
import enviavel.PedidoParaSairDaSala;
import enviavel.AvisoDeEntradaNaSala;
import enviavel.EscolhaDeNome;
import enviavel.AvisoDeSalaCheia;
import enviavel.EscolhaDeSala;
import enviavel.SalasDisponiveis;
import enviavel.Mensagem;
import enviavel.UsuarioDisponivel;
import enviavel.*;
import bd.*;
import bd.daos.*;
import bd.bdos.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class CuidadoraDeUsuario extends Thread
{
    private Socket  conexao;
    private Salas   salas;
    private Usuario usuario;

    /**
    Construtor da classe
    @param conexao � a socket
    @param salas � o vetor de salas
    @throws se algum parametro for nulo
    */
    public CuidadoraDeUsuario (Socket conexao, Salas salas) throws Exception
    {
        if(conexao == null)
          throw new Exception("parametro de conexao nulo");
        if(salas == null)
          throw new Exception("parametro de Salas nulo");

        this.conexao=conexao;
        this.salas  =salas;
    }

    /**
    M�todo para
    1� - interagir com o usu�rio via OOS e OIS ate descobrir o nome da sala em que ele deseja entrar, eventualmente, informando sala cheia
    2� - procurar em salas a sala com o nome desejado
    3� - interagir com o usu�rio via OOS e OIS ate descobrir o nome que ele deseja usar, eventualmente, informando nome invalido ou ja usado
	4� - instanciar o Usuario, fornecendo, conexao, OOS, OIS, nome e sala
    5� - fazer varias vezes this.usuario.envia(new AvisoDeEntradaDaSala(i)), onde i � o nome de algum usuario que ja estava na sala
	6� - fazer varias vezes i.envia(new AvisoDeEntradaDaSala(usuario.getNome()), onde i � o nome de algum usuario que ja estava na sala
    7� - incluir o usuario na sala
    */
    private void preSala(ObjectOutputStream paramOOS,ObjectInputStream paramOIS) throws Exception
    {
        // declarar e instanciar OOS e OIS
        ObjectOutputStream oos = paramOOS;
        ObjectInputStream ois = paramOIS;

        String[] salasExistentes = this.salas.getSalas();

        oos.writeObject(new SalasDisponiveis(salasExistentes));
        oos.flush();
        for(;;)
        {
            Coisa recebido = (Coisa)ois.readObject();

            if(recebido instanceof EscolhaDeNome)
            {
                String nomeUsuario = ((EscolhaDeNome)recebido).toString();
                
                for(;;)
                { 
                    Sala salaSelecionada = null;
                    
                    recebido = (Coisa)ois.readObject();
                    if(recebido instanceof EscolhaDeSala)
                    {               
                        System.out.println(((EscolhaDeSala)recebido).toString());
                        synchronized(this.salas)
                        {
                            salaSelecionada = this.salas.getSala(((EscolhaDeSala)recebido).toString());
                        }
                        
                        boolean estaCheia;
                        int jaExiste;
                        
                        synchronized(salaSelecionada)
                        {
                            estaCheia = salaSelecionada.isCheia();
                            jaExiste = salaSelecionada.existeUsuario(nomeUsuario);
                        }
                            
                        if(!estaCheia)
                        {
                            if(jaExiste == -1)
                            {
                                this.usuario = new Usuario(this.conexao, oos, ois, nomeUsuario, salaSelecionada);
                                String[] usuarios;
                                
                                synchronized(salaSelecionada)
                                {
                                    usuarios = salaSelecionada.getUsuarios();
                                    if(usuarios!=null)
                                    {
                                        for(int i = 0; i < salaSelecionada.getLotacao(); i++)
                                        {
                                         salaSelecionada.getUsuario(i).envia(new AvisoDeEntradaNaSala(this.usuario.getNome()));
                                        }
                                    }
                                }
                                if(usuarios != null)
                                {
                                for(String nome : usuarios)
                                {
                                        this.usuario.envia(new AvisoDeEntradaNaSala(nome));
                                }
                                }
                                synchronized(salaSelecionada)
                                {
                                        salaSelecionada.incluir(this.usuario);
                                }
                                     oos.writeObject(new UsuarioDisponivel());
                                     return;
                            }
                            else
                            {
                                oos.writeObject(new AvisoDeNomeJaExiste());
                                oos.flush();
                            break;
                            }
                        }
                    }
                    else
                    {
                        oos.writeObject(new AvisoDeSalaCheia(salaSelecionada.getNome()));
                        oos.flush();
                    }
                }              
            }
        }
    }

    /**
    M�todo que ser� executado ap�s o usu�rio entrar na sala e rodar� at� ele sair da sala
    */
    private void conversar()
    {
        try
        {
            Coisa recebido = null;
            Sala salaAtual = this.usuario.getSala();

            do
            {
                recebido = usuario.recebe();
                if(recebido instanceof Mensagem)
                {
                    Mensagem novaMsg = (Mensagem)recebido;
                    if(novaMsg.getRemetente() == null)
                    {
                        synchronized(salaAtual)
                        {
                            for(int i = 0; i < salaAtual.getLotacao(); i++)
                            {
                                if(this.usuario != salaAtual.getUsuario(i))                            
                                    salaAtual.getUsuario(i).envia(novaMsg);
                            
                            }     
                        }
                    }
                    else
                    {
                        synchronized(salaAtual)
                        {
                            if(salaAtual.existeUsuario(novaMsg.getRemetente()) > -1)
                                salaAtual.getUsuario(novaMsg.getRemetente()).envia(novaMsg);
                            else
                                this.usuario.envia(new AvisoUsuarioInexistente(novaMsg.getRemetente()));
                        }
                    }
                }
            }while (!(recebido instanceof PedidoParaSairDaSala));

            synchronized(salaAtual)
            {
                for(int i = 0; i < salaAtual.getLotacao(); i++)
                {
                    if(this.usuario != salaAtual.getUsuario(i))
                        salaAtual.getUsuario(i).envia(new AvisoDeSaidaDaSala(this.usuario.getNome()));
                }
            }
                salaAtual.excluir(this.usuario.getNome());
                this.usuario.fechaTudo();
                this.usuario = null;
            }
        catch(Exception e)
        {}
    }

    /**
    M�todo run personalizado para sobrescrever o padr�o
    */
    public void run ()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
            preSala(oos,ois);
            conversar(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}