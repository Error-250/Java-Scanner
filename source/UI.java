/*UI*/
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import myProcessBar.*;
import myButton.*;

public class UI{
    protected JFrame myUI;
    protected JPanel myUIP;

    protected JPanel top;
    protected JPanel top_1l;
    protected JLabel lable_target;
    protected JTextField target;

    protected JPanel top_1r;
    protected JLabel label_StartPort;
    protected JSpinner startPort;
    protected JLabel label_EndPort;
    protected JSpinner endPort;

    protected JPanel top_2l;
    protected JLabel lable_threads;
    protected JSpinner threads;
    protected JLabel label_timeout;
    protected JTextField timeout;

    protected MyButton startScan;

    protected MyProgress pbTip;

    protected JTable scanResult;
    protected DefaultTableModel model;
    protected JScrollPane scanTableScroll;

    public UI(){
        this("PortScanner");
    }

    public UI(String name){
        myUI = new JFrame(name);
        myUIP = new JPanel();
        myUIP.setLayout(new BorderLayout());
    }

    public void init(){
        top_1l = new JPanel();
        lable_target = new JLabel("Target:");
        target = new JTextField(15);
        target.setText("127.0.0.1");

        top_1l.add(lable_target);
        top_1l.add(target);

        top_1r = new JPanel();
        SpinnerNumberModel startport_model = new SpinnerNumberModel(1,1,65535,1);
        label_StartPort = new JLabel("StartPort");
        startPort = new JSpinner(startport_model);
        SpinnerNumberModel endport_model = new SpinnerNumberModel(1024,1,65535,1);
        label_EndPort = new JLabel("EndPort");
        endPort = new JSpinner(endport_model);

        top_1r.add(label_StartPort);
        top_1r.add(startPort);
        top_1r.add(label_EndPort);
        top_1r.add(endPort);

        top_2l = new JPanel();
        lable_threads = new JLabel("Threads:");
        SpinnerNumberModel threads_model = new SpinnerNumberModel(100,10,500,10);
        threads = new JSpinner(threads_model);
        threads.setValue(100);
        label_timeout = new JLabel("TimeOut:");
        timeout = new JTextField(5);
        timeout.setText("0");

        top_2l.add(lable_threads);
        top_2l.add(threads);
        top_2l.add(label_timeout);
        top_2l.add(timeout);

        startScan = new MyButton("StartScan");

        top = new JPanel();
        top.setLayout(new GridLayout(2,2));
        top.add(top_1l);
        top.add(top_1r);
        top.add(top_2l);
        top.add(startScan);

        pbTip = new MyProgress(100,0);
        pbTip.setValue(0);
        pbTip.setString("0%");
        //pbTip.setStringPainted(true);

        String []columns = {"Port","Type"};
        model = new DefaultTableModel(columns,0);
        scanResult = new JTable(model);
        scanTableScroll = new JScrollPane(scanResult);

        myUIP.add(top,"North");
        myUIP.add(pbTip);
        myUIP.add(scanTableScroll,"South");

        myUI.add(myUIP);

        startScan.addActionListener(new ScanAction(this));
    }
    public void showWindow(){
        myUI.setBounds(400,100,750,550);
        myUI.setVisible(true);
        myUI.setDefaultCloseOperation(3);
        myUI.pack();
        myUI.setResizable(false);
    }

    public void setTitle(long time){
        if(time > 1000){
            myUI.setTitle("PortScanner  times:" + time * 1.0 / 1000 + "s");
        }else{
            myUI.setTitle("PortScanner  times:" + time + "ms");
        }
    }

    public void setTitle(){
        myUI.setTitle("PortScanner"+ "  Scaning " + target.getText());
    }

    public static void main(String []rags){
        UI myUI = new UI();
        myUI.init();
        myUI.showWindow();
    }
}
