package main.java;

import com.sun.istack.internal.NotNull;

import javax.jws.WebParam;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MaseEscape {
    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        ChessBoard chessBoard = new ChessBoard(1000000,1000000);
        chessBoard.resetMaxTrappedSpace(blocked);
        Model sourceModel = new Model(source,chessBoard);
        boolean flag = sourceModel.searchTarget(target);
        chessBoard.drawBoard(source,target);
        Model targetModel = new Model(target,chessBoard);
        return flag&&targetModel.searchTarget(source);
    }
    private class Model{
        private int[] source;
        private ChessBoard chessBoard;
        boolean[][] markIsSearch;
        private int Xrelative;
        private int Yrelative;
        public Model(int[] source,ChessBoard chessBoard) {
            this.source = source==null?new int[]{0,0}:source;
            this.chessBoard = chessBoard;
            markIsSearch = new boolean[401][401];
            this.markIsSearch = this.chessBoard.resetTouchAble(markIsSearch,source,this);
        }
        public boolean searchTarget(int[] target){
            Queue<int[]> cacheQueue = new LinkedList<>();
            cacheQueue.add(this.source);
            markIsSearch[this.source[0]-Xrelative][this.source[1]-Yrelative]=true;
            int routeNums = 0;
            while (!cacheQueue.isEmpty()){
                int route[] = cacheQueue.remove();
                ++routeNums;
                int var1 = route[0];
                int var2 = route[1];
                markIsSearch[var1-Xrelative][var2-Yrelative]=true;
                if (isTouch(route,target)){
                    System.out.println(routeNums);
                    return true;
                }
                if (routeNums>chessBoard.getMaxTrappedSpace()){
                    System.out.println(routeNums);
                    return true;
                }
                if (var1-1>=0&&!markIsSearch[var1-1-Xrelative][var2-Yrelative]){
                    cacheQueue.add(new int[]{var1-1,var2});
                    markIsSearch[var1-1-Xrelative][var2-Yrelative]=true;
                }
                if (var2+1<=chessBoard.colLength&&!markIsSearch[var1-Xrelative][var2+1-Yrelative]){
                    cacheQueue.add(new int[]{var1,var2+1});
                    markIsSearch[var1-Xrelative][var2+1-Yrelative]=true;
                }
                if (var1+1<=chessBoard.rowLength&&!markIsSearch[var1+1-Xrelative][var2-Yrelative]){
                    cacheQueue.add(new int[]{var1+1,var2});
                    markIsSearch[var1+1-Xrelative][var2-Yrelative]=true;
                }
                if (var2-1>=0&&!markIsSearch[var1-Xrelative][var2-1-Yrelative]){
                    cacheQueue.add(new int[]{var1,var2-1});
                    markIsSearch[var1-Xrelative][var2-1-Yrelative]=true;
                }
            }
            System.out.println(routeNums);
            return false;
        }
        private boolean isTouch(int[] route, int[] target){
            return route[0]==target[0]&&route[1]==target[1];
        }
        public int[] getSource() {
            return source;
        }

        public void setSource(int[] source) {
            this.source = source;
        }

        public ChessBoard getChessBoard() {
            return chessBoard;
        }

        public void setChessBoard(ChessBoard chessBoard) {
            this.chessBoard = chessBoard;
        }
    }
    private class ChessBoard{
        private int rowLength;
        private int colLength;
        private int maxTrappedSpace;
        private int[][] blocked;
        boolean[][] mark;
        private int maxXlength = 0;
        private int maxYlength = 0;
        private int Xrelative;
        private int Yrelative;
        public boolean[][] resetTouchAble(boolean[][] mark, int[] source, Model model){
            int xPosition = mark.length/2;
            int yPosition = mark[0].length/2;
            this.Xrelative = source[0]-xPosition;
            this.Yrelative = source[1]-yPosition;
            model.Xrelative = Xrelative;
            model.Yrelative = Yrelative;
            if(this.mark==null){
                this.mark = mark;
            }
            for (int var3=0;var3<this.blocked.length;++var3){
                int[] point = blocked[var3];
                int pxRelative = point[0] - Xrelative;
                int pyRelative = point[1]-Yrelative;
                if(pxRelative>=0&&mark.length>pxRelative&&pyRelative>=0&&pyRelative<mark[0].length){
                    mark[pxRelative][pyRelative] = true;
                }
                maxXlength = point[0]>maxXlength?point[0]:maxXlength;
                maxYlength = point[1]>maxYlength?point[1]:maxYlength;

            }
            return mark;
        }
        public void drawBoard(int[] source,int[] target){
            int num=0;
            for (int var4=245;155<var4;--var4){
                StringBuilder stringBuilder = new StringBuilder();
                for (int var5=155;var5<245;++var5){
                    if (var4==source[1]-Yrelative&&var5==source[0]-Xrelative){
                        stringBuilder.append("A ");
                    }else if (mark[var5][var4]){
                        stringBuilder.append("pp");
                        ++num;
                    } else if (var4==target[1]-Yrelative&&var5==target[0]-Xrelative){
                        stringBuilder.append("B ");
                    }else {
                        stringBuilder.append("# ");
                    }

                }
                System.out.println(stringBuilder.toString());
            }
            System.out.println("num"+num);
        }
        public int getRowLength() {
            return rowLength;
        }

        public void setRowLength(int rowLength) {
            this.rowLength = rowLength;
        }

        public int getColLength() {
            return colLength;
        }

        public void setColLength(int colLength) {
            this.colLength = colLength;
        }

        public int getMaxTrappedSpace() {
            return maxTrappedSpace;
        }

        public void setMaxTrappedSpace(int maxTrappedSpace) {
            this.maxTrappedSpace = maxTrappedSpace;
        }

        public ChessBoard(int rowLength, int colLength){
            this.rowLength = rowLength;
            this.colLength = colLength;
            this.maxTrappedSpace = 0;
        }
        public ChessBoard(int rowLength, int colLength,int[][] blocked){
            this.rowLength = rowLength;
            this.colLength = colLength;
            this.resetMaxTrappedSpace(blocked);
        }
        public int resetMaxTrappedSpace(int[][] blocked){
            this.blocked = blocked;
            int blocks = blocked.length;
            if (blocks>=rowLength){
                this.maxTrappedSpace = (int)Math.floor((rowLength*colLength-blocks)/2);
            }else if (blocks<=1){
                this.maxTrappedSpace = 0;
            }else {
                this.maxTrappedSpace = (blocks-1)*blocks/2;
            }
            return this.maxTrappedSpace;
        }

    }
}
