package com.demo.mob.bean;

/**
 * Created by chenjt on 2017/1/16.
 */

public class ShareItem {

    private String name;
    private int icon;
    private int sequence;

    public ShareItem(String name, int icon, int sequence) {
        this.name = name;
        this.icon = icon;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public class NameConstant{
        public static final int MSG_SHARE_CANCEL = 1;
        public static final int MSG_SHARE_ERROR = 2;
        public static final int MSG_SHARE_COMPLETE = 3;
    }
}
