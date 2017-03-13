package com.demo.mob.bean;

/**
 * Created by chenjt on 2017/1/9.
 */

public class MenuItem {
    private String name;
    private int sequence;

    public MenuItem(String name, int sequence) {
        this.name = name;
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class NameConstant{
        public static final int SHARE = 0x00000001;
        public static final int AUTHORIZE = 0x00000002;
        public static final int SMS = 0x00000003;
        public static final int REVIEW = 0x00000004;

    }
}
