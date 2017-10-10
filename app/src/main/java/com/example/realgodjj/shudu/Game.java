package com.example.realgodjj.shudu;

public class Game {
    //添加初始化字符串
    private final String str = "360000000004230800000004200"
            + "070460003820000014500013020" + "001900000007048300000000045";
    private int shudu[] = new int[9 * 9];
    private int used[][][] = new int[9][9][];//存储每个单元格内不可用的数据

    public Game() {
        shudu = fromPuzzleString(str);
        calculateAllUsedTiles();
    }

    //根据一个字符串，生成一个整形数组，所谓数独游戏的初始化数据
    private int[] fromPuzzleString(String str) {
        int shudu[] = new int[str.length()];
        for (int i = 0; i < shudu.length; i++) {
            shudu[i] = str.charAt(i) - '0';
        }
        return shudu;
    }

    //根据九宫格当中的坐标，返回该坐标所应该填写的数字
    public int getTile(int x, int y) {
        return shudu[y * 9 + x];
    }

    //根据九宫格当中的坐标，设置该坐标所应该填写的数字
    public void setTile(int x, int y, int value) {
        shudu[y * 9 + x] = value;
    }

    //根据九宫格当中的坐标，返回改坐标对应的数字（字符串形式）
    public String getTileString(int x, int y) {
        int number = getTile(x, y);
        String str;
        if (number == 0) {
            str = "";
        } else {
            str = String.valueOf(number);
        }
        return str;
    }

    //计算某一单元格中已经不可用的数据
    public int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];

        //判断某一纵列不可用的数据
        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int t = getTile(x, i);
            if (t != 0) {
                c[t - 1] = t;
            }
        }

        //判断某一横行不可用的数据
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int t = getTile(i, y);
            if (t != 0) {
                c[t - 1] = t;
            }
        }

        //判断小九宫格中不可用的数据
//        int startX = (x / 3) + x % 3;
        int startX = (x / 3) * 3;
//        int startY = (y / 3) + y % 3;
        int startY = (y / 3) * 3;
        for (int i = startX; i < startX + 3; i++) {
            for (int j = startY; j < startY + 3; j++) {
                if (x == i && y == j)
                    continue;
                int t = getTile(i, j);
                if (t != 0) {
                    c[t - 1] = t;
                }
            }
        }

        //压缩数组（去掉数组中的0）
        int noUsed = 0;
        for (int t : c) {
            if (t != 0)
                noUsed++;
        }
        int c1[] = new int[noUsed];
        noUsed = 0;
        for (int t : c) {
            if (t != 0)
                c1[noUsed++] = t;
        }
        return c1;
    }

    //用于计算所有单元格对应的不可用数据
    public void calculateAllUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
            }
        }
    }

    //取出某一单元格当中不可用的数据
    public int[] getUsedTilesByCoor(int x, int y) {
        return used[x][y];
    }

    public boolean setTileIfValid(int x, int y, int value) {
        int tiles[] = getUsedTiles(x, y);
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value) {
                    return false;
                }
            }
        }
        setTile(x, y, value);
        calculateAllUsedTiles();
        return true;
    }

    protected int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }
}
