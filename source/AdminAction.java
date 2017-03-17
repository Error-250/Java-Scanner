import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class AdminAction implements ActionListener{
    protected AdminUI uis;
    private int threads = 10;
    protected LinkedBlockingQueue<String> tasks = new LinkedBlockingQueue<String>();
    public AdminAction(AdminUI tm){
        uis = tm;
    }
    public String formaturl(String paths){
        if(paths.startsWith("/"))
            return uis.target.getText().trim() + paths.trim();
        return uis.target.getText().trim() + "/" + paths.trim();
    }
    public boolean tasks(){
        try{
            tasks.clear();
            FileInputStream in = new FileInputStream("./Dict_Admin.txt");
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\n");
            while(scan.hasNext()){
                tasks.put(formaturl(scan.next()));
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public void startTask(){
        tasks();
        threads = Thread.activeCount();
        uis.model.setRowCount(0);
        AdminThread t[] = new AdminThread[10];
        for(int i = 0;i < 5;i ++){
            t[i] = new AdminThread(this);
            t[i].start();
        }
        try{
            for(int i = 0;i < 5;i++){
                t[i].join();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        uis.mainWin.setTitle("AdminScan  Done.");
    }

    @Override
    public void actionPerformed(ActionEvent ev){
        if(Thread.activeCount() < threads){
            uis.mainWin.setTitle("AdminScan  Scaning...");
            new Thread(){
                @Override
                public void run(){
                    startTask();
                }
            }.start();
        }
    }
}
