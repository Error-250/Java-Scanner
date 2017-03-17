import java.net.*;

public class AdminThread extends Thread{
    private AdminAction header;
    public AdminThread(AdminAction aa){
        header = aa;
    }
    public int scanadmin(String url){//200 403
        try{
            URL ul = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ul.openConnection();
            con.setRequestProperty("User-agent","Mozilla/4.0");
            return con.getResponseCode();
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void run(){
        int code = 0;
        String currentTask;
        while(header.tasks.size()>0){
            currentTask = header.tasks.poll();
            if(currentTask == null)
                break;
            code = scanadmin(currentTask);
            synchronized(this){
                if(code == 200 || code == 403){
                    String []rels = {currentTask,String.valueOf(code)};
                    header.uis.model.insertRow(header.uis.model.getRowCount(),rels);
                    System.out.println(currentTask + "   [" + code + "]");
                }
            }
        }
    }
}
