package com.demo.mob.fragment.gameutils;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by chenjt on 2017/4/5.
 */

public class Calculate {
    private Integer[] red;
    private Integer[] blue;
    private Integer[] result;
    private Random r;
    private int ball_num;
    private int random_num = 0;

    public Calculate(){
        restart();
    }

    public void startGeme(int num){
        if (num < 7){
            start_Red_Game(num);
        }else{
            start_Blue_Game();
        }
    }


    private void start_Red_Game(int num){
        setNum(red[r.nextInt(33-random_num)]);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0;i < red.length;i++){
            if (ball_num == red[i]){
                result[num-1] = ball_num;
                continue;
            }
            list.add(red[i]);
        }
        random_num = num;
        red = list.toArray(new Integer[33-num]);
    }

    private void start_Blue_Game(){
        setNum(blue[r.nextInt(16)]);
        result[6] = ball_num;
    }

    public void restart(){
        red = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33};
        blue = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        result = new Integer[7];
        r = new Random();
        random_num= 0;
        ball_num = 0;
    }

    public String getResult(){
        Integer[] front = {result[0],result[1],result[2],result[3],result[4],result[5]};
        Arrays.sort(front);
        return "红球 :" + Arrays.toString(front).replace("[","").replace("]","") + "  -----  蓝球 :" + result[6];
    }

    public String getNum() {
        return String.valueOf(ball_num);
    }

    private void setNum(int ball_num) {
        this.ball_num = ball_num;
    }
}
