/*Scan Action*/
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.concurrent.LinkedBlockingQueue;

public class ScanAction implements ActionListener{
    private int tasks_sum = 0;
    protected UI myui;
    protected LinkedBlockingQueue<Integer> task = new LinkedBlockingQueue<Integer>();

    public ScanAction(UI myuis){
        myui = myuis;
    }

    public boolean tasks(){
        int start =(int) myui.startPort.getValue();
        int end = (int)myui.endPort.getValue();
        tasks_sum = end - start;
        if(end <= start)
            return false;
        task.clear();
        try{
            for(int i = start;i <= end;i++){
                task.put(i);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public void scans(){
        if(!tasks()){
            JOptionPane.showMessageDialog(null,"endport < startport");
            return;
        }
        myui.pbTip.setValue(0);
        myui.pbTip.setString("0%");
        myui.pbTip.setMax(tasks_sum);
        int threads = (int)myui.threads.getValue();
        ScanThread st[] = new ScanThread[threads];
        myui.model.setRowCount(0);
        long startTime=System.currentTimeMillis();
        for(int i = 0;i < threads;i++){
            st[i] = new ScanThread(this,myui.target.getText().trim());
            st[i].setTimeout(Integer.valueOf(myui.timeout.getText()));
            st[i].start();
        }
        try{
            for(int i = 0;i < threads;i++){
                st[i].join();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        long time = endTime - startTime;
        myui.setTitle(time);
    }

    public void getProcess(){
        try{
            while(task.size() > 0){
                myui.pbTip.setValue(tasks_sum - task.size());
                myui.pbTip.setTitle();
                Thread.sleep(300);
            }
            myui.pbTip.setValue(tasks_sum);
            myui.pbTip.setTitle();
            Thread.sleep(300);
        }catch(Exception e){
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev){
        myui.setTitle();
        new Thread(){
            @Override
            public void run(){
                scans();
            }
        }.start();
        new Thread(){
            @Override
            public void run(){
                getProcess();
            }
        }.start();
    }
}
