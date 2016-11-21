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
    boolean threadFlg = false;
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

        }catch( Exception f){stop();}
        finally{}

        while(threadFlg){
            try{

                //RS232C 通信の宣言
                CommPortIdentifier ports = CommPortIdentifier.getPortIdentifier( "COM1" );
                SerialPort port = ( SerialPort )ports.open( "RS232C",1000 );
                port.setSerialPortParams( 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                port.setFlowControlMode( SerialPort.FLOWCONTROL_NONE );
                //宣言終わり

                OutputStream out1 = port.getOutputStream();
                //出力ストリーム

                //207kHz始め
                int msg1 = 0xFE;	//プリアンブル	同期用コード
                int msg2 = 0xFE;	//同期用コード
                int msg3 = 0x5A;	//受信アドレス	（受信器）
                int msg4 = 0xE0;	//送信アドレス　（PC）
                int msg5 = 0x05;	//コマンド		（周波数データの設定）
                int msg6 = 0x00;	//周波数設定
                int msg7 = 0x70;	//
                int msg8 = 0x20;	//
                int msg9 = 0x00;	//設定終わり
                int msg10 = 0xFD;	//ポストアンブル	コマンド終わり

                out1.write( msg1);	//msg1~10を送信
                out1.write( msg2);
                out1.write( msg3);
                out1.write( msg4);
                out1.write( msg5);
                out1.write( msg6);
                out1.write( msg7);
                out1.write( msg8);
                out1.write( msg9);
                out1.write( msg10);

                Calendar cal3 = Calendar.getInstance();			//オブジェクトの生成
                int yea = cal3.get(Calendar.YEAR);			//現在の年を取得
                int mont = cal3.get(Calendar.MONTH)+1;		//現在の月を取得
                int da = cal3.get(Calendar.DATE);			//現在の日を取得
                int hou = cal3.get(Calendar.HOUR_OF_DAY);	//現在の時を取得
                int minut = cal3.get(Calendar.MINUTE);		//現在の分を取得
                int secon = cal3.get(Calendar.SECOND);		//現在の秒を取得

                out.write(yea + "/"+ mont + "/" + da +"," +hou + ":" +minut + ":" + secon +",");
                //CSVファイルに現在時間書き込み

                txt2.setText(""+(yea + "/"+ mont + "/" + da +"," +hou + ":" +minut + ":" + secon +" 現在測定中"));
                //テキストボックスに現在時間書き込み

                Thread.sleep(9000);
                //9000ms　待ち
                int msg11 = 0xFE;	//プリアンブル	同期用コード
                int msg12 = 0xFE;	//同期用コード
                int msg13 = 0x5A;	//受信アドレス	（受信器）
                int msg14 = 0xE0;	//送信アドレス　（PC）
                int msg15 = 0x15;	//コマンド
                int msg16 = 0x02;	//サブコマンド　（S メータレベルの読み込み）
                int msg17 = 0xFD;	//ポストアンブル	コマンド終わり

                out1.write( msg11);	//msg11~17を送信
                out1.write( msg12);
                out1.write( msg13);
                out1.write( msg14);
                out1.write( msg15);
                out1.write( msg16);
                out1.write( msg17);

                InputStream in = port.getInputStream();
                //入力ストリーム

                //以下データの読み込み
                int i = 0;

                for(i = 0; i < 32; i++){

                    int a;
                    a = in.read();
                    String data = Integer.toHexString(a);
                    h[i] = data;

                }
                //データ読み込み以上

                out.write(h[29]+h[30]);//配列の29番目と30番目をCSVファイルに書く

                out.flush();

                out.write(",");
                //207kHz	終了
                Thread.sleep(9000);

                //194kHz 開始
                int msg21 = 0xFE;
                int msg22 = 0xFE;
                int msg23 = 0x5A;
                int msg24 = 0xE0;
                int msg25 = 0x05;
                int msg26 = 0x00;
                int msg27 = 0x40;
                int msg28 = 0x19;
                int msg29 = 0x00;
                int msg210 = 0xFD;

                out1.write( msg21);
                out1.write( msg22);
                out1.write( msg23);
                out1.write( msg24);
                out1.write( msg25);
                out1.write( msg26);
                out1.write( msg27);
                out1.write( msg28);
                out1.write( msg29);
                out1.write( msg210);

                Thread.sleep(9000);

                int msg211 = 0xFE;
                int msg212 = 0xFE;
                int msg213 = 0x5A;
                int msg214 = 0xE0;
                int msg215 = 0x15;
                int msg216 = 0x02;
                int msg217 = 0xFD;

                out1.write( msg211);
                out1.write( msg212);
                out1.write( msg213);
                out1.write( msg214);
                out1.write( msg215);
                out1.write( msg216);
                out1.write( msg217);

                InputStream in2 = port.getInputStream();

                int i2 = 0;
                for(i = 0; i2 < 32; i2++){

                    int b;
                    b = in2.read();
                    String data = Integer.toHexString(b);
                    h2[i2] = data;
                }

                out.write(h2[29]+h2[30]);
                out.flush();

                out.write(",");
                //194kHz	終了

                Thread.sleep(9000);

                //295kHz 開始
                int msg321 = 0xFE;
                int msg322 = 0xFE;
                int msg323 = 0x5A;
                int msg324 = 0xE0;
                int msg325 = 0x05;
                int msg326 = 0x00;
                int msg327 = 0x50;
                int msg328 = 0x29;
                int msg329 = 0x00;
                int msg3210 = 0xFD;

                out1.write( msg321);
                out1.write( msg322);
                out1.write( msg323);
                out1.write( msg324);
                out1.write( msg325);
                out1.write( msg326);
                out1.write( msg327);
                out1.write( msg328);
                out1.write( msg329);
                out1.write( msg3210);

                Thread.sleep(9000);

                int msg3211 = 0xFE;
                int msg3212 = 0xFE;
                int msg3213 = 0x5A;
                int msg3214 = 0xE0;
                int msg3215 = 0x15;
                int msg3216 = 0x02;
                int msg3217 = 0xFD;

                out1.write( msg3211);
                out1.write( msg3212);
                out1.write( msg3213);
                out1.write( msg3214);
                out1.write( msg3215);
                out1.write( msg3216);
                out1.write( msg3217);

                InputStream in3 = port.getInputStream();

                int i3 = 0;
                for(i = 0; i3 < 32; i3++){

                    int c;
                    c = in3.read();
                    String data = Integer.toHexString(c);
                    h3[i3] = data;
                }

                out.write(h3[29]+h3[30]);
                out.flush();

                out.write(",");
                //295kHz	終了

                Thread.sleep(9000);

                //522kHz 開始
                int msg421 = 0xFE;
                int msg422 = 0xFE;
                int msg423 = 0x5A;
                int msg424 = 0xE0;
                int msg425 = 0x05;
                int msg426 = 0x00;
                int msg427 = 0x20;
                int msg428 = 0x52;
                int msg429 = 0x00;
                int msg4210 = 0xFD;

                out1.write( msg421);
                out1.write( msg422);
                out1.write( msg423);
                out1.write( msg424);
                out1.write( msg425);
                out1.write( msg426);
                out1.write( msg427);
                out1.write( msg428);
                out1.write( msg429);
                out1.write( msg4210);

                Thread.sleep(9000);

                int msg4211 = 0xFE;
                int msg4212 = 0xFE;
                int msg4213 = 0x5A;
                int msg4214 = 0xE0;
                int msg4215 = 0x15;
                int msg4216 = 0x02;
                int msg4217 = 0xFD;

                out1.write( msg4211);
                out1.write( msg4212);
                out1.write( msg4213);
                out1.write( msg4214);
                out1.write( msg4215);
                out1.write( msg4216);
                out1.write( msg4217);

                InputStream in4 = port.getInputStream();

                int i4 = 0;
                for(i = 0; i4 < 32; i4++){

                    int d;
                    d = in4.read();
                    String data = Integer.toHexString(d);
                    h4[i4] = data;
                }

                out.write(h4[29]+h4[30]);
                out.flush();

                out.write(",");
                //522kHz	終了

                Thread.sleep(9000);

                //40kHz 開始
                int msg51 = 0xFE;
                int msg52 = 0xFE;
                int msg53 = 0x5A;
                int msg54 = 0xE0;
                int msg55 = 0x05;
                int msg56 = 0x00;
                int msg57 = 0x00;
                int msg58 = 0x04;
                int msg59 = 0x00;
                int msg510 = 0xFD;

                out1.write( msg51);
                out1.write( msg52);
                out1.write( msg53);
                out1.write( msg54);
                out1.write( msg55);
                out1.write( msg56);
                out1.write( msg57);
                out1.write( msg58);
                out1.write( msg59);
                out1.write( msg510);

                Thread.sleep(9000);

                int msg511 = 0xFE;
                int msg512 = 0xFE;
                int msg513 = 0x5A;
                int msg514 = 0xE0;
                int msg515 = 0x15;
                int msg516 = 0x02;
                int msg517 = 0xFD;

                out1.write( msg511);
                out1.write( msg512);
                out1.write( msg513);
                out1.write( msg514);
                out1.write( msg515);
                out1.write( msg516);
                out1.write( msg517);

                InputStream in5 = port.getInputStream();

                int i5 = 0;
                for(i = 0; i5 < 32; i5++){

                    int e;
                    e = in5.read();
                    String data = Integer.toHexString(e);
                    h5[i5] = data;
                }

                out.write(h5[29]+h5[30]);
                out.flush();

                out.write(",");
                //40kHz	終了

                Thread.sleep(9000);

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

                out1.write( msg61);
                out1.write( msg62);
                out1.write( msg63);
                out1.write( msg64);
                out1.write( msg65);
                out1.write( msg66);
                out1.write( msg67);
                out1.write( msg68);
                out1.write( msg69);
                out1.write( msg610);

                Thread.sleep(9000);

                int msg611 = 0xFE;
                int msg612 = 0xFE;
                int msg613 = 0x5A;
                int msg614 = 0xE0;
                int msg615 = 0x15;
                int msg616 = 0x02;
                int msg617 = 0xFD;

                out1.write( msg611);
                out1.write( msg612);
                out1.write( msg613);
                out1.write( msg614);
                out1.write( msg615);
                out1.write( msg616);
                out1.write( msg617);

                InputStream in6 = port.getInputStream();

                int i6 = 0;
                for(i = 0; i6 < 32; i6++){

                    int f;
                    f = in6.read();
                    String data = Integer.toHexString(f);
                    h6[i6] = data;
                }

                out.write(h6[29]+h6[30]);
                out.flush();

                out.write(",");
                //80kHz	終了

                Thread.sleep(9000);

                //78.9kHz 開始
                int msg71 = 0xFE;
                int msg72 = 0xFE;
                int msg73 = 0x5A;
                int msg74 = 0xE0;
                int msg75 = 0x05;
                int msg76 = 0x00;
                int msg77 = 0x89;
                int msg78 = 0x07;
                int msg79 = 0x00;
                int msg710 = 0xFD;

                out1.write( msg71);
                out1.write( msg72);
                out1.write( msg73);
                out1.write( msg74);
                out1.write( msg75);
                out1.write( msg76);
                out1.write( msg77);
                out1.write( msg78);
                out1.write( msg79);
                out1.write( msg710);

                Thread.sleep(9000);

                int msg711 = 0xFE;
                int msg712 = 0xFE;
                int msg713 = 0x5A;
                int msg714 = 0xE0;
                int msg715 = 0x15;
                int msg716 = 0x02;
                int msg717 = 0xFD;

                out1.write( msg711);
                out1.write( msg712);
                out1.write( msg713);
                out1.write( msg714);
                out1.write( msg715);
                out1.write( msg716);
                out1.write( msg717);

                InputStream in7 = port.getInputStream();

                int i7 = 0;
                for(i = 0; i7 < 32; i7++){

                    int g;
                    g = in7.read();
                    String data = Integer.toHexString(g);
                    h7[i7] = data;
                }

                out.write(h7[29]+h7[30]);
                out.flush();

                out.write(",");
                //78.9kHz	終了

                Thread.sleep(9000);

                //100kHz 開始
                int msg81 = 0xFE;
                int msg82 = 0xFE;
                int msg83 = 0x5A;
                int msg84 = 0xE0;
                int msg85 = 0x05;
                int msg86 = 0x00;
                int msg87 = 0x00;
                int msg88 = 0x10;
                int msg89 = 0x00;
                int msg810 = 0xFD;

                out1.write( msg81);
                out1.write( msg82);
                out1.write( msg83);
                out1.write( msg84);
                out1.write( msg85);
                out1.write( msg86);
                out1.write( msg87);
                out1.write( msg88);
                out1.write( msg89);
                out1.write( msg810);

                Thread.sleep(9000);

                int msg811 = 0xFE;
                int msg812 = 0xFE;
                int msg813 = 0x5A;
                int msg814 = 0xE0;
                int msg815 = 0x15;
                int msg816 = 0x02;
                int msg817 = 0xFD;

                out1.write( msg811);
                out1.write( msg812);
                out1.write( msg813);
                out1.write( msg814);
                out1.write( msg815);
                out1.write( msg816);
                out1.write( msg817);

                InputStream in8 = port.getInputStream();

                int i8 = 0;
                for(i = 0; i8 < 32; i8++){

                    int h;
                    h = in8.read();
                    String data = Integer.toHexString(h);
                    h8[i8] = data;
                }

                out.write(h8[29]+h8[30]);
                out.flush();

                out.write(",");
                //100kHz	終了

                Thread.sleep(9000);

                //44kHz 開始
                int msg91 = 0xFE;
                int msg92 = 0xFE;
                int msg93 = 0x5A;
                int msg94 = 0xE0;
                int msg95 = 0x05;
                int msg96 = 0x00;
                int msg97 = 0x40;
                int msg98 = 0x04;
                int msg99 = 0x00;
                int msg910 = 0xFD;

                out1.write( msg91);
                out1.write( msg92);
                out1.write( msg93);
                out1.write( msg94);
                out1.write( msg95);
                out1.write( msg96);
                out1.write( msg97);
                out1.write( msg98);
                out1.write( msg99);
                out1.write( msg910);

                Thread.sleep(9000);

                int msg911 = 0xFE;
                int msg912 = 0xFE;
                int msg913 = 0x5A;
                int msg914 = 0xE0;
                int msg915 = 0x15;
                int msg916 = 0x02;
                int msg917 = 0xFD;

                out1.write( msg911);
                out1.write( msg912);
                out1.write( msg913);
                out1.write( msg914);
                out1.write( msg915);
                out1.write( msg916);
                out1.write( msg917);

                InputStream in9 = port.getInputStream();

                int i9 = 0;
                for(i = 0; i9 < 32; i9++){

                    int j;
                    j = in9.read();
                    String data = Integer.toHexString(j);
                    h9[i9] = data;
                }

                out.write(h9[29]+h9[30]);
                out.flush();

                out.write(",");
                //44kHz	終了

                Thread.sleep(9000);

                //240kHz 開始
                int msg101 = 0xFE;
                int msg102 = 0xFE;
                int msg103 = 0x5A;
                int msg104 = 0xE0;
                int msg105 = 0x05;
                int msg106 = 0x00;
                int msg107 = 0x00;
                int msg108 = 0x24;
                int msg109 = 0x00;
                int msg1010 = 0xFD;

                out1.write( msg101);
                out1.write( msg102);
                out1.write( msg103);
                out1.write( msg104);
                out1.write( msg105);
                out1.write( msg106);
                out1.write( msg107);
                out1.write( msg108);
                out1.write( msg109);
                out1.write( msg1010);

                Thread.sleep(9000);

                int msg1011 = 0xFE;
                int msg1012 = 0xFE;
                int msg1013 = 0x5A;
                int msg1014 = 0xE0;
                int msg1015 = 0x15;
                int msg1016 = 0x02;
                int msg1017 = 0xFD;

                out1.write( msg1011);
                out1.write( msg1012);
                out1.write( msg1013);
                out1.write( msg1014);
                out1.write( msg1015);
                out1.write( msg1016);
                out1.write( msg1017);

                InputStream in10 = port.getInputStream();

                int i10 = 0;
                for(i = 0; i10 < 32; i10++){

                    int k;
                    k = in10.read();
                    String data = Integer.toHexString(k);
                    h10[i10] = data;
                }

                out.write(h10[29]+h10[30]);
                out.flush();

                out.write(",");
                //240kHz	終了
                Thread.sleep(9000);

                //60kHz 開始
                int msg111 = 0xFE;
                int msg112 = 0xFE;
                int msg113 = 0x5A;
                int msg114 = 0xE0;
                int msg115 = 0x05;
                int msg116 = 0x00;
                int msg117 = 0x00;
                int msg118 = 0x06;
                int msg119 = 0x00;
                int msg1110 = 0xFD;

                out1.write( msg111);
                out1.write( msg112);
                out1.write( msg113);
                out1.write( msg114);
                out1.write( msg115);
                out1.write( msg116);
                out1.write( msg117);
                out1.write( msg118);
                out1.write( msg119);
                out1.write( msg1110);

                Thread.sleep(9000);

                int msg1111 = 0xFE;
                int msg1112 = 0xFE;
                int msg1113 = 0x5A;
                int msg1114 = 0xE0;
                int msg1115 = 0x15;
                int msg1116 = 0x02;
                int msg1117 = 0xFD;

                out1.write( msg1111);
                out1.write( msg1112);
                out1.write( msg1113);
                out1.write( msg1114);
                out1.write( msg1115);
                out1.write( msg1116);
                out1.write( msg1117);

                InputStream in11 = port.getInputStream();

                int i11 = 0;
                for(i = 0; i11 < 32; i11++){

                    int l;
                    l = in11.read();
                    String data = Integer.toHexString(l);
                    h11[i11] = data;
                }

                out.write(h11[29]+h11[30]);
                out.flush();

                out.write(",");
                //60kHz	終了

                Thread.sleep(9000);

                //50kHz 開始
                int msg121 = 0xFE;
                int msg122 = 0xFE;
                int msg123 = 0x5A;
                int msg124 = 0xE0;
                int msg125 = 0x05;
                int msg126 = 0x00;
                int msg127 = 0x00;
                int msg128 = 0x05;
                int msg129 = 0x00;
                int msg1210 = 0xFD;

                out1.write( msg121);
                out1.write( msg122);
                out1.write( msg123);
                out1.write( msg124);
                out1.write( msg125);
                out1.write( msg126);
                out1.write( msg127);
                out1.write( msg128);
                out1.write( msg129);
                out1.write( msg1210);

                Thread.sleep(9000);

                int msg1211 = 0xFE;
                int msg1212 = 0xFE;
                int msg1213 = 0x5A;
                int msg1214 = 0xE0;
                int msg1215 = 0x15;
                int msg1216 = 0x02;
                int msg1217 = 0xFD;

                out1.write( msg1211);
                out1.write( msg1212);
                out1.write( msg1213);
                out1.write( msg1214);
                out1.write( msg1215);
                out1.write( msg1216);
                out1.write( msg1217);

                InputStream in12 = port.getInputStream();

                int i12 = 0;
                for(i = 0; i12 < 32; i12++){

                    int m;
                    m = in12.read();
                    String data = Integer.toHexString(m);
                    h12[i12] = data;
                }

                out.write(h12[29]+h12[30]);
                out.flush();

                out.write(",");
//50kHz	終了
                Thread.sleep(9000);

                out1.flush();
                out1.close();
                port.close();

                out.newLine();//改行
                out.flush();

            }catch( Exception f){stop();}
            finally{}

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
}