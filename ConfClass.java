import java.io.*;


public class ConfClass {
        public static String DBSERVER = "jdbc:mysql://localhost/team";
        public static String USERID = "radko28";
        public static String PASSWORD = "agatha";
        public static String LOOK = "metal";
        
        public ConfClass(String file) {
                if(file.length() == 0) {
                        writeFile("conf.txt");                                
                } else
                        readFile(file);
        }
        public void readFile(String file) {
                try {                
                        FileReader fr = new FileReader(file);
                        BufferedReader in = new BufferedReader(fr);
                        String line = "";
                        while((line =in.readLine()) != null) {
                                int i = line.indexOf('=');
                                String value = line.substring(i + 1,line.length());
                                if((line.substring(0,i)).equals("dbserver"))
                                        DBSERVER = value.trim();
                                else if((line.substring(0,i)).equals("look")) {
                                        LOOK = value.trim();
                                }
                         
                        }
                        fr.close();
                } catch(IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();                        
                }
        }
        
        public void writeFile(String file) {
                try {                
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter out = new BufferedWriter(fw);
                        out.write("dbserver=" + DBSERVER);
                        out.newLine();
                        out.write("look="+LOOK);
                        out.newLine();                                
                        out.close();
                        System.out.println("look = " + LOOK);
                } catch(IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();                        
                }
        }
}
