package com.dashboard;

public class SingletonDesign {

    private static SingletonDesign instance;

    private SingletonDesign(){
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SingletonDesign getInstance() {

            if(instance==null){
                synchronized (SingletonDesign.class){
                    if(instance==null) {
                        SingletonDesign singletonDesign = new SingletonDesign();
                        singletonDesign.setName("qqq");
                        return instance=singletonDesign;
                    }
            }
        }
        return instance;
    }
}

class test {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000; // 定义线程数量

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + " - " + SingletonDesign.getInstance().getName());
            });
            threads[i].start();
        }

        // 等待所有线程结束
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
    }
}

