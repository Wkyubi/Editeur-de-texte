import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class EditeurTexte extends Component implements ActionListener {

    JFrame frame = new JFrame("Mon Editeur");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fichier = new JMenu("Fichier");
    private JMenu editer = new JMenu("Editer");
    private JMenu quitter = new JMenu("Quitter");
    private JMenu aide = new JMenu("?");


    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem sauver = new JMenuItem("Sauver");
    private JMenuItem quitter2 = new JMenuItem("Quitter");
    private JMenuItem copier = new JMenuItem("Copier");
    private JMenuItem couper = new JMenuItem("Couper");
    private JMenuItem coller = new JMenuItem("Coller");
    private JMenuItem aide1 = new JMenuItem("Aide");

    private JButton sortie = new JButton(new ImageIcon("logo_sortie.jpg"));
    private JButton copier_icon = new JButton(new ImageIcon("icon_copier.png"));
    private JButton couped_icon = new JButton(new ImageIcon("icon_couped.jpg"));
    private JButton icon_colled = new JButton(new ImageIcon("colled.jpg"));


    private JTextArea textArea = new JTextArea();

    private JScrollPane scroll = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    private JTextField adresse = new JTextField();
    private BufferedReader fis;
    private BufferedWriter out;

    // Fenetre de dialogue sur la "croix"

    class ExitListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "sortie de l'application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION)
                System.exit(0);

            else if (option == JOptionPane.NO_OPTION){
                ;
            }
        }
    }


    public EditeurTexte() {

        frame.setMinimumSize(new Dimension(200,200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new ExitListener());


        //Menu Fichier
        this.fichier.add(ouvrir);
        this.fichier.add(sauver);
        this.fichier.add(quitter2);
        this.menuBar.add(fichier);

        // Fenetre de dialogue sur le menu
        quitter.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JOptionPane jop = new JOptionPane();
                int option = jop.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "sortie de l'application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) System.exit(0);
            }
            @Override
            public void menuDeselected(MenuEvent e) { }
            @Override
            public void menuCanceled(MenuEvent e) { }
        });


        //Menu Editer
        this.editer.add(copier);
        this.editer.add(couper);
        this.editer.add(coller);
        this.menuBar.add(editer);

        //Menu Quitter
        this.menuBar.add(quitter);

        this.aide.add(aide1);
        this.menuBar.add(aide);

        //Icon de menuBar
        copier_icon.setPreferredSize(new Dimension(25, 25));
        this.menuBar.add(copier_icon);
        copier_icon.setPreferredSize(new Dimension(25, 25));
        this.menuBar.add(couped_icon);
        couped_icon.setPreferredSize(new Dimension(25, 25));
        this.menuBar.add(icon_colled);
        icon_colled.setPreferredSize(new Dimension(25, 25));
        menuBar.add(Box.createHorizontalGlue()); // Mettre icon à droite
        this.menuBar.add(sortie);
        sortie.setPreferredSize(new Dimension(25, 25));
        sortie.setBackground(Color.WHITE);
        copier_icon.setBackground(Color.WHITE);
        couped_icon.setBackground(Color.WHITE);
        icon_colled.setBackground(Color.WHITE);

        sortie.addActionListener(this);
        ouvrir.addActionListener(this);
        sauver.addActionListener(this);
        quitter.addActionListener(this);
        quitter2.addActionListener(this);
        couper.addActionListener(this);
        coller.addActionListener(this);
        copier.addActionListener(this);
        icon_colled.addActionListener(this);
        copier_icon.addActionListener(this);
        couped_icon.addActionListener(this);
        aide1.addActionListener(this);


        frame.setJMenuBar(menuBar);


        // Zone de texte
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        scroll.setPreferredSize(new Dimension(500, 400));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);



        frame.pack();
        frame.setVisible(true);

    }
    // ____________   ACTION EVENT   ___________  //

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ouvrir) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(".")); // Permet d'ouvrir à partir du dossier source

            FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
            fc.setFileFilter(ff);

            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String nomFic = fc.getSelectedFile().getAbsolutePath();
                try {
                    try {
                        fis = new BufferedReader(new FileReader(nomFic));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    String line = new String();
                    String s = new String();
                    while ((line = fis.readLine()) != null) {
                        s += line + "\n";
                    }
                    textArea.setText(s);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (e.getSource() == sauver) {
            JFileChooser fc2 = new JFileChooser();
            fc2.setCurrentDirectory(new File(".")); //Permet de sauver à partir du dossier source
            int resultatSauver = fc2.showSaveDialog(this);
            if (resultatSauver == JFileChooser.APPROVE_OPTION)
            {
                String fichier = fc2.getSelectedFile().toString();
                if (fichier.endsWith(".txt") || fichier.endsWith(".TXT")) {
                    ;
                } else {
                    fichier = fichier + ".txt";
                }
                try {
                    out = new BufferedWriter(new FileWriter(fichier));
                    out.write(textArea.getText());
                    out.close();
                } catch (IOException er) {
                    ;
                }
            }


        } else if (e.getSource() == quitter2 || e.getSource() == sortie)

        {
            //Boite de dialogue

            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null, "Voulez vous vraiment quitter ?", "sortie de l'application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) System.exit(0);

        } else if (e.getSource() == copier || e.getSource() == copier_icon)

        {
            textArea.copy();
        } else if (e.getSource() == coller || e.getSource() == icon_colled)

        {
            textArea.paste();
        } else if (e.getSource() == couper || e.getSource() == couped_icon)

        {
            textArea.cut();
        }
        else if (e.getSource() == aide1)
        {
            JOptionPane jop1 = new JOptionPane();
            jop1 = new JOptionPane();
            jop1.showMessageDialog(null, "Tiens, de l'aide.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void main(String[] args) {
        EditeurTexte editeur = new EditeurTexte();

    }
}







