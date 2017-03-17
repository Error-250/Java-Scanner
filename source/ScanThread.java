/*ScanThread*/
import java.net.*;

public class ScanThread extends Thread{
    private ScanAction rel;
    private Socket s;
    private String target;
    private SocketAddress addr;
    private int timeout = 0;
    private int port = 0;
    
    public ScanThread(ScanAction myui,String target){
        rel = myui;
        this.target = target;
    }
    
    public void setTask(int port){
        this.port = port;
    }
    
    public void setTimeout(int timeout){
        this.timeout = timeout;
    }
    
    public String getType(int port){
        switch(port){
            case 20:return "Ftp����";
            case 21:return "Ftp";
            case 22:return "SSH";
            case 23:return "telnet";
            case 80:return "HTTP";
            case 139:return "NETBIOS Name Service";
            case 445:return "SMB";
            case 1433:return "SQL Server����";
            case 3389:return "windowsԶ�̵�¼";
            case 8080:return "HTTP";
            default: return "δ֪";
        }
    }
    
    @Override
    public void run(){
        while(rel.task.size() > 0){
            try{
                port = rel.task.poll();
                s = new Socket();
                addr = new InetSocketAddress(target,port);
                s.connect(addr,timeout);
                synchronized(this){
                    String []adds = {String.valueOf(port),getType(port)};
                    rel.myui.model.insertRow(rel.myui.model.getRowCount(),adds);
                }
            }catch(Exception e){
            }
        }
    }
}