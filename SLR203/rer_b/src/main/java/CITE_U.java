import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CITE_U {
    private JPanel citeU;
    private JScrollPane displayPanel;
    private JButton REFRESHButton;
    private JTable dataTable;
    private TableContent tc;

    public CITE_U() {
        REFRESHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tc = new TableContent();
                dataTable.setModel(tc);
            }
        });
        tc = new TableContent();
        dataTable.setModel(tc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CITE_U");
        frame.setContentPane(new CITE_U().citeU);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
