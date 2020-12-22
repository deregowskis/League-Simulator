import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Class match represents every game that is played in a simulator.
 */
public class Match implements Runnable {
    Interface frame;
    Boolean islast=false;
    /**
     * Clubs involved in a game.
     */
    Club team1;
    Club team2;


    /**
     * probability of events and types of players involved in them.
     */
    final double changePossesion = 0.5;
    final double action = 0.5;
    final double foul = 0.35;
    final double defenderfoul = 0.6;
    final double forwardfoul = 0.1;
    final double defendershoot = 0.1;
    final double forwardshoot = 0.6;
    final double defenderassist = 0.1;
    final double midfielderassist = 0.6;
    Position position;
    Position assistantPosition;
    Random r = new Random();

    /**
     * Parameters connected with ball possesion during the game.
     */
    boolean possesionTeam1 = true;
    double possesion1counter = 0;
    double possesion2counter = 0;

    /**
     * Constructor involves:
     *
     * @param team1 (host team)
     * @param team2 (away team)
     */
    public Match(Club team1, Club team2, Interface frame) {
        this.team1 = team1;
        this.team2 = team2;
        this.frame=frame;
    }

    /**
     * play() is the main method for this class.
     *
     * @throws InterruptedException
     */
    @Override
    public void run() {
        /**
         * It's based on a loop that runs 90 times - one for every minute.
         */
        frame.headerHost.setText(team1.toString());
        frame.headerHost.setBounds((int) (300-team1.toString().length()*11.8)/2, 50, 300, 200);
        frame.headerGuest.setText(team2.toString());
        frame.headerGuest.setBounds((int) (400+(300-team2.toString().length()*11.8)/2), 50, 300, 200);
        frame.logoHost.setIcon(new ImageIcon((new ImageIcon("Logos/"+team1+".png")).getImage().
                getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING)));
        frame.logoGuest.setIcon(new ImageIcon((new ImageIcon("Logos/"+team2+".png")).getImage().
                getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING)));
        frame.score.setBounds(300,50,100,200);
        frame.time.setBounds(335,50,100,50);
        try {


            for (int i = 1; i < 91; i++) {

                frame.score.setText(team1.currentGoals + "  :  " + team2.currentGoals);
                if(i>1 & !frame.skip){
                    statistics(i-1);
                        Thread.sleep(500);
                    }



                /**
                 * Ball possesion update
                 */


                if (possesionTeam1) {
                    possesion1counter += 1;
                } else {
                    possesion2counter += 1;
                }


                /**
                 * When the possesion is changed in this minute, nothing else may happen.
                 */
                if (r.nextDouble() < changePossesion) {
                    possesionTeam1 = !possesionTeam1;
                    printMinutes(i);
                    frame.time.setText(i+"'");
                    continue;
                }

                /**
                 * If statement determines whether any action is going to happen or not.
                 */
                if (r.nextDouble() < action) {


                    /**
                     * Another if statement determines whether the action is a shoot or a foul.
                     */
                    if (r.nextDouble() < foul) {

                        /**
                         * A double positionrate determines position of a player.
                         */
                        double positionrate = r.nextDouble();
                        if (positionrate >= (1 - defenderfoul)) {
                            position = Position.DEFENDER;
                        } else if (positionrate < forwardfoul) {
                            position = Position.FORWARD;
                        } else {
                            position = Position.MIDFIELDER;
                        }

                        /**
                         * (see: @getPosition method in Club class and @foul method in Player class)
                         */
                        if (possesionTeam1) {
                            team2.getPosition(position).get(r.nextInt((team2.getPosition(position)).size()))
                                    .foul(team1.players.get(r.nextInt(11)),i);
                        } else {
                            team1.getPosition(position).get(r.nextInt((team1.getPosition(position)).size()))
                                    .foul(team2.players.get(r.nextInt(11)),i);
                        }
                    } else {

                        /**
                         * Doubles positionrate and assistantpositionrate determine position of a player.
                         */
                        double positionrate = r.nextDouble();
                        double assistantpositionrate = r.nextDouble();
                        if (positionrate > (1 - forwardshoot)) {
                            position = Position.FORWARD;
                        } else if (positionrate < defendershoot) {
                            position = Position.DEFENDER;
                        } else {
                            position = Position.MIDFIELDER;
                        }
                        if (assistantpositionrate >= (1 - midfielderassist)) {
                            assistantPosition = Position.MIDFIELDER;
                        } else if (assistantpositionrate < defenderassist) {
                            assistantPosition = Position.DEFENDER;
                        } else {
                            assistantPosition = Position.FORWARD;
                        }

                        /**
                         * (see: @getPosition method in Club class and @shoot method in Player class)
                         */
                        if (possesionTeam1) {
                            if (assistantPosition != position) {
                                team1.getPosition(position).get(r.nextInt((team1.getPosition(position)).size()))
                                        .shoot(team1.getPosition(assistantPosition)
                                                        .get(r.nextInt((team1.getPosition(assistantPosition)).size())),
                                                team2.getPosition(Position.GOALKEEPER).get(0), i);
                            } else {
                                /**
                                 * The same player cannot both have an assist and score.
                                 */
                                int notthesame1 = r.nextInt((team1.getPosition(position)).size());
                                int notthesame2 = r.nextInt((team1.getPosition(assistantPosition)).size());
                                while (notthesame1 == notthesame2) {
                                    notthesame2 = r.nextInt((team1.getPosition(assistantPosition)).size());
                                }
                                team1.getPosition(position).get(notthesame1)
                                        .shoot(team1.getPosition(assistantPosition)
                                                        .get(notthesame2),
                                                team2.getPosition(Position.GOALKEEPER).get(0),i);
                            }
                            possesionTeam1 = false;
                        } else {
                            if (assistantPosition != position) {
                                team2.getPosition(position).get(r.nextInt((team2.getPosition(position)).size()))
                                        .shoot(team2.getPosition(assistantPosition)
                                                        .get(r.nextInt((team2.getPosition(assistantPosition)).size())),
                                                team1.getPosition(Position.GOALKEEPER).get(0),i);
                            } else {
                                int notthesame1 = r.nextInt((team2.getPosition(position)).size());
                                int notthesame2 = r.nextInt((team2.getPosition(assistantPosition)).size());
                                while (notthesame1 == notthesame2) {
                                    notthesame2 = r.nextInt((team2.getPosition(assistantPosition)).size());
                                }
                                team2.getPosition(position).get(notthesame1)
                                        .shoot(team2.getPosition(assistantPosition)
                                                        .get(notthesame2),
                                                team1.getPosition(Position.GOALKEEPER).get(0),i);
                            }
                            possesionTeam1 = true;
                        }
                    }
                    frame.time.setText(i+"'");
                    continue;
                }
                /**
                 * Otherwise nothing happens.
                 */
                frame.time.setText(i+"'");
                printMinutes(i);
            }
            frame.score.setText(team1.currentGoals + "  :  " + team2.currentGoals);
            statistics(90);
            endgame();




            if(islast) {
                Thread.sleep(4000);
                for(JLabel x: frame.minutes){
                    x.setVisible(false);

                }
                for(JLabel x: frame.classificationstat){
                    x.setVisible(false);
                }
                for(JLabel x: frame.statisticsLabels){
                    x.setVisible(false);
                }
                frame.nextMatch.setEnabled(false);
                frame.skipMatch.setVisible(false);
                frame.headerHost.setVisible(false);
                frame.headerGuest.setVisible(false);
                frame.logoHost.setVisible(false);
                frame.logoGuest.setVisible(false);
                frame.score.setVisible(false);
                frame.time.setVisible(false);
                frame.info.setVisible(true);
                frame.info.setText("Tournament has ended!");
                frame.info.setBounds(100,300,500,100);
                frame.info.setFont(new Font("Verdana", Font.BOLD, 30));
            }
            else {

                frame.nextMatch.setEnabled(true);
            }
            frame.statistics.setEnabled(true);
            frame.table.setEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    /**
     * Method endgame() is used to update data about teams' results when it's finished.
     */
    void endgame() {
        team1.count();
        team2.count();

        team1.matches += 1;
        team2.matches += 1;

        team1.goalsfor += team1.currentGoals;
        team1.goalsagainst += team2.currentGoals;
        team2.goalsfor += team2.currentGoals;
        team2.goalsagainst += team1.currentGoals;

        if (team1.currentGoals > team2.currentGoals) {
            team1.wins += 1;
            team1.points += 3;
            team2.loses += 1;
        } else if (team2.currentGoals > team1.currentGoals) {
            team2.wins += 1;
            team2.points += 3;
            team1.loses += 1;
        } else {
            team1.draws += 1;
            team1.points += 1;
            team2.draws += 1;
            team2.points += 1;
        }
        team1.refresh();
        team2.refresh();

    }

    /**
     * Method statistics prints the match statistics to the viever.
     * (Note: it's not a final form).
     */
    void statistics(int i) {
        double possesion1 = Math.round((possesion1counter / i) * 100);
        double possesion2 = Math.round((possesion2counter / i) * 100);

        frame.statisticsLabels.get(0).setText("             Statistics");
        frame.statisticsLabels.get(0).setFont(new Font("Verdana", Font.BOLD, 13));
        frame.statisticsLabels.get(1).setText((int) possesion1 + "%" + "    POSSESION    " + (int) possesion2 +"%");
        frame.statisticsLabels.get(2).setText(team1.currentshoots + "       SHOOTS        " + team2.currentshoots);
        frame.statisticsLabels.get(3).setText(team1.currentShootsonTarget + "      ON TARGET      " + team2.currentShootsonTarget);
        frame.statisticsLabels.get(4).setText(team1.currentFouls + "       FOULS         " + team2.currentFouls);
        frame.statisticsLabels.get(5).setText(team1.currentYellows + "    YELLOW CARDS     " + team2.currentYellows);
        frame.statisticsLabels.get(6).setText(team1.currentReds + "      RED CARDS      " + team2.currentReds);

    }


    void printMinutes(int i){
        if(i==1){
            frame.minutes.get(0).setText(i+"':");}
        else if(i==2){
            frame.minutes.get(1).setText(i+"':");}
        else if(i==3){
            frame.minutes.get(2).setText(i+"':");}
        else if(i==4){
            frame.minutes.get(3).setText(i+"':");}
        else if (i==5){
            frame.minutes.get(4).setText(i+"':");

        }
        else{
            frame.minutes.get(0).setText(frame.minutes.get(1).getText());
            frame.minutes.get(1).setText(frame.minutes.get(2).getText());
            frame.minutes.get(2).setText(frame.minutes.get(3).getText());
            frame.minutes.get(3).setText(frame.minutes.get(4).getText());
            frame.minutes.get(4).setText(i+"':");
        }
    }
}