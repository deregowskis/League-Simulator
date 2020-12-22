import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Interface extends JFrame implements ActionListener {
    Boolean skip;
    Tournament tournament;
    ArrayList<String> chosenClubs = new ArrayList<>();
    JLabel title;
    JLabel note;
    JLabel headerHost;
    JLabel headerGuest;
    JLabel score;
    JLabel logoHost;
    JLabel logoGuest;
    JLabel time;
    JButton openSimulator;
    JButton exit;
    JButton chooseClubs;
    JButton nextMatch;
    JButton table;
    JButton statistics;
    JButton skipMatch;
    JTable leagueTable;
    String[] columns;
    String[][] rows;
    JScrollPane scrollPane;

    ArrayList<JLabel> minutes = new ArrayList<>() {
        {
            for (int i = 0; i < 5; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<JLabel> classificationstat = new ArrayList<>() {
        {
            for (int i = 0; i < 13; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<JLabel> tableLabels = new ArrayList<>() {
        {
            for (int i = 0; i < 13; i++) {
                add(new JLabel());
            }
        }
    };


    ArrayList<JLabel> statisticsLabels = new ArrayList<>() {
        {
            for (int i = 0; i < 7; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<JRadioButton> RadioBox = new ArrayList<>() {
        {
            for (int i = 0; i < 8; i++) {
                add(new JRadioButton());
            }
        }
    };


    ArrayList<JCheckBox> checkBoxes = new ArrayList<>() {
        {
            for (int i = 0; i < 16; i++) {
                add(new JCheckBox());
            }
        }
    };

    ArrayList<JLabel> logos = new ArrayList<>() {
        {
            for (int i = 0; i < 16; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<String> classification = new ArrayList<>() {
        {
            add("Top Goalscorers");
            add("Top Asistants");
            add("Canadian classification");
            add("The most Yellow Cards");
            add("The most Red Cards");
            add("The most fouling players");
            add("The most frequent fouled players");
            add("The most frequent shooting players");
            add("topGoalscorers");
            add("topAssistants");
            add("topCanadians");
            add("topYellows");
            add("topReds");
            add("topFouls");
            add("topFouled");
            add("topShoots");

        }
    };

    ArrayList<String> clubs = new ArrayList<>() {
        {
            add("AC Milan");
            add("Ajax");
            add("Bayern Munich");
            add("Benfica");
            add("Borussia Dortmund");
            add("FC Barcelona");
            add("Juventus");
            add("Lech Poznan");
            add("Legia Warsaw");
            add("Liverpool");
            add("Lokomotiv Moscow");
            add("Manchester City");
            add("Manchester United");
            add("PSG");
            add("Rangers");
            add("Real Madrid");
        }
    };

    public Interface() throws Exception {

        setSize(1000, 800);
        setTitle("Tournament Simulator");
        setLayout(null);

        title = new JLabel("Tournament Simulator");
        title.setFont(new Font("Verdana", Font.BOLD, 17));
        title.setBounds(400, 100, 300, 100);
        add(title);

        openSimulator = new JButton("OPEN SIMULATOR");
        openSimulator.setBounds(400, 300, 200, 100);
        add(openSimulator);
        openSimulator.addActionListener(this);

        exit = new JButton("EXIT");
        exit.setBounds(400, 500, 200, 100);
        add(exit);
        exit.addActionListener(this);

        chooseClubs = new JButton("Choose Clubs");
        chooseClubs.setBounds(400, 625, 200, 100);
        add(chooseClubs);
        chooseClubs.addActionListener(this);
        chooseClubs.setVisible(false);

        note = new JLabel("You have to choose 4 clubs");
        note.setFont(new Font("Verdana", Font.BOLD, 13));
        note.setBounds(100, 650, 200, 50);
        add(note);
        note.setVisible(false);

        int i = 0;
        for (JLabel logo : logos) {
            logo.setIcon(new ImageIcon((new ImageIcon("Logos/" + clubs.get(i) + ".png")).getImage().
                    getScaledInstance(75, 75, Image.SCALE_AREA_AVERAGING)));
            logo.setBounds(((i) % 4) * 200 + 150, (i / 4 + 1) * 125 - 40, 100, 100);
            add(logo);
            logo.setVisible(false);
            i++;
        }

        i = 0;
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setText(clubs.get(i));
            checkBox.setBounds(((i) % 4) * 200 + 150, (i / 4 + 1) * 125 + 60, 160, 20);
            add(checkBox);
            checkBox.addActionListener(this);
            checkBox.setVisible(false);
            i++;
        }

        columns = new String[]{"POS.", "TEAM", "GP", "W", "D", "L", "GF", "GA", "GD", "Pts"};
        rows = new String[][]{{"k","k","k","k","k","k","k","k","k","k"},{"k","k","k","k","k","k","k","k","k","k"},
                {"k","k","k","k","k","k","k","k","k","k"},{"k","k","k","k","k","k","k","k","k","k"}};
        leagueTable = new JTable(rows,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        leagueTable.setBounds(100,200,400,70);
        leagueTable.setPreferredScrollableViewportSize(new Dimension(400, 70));
        leagueTable.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(leagueTable);
        add(scrollPane);
        scrollPane.setVisible(false);

        nextMatch = new JButton("Next Match");
        nextMatch.setBounds(700, 100, 200, 100);
        add(nextMatch);
        nextMatch.addActionListener(this);
        nextMatch.setVisible(false);

        headerHost = new JLabel("");
        headerHost.setFont(new Font("Verdana", Font.BOLD, 20));
        add(headerHost);
        headerHost.setVisible(false);

        headerGuest = new JLabel("");
        headerGuest.setFont(new Font("Verdana", Font.BOLD, 20));
        add(headerGuest);
        headerGuest.setVisible(false);

        logoHost = new JLabel();
        logoHost.setBounds(115, 25, 100, 100);
        add(logoHost);
        logoHost.setVisible(false);

        logoGuest = new JLabel();
        logoGuest.setBounds(515, 25, 100, 100);
        add(logoGuest);
        logoGuest.setVisible(false);

        score = new JLabel();
        score.setFont(new Font("Verdana", Font.BOLD, 30));
        add(score);
        score.setVisible(false);

        time = new JLabel();
        time.setFont(new Font("Verdana", Font.BOLD, 15));
        add(time);
        time.setVisible(false);

        i = 0;
        for (JLabel minute : minutes) {
            minute.setText("");
            minute.setFont(new Font("Verdana", Font.BOLD, 10));
            minute.setBounds(75, 450 + i * 35, 700, 25);
            add(minute);
            minute.setVisible(false);
            i++;
        }
        i = 0;
        for (JLabel stat : statisticsLabels) {
            stat.setText("");
            stat.setFont(new Font("Verdana", Font.BOLD, 10));
            stat.setBounds(260, 180 + i * 35, 700, 25);
            add(stat);
            stat.setVisible(false);
            i++;
        }




        table = new JButton("Table");
        table.setBounds(700, 250, 200, 100);
        add(table);
        table.addActionListener(this);
        table.setVisible(false);

        i = 0;
        for (JLabel label : tableLabels) {
            label.setText("");
            label.setFont(new Font("Verdana", Font.BOLD, 10));
            label.setBounds(100, 100 + i * 25, 700, 25);
            add(label);
            label.setVisible(false);
            i++;
        }

        statistics = new JButton("Statistics");
        statistics.setBounds(700, 400, 200, 100);
        add(statistics);
        statistics.addActionListener(this);
        statistics.setVisible(false);

        i = 0;
        for (JLabel statistic : classificationstat) {
            statistic.setText("");
            statistic.setFont(new Font("Verdana", Font.BOLD, 10));
            statistic.setBounds(100, 100 + i * 25, 700, 25);
            add(statistic);
            statistic.setVisible(false);
            i++;
        }
        i = 0;

        for (JRadioButton button : RadioBox) {
            button.setText(classification.get(i));
            button.setBounds(100, 500 + 25 * i, 300, 20);
            add(button);
            button.addActionListener(this);
            button.setVisible(false);
            i++;
        }


        skipMatch = new JButton("Skip match");
        skipMatch.setBounds(240, 650, 200, 100);
        add(skipMatch);
        skipMatch.addActionListener(this);
        skipMatch.setVisible(false);


    }

    @Override
    public void actionPerformed(ActionEvent e) {


        Object source = e.getSource();
        if (source == openSimulator) {

            title.setVisible(false);
            openSimulator.setVisible(false);
            exit.setVisible(false);
            scrollPane.setVisible(false);
            exit.setBounds(700, 550, 200, 100);
            chooseClubs.setVisible(true);
            for (JLabel logo : logos) {
                logo.setVisible(true);
            }

            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setVisible(true);
            }

        } else if (source == chooseClubs) {
            if (chosenClubs.size() != 4) {
                note.setVisible(true);


            } else {

                try {
                    tournament = new Tournament(new Club(chosenClubs.get(0), this),
                            new Club(chosenClubs.get(1), this), new Club(chosenClubs.get(2), this), new Club(chosenClubs.get(3), this), this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                tournament.addSchedule();
                note.setVisible(false);
                chooseClubs.setVisible(false);
                for (JCheckBox checkBox : checkBoxes) {
                    checkBox.setVisible(false);
                }
                for (JLabel logo : logos) {
                    logo.setVisible(false);
                }
                nextMatch.setVisible(true);
                table.setVisible(true);
                statistics.setVisible(true);
                exit.setVisible(true);
            }


        } else if (checkBoxes.contains(source)) {
            JCheckBox a = (JCheckBox) source;
            if (chosenClubs.size() == 4) {

                a.setSelected(false);
            }
            if (a.isSelected()) {
                chosenClubs.add(a.getText());
            } else {
                chosenClubs.remove(a.getText());
            }
        } else if (source == nextMatch) {
            skip = false;
            for (JRadioButton button : RadioBox) {
                button.setVisible(false);
            }
            for (JLabel minute : minutes) {
                minute.setText("");
                minute.setVisible(true);
            }

            for (JLabel statistic : statisticsLabels) {
                statistic.setVisible(true);
            }
            for(JLabel stat: classificationstat){
                stat.setVisible(false);
            }
            for (JLabel x : tableLabels) {
                x.setVisible(false);
            }

            headerHost.setVisible(true);
            headerGuest.setVisible(true);
            logoHost.setVisible(true);
            logoGuest.setVisible(true);
            score.setVisible(true);
            time.setVisible(true);
            skipMatch.setVisible(true);
            nextMatch.setEnabled(false);
            statistics.setEnabled(false);
            table.setEnabled(false);
            scrollPane.setVisible(false);

            try {
                tournament.run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        } else if (source == table) {
            for (JLabel minute : minutes) {
                minute.setVisible(false);
            }
            for (JLabel statistic : statisticsLabels) {
                statistic.setVisible(false);
            }
            for (JRadioButton button : RadioBox) {
                button.setVisible(false);
            }
            for (JLabel x : classificationstat) {
                x.setVisible(false);
            }
            for (JLabel x : tableLabels) {
                x.setVisible(false);//true
            }
            table.setEnabled(false);
            statistics.setEnabled(true);
            headerHost.setVisible(false);
            headerGuest.setVisible(false);
            logoHost.setVisible(false);
            logoGuest.setVisible(false);
            score.setVisible(false);
            time.setVisible(false);
            skipMatch.setVisible(false);

            tournament.table();
            leagueTable.setVisible(true);

        } else if (source == statistics) {
            for (JLabel minute : minutes) {
                minute.setVisible(false);
            }
            for (JLabel statistic : statisticsLabels) {
                statistic.setVisible(false);
            }
            for (JLabel x : tableLabels) {
                x.setVisible(false);
            }
            table.setEnabled(true);
            statistics.setEnabled(false);
            headerHost.setVisible(false);
            headerGuest.setVisible(false);
            logoHost.setVisible(false);
            logoGuest.setVisible(false);
            score.setVisible(false);
            time.setVisible(false);
            skipMatch.setVisible(false);
            scrollPane.setVisible(false);

            for (JRadioButton button : RadioBox) {
                button.setVisible(true);
                button.setSelected(false);


            }

        } else if (RadioBox.contains(source)) {
            JRadioButton button = (JRadioButton) source;

            for (JRadioButton button2 : RadioBox) {
                if (button2 != button) {
                    button2.setSelected(false);
                }
            }
            if (button.isSelected()) {
                for(JLabel stat: classificationstat){
                    stat.setText("");
                    stat.setVisible(true);
                }

                ArrayList<Method> abc = new ArrayList<>(Arrays.asList(tournament.getClass().getDeclaredMethods()));
                for (Method m : abc) {
                    if (m.getName().equals(classification.get(RadioBox.indexOf(button) + 8))) {
                        try {
                            m.invoke(tournament);
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();

                        }

                    }
                }


            }
            else{
                for(JLabel stat: classificationstat){
                    stat.setVisible(false);
                }
                //Print choose type of statistic

            }


        } else if (source == skipMatch) {
            skip = true;
            skipMatch.setVisible(false);
        } else if (source == exit) {
            dispose();
        }


    }
}