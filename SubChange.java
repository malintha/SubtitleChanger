
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class SubChange {
        static String sign="";
        static long gap;
    public static void main(String args[]) throws IOException, ParseException{
               
        System.out.println("Enter amount of milliseconds you want to change");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        gap = (long)(Integer.parseInt(br.readLine()));
        System.out.println("Enter + for bring subtitle to forward, - to backward");
        sign = br.readLine();
        
        BufferedReader fbr = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Documents\\Movies\\Killing Them Softly 2012 DVDRIP XViD-RESiSTANCE.srt"));
        
        
        File subText = new File("C:\\Users\\Administrator\\Documents\\Movies\\Killing Them Softly 2012 DVDRIP XViD-RESiSTANCE_updated.srt");
        if(subText.exists()){
        subText.delete();
        subText.createNewFile();
        }
        else
            subText.createNewFile();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(subText.getAbsoluteFile()));        
        
        String line = fbr.readLine();
        while(line!=null){
            
            if(isTime(line)){
            String[] changed = changeLine(line);
            line=changed[0]+" --> "+changed[1];
            }
            bw.write(line);
            bw.newLine();
            line=fbr.readLine();          
            }
    if(subText.exists())
            System.out.println("All done successfully");
    }
    
    
    static String[] changeLine(String line){
       Subtime st;
       String[] changedVal=new String[2];
       String[] segments = line.split("-->");
       for(int i=0;i<2;i++){
           
           String eachSeg=segments[i];
           
           String timeVal = eachSeg.trim();
           //System.out.println(timeVal);
          st=new Subtime(timeVal);
           if(sign.equals("+")){
               changedVal[i]=st.addTime(gap);         
           }
           else if(sign.equals("-")){
               changedVal[i]=st.redTime(gap);
           }
           
       
       }
       
       
        return changedVal;
    }

    
    static boolean isTime(String line){
        if(line.contains("-->"))        
        if(line.matches(".*(\\d+)>*."))
            return true;
        
    return false;
    }
    
}

class Subtime{
    
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS");
        Date dt;
        
        public Subtime(String line){
        try {
            dt = sdf.parse(line);
        } catch (ParseException ex) {
            Logger.getLogger(Subtime.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        public String addTime(long millis){
           Date dt1 = new Date((long) (dt.getTime()+millis));
        return sdf.format(dt1);
        }
       
        public String redTime(long millis){
            Date dt1 = new Date((long) (dt.getTime()-millis));
            return sdf.format(dt1);
            
        }
  
}

