package com.jack.readlog.model;

/**
 * Created by Administrator on 2017/5/18.
 */

public class Result {
    private String srcDir;//系统生成的log路径
    private String destDir;//存储路径
    private int state = State.UNREAD.getIndex();//拷贝状态

    public enum State {
        UNREAD(0, "未读取"), SUCCESS(1, "成功"), FAILURE(2, "失败");

        private int index;
        private String info;

        State(int index, String info) {
            this.index = index;
            this.info = info;
        }

        public int getIndex() {
            return index;
        }

        public String getInfo() {
            return info;
        }

        public static State getState(int index) {
            for (State state : State.values()) {
                if (state.getIndex() == index) {
                    return state;
                }
            }
            return null;

        }
    }


    public String getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(String srcDir) {
        this.srcDir = srcDir;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Result{" +
                "srcDir='" + srcDir + '\'' +
                ", destDir='" + destDir + '\'' +
                ", state=" + state +
                '}';
    }
}
