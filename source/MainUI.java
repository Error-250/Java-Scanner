import javax.swing.*;

public class MainUI{
    public static void main(String []args){
        AdminUI myUI1 = new AdminUI();
        UI scUI = new UI();
        myUI1.init();
        scUI.init();
        JTabbedPane myT = new JTabbedPane();
        myT.addTab("PortScan",scUI.myUIP);
        myT.addTab("AdminScan",myUI1.mainWP);

        JFrame m = new JFrame("Test");
        m.add(myT);

        m.setDefaultCloseOperation(3);
        m.pack();
        m.setVisible(true);
    }
}
