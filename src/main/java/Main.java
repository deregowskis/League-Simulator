import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        //Tournament tournament = new Tournament(new Club("Legia Warsaw"),new Club("Liverpool"),
        //        new Club("Bayern Munich"),new Club("Manchester United"));
        //tournament.addSchedule();
        //for (int i = 0; i < 12; i++) {
        //    tournament.run();
        //    tournament.table();
        //}
        //tournament.topGoalscorers();
        //tournament.topAssistants();
        //tournament.topCanadians();
        //tournament.topYellows();
        //tournament.topReds();
        //tournament.topFouls();
        //tournament.topFouled();
        //tournament.topShoots();


        com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "INSERT YOUR LICENSE KEY HERE", "my company");
        UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        Interface frame = new Interface();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
