package com.simplicity;

public class Waktu {
    private static Waktu waktuTotal = null;
    private int waktu;

    // Singleton, private constructor
    private Waktu() {
        waktu = 0;
    }

    public synchronized static Waktu waktu() {
        if (waktuTotal == null) {
            waktuTotal = new Waktu();
        }

        return waktuTotal;
    }

    public synchronized void addWaktu(int tambahanWaktu) {
        waktu += tambahanWaktu;
    }

    public int getWaktu() {
        return waktu;
    }

    // public static void main(String[] args) throws InterruptedException {
    //     Waktu w = Waktu.waktu();

    //     Runnable runA = new Runnable() {
    //         public void run() {
    //             for (int i=0; i<3; i++) {
    //                 w.addWaktu(10);
    //                 System.out.println(w.getWaktu());
    //                 try {
    //                     Thread.sleep(3000);
    //                 } catch (InterruptedException e) {
    //                     return;
    //                 }
    //             }
    //         }
    //     };

    //     Thread ta = new Thread(runA, "threadA");
    //     ta.start();

    //     Runnable runB = new Runnable() {
    //         public void run() {
    //             for (int i=0; i<3; i++) {
    //                 w.addWaktu(1);
    //                 System.out.println(w.getWaktu());
    //             }
    //         }
    //     };

    //     Thread tb = new Thread(runB, "threadB");
    //     tb.start();
    // }
}
