import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class oppsass{
static int timeleft=30;
static int curqn=0;
static int score = 0;
static String[] qn={"1.What is java?","2.What is c?","3.What is python?","4.What is java script?","5.What is c++?"};
static String opn[][]={{"a)Language","b)Pen","c)Pencil","d)Chair"},{"a)Apple","b)Ball","c)Language","d)Dog"},
{"a)Keerthana","b)Language","c)Sahhana","d)Rubiga"},{"a)Write","b)Read","c)Append","d)Language"},
{"a)Language","b)abc","c)def","d)ghi"}};
static String[] ans = {"a)Language","c)Language","b)Language","d)Language","a)Language"  
};


static void checkAnswer(JRadioButton r1, JRadioButton r2, JRadioButton r3, JRadioButton r4, int qn){
    String selected="";
    if(r1.isSelected()) 
        selected = r1.getText();
    else if(r2.isSelected()) 
        selected = r2.getText();
    else if(r3.isSelected()) 
        selected = r3.getText();
    else if(r4.isSelected()) 
        selected = r4.getText();
    if(selected.equals(ans[qn])) {
        score++;
    }
}

static Thread timerThread;
public static void main (String args[]){
JFrame f=new JFrame("Quiz");
JLabel l=new JLabel();
JLabel tl=new JLabel("Timeleft "+timeleft+"s");
JRadioButton r1=new JRadioButton("a)");
JRadioButton r2=new JRadioButton("b)");
JRadioButton r3=new JRadioButton("c)");
JRadioButton r4=new JRadioButton("d)");
ButtonGroup b=new ButtonGroup();
JButton next=new JButton("Next->");

next.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        checkAnswer(r1,r2,r3,r4, curqn);
        stopTimer();
        Nextqn(l,r1,r2,r3,r4,b,tl);
    }
});

r1.setBounds(40,65,100,30);
r2.setBounds(40,100,100,30);
r3.setBounds(40,135,100,30);
r4.setBounds(40,170,100,30);
l.setBounds(30,30,100,30);
tl.setBounds(300,30,150,30);
next.setBounds(200,220,100,30);
f.add(l); f.add(tl);
b.add(r1); b.add(r2); b.add(r3); b.add(r4);
f.add(r1); f.add(r2); f.add(r3); f.add(r4);
f.add(next);
f.setSize(500,500);
f.setLayout(null);
f.setVisible(true);
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Nextqn(l,r1,r2,r3,r4,b,tl);
}
static void Nextqn(JLabel qlabel,JRadioButton r1,JRadioButton r2,JRadioButton r3,JRadioButton r4,ButtonGroup b,JLabel tl){
if(curqn<5){
qlabel.setText(qn[curqn]);
r1.setText(opn[curqn][0]);r2.setText(opn[curqn][1]);
r3.setText(opn[curqn][2]);r4.setText(opn[curqn][3]);
b.clearSelection();
timeleft=30;
tl.setText("Time left "+timeleft+"s");
curqn++;
startTimer(tl,qlabel,r1,r2,r3,r4);
}
else{
qlabel.setText("QUIZ FINISHED!!! Your Score: " + score + "/5");
r1.setVisible(false);r2.setVisible(false);
r3.setVisible(false);r4.setVisible(false);
tl.setText("");
}}
static void startTimer(final JLabel tl,final JLabel qlabel,final JRadioButton r1,final JRadioButton r2,final JRadioButton r3,final JRadioButton r4){
stopTimer();
timerThread=new Thread(new Runnable(){
public void run(){
while(timeleft>0){
try{
Thread.sleep(1000);}
catch(InterruptedException e){
return; }
timeleft--;
SwingUtilities.invokeLater(new Runnable(){
public void run(){
tl.setText("Time left "+timeleft+"s");
}});}
SwingUtilities.invokeLater(new Runnable(){
public void run(){
tl.setText("Time's up!!!");
Nextqn(qlabel,r1,r2,r3,r4,new ButtonGroup(),tl);
}
});
}
});
timerThread.start();
}
static void stopTimer(){
if(timerThread!=null&&timerThread.isAlive()){
timerThread.interrupt();
}}}
