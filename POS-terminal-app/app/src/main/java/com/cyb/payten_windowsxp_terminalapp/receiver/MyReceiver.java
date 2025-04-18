//package com.cyb.payten_windowsxp_terminalapp.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import com.cyb.payten_windowsxp_terminalapp.MainActivity;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//public class MyReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
////        String response = intent.getStringExtra("ResponseResult");
////        try {
////            JSONObject jsonObject = new JSONObject(response);
////            JSONObject financialObject = jsonObject.getJSONObject("response").getJSONObject("financial");
////            JSONObject resultObject = financialObject.getJSONObject("result");
////            String message = resultObject.getString("message");
////            if (message.equals("Odobreno")) {
////                Log.d("MyReceiver", "Approved");
////                Intent wasingIntent = new Intent(context, MainActivity.class);
////                wasingIntent.setAction(intent.getAction());
////                wasingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                wasingIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                wasingIntent.putExtra("navigateTo", "washing");
////                Log.d("MyReceiver", "Intent: " + wasingIntent);
////                context.startActivity(wasingIntent);
////            } else {
////                Intent failedIntent = new Intent(context, MainActivity.class);
////                failedIntent.setAction(intent.getAction());
////                failedIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                failedIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                failedIntent.putExtra("navigateTo", "fail_screen");
////                Log.d("MyReceiver", "Intent: " + failedIntent);
////                context.startActivity(failedIntent);
////            }
////        } catch (JSONException e) {
////            throw new RuntimeException(e);
////        }
//    }
//}
//
//
///*
// if (message.equals("Odobreno")) {
//                Log.d("MyReceiver--", "Approved") ;
//                Intent startWashingIntent = new Intent(context, SplashScreenKt.class);
//                Log.d("MyReceiver--", startWashingIntent.toString()) ;
//                Log.d("MyReceiver--", startWashingIntent.toString()) ;
//                startWashingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startWashingIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(startWashingIntent);
//            }else {
//                Log.d("MyReceiver--", "Approved") ;
//                Intent startWashingIntent = new Intent(context, MainActivity.class);
//                Log.d("MyReceiver--", startWashingIntent.toString()) ;
//                Log.d("MyReceiver--", startWashingIntent.toString()) ;
//                startWashingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startWashingIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(startWashingIntent);
//            }
//
// */