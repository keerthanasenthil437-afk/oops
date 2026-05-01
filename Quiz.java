import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz {

    static int timeleft = 30;
    static int curqn = 0;
    static int score = 0;

    static String[] qn = {
        "1. What is the capital of India?",
        "2. Which language is used for Android development?",
        "3. Who invented Java?",
        "4. What does CPU stand for?",
        "5. Which is not a programming language?"
    };

    static String opn[][] = {
        {"a) Delhi", "b) Mumbai", "c) Chennai", "d) Kolkata"},
        {"a) Python", "b) Java", "c) C", "d) HTML"},
        {"a) Dennis Ritchie", "b) James Gosling", "c) Bjarne Stroustrup", "d) Elon Musk"},
        {"a) Central Process Unit", "b) Central Processing Unit", "c) Computer Personal Unit", "d) Central Processor Utility"},
        {"a) Java", "b) Python", "c) HTML", "d) C++"}
    };

    static String[] ans = {
        "a) Delhi",
        "b) Java",
        "b) James Gosling",
        "b) Central Processing Unit",
        "c) HTML"
    };

    static Thread timerThread;
    static JButton next;

    // ✅ CHECK ANSWER
    static boolean checkAnswer(JRadioButton r1, JRadioButton r2,
                               JRadioButton r3, JRadioButton r4, int qn) {

        String selected = "";

        if (r1.isSelected()) selected = r1.getText();
        else if (r2.isSelected()) selected = r2.getText();
        else if (r3.isSelected()) selected = r3.getText();
        else if (r4.isSelected()) selected = r4.getText();

        if (selected.equals(ans[qn])) {
            score++;
            return true;
        }
        return false;
    }

    public static void main(String args[]) {

        JFrame f = new JFrame("Quiz");

        JLabel l = new JLabel();
        JLabel tl = new JLabel("Time left: " + timeleft + "s");

        JRadioButton r1 = new JRadioButton();
        JRadioButton r2 = new JRadioButton();
        JRadioButton r3 = new JRadioButton();
        JRadioButton r4 = new JRadioButton();

        ButtonGroup b = new ButtonGroup();

        next = new JButton("Next ->");

        // ✅ BUTTON ACTION WITH COLOR FEEDBACK
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                stopTimer();

                boolean isCorrect = checkAnswer(r1, r2, r3, r4, curqn - 1);

                // 🎨 Color feedback
                if (isCorrect) {
                    f.getContentPane().setBackground(Color.GREEN);
                } else {
                    f.getContentPane().setBackground(Color.RED);
                }

                // ⏳ Delay then next question
                Timer delay = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        f.getContentPane().setBackground(null);
                        Nextqn(l, r1, r2, r3, r4, b, tl);
                    }
                });

                delay.setRepeats(false);
                delay.start();
            }
        });

        // UI SETUP
        l.setBounds(30, 30, 450, 30);
        tl.setBounds(300, 10, 150, 30);

        r1.setBounds(40, 80, 300, 30);
        r2.setBounds(40, 120, 300, 30);
        r3.setBounds(40, 160, 300, 30);
        r4.setBounds(40, 200, 300, 30);

        next.setBounds(200, 260, 120, 30);

        b.add(r1);
        b.add(r2);
        b.add(r3);
        b.add(r4);

        f.add(l);
        f.add(tl);
        f.add(r1);
        f.add(r2);
        f.add(r3);
        f.add(r4);
        f.add(next);

        f.setSize(500, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Nextqn(l, r1, r2, r3, r4, b, tl);
    }

    // ✅ LOAD NEXT QUESTION
    static void Nextqn(JLabel qlabel, JRadioButton r1, JRadioButton r2,
                       JRadioButton r3, JRadioButton r4,
                       ButtonGroup b, JLabel tl) {

        if (curqn < qn.length) {

            qlabel.setText(qn[curqn]);

            r1.setText(opn[curqn][0]);
            r2.setText(opn[curqn][1]);
            r3.setText(opn[curqn][2]);
            r4.setText(opn[curqn][3]);

            b.clearSelection();

            timeleft = 30;
            tl.setText("Time left: " + timeleft + "s");

            curqn++;

            startTimer(tl, qlabel, r1, r2, r3, r4, b);
        }
        else {
            stopTimer();

            qlabel.setText("QUIZ FINISHED!!! Your Score: " + score + "/" + qn.length);

            r1.setVisible(false);
            r2.setVisible(false);
            r3.setVisible(false);
            r4.setVisible(false);

            tl.setText("");

            next.setEnabled(false);

            JOptionPane.showMessageDialog(null,
                    "QUIZ FINISHED!\nYour Score: " + score + "/" + qn.length);
        }
    }

    // ✅ TIMER FUNCTION
    static void startTimer(final JLabel tl, final JLabel qlabel,
                           final JRadioButton r1, final JRadioButton r2,
                           final JRadioButton r3, final JRadioButton r4,
                           final ButtonGroup b) {

        stopTimer();

        timerThread = new Thread(new Runnable() {
            public void run() {

                while (timeleft > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }

                    timeleft--;

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            tl.setText("Time left: " + timeleft + "s");
                        }
                    });
                }

                // ⏰ TIME UP
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        tl.setText("Time's up!");

                        boolean isCorrect = checkAnswer(r1, r2, r3, r4, curqn - 1);

                        if (isCorrect) {
                            qlabel.setForeground(Color.GREEN);
                        } else {
                            qlabel.setForeground(Color.RED);
                        }

                        Timer delay = new Timer(1000, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                qlabel.setForeground(Color.BLACK);
                                Nextqn(qlabel, r1, r2, r3, r4, b, tl);
                            }
                        });

                        delay.setRepeats(false);
                        delay.start();
                    }
                });
            }
        });

        timerThread.start();
    }

    // ✅ STOP TIMER
    static void stopTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }
}