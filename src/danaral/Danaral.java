/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danaral;

import com.itextpdf.text.DocumentException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Manel
 */
public class Danaral {

    Cititor cititor;
    static Danaral ref;

   
    static String ACTIVITIES_TOKEN = "Activities on";
    static String WORKING_TOKEN = "work for";
    static String LI_TOKEN = "li";
    static String DRIVING_TOKEN = "driving for";
    static boolean DEBUG = false;
    static String TITLE_TOKEN = "title";
    static final String IANUARIE = "jan";
    static final String FEBRUARIE = "feb";
    static final String MARTIE = "mar";
    static final String APRILIE = "apr";
    static final String MAI = "may";
    static final String IUNIE = "jun";
    static final String IULIE = "jul";
    static final String AUGUST = "aug";
    static final String SEPTEMBRIE = "sep";
    static final String OCTOMBRIE = "oct";
    static final String NOIEMBRIE = "nov";
    static final String DECEMBRIE = "dec";
    formPrincipal guiPrincipal;
    RaportTahografForm tahografForm;
    EvenimenteForm evenimenteForm;
    static String RAPORT_NOAPTE="rapnoapte";
    static String RAPORT_TIP_1="rt1";
    
    static int oreNoapte[] = {22, 23, 24,0, 1, 2, 3, 4, 5};

    static Danaral getDanaral() {
        return ref;
    }

    final static int DIAGRAMA_2_INIT_VAL = 80000;
    final static int DIAGRAMA_1_INIT_VAL = 90000;
    static int DIAGRAMA_1 = DIAGRAMA_1_INIT_VAL;
    static int DIAGRAMA_2 = DIAGRAMA_2_INIT_VAL;

    public Danaral() throws IOException {
        cititor = new Cititor();
        guiPrincipal=new formPrincipal();
        ref = this;
        tahografForm=new RaportTahografForm();
        evenimenteForm=new EvenimenteForm();
        /*List<RaportZi> rapoarte = cititor.load("C:\\Users\\Manel\\Desktop\\exemplu.html");

        cititor.sort(rapoarte);*/
    }
    
    void openGUI(){
        guiPrincipal.setLocationRelativeTo(null);
        guiPrincipal.setVisible(true);
    }

    public static String getPathToJar() throws URISyntaxException {
        String x = Danaral.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        StringBuilder b = new StringBuilder(x);
        b.delete(0, 1);
        return b.toString();
    }

    public static String get_path(String finalName) throws URISyntaxException {
        String full = getPathToJar();
        StringTokenizer b = new StringTokenizer(full, "/\\");
        String x = "";
        while (b.hasMoreElements()) {
            String y = b.nextToken();
            if (!(y.toLowerCase().equals(finalName.toLowerCase())) && !(y.toLowerCase().equals("store"))) {
                x += y + "\\";
            }
        }
        return x;
    }
    static String PATH="";
    static String kilometriRegex = "activityDayDistance: ?(\\d\\d?\\d?\\d?\\d?\\d?) km";

    /**
     * @param args the command line arguments
     */
    static String getUser(){
        return System.getProperty("user.name");
    }
    static void resetLicense() throws FileNotFoundException, IOException{
        FileOutputStream fileOut=new FileOutputStream(fisier);
        try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            Licenta l=new Licenta();
            l.user=Licenta.defaultUser;
            out.writeObject(l);
        }
        System.out.println("Licenta resetata ");
    }

    static Licenta getLicense() throws IOException, ClassNotFoundException, java.io.EOFException {
        FileInputStream fileIn = new FileInputStream(fisier);
        try {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Licenta ans = (Licenta) in.readObject();
            fileIn.close();
            in.close();
            return ans;
        } catch (Exception ex) {
            throw new IOException("bai");
        }

    }

    static String fisier;

    static String getKey() {
        DateFormat dateFormat2 = new SimpleDateFormat("hhmmMMddyyyy");
        String dateString2 = dateFormat2.format(new Date());
        StringBuilder key = new StringBuilder();
        //System.out.println(dateString2);
        for (int i = 0; i < dateString2.length(); i++) {
            if (Character.isDigit(dateString2.charAt(i))) {
                char c = dateString2.charAt(i);
                int cifra = c - '0';
                char alpha = (char) ('a' + cifra);
                key.append(alpha);
            } else {
                //key.append(" ");
            }
        }
        return key.toString();
    }

    static class Licenta implements Serializable {

        String user;
        static String defaultUser = "palincaUser";


        public Licenta() {

        }
        
    }
    static String master="28>2>;0C0<D;C0";
    static String decryptPassword(String pass) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isAlphabetic(pass.charAt(i))) {
                char cifra = (char) (pass.charAt(i) - '1');
                ret.append(cifra);
            } else {
                //ret.append(" ");
            }
        }
        return ret.toString();
    }

    static void giveLicense() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fisier);
        try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            Licenta l = new Licenta();
            l.user = getUser();
            out.writeObject(l);
            System.out.println("Licenta acceptata ");
        }

    }

    static String getKeyFor(String hhmmddMMYYYY) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < hhmmddMMYYYY.length(); i++) {
            if (Character.isDigit(hhmmddMMYYYY.charAt(i))) {
                char c = hhmmddMMYYYY.charAt(i);
                int cifra = c - '0';
                char alpha = (char) ('a' + cifra);
                key.append(alpha);
            } else {
                //key.append(" ");
            }
        }
        return key.toString();
    }

    static boolean goodPassword(String x) {
        String parolaBuna = getKey();
        List<String> paroleBune = new ArrayList<>();
        paroleBune.add(parolaBuna);
        DateFormat dateFormat2 = new SimpleDateFormat("hh mm MM dd yyyy");
        String dateString2 = dateFormat2.format(new Date());
        int ora=Integer.valueOf(dateString2.split(" ")[0]),minut=Integer.valueOf(dateString2.split(" ")[1]);
        String oraString="";
        String minutString = "";
        for (int i = 0; i < 5; i++) {
            minut--;
            if (minut < 0) {
                minut = 59;
                ora--;
                ora %= 24;
                ora = Math.abs(ora);
            }
            oraString = Integer.toString(ora);
            minutString = Integer.toString(minut);
            if (ora < 10) {
                oraString = "0" + oraString;
            }
            if (minut < 10) {
                minutString = "0" + minutString;
            }

//            System.out.println("ore " + oraString);
//            System.out.println("minute " + minutString);
//            System.out.println("dd " + dateString2.split(" ")[2]);
//            System.out.println("mm " + dateString2.split(" ")[3]);
//            System.out.println("yyyy " + dateString2.split(" ")[4]);

            paroleBune.add(getKeyFor(oraString + minutString + dateString2.split(" ")[2] + dateString2.split(" ")[3] + dateString2.split(" ")[4]));
        }
        ora = Integer.valueOf(dateString2.split(" ")[0]);
        minut = Integer.valueOf(dateString2.split(" ")[1]);
        for (int i = 0; i < 5; i++) {
            minut++;
            if (minut >59) {
               ora++;
               ora%=24;
               minut=0;
            }
            oraString = Integer.toString(ora);
            minutString = Integer.toString(minut);
            if (ora < 10) {
                oraString = "0" + oraString;
            }
            if (minut < 10) {
                minutString = "0" + minutString;
            }

//            System.out.println("ore " + oraString);
//            System.out.println("minute " + minutString);
//            System.out.println("dd " + dateString2.split(" ")[2]);
//            System.out.println("mm " + dateString2.split(" ")[3]);
//            System.out.println("yyyy " + dateString2.split(" ")[4]);
            paroleBune.add(getKeyFor(oraString + minutString + dateString2.split(" ")[2] + dateString2.split(" ")[3] + dateString2.split(" ")[4]));
        }
        //System.out.println(paroleBune);
        for (String s:paroleBune){
            if (s.equals(x)){
                return true;
            }
        }
        return false;
    }
    
    static Dictionary dictionar=new Hashtable(); 
    
    public static void main(String[] args) throws IOException, URISyntaxException, DocumentException, ClassNotFoundException {
        
         dictionar.put("General events: Motion data error", "Eroare senzori miscare");
        dictionar.put("General events: Power supply interruption", "Intreruperea alimentarii");
        dictionar.put("General events: Driving without an appropriate card", "Conducere fara card");
        dictionar.put("General events: Over speeding", "Depasirea vitezei legale");
        dictionar.put("General events: Over speeding", "Depasirea vitezei legale");
        dictionar.put("Recording equipment faults: Downloading fault", "defectiune incarcare date");
        dictionar.put("Recording equipment faults: VU internal fault", "eroare interna");
        dictionar.put("Recording equipment faults: No further details", "eroare echipament de inregistrare fara detalii");
        dictionar.put("Recording equipment faults: Sensor fault", "problema senzor");
        dictionar.put("General events: Card insertion while driving", "inserare card in timpul condusului");
        dictionar.put("Vehicle unit related security breach attempt events: Hardware sabotage", 
                "Securitatea vehiculului: sabotaj hardware");
        dictionar.put("General events: Last card session not correctly closed", "ultima sesiune a cardului nu a fost incheiata corect");
        
        
        
        dictionar.put("the most serious event for one of the last 10 days of occurrence", 
                "cel mai grav eveniment într-una din ultimele 10 zile în care a survenit");
        dictionar.put("one of the 5 longest events over the last 365 days", 
                "unul dintre cele mai grave 5 evenimente care au survenit în ultimele 365 de zile");
        dictionar.put("the longest event for one of the last 10 days of occurrence", 
                "evenimentul cu cea mai mare durată într-una din ultimele 10 zile în care a survenit");
        dictionar.put("one of the 10 most recent (or last) events or faults", 
                "unul dintre cele mai recente (sau ultimele) 10 evenimente sau anomalii");
        dictionar.put("the first event or fault having occurred after the last calibration", 
                "primul eveniment sau prima anomalie care a survenit după ultima calibrare");
        dictionar.put("the last event for one of the last 10 days of occurrence", 
                "ultimul eveniment într-una din ultimele 10 zile în care a survenit");
        
        
        String path = get_path("ProgramLicentiat.jar");
        fisier = path + "date.lcs";

        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        Licenta l;
        try {
            l = getLicense();
            
        } catch (Exception ex) {
            resetLicense();
            l=getLicense();
        }
        System.out.println("Am pornit programul cu user-ul " + getUser() + " in fisier am gasit " + l.user);

        if (l.user.equals(Licenta.defaultUser) || !l.user.equals(getUser())) {
            System.out.println("Nelicentiat");
            //System.out.println("Key perfect " + getKey());
            int counter = 0;
            while (true) {

                if (counter < 5) {
                    String x = JOptionPane.showInputDialog(null, "Introdu parola de activare");
                    try {
                        if (x.equals(master) || goodPassword(x)) {
                            giveLicense();
                            JOptionPane.showMessageDialog(null, "Parola acceptata, te rog reporneste programul");
                            break;
                        }
                    } catch (Exception ex) {
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Oprire automata");
                    break;
                }
                counter++;
            }

        } else {
            Danaral danaral = new Danaral();
            danaral.openGUI();
            System.out.println(Arrays.toString(args));
            if (args.length == 1) {
                System.out.println(args[0]);
                danaral.cititor.load(args[0]);
                danaral.guiPrincipal.updateUIFromBatch();
                System.out.println("Am incarcat fisierul");
            }
            PATH = get_path("Program.jar");
        }
        
    }

    
    //<editor-fold desc="dump" defaultstate="collapsed">
            /*
        java.nio.file.Path path = Paths.get(Danaral.class.getResource("noapte.png").toURI());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextImageExample.pdf"));
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);

        document.close();*/


       /* System.out.println(Pattern.matches(kilometriRegex, 
                                            "Activities on Fri Jan 25 2019: activityRecordPreviousLength: 14 Bytes activityRecordLength: 44 Bytes activityRecordDate: Fri Jan 25 2019 activityPresenceCounter: 2 activityDayDistance: 425 km Visualization: break/rest, from 0:0"));
        */
        
       

        /*
        RaportZi oziulica = rapoarte.get(11);
        Object [][]data=new Object[oziulica.programCondus.size()][6];
        for (int i=0;i<data.length;i++){
            data[i][0]=oziulica.programCondus.get(i).getActivitate();
            data[i][1]=oziulica.programCondus.get(i).getTura();
            data[i][2]=oziulica.programCondus.get(i).getTimp();
            data[i][3]=oziulica.programCondus.get(i).getOraInceput();
            data[i][4]=oziulica.programCondus.get(i).getOraIncheiere();
        }
        oziulica.show();
        JFrame f = new JFrame();
        f.setTitle("Program in data de " + oziulica.data);
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        JTable table = new JTable(data, new Object[]{"Activitate", "Tura", "Durata", "Inceput", "Incheiere"});
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        f.add(new JScrollPane(table));
        f.setVisible(true);
         */
    //</editor-fold>

}
