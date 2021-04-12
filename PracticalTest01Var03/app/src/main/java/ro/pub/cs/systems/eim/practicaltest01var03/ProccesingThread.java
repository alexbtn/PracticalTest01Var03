//package ro.pub.cs.systems.eim.practicaltest01var03;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import java.util.Date;
//
//public class ProccesingThread extends Thread {
//    private Context context = null;
//    private boolean isRunning = true;
//
//
//    private double sum;
//    private double diff;
//
//    public ProccesingThread(Context context, int firstNumber, int secondNumber) {
//        this.context = context;
//
//        sum = firstNumber + secondNumber;
//        diff = firstNumber - secondNumber;
//    }
//
//    @Override
//    public void run() {
//        Log.d("[Thread]", "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
//        sendMessage();
//        sleep();
//        sendMessage();
//        Log.d("[Thread]", "Thread has stopped!");
//    }
//
//    private void sendMessage() {
//        Intent intent = new Intent();
//        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
//        intent.putExtra("broadCastExtra",
//                new Date(System.currentTimeMillis()) + " " + sum + " " + diff);
//        context.sendBroadcast(intent);
//    }
//
//    private void sleep() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//        }
//    }
//
//    public void stopThread() {
//        isRunning = false;
//    }
//}
