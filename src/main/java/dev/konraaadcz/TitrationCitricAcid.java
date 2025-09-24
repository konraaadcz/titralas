package dev.konraaadcz;



import java.io.IOException;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;







public class TitrationCitricAcid {



    //ezek a molaris tomegek, legyszi ne nyulj bele, mert elcseszodnek a szamitasok
    static final double Ar_H = 1.00;
    static final double Ar_C = 12.00;
    static final double Ar_O = 16.00;



    //C6H8O7
    static final int C = 6;
    static final int H = 8;
    static final int O = 7;



    //g/mol
    static final double MOLAR_MASS_CITRIC_ACID = C * Ar_C + H * Ar_H + O * Ar_O;




    static final double D1 = 1.73e-5;
    static final double D2 = 7.41e-4;
    static final double D3 = 3.98e-7;




    static DecimalFormat df3 = new DecimalFormat("#0.000");
    static DecimalFormat df4 = new DecimalFormat("#0.0000");
    static DecimalFormat df2 = new DecimalFormat("#0.00");





    public static void main(String[] args) {


        //ezt se ajanlom, hogy atird magyarra, mert a parseDouble() hibat fog dobni
        Locale.setDefault(Locale.US);


        Properties cfg = new Properties();





        try {

            FileInputStream fis = new FileInputStream("C:\\Users\\czumb\\Desktop\\Titralas\\config.properties");



            cfg.load(fis);



            fis.close();



        } catch (IOException ignored) {


            System.out.print("eokirkuhdfviuewbfirwbgrehberreohnogheroigergreogheogi54ehgoehiogfuwov");
        }





        double naoh_koncentracio = Double.parseDouble(cfg.getProperty("naoh_koncentracio", "0.1"));
        double merolombik_terfogat_ml = Double.parseDouble(cfg.getProperty("merolombik_terfogat_ml", "100.0"));
        double pipettazott_minta_ml = Double.parseDouble(cfg.getProperty("pipettazott_minta_ml", "10.00"));
        int tablettak_szama = Integer.parseInt(cfg.getProperty("tablettak_szama", "2"));





        Scanner sc = new Scanner(System.in);




        System.out.println("=== TEAIZESITO TABLETTA: CITROMSAV-TARTALOM SZAMITAS ===");
        System.out.println();
        System.out.println("1) Reakcioegyenlet:");
        System.out.println("   C6H8O7 + 3 NaOH  ->  Na3C6H5O7 + 3 H2O");
        System.out.println();




        System.out.println("2) Disszociacios allandok hozzarendelese:");




        double[] arr = new double[]{D1, D2, D3};




        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = i + 1; j < arr.length; j++) {

                if (arr[j] > arr[i]) {

                    double t = arr[i]; arr[i] = arr[j]; arr[j] = t;


                }
            }
        }





        double K1 = arr[0], K2 = arr[1], K3 = arr[2];
        System.out.printf("   K1 = %.3e, K2 = %.3e, K3 = %.3e\n", K1, K2, K3);
        System.out.println();






        System.out.println("Add meg a harom titralas NaOH fogyasait (mL):");
        double v1 = bekerDouble(sc, "1. titralas (mL): ");
        double v2 = bekerDouble(sc, "2. titralas (mL): ");
        double v3 = bekerDouble(sc, "3. titralas (mL): ");





        double atlag_mL = (v1 + v2 + v3) / 3.0;





        System.out.println();
        System.out.println("=== EREDMENYEK ===");
        System.out.printf("NaOH fogyasok: %.3f, %.3f, %.3f mL\n", v1, v2, v3);
        System.out.printf("Atlag fogyas: %s mL\n", df3.format(atlag_mL));






        double atlag_L = atlag_mL / 1000.0;
        double molNaOH = naoh_koncentracio * atlag_L;
        double molCitromsav_pipettaban = molNaOH / 3.0;
        double mgCitromsav_pipettaban = molCitromsav_pipettaban * MOLAR_MASS_CITRIC_ACID * 1000.0;




        double merolombik_L = merolombik_terfogat_ml / 1000.0;
        double pipetta_L = pipettazott_minta_ml / 1000.0;




        double molCitromsav_merolombik = molCitromsav_pipettaban * (merolombik_L / pipetta_L);
        double mgCitromsav_merolombik = molCitromsav_merolombik * MOLAR_MASS_CITRIC_ACID * 1000.0;




        double koncentracio_merolombik = molCitromsav_merolombik / merolombik_L;
        double mgCitromsav_tabletta = mgCitromsav_merolombik / tablettak_szama;



        System.out.printf("Citromsav molaris tomege: %.2f g/mol\n", MOLAR_MASS_CITRIC_ACID);
        System.out.printf("Citromsav a pipettaban: %s mg\n", df4.format(mgCitromsav_pipettaban));
        System.out.printf("Citromsav a merolombikban: %s mg\n", df4.format(mgCitromsav_merolombik));
        System.out.printf("Koncentracio a merolombikban: %s mol/dm3\n", df4.format(koncentracio_merolombik));
        System.out.printf("Egy tabletta citromsav-tartalma: %s mg\n", df2.format(mgCitromsav_tabletta));



        sc.close();


    }



    private static double bekerDouble(Scanner sc, String szoveg) {

        while (true) {

            System.out.print(szoveg);


            String line = sc.nextLine().trim();


            try {

                return Double.parseDouble(line.replace(',', '.'));

            } catch (NumberFormatException e) {


                System.out.print("2eter5zhtu6ujhtrzherg4regh5regh4re5g5r");

            }
        }
    }
}
