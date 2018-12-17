import enviavel.EscolhaDeNome;
import enviavel.EscolhaDeSala;
import enviavel.Mensagem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.text.StyledDocument;
import enviavel.*;

public class JanelaChat
{
    private JFrame janela = new JFrame("sala");
    private JLabel lblLogin = new JLabel("escolha sua sala e o seu nick desejado:");
    private JLabel lblSalaAtual = new JLabel("Sala: ");
    private JButton btnConectar = new JButton("Conectar");
    private JButton btnEnviar = new JButton("Enviar");
    private JTextField txtNomeUsuario = new JTextField();
    private JTextField txtMensagem = new JTextField();
    private JComboBox salas = new JComboBox();
    private JTextPane areaChat = new JTextPane();
    private JScrollPane chat = new JScrollPane(areaChat);
    private StyledDocument doc = areaChat.getStyledDocument();
    private Socket conexao;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String nome;

    //paineis
    private JPanel painelLogin = new JPanel();
    private JPanel painelChat = new JPanel(new BorderLayout());
    private JPanel subpainelChat = new JPanel(new BorderLayout());
    private CardLayout layoutJanela = new CardLayout();
    //

    public JanelaChat(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) throws Exception
    {
        if(socket == null)
          throw new Exception("parametro socket nulo");
        this.conexao = socket;
        this.oos = oos;
        this.ois = ois;
        
        inicializa();
    }

    public void fechaTudo() 
    {   
        try
        {
            this.conexao.close();
            this.janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void exibirSalas(String[] salasDisponiveis) throws Exception
    {
        if(salasDisponiveis == null)
           throw new Exception("parametro vetor de string nulo");
        for(String sala : salasDisponiveis)
        {
          salas.addItem(sala);
        }
    }
    public void jogaErro(String erro) throws Exception
    {
        if(erro == null)
          throw new Exception("n�o ha um erro para ser mostrado");
        JOptionPane.showMessageDialog(null, erro);
    }
    

     public void iniciaConversa()
    {       
        lblSalaAtual.setText(lblSalaAtual.getText() + salas.getSelectedItem());
        layoutJanela.show(janela.getContentPane(),"chat");
        this.janela.setSize(850,500);
    }

    public void inicializa()
    {
        TratadorDeEvento tratador = new TratadorDeEvento();
        btnConectar.addActionListener(tratador);
        painelLogin.setLayout(new GridLayout(10,1));

        painelLogin.add(lblLogin);
        painelLogin.add(btnConectar);
        painelLogin.add(txtNomeUsuario);
        painelLogin.add(salas);

        txtNomeUsuario.setColumns(30);

        this.janela.setSize(750,600);
        this.janela.getContentPane().setLayout(layoutJanela);

        this.janela.addComponentListener (new TratadorDeRedimensionamento());
        this.janela.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);

        painelChat.setBorder(javax.swing.BorderFactory.createTitledBorder("Conversas"));
        painelChat.setLayout(new BorderLayout());

        areaChat.setEnabled(false);
        txtMensagem.setColumns(50);
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(tratador);
        
        
        subpainelChat.add( txtMensagem , BorderLayout.EAST);
        subpainelChat.add(btnEnviar, BorderLayout.WEST);
        painelChat.add(chat, BorderLayout.CENTER);
        painelChat.add(lblSalaAtual, BorderLayout.NORTH);
        painelChat.add(subpainelChat,BorderLayout.SOUTH);
        
        this.janela.addWindowListener(new WindowAdapter()
        {
	public void windowClosing(WindowEvent evt)  
        {
		if (JOptionPane.showConfirmDialog(null,"Deseja sair")==JOptionPane.OK_OPTION)
                {
                    try{
                    oos.writeObject(new PedidoParaSairDaSala());
                    }
                    catch(Exception e)
                    {}
                    finally
                    {
                         System.exit(0);
                    }
	        }    
        }
        });
        this.janela.setVisible(true);

        this.janela.add(painelLogin, "login");
        janela.add(painelChat,"chat");
    }

    public void mostra(String conteudo, String remetente) throws Exception
    {
        if(remetente == null || remetente.trim().equals(""))
          throw new Exception("remetente null");

        if(conteudo == null || conteudo.trim().equals(""))
           throw new Exception("texto null");

        doc.insertString(doc.getLength(), remetente +": " + conteudo + "\n",
                 doc.getStyle("pink"));
    }

    public void mostra(String conteudo, String remetente, String destino) throws Exception
    {
        if(remetente == null || destino.trim().equals(""))
                throw new Exception("destino null");

        if(remetente == null || remetente.trim().equals(""))
                throw new Exception("remetente null");

        if(conteudo == null || conteudo.trim().equals(""))
                throw new Exception("mensagem null");

        doc.insertString(doc.getLength(), "de " + remetente + "para " + destino + ": " + conteudo + "\n",  doc.getStyle("yellow"));
    }

    public void exibeAvisoMovimento(String aviso) throws Exception
    {
        if(aviso == null || aviso.trim().equals(""))
           throw new Exception("aviso null");
 
        doc.insertString(doc.getLength(),aviso + "\n",
        doc.getStyle("bold"));
    }


    private class TratadorDeRedimensionamento implements ComponentListener
    {
        public void componentResized(ComponentEvent e)
        {
            Font fonte = new Font("Times New Roman", Font.PLAIN, Math.min(janela.getHeight(),janela.getWidth())*22/600);
            btnEnviar.setFont(fonte);
            lblLogin.setFont(fonte);
            lblSalaAtual.setFont(fonte);
            chat.setFont(fonte);
            btnConectar.setFont(fonte);
            salas.setFont(fonte);
            txtMensagem.setFont(fonte);
            txtNomeUsuario.setFont(fonte);
        }

        public void componentMoved(java.awt.event.ComponentEvent e)
        {}

        public void componentShown(java.awt.event.ComponentEvent e)
        {}

        public void componentHidden(java.awt.event.ComponentEvent e)
        {}
    }

    private class TratadorDeEvento implements ActionListener
    {        
        public void trateClickNoBotaoConectar()
        {
            try
            {
                //ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
                
                String s = salas.getSelectedItem().toString();
                String n = txtNomeUsuario.getText();
                if(n == null || n.trim().equals("") || n.length() >30 || n.contains("(") || n.contains(")"))
                {
                   jogaErro("Seu nome esta vazio ou possui um dos caracteres: '(' ')' porfavor corrigia-o");
                }
                else
                {
                    oos.writeObject(new EscolhaDeNome(n));
                    oos.flush();
                    oos.writeObject(new EscolhaDeSala(s));
                    oos.flush();
                    nome = txtNomeUsuario.getText().trim();
                }
            }
            catch(Exception err)
            {
            System.out.println("fudeu");
            }
        }

        public void trateClickNoBotaoEnviar()
        {
            String s = txtMensagem.getText().trim();
            try
            {
                
                if(s != null || !(s.trim().equals("")))
                {
                    if(s.startsWith("(") || s.contains(")"))
                    {
                       String nomeDestino = s.substring((s.indexOf("(") + 1), (s.indexOf(")")));
                       String conteudo = s.substring(s.indexOf(")")+1);
                       oos.writeObject(new Mensagem(conteudo, nome,nomeDestino));
                       oos.flush();
                       txtMensagem.setText("");            
                       mostra(conteudo,nome, nomeDestino);
                    }
                    else
                    {
                    mostra(s, "Você");                   
                    oos.writeObject(new Mensagem(s, nome + ": "));
                    oos.flush();
                    txtMensagem.setText("");
                    }
                }
            }
            catch(Exception err)
            {}
        }

        public void actionPerformed (ActionEvent e)
        {
            String comando = e.getActionCommand();

            if(comando == "Conectar")
            {
                trateClickNoBotaoConectar();
            }
            if(comando == "Enviar")
            {
                trateClickNoBotaoEnviar();
            }
        }
    }
}