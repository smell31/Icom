package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

class MyFlame extends Frame implements Runnable,ActionListener {

    Button btn1;
    Button btn2;
    TextField txt1;
    TextField txt2;
    boolean
            threadFlg = false;
    Thread td;

    java.io.BufferedWriter out = null;

    int data2,data3,data4,data5,data6;

    String h[] = new String[32];
    String h2[] = new String[32];
    String h3[] = new String[32];
    String h4[] = new String[32];
    String h5[] = new String[32];
    String h6[] = new String[32];
    String h7[] = new String[32];
    String h8[] = new String[32];
    String h9[] = new String[32];
    String h10[] = new String[32];
    String h11[] = new String[32];
    String h12[] = new String[32];

    public MyFlame(String title) {
        setTitle(title);
        setLayout(null);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }

        });

        setBackground(SystemColor.control);

        btn1 = new Button("計測開始(1)");
        btn1.addActionListener(this);
        add(btn1);
        btn1.setBounds(60,40,75,50);

        btn2 = new Button("計測終了");
        btn2.addActionListener(this);
        add(btn2);
        btn2.setBounds(160,40,75,50);

        txt1 = new TextField("計測開始を押してください");
        add(txt1);
        txt1.setBounds(55,120,190,20);

        txt2 = new TextField("計測していません");
        add(txt2);
        txt2.setBounds(55,160,190,20);

    }


    public void actionPerformed(ActionEvent e){

        if(e.getSource() == btn1){
            myStart();
            btn1.setEnabled(false);
        }
        else if(e.getSource() == btn2){
            stop();
            btn1.setEnabled(true);
        }
    }

    public void myStart(){

        td = new Thread(this);
        threadFlg = true;

        Calendar cal1 = Calendar.getInstance();		//オブジェクトの作成
        int year = cal1.get(Calendar.YEAR);			//年を取得
        int month = cal1.get(Calendar.MONTH)+1;		//月を取得
        int day = cal1.get(Calendar.DATE);			//日を取得
        int hour = cal1.get(Calendar.HOUR_OF_DAY);	//時を取得
        int minute = cal1.get(Calendar.MINUTE);		//分を取得
        int second = cal1.get(Calendar.SECOND);		//秒を取得

        txt1.setText(""+(year+"/"+ month +"/"+ day +" "+ hour +":"+ minute +":"+ second+" から測定中"));

        td.start();

    }

    public void run(){

        try{
            Calendar cal = Calendar.getInstance();	//オブジェクトの作成
            int ne = cal.get(Calendar.YEAR);		//現在の年を取得
            int tu = cal.get(Calendar.MONTH)+1;		//現在の月を取得
            int hi = cal.get(Calendar.DATE);		//現在の日を取得

            String M;
            if(tu < 10)
            {M="0"+Integer.toString(tu);}
            else
            {M=Integer.toString(tu);}

            String D;
            if(hi < 10)
            {D="0"+Integer.toString(hi);}
            else
            {D=Integer.toString(hi);}
            out = new java.io.BufferedWriter(new java.io.FileWriter(""+ne+M+D+"data1.csv"));
            //年～秒のCSVファイル作成

            out.write("日付"+","+"時間"+","+"207kHz"+","+"194kHz"+","+"295kHz"+","+"522kHz"+","+"40kHz"+","+"80kHz"+","+"78.9kHz"+","+"100kHz"+","+"44kHz"+","+"240kHz"+","+"60kHz"+","+"50kHz");
            //書き込み

            out.newLine();	//改行

        }catch( Exception f){
            stop();
        } finally{

        }
        System.out.println("A");

        while(threadFlg) {
            try {

                //RS232C 通信の宣言
                CommPortIdentifier ports;
                ports = CommPortIdentifier.getPortIdentifier("COM5");
                SerialPort port = (SerialPort) ports.open("RS232C", 1000);
                port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                //宣言終わり


                OutputStream out1 = port.getOutputStream();
                //出力ストリーム

                System.out.println("B");

                //80kHz 開始
                int msg61 = 0xFE;
                int msg62 = 0xFE;
                int msg63 = 0x5A;
                int msg64 = 0xE0;
                int msg65 = 0x05;
                int msg66 = 0x00;
                int msg67 = 0x00;
                int msg68 = 0x08;
                int msg69 = 0x00;
                int msg610 = 0xFD;

                out1.write(msg61);
                out1.write(msg62);
                out1.write(msg63);
                out1.write(msg64);
                out1.write(msg65);
                out1.write(msg66);
                out1.write(msg67);
                out1.write(msg68);
                out1.write(msg69);
                out1.write(msg610);

                Thread.sleep(9000);

                int msg611 = 0xFE;
                int msg612 = 0xFE;
                int msg613 = 0x5A;
                int msg614 = 0xE0;
                int msg615 = 0x15;
                int msg616 = 0x02;
                int msg617 = 0xFD;

                out1.write(msg611);
                out1.write(msg612);
                out1.write(msg613);
                out1.write(msg614);
                out1.write(msg615);
                out1.write(msg616);
                out1.write(msg617);

                InputStream in6 = port.getInputStream();

                int i6 = 0;
                int i;
                for (i = 0; i6 < 32; i6++) {

                    int f;
                    f = in6.read();
                    String data = Integer.toHexString(f);
                    h6[i6] = data;
                }

                out.write(h6[29] + h6[30]);
                out.flush();

                out.write(",");
                //80kHz	終了


                Thread.sleep(9000);

                out1.flush();
                out1.close();
                port.close();

                out.newLine();//改行
                out.flush();

            } catch (Exception f) {
                f.printStackTrace();
                stop();
            } finally {

            }
        }
    }

    public void stop(){
        threadFlg = false;

        Calendar cal4 = Calendar.getInstance();	//オブジェクトの生成

        int ye = cal4.get(Calendar.YEAR);			//現在の年を取得
        int mon = cal4.get(Calendar.MONTH)+1;		//現在の月を取得
        int d = cal4.get(Calendar.DATE);			//現在の日を取得
        int ho = cal4.get(Calendar.HOUR_OF_DAY);	//現在の時を取得
        int minu = cal4.get(Calendar.MINUTE);		//現在の分を取得
        int seco = cal4.get(Calendar.SECOND);		//現在の秒を取得

        txt2.setText(""+(ye + "/"+ mon + "/" + d +"," +ho + ":" +minu + ":" + seco +" に測定終了"));

        try{
            out.flush();
            out.close();
        }catch(Exception f){}

    }


}