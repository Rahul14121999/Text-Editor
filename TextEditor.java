import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;

public class TextEditor implements ActionListener{
    JFrame myframe;
    JMenuBar menubar;
    JTextArea textArea;
    TextEditor()
    {
        myframe=new JFrame("Text Editor");
        myframe.setBounds(100,100,800,700);
                    //initialising menu bar
        menubar=new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
                    //creating menu items for file menu
        JMenuItem newfile = new JMenuItem("New File");
        JMenuItem open = new JMenuItem("Open File");
        JMenuItem print = new JMenuItem("Print File");
        JMenuItem save = new JMenuItem("Save File");
        file.add(newfile);
        file.add(open);
        file.add(save);
        file.add(print);
                    //providing functions through action listener
        newfile.addActionListener(this);
        open.addActionListener(this);
        print.addActionListener(this);
        save.addActionListener(this);
                    // creating menu items for edit menu
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem close = new JMenuItem("Close");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(close);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        close.addActionListener(this);
                    // adding file menu and edit menu to menubar
        menubar.add(file);
        menubar.add(edit);
        myframe.setJMenuBar(menubar);           // adding menu bar to the frame
                    // initialising textarea
        textArea=new JTextArea();
        myframe.add(textArea);
        myframe.setVisible(true);

    }
    public static void main(String arg[])
    {
        TextEditor obj = new TextEditor();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String call = e.getActionCommand();
        if(call == "New File")
        {
            textArea.setText("");
        }
        else if(call=="Cut")
        {
            textArea.cut();
        }
        else if(call=="Copy")
        {
            textArea.copy();
        }
        else if(call=="Paste")
        {
            textArea.paste();
        }
        else if(call=="Close")
        {
            myframe.setVisible(false);
        }
        else if(call=="Save File")
        {               //steps to implement save file
            JFileChooser fileChooser = new JFileChooser("C:");          // file chooser to choose where we want to save our file
            int ans= fileChooser.showOpenDialog(null);        //show open button and store ans what user click i.e. open or cancel
            if(ans== JFileChooser.APPROVE_OPTION)                     // if user clicks open button
            {   // create file object to store absolute path of approved or selected file
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                // create buffer writer
                BufferedWriter writer = null;
                // use try and catch to handle exceptions and perform operations on writer

                try {
                    writer=new BufferedWriter(new FileWriter(file,false));// append false becoz i dont want to write any extra thing other than that written in our actual file
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.flush(); //it is a best practice to clear writer after ur operation is done
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(call=="Open File")
        {
            JFileChooser fileChooser = new JFileChooser("C:"); //use file chooser to choose the file u want to open
            int ans = fileChooser.showOpenDialog(null);
            if(ans==JFileChooser.APPROVE_OPTION)
            {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); // get the path of selected file
                try {
                    String s1="",s2=""; //take 2 empty strings
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    try {
                        s2=reader.readLine();           //read the first line
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    while(true)
                    {
                        try {
                            if (!((s1=reader.readLine())!=null)) break;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        s2+=s1+"\n";        //\n for line break
                    }
                    textArea.setText(s2);       //set text of s2 inside text area
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(call=="Print File")
        {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

