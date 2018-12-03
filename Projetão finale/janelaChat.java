public class janelaVisualChat extends javax.swing.JFrame 
{
  public janelaVisualChat() 
  {
          initComponents();
  }
  
  java.awt.EventQueue.invokeLater(new Runnable() 
  {
      public void run() 
      {
        new janelaVisualChat().setVisible(true);
      }
  });
  
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}