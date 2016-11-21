package com.company;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        MyFlame frm1 = new MyFlame("Green");
        frm1.setLocation((d.width-600)/2,(d.height-400)/2);
        frm1.setSize(300,200);
        frm1.setVisible(true);

        FrameMenu frm2 = new FrameMenu("");
        frm2.setLocation((d.width-1000)/2,(d.height-400)/2);
        frm2.setSize(1000,400);
        frm2.setVisible(true);
        frm2.init();
    }
}
