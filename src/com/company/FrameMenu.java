package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

class FrameMenu extends Frame implements ActionListener,WindowListener{

    /*各種宣言*/
    Label lab1,lab2,lab3,lab4,lab5,lab6,lab7,lab8,lab9,lab10,lab11,lab12,lab13;
    MenuItem Open;
    FileDialog fd;
    Dialog dia;
    Button
            okButton,cancelButton,nextButton,backButton,nnextButton,bbackButton,
            openButton,startButton,clearButton,searchButton;
    Checkbox cbx1,cbx2,cbx3,cbx4,cbx5,cbx6,cbx7,cbx8,cbx9,cbx10,cbx11,cbx12;
    Choice search_cho;
    Color colors[] = {new Color(0,0,255),new Color(255,20,147),new Color(0,0,128),
            new Color(0,255,0),new Color(255,0,255),new Color(153,50,204),
            new Color(255,255,255),new Color(255,0,0),new Color(0,0,0),
            new Color(255,69,0),new Color(0,139,139),new Color(105,105,105)};

			/*日付と時間の配列*/

    String[] date = new String[8000];
    String[] time = new String[8000];

    /*内部処理の配列*/
    int[] time1 = new int[8000];
    int[] data1 = new int[8000];
    int[] data2 = new int[8000];
    int[] data3 = new int[8000];
    int[] data4 = new int[8000];
    int[] data5 = new int[8000];
    int[] data6 = new int[8000];
    int[] data7 = new int[8000];
    int[] data8 = new int[8000];
    int[] data9 = new int[8000];
    int[] data10 = new int[8000];
    int[] data11 = new int[8000];
    int[] data12 = new int[8000];

    /*表示される分配列*/
    int[] ftime1 = new int[800];
    int[] fdata1 = new int[800];
    int[] fdata2 = new int[800];
    int[] fdata3 = new int[800];
    int[] fdata4 = new int[800];
    int[] fdata5 = new int[800];
    int[] fdata6 = new int[800];
    int[] fdata7 = new int[800];
    int[] fdata8 = new int[800];
    int[] fdata9 = new int[800];
    int[] fdata10 = new int[800];
    int[] fdata11 = new int[800];
    int[] fdata12 = new int[800];

    int count1=0;
    int count2=0;
    int item_idx=0;
    int cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12;
    int Xaxis = 16;
    int Yaxis = 280;

    public FrameMenu(final String title){
        setTitle(title);
        setLayout(null);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(final WindowEvent e){
                System.exit(0);

            }
        });
    }

    public void init(){
        setLayout(null);

        MenuBar mb = new MenuBar();

        Menu m = new Menu("ファイル");
        m.add(Open = new MenuItem("開く",new MenuShortcut('Z',false)));
        Open.addActionListener(this);
        mb.add(m);

        Menu n = new Menu("実行");
        n.add(Open = new MenuItem("描写",new MenuShortcut('X',false)));
        Open.addActionListener(this);
        mb.add(n);
        n.add(Open = new MenuItem("初期化",new MenuShortcut('D',false)));
        Open.addActionListener(this);
        mb.add(n);
        n.add(Open = new MenuItem("検索",new MenuShortcut('S',false)));
        Open.addActionListener(this);
        mb.add(n);

        Menu e = new Menu("移動");
        n.add(Open = new MenuItem("→",new MenuShortcut('.',false)));
        Open.addActionListener(this);
        mb.add(e);
        n.add(Open = new MenuItem("←",new MenuShortcut(',',false)));
        Open.addActionListener(this);
        mb.add(e);

        setMenuBar(mb);
        setVisible(true);
        addWindowListener(this);

        setBackground(SystemColor.control);

				/*チェックボックス*/
        cbx1 = new Checkbox("207kHz",true);
        add(cbx1);
        cbx1.setBounds(850,80,60,20);

        cbx2 = new Checkbox("194kHz",true);
        add(cbx2);
        cbx2.setBounds(850,100,60,20);

        cbx3 = new Checkbox("295kHz",true);
        add(cbx3);
        cbx3.setBounds(850,120,60,20);

        cbx4 = new Checkbox("522kHz",true);
        add(cbx4);
        cbx4.setBounds(850,140,60,20);

        cbx5 = new Checkbox("40kHz",true);
        add(cbx5);
        cbx5.setBounds(850,160,60,20);

        cbx6 = new Checkbox("80kHz",true);
        add(cbx6);
        cbx6.setBounds(850,180,60,20);

        cbx7 = new Checkbox("78.9kHz",true);
        add(cbx7);
        cbx7.setBounds(850,200,60,20);

        cbx8 = new Checkbox("100kHz",true);
        add(cbx8);
        cbx8.setBounds(850,220,60,20);

        cbx9 = new Checkbox("44kHz",true);
        add(cbx9);
        cbx9.setBounds(850,240,60,20);

        cbx10 = new Checkbox("240kHz",true);
        add(cbx10);
        cbx10.setBounds(850,260,60,20);

        cbx11 = new Checkbox("60kHz",true);
        add(cbx11);
        cbx11.setBounds(850,280,60,20);

        cbx12 = new Checkbox("50kHz",true);
        add(cbx12);
        cbx12.setBounds(850,300,60,20);

				/*ラベル*/
        lab1 = new Label("");
        add(lab1);
        lab1.setBounds(300,55,300,20);

        lab2 = new Label("");
        add(lab2);
        lab2.setBounds(10,55,200,20);

        lab3 = new Label("",Label.CENTER);
        add(lab3);
        lab3.setBounds(5,355,50,20);

        lab4 = new Label("",Label.CENTER);
        add(lab4);
        lab4.setBounds(100,355,50,20);

        lab5 = new Label("",Label.CENTER);
        add(lab5);
        lab5.setBounds(200,355,50,20);

        lab6 = new Label("",Label.CENTER);
        add(lab6);
        lab6.setBounds(300,355,50,20);

        lab7 = new Label("",Label.CENTER);
        add(lab7);
        lab7.setBounds(400,355,50,20);

        lab8 = new Label("",Label.CENTER);
        add(lab8);
        lab8.setBounds(500,355,50,20);

        lab9 = new Label("",Label.CENTER);
        add(lab9);
        lab9.setBounds(600,355,50,20);

        lab10 = new Label("",Label.CENTER);
        add(lab10);
        lab10.setBounds(700,355,50,20);

        lab11 = new Label("",Label.CENTER);
        add(lab11);
        lab11.setBounds(800,355,50,20);

        lab12 = new Label("",Label.CENTER);
        add(lab12);
        lab12.setBounds(5,375,70,20);

        lab13 = new Label("",Label.CENTER);
        add(lab13);
        lab13.setBounds(220,55,60,20);


        openButton = new Button("開く");
        add(openButton);
        openButton.addActionListener(this);
        openButton.setBounds(920,140,50,30);

        startButton = new Button("描写");
        add(startButton);
        startButton.addActionListener(this);
        startButton.setBounds(920,190,50,30);

        clearButton = new Button("初期化");
        add(clearButton);
        clearButton.addActionListener(this);
        clearButton.setBounds(920,240,50,30);

        searchButton = new Button("検索");
        add(searchButton);
        searchButton.addActionListener(this);
        searchButton.setBounds(920,290,50,30);

        bbackButton = new Button("←←");
        add(bbackButton);
        bbackButton.addActionListener(this);
        bbackButton.setBounds(850,330,25,25);

        backButton = new Button("←");
        add(backButton);
        backButton.addActionListener(this);
        backButton.setBounds(880,330,25,25);

        nextButton = new Button("→");
        add(nextButton);
        nextButton.addActionListener(this);
        nextButton.setBounds(910,330,25,25);

        nnextButton = new Button("→→");
        add(nnextButton);
        nnextButton.addActionListener(this);
        nnextButton.setBounds(940,330,25,25);

    }

    public void paint(final Graphics g){
        g.setColor(new Color(211,211,211));
        g.fillRect(15,80,800,270);
        g.setColor(new Color(128,128,128));
        g.drawLine(115, 80, 115, 350);
        g.drawLine(215, 80, 215, 350);
        g.drawLine(315, 80, 315, 350);
        g.drawLine(415, 80, 415, 350);
        g.drawLine(515, 80, 515, 350);
        g.drawLine(615, 80, 615, 350);
        g.drawLine(715, 80, 715, 350);

				/*折れ線表示　縦軸 data　横軸 time　データ数 count2-1*/
        if(cb1 == 1){
            g.setColor(colors[0]);
            g.fillRect(830, 85, 10, 10);
            g.drawPolyline(ftime1, fdata1, count2-1);

        }

        if(cb2 == 1){
            g.setColor(colors[1]);
            g.fillRect(830, 105, 10, 10);
            g.drawPolyline(ftime1, fdata2, count2-1);

        }

        if(cb3 == 1){
            g.setColor(colors[2]);
            g.fillRect(830, 125, 10, 10);
            g.drawPolyline(ftime1, fdata3, count2-1);

        }

        if(cb4 == 1){
            g.setColor(colors[3]);
            g.fillRect(830, 145, 10, 10);
            g.drawPolyline(ftime1, fdata4, count2-1);

        }

        if(cb5 == 1){
            g.setColor(colors[4]);
            g.fillRect(830, 165, 10, 10);
            g.drawPolyline(ftime1, fdata5, count2-1);

        }

        if(cb6 == 1){
            g.setColor(colors[5]);
            g.fillRect(830, 185, 10, 10);
            g.drawPolyline(ftime1, fdata6, count2-1);

        }

        if(cb7 == 1){
            g.setColor(colors[6]);
            g.fillRect(830, 205, 10, 10);
            g.drawPolyline(ftime1, fdata7, count2-1);

        }

        if(cb8 == 1){
            g.setColor(colors[7]);
            g.fillRect(830, 225, 10, 10);
            g.drawPolyline(ftime1, fdata8, count2-1);

        }

        if(cb9 == 1){
            g.setColor(colors[8]);
            g.fillRect(830, 245, 10, 10);
            g.drawPolyline(ftime1, fdata9, count2-1);

        }

        if(cb10 == 1){
            g.setColor(colors[9]);
            g.fillRect(830, 265, 10, 10);
            g.drawPolyline(ftime1, fdata10, count2-1);

        }

        if(cb11 == 1){
            g.setColor(colors[10]);
            g.fillRect(830, 285, 10, 10);
            g.drawPolyline(ftime1, fdata11, count2-1);

        }

        if(cb12 == 1){
            g.setColor(colors[11]);
            g.fillRect(830, 305, 10, 10);
            g.drawPolyline(ftime1, fdata12, count2-1);
        }

					/*枠*/
        g.setColor(new Color(128,128,128));
        g.drawRect(15, 80, 800, 270);
    }

    public void actionPerformed(final ActionEvent e){
        if(e.getActionCommand() == ("開く")){
            fd = new FileDialog(new Frame(),"ファイルダイアログ");
            fd.setVisible(true);
            String Directory = fd.getDirectory()+fd.getFile();

            String line;
            String[] arrayline;

            clear();

            for(int j = 0; j<800; j++)
                ftime1[j] = Xaxis+j;

            try{
                BufferedReader L01 = new BufferedReader(new FileReader(Directory));
                while((line = L01.readLine())!=null){
                    arrayline = line.split(",");

                    if(count1>=1){
                        date[count1-1] = arrayline[0];
                        time[count1-1] = arrayline[1];
                        data1[count1] = Integer.parseInt(arrayline[2]);
                        data1[count1] = Yaxis-data1[count1];
                        data2[count1] = Integer.parseInt(arrayline[3]);
                        data2[count1] = Yaxis-data2[count1];
                        data3[count1] = Integer.parseInt(arrayline[4]);
                        data3[count1] = Yaxis-data3[count1];
                        data4[count1] = Integer.parseInt(arrayline[5]);
                        data4[count1] = Yaxis-data4[count1];
                        data5[count1] = Integer.parseInt(arrayline[6]);
                        data5[count1] = Yaxis-data5[count1];
                        data6[count1] = Integer.parseInt(arrayline[7]);
                        data6[count1] = Yaxis-data6[count1];
                        data7[count1] = Integer.parseInt(arrayline[8]);
                        data7[count1] = Yaxis-data7[count1];
                        data8[count1] = Integer.parseInt(arrayline[9]);
                        data8[count1] = Yaxis-data8[count1];
                        data9[count1] = Integer.parseInt(arrayline[10]);
                        data9[count1] = Yaxis-data9[count1];
                        data10[count1] = Integer.parseInt(arrayline[11]);
                        data10[count1] = Yaxis-data10[count1];
                        data11[count1] = Integer.parseInt(arrayline[12]);
                        data11[count1] = Yaxis-data11[count1];
                        data12[count1] = Integer.parseInt(arrayline[13]);
                        data12[count1] = Yaxis-data12[count1];
                    }

                    ++count1;

                }

                L01.close();

            }
            catch(FileNotFoundException L04){
                lab2.setText("ファイルを指定してください。");
            }
            catch(IOException L05){
                lab2.setText(Directory+"を読み込めませんでした。");
            }

            start();

        }

        if(e.getActionCommand() == ("描写")){
            start();
        }

        if(e.getActionCommand() == ("初期化")){
            clear();
            repaint();
            settime();
        }

        if(e.getActionCommand() == ("検索")){
            Search();
        }

        else if(e.getActionCommand() == "OK"){
            item_idx = search_cho.getSelectedIndex();
            start();
            dia.dispose();
        }

        else if(e.getActionCommand() == "CANCEL"){
            dia.dispose();
        }

        else if(e.getActionCommand() == "←"){
            item_idx -= 100;
            if(item_idx<0)item_idx = 0;
            start();
        }

        else if(e.getActionCommand() == "←←"){
            item_idx -= 400;
            if(item_idx<0)item_idx = 0;
            start();
        }

        else if(e.getActionCommand() == "→"){
            item_idx += 100;
            if(item_idx>count1)item_idx = count1;
            start();
        }

        else if(e.getActionCommand() == "→→"){
            item_idx += 400;
            if(item_idx>count1)item_idx = count1;
            start();
        }
    }


    public void settime(){
        int c = count1-1;
        int i = item_idx;
        lab1.setText("");
        lab2.setText("");
        lab13.setText("");

        if(count1>2){
            lab1.setText(date[0]+time[0]+"～"+date[count1-2]+time[count1-2]);
            lab2.setText("File Path:"+fd.getFile());
            lab13.setText("data:"+c);
        }

        if(i == -1)i=0;
        lab3.setText(time[i]);
        lab4.setText(time[i+100]);
        lab5.setText(time[i+200]);
        lab6.setText(time[i+300]);
        lab7.setText(time[i+400]);
        lab8.setText(time[i+500]);
        lab9.setText(time[i+600]);
        lab10.setText(time[i+700]);
        lab11.setText(time[i+800]);
        lab12.setText(date[i]);
    }

    public void clear(){
        count1 = 0;
        count2 = 0;
        item_idx = 0;

        for(int i = 0; i < 8000; i++){
            time[i] = "";
            date[i] = "";
            data1[i] = Yaxis;
            data2[i] = Yaxis;
            data3[i] = Yaxis;
            data4[i] = Yaxis;
            data5[i] = Yaxis;
            data6[i] = Yaxis;
            data7[i] = Yaxis;
            data8[i] = Yaxis;
            data9[i] = Yaxis;
            data10[i] = Yaxis;
            data11[i] = Yaxis;
            data12[i] = Yaxis;
        }

        for(int i = 0; i < 800; i++){
            fdata1[i] = Yaxis;
            fdata2[i] = Yaxis;
            fdata3[i] = Yaxis;
            fdata4[i] = Yaxis;
            fdata5[i] = Yaxis;
            fdata6[i] = Yaxis;
            fdata7[i] = Yaxis;
            fdata8[i] = Yaxis;
            fdata9[i] = Yaxis;
            fdata10[i] = Yaxis;
            fdata11[i] = Yaxis;
            fdata12[i] = Yaxis;
        }
    }

    public void cbx(){
        cb1 = cb2 = cb3 = cb4 = cb5 = cb6 = cb7 = cb8 = cb9 = cb10 = cb11 = cb12 = 0;
        if(cbx1.getState() == true)cb1 = 1;
        if(cbx2.getState() == true)cb2 = 1;
        if(cbx3.getState() == true)cb3 = 1;
        if(cbx4.getState() == true)cb4 = 1;
        if(cbx5.getState() == true)cb5 = 1;
        if(cbx6.getState() == true)cb6 = 1;
        if(cbx7.getState() == true)cb7 = 1;
        if(cbx8.getState() == true)cb8 = 1;
        if(cbx9.getState() == true)cb9 = 1;
        if(cbx10.getState() == true)cb10 = 1;
        if(cbx11.getState() == true)cb11 = 1;
        if(cbx12.getState() == true)cb12 = 1;
    }

    public void copyarray(){
        int l = item_idx;
        count2 = 0;
        if(l>0)l -= 1;

        for(int p = 0; p < 800; ++p){
            fdata1[p] = data1[l];
            fdata2[p] = data2[l];
            fdata3[p] = data3[l];
            fdata4[p] = data4[l];
            fdata5[p] = data5[l];
            fdata6[p] = data6[l];
            fdata7[p] = data7[l];
            fdata8[p] = data8[l];
            fdata9[p] = data9[l];
            fdata10[p] = data10[l];
            fdata11[p] = data11[l];
            fdata12[p] = data12[l];
            ++l;
            ++count2;
            if(l>count1) p = 800;
        }
    }

    public void start(){
        cbx();
        copyarray();
        settime();
        repaint();
    }

    public void Search(){
        dia = new Dialog(this,"検索",true);
        dia.setBackground(SystemColor.control);
        dia.setLayout(null);
        dia.addWindowListener(new WindowAdapter(){
            public void windowClosing(final WindowEvent e){
                System.exit(0);
            }
        });

        search_cho = new Choice();
        for(int i = 0; i < count1; i++) search_cho.add(date[1]+""+time[i]);
        dia.add(search_cho);
        if(item_idx>0) search_cho.select(item_idx);
        search_cho.setBounds(40,50,120,20);

        okButton = new Button("OK");
        dia.add(okButton);
        okButton.addActionListener(this);
        okButton.setBounds(40,90,50,30);

        cancelButton = new Button("CANCEL");
        dia.add(cancelButton);
        cancelButton.addActionListener(this);
        cancelButton.setBounds(105,90,55,30);

        dia.setSize(200,150);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        dia.setLocation((d.width-200)/2,(d.height-150)/2);
        dia.setVisible(true);
        dia.setResizable(false);
    }

    public void windowClosing(final WindowEvent e){
        System.exit(0);
    }

    public void windowIconified(WindowEvent evt){}
    public void windowDeiconified(WindowEvent evt){}
    public void windowActivated(WindowEvent evt){}
    public void windowDeactivated(WindowEvent evt){}
    public void windowOpened(WindowEvent evt){}
    public void windowClosed(WindowEvent evt){
        dispose();

    }
}