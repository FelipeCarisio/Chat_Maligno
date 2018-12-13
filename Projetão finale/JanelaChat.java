import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.text.StyledDocument;
import enviavel;

public class JanelaChat
{
	private JFrame janela = new JFrame("sala");
	private JLabel lblLogin = new JLabel("escolha sua sala e o seu nick desejado:");
	private JLabel lblSalaAtual = new JLabel("Sala: ");
	private JButton btnConectar = new JButton("conectar");
	private JButton btnEnviar = new JButton("Enviar =>");
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
	private JPanel painelChat = new JPanel();

	//

	public JanelaChat(Socket socket) throws Exception
	{
		if(socket == null)
		  throw new Exception("parametro socket nulo");
		this.conexao = socket;
		this.oos = new ObjectOutputStream(this.conexao.getOutputStream());
		inicializa();
	}

    public fechaTudo()
    {
		this.conexao.close();
		this.transmissor.close();
		this.janela.close();
	}
	public exibirSalas(String[] salasDisponiveis) throws Exception
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
		  throw new Exception("não ha um erro para ser mostrado");
		JOptionPane.showMessageDialog(null, erro);
	}

    public void iniciaConversa()
    {
		this.janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.nome = txtNomeUsuario.getText().trim();
		lblSalaAtual.setText(lbl.SalaAtual.getText() + salas.getSelectedItem());
		this.janela.remove(painelLogin);

		painelChat.setBorder(javax.swing.BorderFactory.createTitledBorder("Conversas"));
		painelChat.setLayout(new BorderLayout());

        TratadorDeEventos tratador = new TratadorDeEventos();
		chat.setEditable(false);
		btnEnviar.setText("Enviar");
		btnEnviar.addActionListener(tratador);

		painelChat.add(btnEnviar);
		painelChat.add(chat);
		painelChat.add(lblSalaAtual);
		painelChat.add(txtMensagem);

		this.janela.setSize(850,500);

	}

	public void inicializa()
	{
		TratadorDeEvento Tratador = new TratadorDeEvento();
		btnConectar.addActionListener(Tratador);
		painelLogin.setLayout(new GridLayout(10,1));

		painelLogin.add(lblLogin);
		painelLogin.add(btnConectar);
		painelLogin.add(txtNomeUsuario);
		painelLogin.add(salas);

		txtNomeUsuario.setColumns(30);

		this.janela.setSize(750,600);
		this.janela.getContentPane().setLayout(new BorderLayout());

		this.janela.addComponentListener (new TratadorDeRedimensionamento());
		this.janela.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.janela.setVisible(true);

		this.janela.add(painelLogin, BorderLayout.CENTER);
	}

    public void mostra(String conteudo, String remetente) throws Exception
    {
		if(remetente == null || remetente.trim().equals(""))
			throw new Exception("remetente null");

		if(conteudo == null || conteudo.trim().equals(""))
			throw new Exception("texto null");

		doc.insertString(doc.getLength(), remetente + ": " + conteudo + "\n",
                         doc.getStyle("pink"));

	}

	 public void mostra(String conteudo, String remetente,String destino) throws Exception
	    {
			if(remetente == null || destino.trim().equals(""))
				throw new Exception("destino null");

			if(remetente == null || remetente.trim().equals(""))
				throw new Exception("remetente null");

			if(conteudo == null || conteudo.trim().equals(""))
				throw new Exception("mensagem null");

			doc.insertString(doc.getLength(), "de:" + destino + "para: " remetente + ": " + conteudo + "\n",
	                         doc.getStyle("yellow"));

	}

	public void exibeAvisoMovimento(String aviso)
	{
		if(aviso == null || aviso.trim().equals(""))
					throw new Exception("aviso null");

				 doc.insertString(doc.getLength(),aviso,
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
   				String s = salas.getSelectedItem()+"";
   				String n = txtNome.getText();
   				if(n = null || n.trim.equals(""))
   				{
                  jogaErro("Seu nome esta vazio ou possui um dos caracteres: '(' ')' porfavor corrigia-o");
				}
				else
				{
				transmissor.writeObject(new EscolhaDeNome(n))
   				transmissor.flush();
   				transmissor.writeObject(new EscolhaDeSala(s));
   				transmissor.flush();
			    }
   			}
   			catch(Exception err)
   			{}
   		}

   		public void trateClickNoBotaoEnviar()
   		{
   			String s = txtMensagem.getText();

   			try
   			{
   				if(s != null || !(s.trim().equals("")))
   				{
					if(s.startsWith("(" || s.contains(")"))
					{
						String nomeDestino = s.substring((s.indexOf("(") + 1), (s.indexOf(")") + 1))
						mostra(s,);
					}
   					mostra(s, "Você: ");
                    String conteudo = nome +": " + s;
   					transmissor.writeObject(new Mensagem(conteudo));
   					transmissor.flush();
   					txtEnviar.setText("");
   				}
   			}
   			catch(Exception err)
   			{}
   		}

   		public void trateClickNoBotaoEnviarPriv()
   		{
   			String s = txtEnviarPriv.getText();

   			try
   			{
   				if(s != null || !(s.trim().equals("")))
   				{
   					mostraPriv(s, "Você(para: " + cbxUsuariosDisp.getSelectedItem() + ")");
   					transmissor.writeObject(new Mensagem(cbxUsuariosDisp.getSelectedItem() + "", nomeUsuario,s));
   					transmissor.flush();
   					txtEnviarPriv.setText("");
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