package main.java;

public class Main {
    public static void main(String[] args) {
        MaseEscape maseEscape = new MaseEscape();
        System.out.println(maseEscape.isEscapePossible(new int[][]{{0,3},{1,3},{2,3},{3,3},{3,2},{3,1},{3,0}},new int[]{0,0},new int[]{100,100}));
    }
    //{691938,300406},{710196,624190},{858790,609485},{268029,225806},{200010,188664}
    //        ,{132599,612099},{329444,633495},{196657,757958},{628509,883388}
}
