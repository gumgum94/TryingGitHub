/* Name: Phu Pham, Anh Vo
 * Course: CSE223
 * Date: June 11 2017
 * The code : Client chat
 */

//Declare the library
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * The Client with GUI
 */
public class ClientMain extends JFrame implements ActionListener, Runnable {
    //socket
    private Socket socket = null;
    //to write to socket
    private BufferedWriter output = null;
    //to read from socket
    private BufferedReader bufferInput;
    private JLabel label;
    // user entry
    private JTextArea tf;
    // to send data to server
    private JButton send;
    // for the data received from server
    private JTextArea ta;
    //server name
    private String serverName;
    //server port
    private int serverPort;
    //data to send
    private String data;
    //server name text box
    private JTextField serverNameText;
    //port name text box
    private JTextField serverPortText;
    //buttont o connect to server
    private JButton connect;
    
    
    // to start the whole thing the client
    public static void main(String[] args) {
         new ClientMain();

    }
    // Constructor 
    ClientMain() {
        //setting title
        super("Chat Client");

        //setting up GUi components
        serverNameText = new JTextField(20);	// The name should be localhost
        serverPortText = new JTextField(20);	// The same as port
        connect = new JButton("Connect"); // Declare the connection
        connect.addActionListener(this); // for action of button
        JPanel connectionDetailsPanel = new JPanel(new GridLayout(2, 3)); // layout of button and text area
        connectionDetailsPanel.add(new JLabel("Server name or IP: ")); // The label
        connectionDetailsPanel.add(serverNameText);
        connectionDetailsPanel.add(new JLabel(""));// Separate line for the text field
        connectionDetailsPanel.add(new JLabel("Server port: "));
        connectionDetailsPanel.add(serverPortText);
        connectionDetailsPanel.add(connect);

        JPanel northPanel = new JPanel(new GridLayout(4, 1));
        northPanel.add(connectionDetailsPanel);
        // the Label and the JTextArea
        label = new JLabel("Enter data to send to server:", SwingConstants.CENTER);
        northPanel.add(label);
        tf = new JTextArea("", 5, 20); // setup text area
        tf.setBackground(Color.WHITE);
        northPanel.add(new JScrollPane(tf)); // create a bar scroll 
        // the  button
        send = new JButton("Send"); // The button connection
        send.setEnabled(false);// Setup the buttons
        tf.setEnabled(false);
        send.addActionListener(this);

        northPanel.add(send);
        add(northPanel, BorderLayout.NORTH); // put the layout on the North

        // The CenterPanel which is the chat room
        ta = new JTextArea("From server:\n", 80, 80);
        ta.setEditable(false);
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));// the Panel on center
        centerPanel.add(new JScrollPane(ta)); // setup the scrollbar 
        ta.setEditable(false);
        add(centerPanel, BorderLayout.CENTER);
        setResizable(false);
        
        //windows proporties
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        serverNameText.requestFocus();

    }

    // called to append text in the TextArea 
    void append(String str) {
        ta.append(str);

    }

    // Connect to Server
    private void connect() {
        serverName = serverNameText.getText();
        // Server Name cannot be Empty
        if (serverName == null || serverName.trim().length() == 0) { // if the texfield is empty
            JOptionPane.showMessageDialog(this, "Please Choose a Server Name or IP Address."); // Alert message
            serverNameText.requestFocus();
            return;}  
         // Server port cannot be Empty
        if (serverPortText.getText() == null || serverPortText.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this,"Please Choose a Server port"); //Alert message
            serverPortText.requestFocus();
            return;}
        
        //set server port
        this.serverPort = Integer.parseInt(serverPortText.getText()); // take the port from user input.

        //run the code
        Thread t = new Thread(this); // declare the thread
        t.start();         } // start the thread
    


    /*
	* Button clicked
     */
    public void actionPerformed(ActionEvent e) 
        //if connect button is clicked
      {if (e.getSource() == connect) {
            connect(); // for ActionListener
            return;}
        
        //if user did not enter data
        data = tf.getText();
      if (data == null || data.trim().length() == 0) { 
       	// if the text field is empty
        JOptionPane.showMessageDialog(null, "Enter data to send"); 
        data = null;
        return; }
       
        //send data to server
        try {
            if (data != null) { // if the text is not empty
                output.write(data + "\n");// write it into socket server
                output.flush();} //close
        	} 	        
         catch (IOException ioe) {
            display("Sending error: " + ioe.getMessage());}        
    }

    //to start the server 
    @Override
    public void run() {
        try {
            //create a socket using sever name or IP and port
            socket = new Socket(serverName, serverPort);
            // set up  stream
            start();
            //if everything is good, enable controls on GUI
            send.setEnabled(true); // setup buttons
            tf.setEnabled(true);
            connect.setEnabled(false);
            //catch execeptions if any
        } catch (UnknownHostException uhe) {
            display("Host unknown: " + uhe.getMessage()); // Alert not the right host/ IP
        } catch (IOException ioe) {
            display("Unexpected exception: " + ioe.getMessage());
        }
        //keep listening to incoming messages
        while (true) { // the infinite loop
            try {
                while (!bufferInput.ready()) { // when steam ready to read
                }
                String output = bufferInput.readLine(); // Received the data from socket
                while (output != null) 
                    {append(output + "\n");
                    output = bufferInput.readLine();} }               
            catch (IOException ioe) {ioe.getMessage();}	
        			}		 }

    
    //set-up stream
    private void start() throws IOException {
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//write data into socket
        // flush output buffer to send header information
        output.flush(); // close
        // set up input stream
        bufferInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));  }
  


    /**
     * to display messages in JOptionPane
     *
     * @param string
     */
    private void display(String string) {
        JOptionPane.showMessageDialog(null, string);
    }

}
