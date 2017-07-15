/* Name: Phu Pham, Anh Vo
 * Course: CSE223
 * Date: June 11 2017
 * The code : Server Chat
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
import java.net.ServerSocket;
import java.net.Socket;


/*
 * The Server with its GUI
 */
public class ServerMain extends JFrame implements ActionListener, Runnable {

    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedWriter output;
    private BufferedReader bufferInput;
    private JLabel label;
    // user entry
    private JTextArea tf;

    // to send data to server
    private JButton send;
    // for the data received from server
    private JTextArea ta;
    private int port;

    //port name text box
    private JTextField serverPortText;
    //button to start the server
    private JButton start;
    // to start the whole thing the server
    
    
    public static void main(String[] args) {
        ServerMain server = new ServerMain();
    }
    
    // Constructor 
    ServerMain() {
        super("Chat Server"); // the title
       
        serverPortText = new JTextField(20);
        start = new JButton("Start"); // Start button
        start.addActionListener(this);
        JPanel connectionDetailsPanel = new JPanel(new GridLayout(1,4));
        connectionDetailsPanel.add(new JLabel("Server port: "));
        connectionDetailsPanel.add(serverPortText);
        connectionDetailsPanel.add(start);
        setResizable(false);
        JPanel northPanel = new JPanel(new GridLayout(4, 1));
        northPanel.add(connectionDetailsPanel);

        // the Label and the JTextArea
        label = new JLabel("Enter data to send to client", SwingConstants.CENTER);
        northPanel.add(label);
        tf = new JTextArea("", 5, 80); // The division of size of parts 
        tf.setBackground(Color.WHITE);
        northPanel.add(new JScrollPane(tf)); // creating a scrollbar
        
        // the  button
        send = new JButton("Send");
        send.setEnabled(false);
        tf.setEnabled(false);
        send.addActionListener(this);
        northPanel.add(send); // add the button Sent
        add(northPanel, BorderLayout.NORTH); // make the panel above

        // The CenterPanel which is the chat room
        ta = new JTextArea("From client:\n", 100, 100); 
        ta.setEditable(false);
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.add(new JScrollPane(ta)); // creating a scrollbar
        ta.setEditable(false);
        add(centerPanel, BorderLayout.CENTER); // the postion on the center

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 650); // the size of a whole panel 
        setVisible(true);
        serverPortText.requestFocus();

    }

    public void startServer() {
        try { server = new ServerSocket(port);        
            display("Server started. Please wait for the client to connect"); //
            socket = server.accept(); // accept the server port
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //write data into socket
            // flush output buffer to send header information
            output.flush(); // close the data page 
            bufferInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));// take out data from socket
            tf.setEnabled(true); // set enable for the textfield
            send.setEnabled(true); // set enable for the button Send
            start.setEnabled(false);// set unable for start button.
            	while (true)  // make an infinite loop
            			{try {while (!bufferInput.ready())
            				{ } //when steam ready to read                  
            		String output = bufferInput.readLine(); // Received the data from socket
                    while (output != null) 
                        {append(output + "\n");
                        output = bufferInput.readLine();} }
                    
                 catch (IOException ioe) {}    }        
       		} catch (IOException ioe) { }  
    }

    // called to append 
    //text in the TextArea 
    void append(String str)  
    	{ ta.append(str);}

    

    // Start the Server
    private void connect() {
        // Server port cannot be Empty
        if (serverPortText.getText() == null || serverPortText.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Please Choose a Server port"); //The message alerts user type port
            return;}
        
        //set server port
        this.port = Integer.parseInt(serverPortText.getText()); // Take port number from an user

        //run the code with thread
        Thread t = new Thread(this);
        t.start();}
    

    /*
	* Button clicked
     */
    public void actionPerformed(ActionEvent e) {
        //if Start button is clicked
        if (e.getSource() == start) {
            connect();
            return;
        }

        //if user did not enter data
        String data = tf.getText();// declare a string from the text field on south
        if (data == null || data.length() == 0) { // if it's empty
            JOptionPane.showMessageDialog(null, "Enter data to send"); // Message to user enter info
            return;
        }
        try {
            output.write(data + "\n");
            output.flush(); 
        } catch (IOException ioe) {
            display("Sending error: " + ioe.getMessage()); // alert error
        }

    }

    @Override
    public void run() 
      {startServer();}
    

   

    /**
     * to display msgs in Joptionpane
     *
     * @param string
     */
    private void display(String string) {
        JOptionPane.showMessageDialog(null, string);
    }

}
