package com.yu.tomato.model;

/**
 * Created by YU on 2015/9/4.
 */
public class TomatoTaskModel {
    // ����ʱ������
    private int tomatoCount;
     // ������������
    private String tomatoTheme;
    // ������������
    private String tomatoDescription;
    // ��������ʼʱ��
    private Long tomatoStartTime;
    // �����������ʱ��
    private Long tomatoEndTime;
    // �����������ȼ�
    private int priority;
    // ���������״̬
    private int state;

    public static enum TomatoState{
        Ready,
        Processing,
        End,
    }


    public TomatoTaskModel(int tomatoCount, String tomatoTheme, String tomatoDescription, Long tomatoStartTime, Long tomatoEndTime, int priority, int state) {
        this.tomatoCount = tomatoCount;
        this.tomatoTheme = tomatoTheme;
        this.tomatoDescription = tomatoDescription;
        this.tomatoStartTime = tomatoStartTime;
        this.tomatoEndTime = tomatoEndTime;
        this.priority = priority;
        this.state = state;
    }

    public int getTomatoCount() {
        return tomatoCount;
    }

    public String getTomatoTheme() {
        return tomatoTheme;
    }

    public String getTomatoDescription() {
        return tomatoDescription;
    }

    public Long getTomatoStartTime() {
        return tomatoStartTime;
    }

    public Long getTomatoEndTime() {
        return tomatoEndTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getState() {
        return state;
    }

    public void setTomatoCount(int tomatoCount) {
        this.tomatoCount = tomatoCount;
    }

    public void setTomatoTheme(String tomatoTheme) {
        this.tomatoTheme = tomatoTheme;
    }

    public void setTomatoDescription(String tomatoDescription) {
        this.tomatoDescription = tomatoDescription;
    }

    public void setTomatoStartTime(Long tomatoStartTime) {
        this.tomatoStartTime = tomatoStartTime;
    }

    public void setTomatoEndTime(Long tomatoEndTime) {
        this.tomatoEndTime = tomatoEndTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setState(int state) {
        this.state = state;
    }
}
