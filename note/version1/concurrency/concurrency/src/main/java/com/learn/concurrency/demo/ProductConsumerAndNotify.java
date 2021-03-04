package com.learn.concurrency.demo;

import java.util.LinkedList;
import java.util.Queue;

/********************************************
 * 文件名称: ProductConsumerAndNotify.java
 * 功能说明: 
 * 开发人员: 雪域青竹
 * 开发时间: 2021/3/1 4:26
 *********************************************/
class Product implements Runnable{
    Queue<Integer> queue;
    int maxValue;
    int count;

    public Product(Queue queue, int maxValue) {
        this.queue = queue;
        this.maxValue = maxValue;
    }
    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.size()>=maxValue){
                    try {
                        System.out.println("Product....满了");
                        queue.wait();
                        System.out.println("Product生产了...醒了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Product生产了..."+count);
                this.queue.add(count++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.notify();
            }
        }
    }
}
class Consumer implements Runnable{
     Queue<Integer> queue;
    public Consumer(Queue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (this.queue.isEmpty()){
                    try {
                        System.out.println("Consumer....空了");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer....消费了"+this.queue.remove());
                queue.notify();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer....唤醒操作");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer...锁结束语句");
        }
    }
}
public class ProductConsumerAndNotify {
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer>  queue=new LinkedList<>();
        new Thread(new Product(queue,25)).start();
        new Thread(new Consumer(queue)).start();

    }
}
