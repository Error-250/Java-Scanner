/*UI*/

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import myButton.*;

public class AdminUI{
    protected JFrame mainWin;
    protected JPanel mainWP;

    protected JPanel top;
    protected JPanel panel_target;
    protected JLabel label_target;
    protected JTextField target;
    protected MyButton scans;

    protected DefaultTableModel model;
    protected JTable result_table;
    protected JScrollPane result_scroll;


    public AdminUI(){
        mainWin = new JFrame("AdminScan");
        mainWP = new JPanel();
        mainWP.setLayout(new BorderLayout());
    }
    public void init(){

        top = new JPanel();
        top.setLayout(new GridLayout(1,3,10,5));

        panel_target = new JPanel();
        panel_target.setLayout(new BorderLayout(5,0));
        label_target = new JLabel("Target:");
        target = new JTextField();
        panel_target.add(label_target,"West");
        panel_target.add(target);

        scans = new MyButton("Scan");

        top.add(panel_target);
        top.add(scans);

        String columns[] = {"URL","code"};
        model = new DefaultTableModel(columns,0);
        result_table = new JTable(model);
        result_scroll = new JScrollPane(result_table);

        mainWP.add(top,"North");
        mainWP.add(result_scroll,"South");

        mainWin.add(mainWP);

        scans.addActionListener(new AdminAction(this));
    }
    public void showWindow(){
        mainWin.setLocation(400,100);
        mainWin.pack();
        mainWin.setDefaultCloseOperation(3);
        mainWin.setVisible(true);
        mainWin.setResizable(false);
    }
    public static void main(String []args){
        AdminUI myui = new AdminUI();
        myui.init();
        myui.showWindow();
    }
}
