import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Tournament class is used to organise and run all the matches within a special formay.
 */
public class Tournament {
    ArrayList<Club> teams = new ArrayList<>();
    ArrayList<Match> schedule = new ArrayList<>();
    int matchNumber = 0;
    Interface frame;

    /**
     * Constructor is used to add 4 teams to a tournament:
     *
     * @param team1
     * @param team2
     * @param team3
     * @param team4
     */
    public Tournament(Club team1, Club team2, Club team3, Club team4, Interface frame) {
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        this.frame=frame;
    }

    /**
     * addSchedule() method creates a schedule of games between the teams. All the teams play twice against
     * each other - once as a host, and once as a guest team.
     */
    void addSchedule() {
        schedule.add(new Match(teams.get(0), teams.get(1), frame));
        schedule.add(new Match(teams.get(2), teams.get(3), frame));
        schedule.add(new Match(teams.get(3), teams.get(0), frame));
        schedule.add(new Match(teams.get(1), teams.get(2), frame));
        schedule.add(new Match(teams.get(0), teams.get(2), frame));
        schedule.add(new Match(teams.get(1), teams.get(3), frame));
        schedule.add(new Match(teams.get(2), teams.get(0), frame));
        schedule.add(new Match(teams.get(3), teams.get(1), frame));
        schedule.add(new Match(teams.get(1), teams.get(0), frame));
        schedule.add(new Match(teams.get(3), teams.get(2), frame));
        schedule.add(new Match(teams.get(0), teams.get(3), frame));
        schedule.add(new Match(teams.get(2), teams.get(1), frame));
        schedule.get(11).islast=true;
    }

    /**
     * run() method is used to play a current match in tournament.
     *
     * @throws InterruptedException
     */
    void run() throws InterruptedException {

        Thread thread = new Thread(schedule.get(matchNumber));
        thread.start();
        matchNumber++;


    }

    /**
     * table() method is used to print actual table situation to a viewer.
     */
    void table() {
        ArrayList<Club> table = teams;
        /**
         * Order of sorting:
         * 1. points
         * 2. goals difference
         * 3. goals for
         */
        table.sort(Comparator.comparingInt(Club::getPoints)
                .thenComparingInt(a -> (a.getGoalsfor() - a.getGoalsagainst()))
                .thenComparingInt(Club::getGoalsfor));
        Collections.reverse(table);
        for (int i = 0; i < 4; i++) {
            frame.rows[i] = new String[]{String.valueOf((i + 1)), table.get(i).name, String.valueOf(table.get(i).matches), String.valueOf(table.get(i).wins),
                    String.valueOf(table.get(i).draws), String.valueOf(table.get(i).loses), String.valueOf(table.get(i).goalsfor),
                    String.valueOf(table.get(i).goalsagainst), String.valueOf((table.get(i).goalsfor - table.get(i).goalsagainst)),
                    String.valueOf(table.get(i).points)};
        }
    }

    /**
     * topGoalscorers() method creates a list of top 10 goalscorers of a tournament.
     */
    void topGoalscorers() {
        ArrayList<Player> goalscorers = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.goals != 0 & player.position!=Position.GOALKEEPER){
                    goalscorers.add(player);
                }
            }
        }
        goalscorers.sort(Comparator.comparingInt(Player::getGoals));
        Collections.reverse(goalscorers);
        frame.classificationstat.get(0).setText(String.format("%40s", "TOP GOALSCORERS"));
        frame.classificationstat.get(1).setText("POS.|          PLAYER          |        CLUB         |  GOALS");
        frame.classificationstat.get(2).setText("----------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(goalscorers.size(),10); i++) {
            if(goalscorers.get(i).goals==counter){
                frame.classificationstat.get(i+3).setText(String.format("%-6s %-1s %-40s %-1s %-17s %-1s %-2s", "=", "|  ", goalscorers.get(i).name +" "
                                + goalscorers.get(i).surname, "|  ", goalscorers.get(i).club, "|  ",
                        goalscorers.get(i).goals));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%-6s %-4s %-40s %-1s %-17s %-1s %-2s", i+1, "|  ", goalscorers.get(i).name + " "
                                + goalscorers.get(i).surname, "|  ", goalscorers.get(i).club, "|  ",
                        goalscorers.get(i).goals));
            }
            counter =goalscorers.get(i).goals;
        }
    }

    /**
     * topAssistants() method creates a list of top 10 assistants of a tournament.
     */
    void topAssistants() {
        ArrayList<Player> assistants = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.assists != 0){
                    assistants.add(player);
                }
            }
        }
        assistants.sort(Comparator.comparingInt(Player::getAssists));
        Collections.reverse(assistants);
        frame.classificationstat.get(0).setText(String.format("%40s", "TOP ASSISTANTS"));
        frame.classificationstat.get(1).setText("POS.|          PLAYER          |         CLUB        |  ASSISTS");
        frame.classificationstat.get(2).setText("----------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(assistants.size(),10); i++) {
            if(assistants.get(i).assists==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", assistants.get(i).name +" "
                                + assistants.get(i).surname, "|  ", assistants.get(i).club, "|  ",
                        assistants.get(i).assists));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", assistants.get(i).name +" "
                                + assistants.get(i).surname, "|  ", assistants.get(i).club, "|  ",
                        assistants.get(i).assists));
            }
            counter =assistants.get(i).assists;
        }
    }

    /**
     * topCanadians() method creates a list of top 10 players in Canadian classification of a tournament.
     */
    void topCanadians() {
        ArrayList<Player> canadians = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.canadians != 0 & player.position!=Position.GOALKEEPER){
                    canadians.add(player);
                }
            }
        }
        canadians.sort(Comparator.comparingInt(Player::getCanadians));
        Collections.reverse(canadians);
        frame.classificationstat.get(0).setText(String.format("%40s", "CANADIAN CLASSIFICATION"));
        frame.classificationstat.get(1).setText("POS.|          PLAYER          |         CLUB        |  SCORE (G+A)");
        frame.classificationstat.get(2).setText("--------------------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(10,canadians.size()); i++) {
            if(canadians.get(i).canadians==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", canadians.get(i).name +" "
                                + canadians.get(i).surname, "|  ", canadians.get(i).club, "|  ",
                        canadians.get(i).canadians + " (" + canadians.get(i).goals + "+" + canadians.get(i).assists + ")"));
            }else {

                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", canadians.get(i).name + " "
                                + canadians.get(i).surname, "|  ", canadians.get(i).club, "|  ",
                        canadians.get(i).canadians + " (" + canadians.get(i).goals + "+" + canadians.get(i).assists + ")"));
            }
            counter =canadians.get(i).canadians;
        }
    }

    void topYellows() {
        ArrayList<Player> yellows = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.yellows != 0){
                    yellows.add(player);
                }
            }
        }
        yellows.sort(Comparator.comparingInt(Player::getYellows));
        Collections.reverse(yellows);

        frame.classificationstat.get(0).setText(String.format("%40s", "THE MOST YELLOW CARDS"));
        frame.classificationstat.get(1).setText(("POS.|          PLAYER          |         CLUB        |  YELLOWS"));
        frame.classificationstat.get(2).setText(("--------------------------------------------------------------------"));
        int counter = 0;
        for (int i = 0; i < Math.min(yellows.size(),10); i++) {
            if(yellows.get(i).yellows==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", yellows.get(i).name +" "
                                + yellows.get(i).surname, "|  ", yellows.get(i).club, "|  ",
                        yellows.get(i).yellows));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", yellows.get(i).name + " "
                                + yellows.get(i).surname, "|  ", yellows.get(i).club, "|  ",
                        yellows.get(i).yellows));
            }
            counter =yellows.get(i).yellows;
        }
    }

    void topReds() {
        ArrayList<Player> reds = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.reds != 0){
                    reds.add(player);
                }
            }
        }
        reds.sort(Comparator.comparingInt(Player::getReds));
        Collections.reverse(reds);
        frame.classificationstat.get(0).setText(String.format("%40s", "THE MOST RED CARDS"));
        frame.classificationstat.get(1).setText("POS.|          PLAYER          |         CLUB        |  REDS");
        frame.classificationstat.get(2).setText("--------------------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(10,reds.size()); i++) {
            if(reds.get(i).reds==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", reds.get(i).name +" "
                                + reds.get(i).surname, "|  ", reds.get(i).club, "|  ",
                        reds.get(i).reds));

            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", reds.get(i).name + " "
                                + reds.get(i).surname, "|  ", reds.get(i).club, "|  ",
                        reds.get(i).reds));
            }
            counter =reds.get(i).reds;
        }
    }

    void topFouls() {
        ArrayList<Player> fouls = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.fouls != 0){
                    fouls.add(player);
                }
            }
        }
        fouls.sort(Comparator.comparingInt(Player::getFouls));
        Collections.reverse(fouls);
        frame.classificationstat.get(0).setText(String.format("%40s", "THE MOST FOULING PLAYERS"));
        frame.classificationstat.get(1).setText(("POS.|          PLAYER          |         CLUB        |  FOULS (Y+R)"));
        frame.classificationstat.get(2).setText("--------------------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(fouls.size(),10); i++) {
            if(fouls.get(i).fouls==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", fouls.get(i).name +" "
                                + fouls.get(i).surname, "|  ", fouls.get(i).club, "|  ",
                        fouls.get(i).fouls + " (" + fouls.get(i).yellows + "+" + fouls.get(i).reds + ")"));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", fouls.get(i).name + " "
                                + fouls.get(i).surname, "|  ", fouls.get(i).club, "|  ",
                        fouls.get(i).fouls + " (" + fouls.get(i).yellows + "+" + fouls.get(i).reds + ")"));
            }
            counter =fouls.get(i).fouls;
        }
    }
    void topFouled() {
        ArrayList<Player> fouled = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.fouled != 0){
                    fouled.add(player);
                }
            }
        }
        fouled.sort(Comparator.comparingInt(Player::getFouled));
        Collections.reverse(fouled);
        frame.classificationstat.get(0).setText(String.format("%40s", "THE MOST FREQUENT FOULED PLAYERS"));
        frame.classificationstat.get(1).setText("POS.|          PLAYER          |         CLUB        |  FOULED");
        frame.classificationstat.get(2).setText("--------------------------------------------------------------------");
        int counter = 0;
        for (int i = 0; i < Math.min(10,fouled.size()); i++) {
            if(fouled.get(i).fouled==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", fouled.get(i).name +" "
                                + fouled.get(i).surname, "|  ", fouled.get(i).club, "|  ",
                        fouled.get(i).fouled));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", fouled.get(i).name + " "
                                + fouled.get(i).surname, "|  ", fouled.get(i).club, "|  ",
                        fouled.get(i).fouled));

            }
            counter =fouled.get(i).fouled;
        }
    }
    void topShoots() {
        ArrayList<Player> shoots = new ArrayList<>();
        for (Club team : teams) {
            for(Player player : team.players){
                if(player.shoots != 0){
                    shoots.add(player);
                }
            }
        }
        shoots.sort(Comparator.comparingInt(Player::getShoots));
        Collections.reverse(shoots);
        frame.classificationstat.get(0).setText(String.format("%40s", "THE MOST FREQUENT SHOOTING PLAYERS"));

        frame.classificationstat.get(1).setText(("POS.|          PLAYER          |         CLUB        |  SHOOTS (OT+G)"));
        frame.classificationstat.get(2).setText(("--------------------------------------------------------------------"));
        int counter = 0;
        for (int i = 0; i < Math.min(10,shoots.size()); i++) {
            if(shoots.get(i).shoots==counter){
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", "=", "|  ", shoots.get(i).name +" "
                                + shoots.get(i).surname, "|  ", shoots.get(i).club, "|  ",
                        shoots.get(i).shoots + " (" + shoots.get(i).shootsonTarget + "+" + shoots.get(i).goals + ")"));
            }else {
                frame.classificationstat.get(i+3).setText(String.format("%3s %1s %22s %1s %17s %1s %2s", i+1, "|  ", shoots.get(i).name + " "
                                + shoots.get(i).surname, "|  ", shoots.get(i).club, "|  ",
                        shoots.get(i).shoots + " (" + shoots.get(i).shootsonTarget + "+" + shoots.get(i).goals + ")"));
            }
            counter =shoots.get(i).shoots;
        }
    }

}