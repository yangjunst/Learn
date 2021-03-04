package com.learn.concurrency.demo;

/********************************************
 * 文件名称: ThreadIsInterrupted.java
 * 功能说明: 
 * 开发人员: 雪域青竹
 * 开发时间: 2021/3/4 14:49
 *********************************************/
public class ThreadIsInterrupted {
    public static void main(String[] args) {
        Thread t=new Thread(()-> {
            while (true){
                while (Thread.currentThread().interrupted()){
                    System.out.println("中断了..."+Thread.currentThread().isInterrupted());
                }
            }
        });
        t.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t.interrupt();
            }
        }.start();
    }
}
