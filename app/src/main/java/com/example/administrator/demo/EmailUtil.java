package com.example.administrator.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 未捕获的异常发送邮件
 * 搭配CrashHandler使用
 * Created by WangPing on 2017/10/24.
 */

public class EmailUtil {
    private Context ctx;

    public EmailUtil(Context ctx) {
        this.ctx = ctx;
    }

    public void sendMail(final String exceptionMsg) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    PackageManager pm = ctx.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
                    long current = System.currentTimeMillis();
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
                    StringBuilder content = new StringBuilder();
                    content.append(exceptionMsg).append("\r\n");
                    content.append("发生异常时间:").append(time).append("\r\n");
                    content.append("应用版本:").append(pi.versionName).append("\r\n");
                    content.append("应用版本号:").append(pi.versionCode).append("\r\n");
                    content.append("android版本号:").append(Build.VERSION.RELEASE).append("\r\n");
                    content.append("android版本号API:").append(Build.VERSION.SDK_INT).append("\r\n");
                    content.append("手机制造商:").append(Build.MANUFACTURER).append("\r\n");
                    content.append("手机型号:").append(Build.MODEL).append("\r\n");
                    String account = "xxwangping@zhphfinance.com"; //发送邮箱的账号
                    String password = "xxxxxxxxxxxxxxxx"; //发送邮箱的密码
                    //发送邮件
                    sendMail(new String[]{"wowangping.com@qq.com", "15680351591@163.com"}, account, "smtp.mxhichina.com",
                            account, password, "崩溃日志", content.toString(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendMail(String[] toMails, String fromMail, String server,
                          String username, String password, String title, String body,
                          String attachment) throws Exception {

        Properties props = System.getProperties();// Get system properties
        //添加邮箱地址
        props.put("mail.smtp.host", server);// Setup mail server
        props.put("mail.smtp.auth", "true");
        //添加邮箱权限
        MyAuthenticator myauth = new MyAuthenticator(username, password);// Get
        Session session = Session.getDefaultInstance(props, myauth);
        MimeMessage message = new MimeMessage(session); // Define message
        //设置目的邮箱
        message.setFrom(new InternetAddress(fromMail)); // Set the from address
        InternetAddress[] addresses = new InternetAddress[toMails.length];
        for (int i = 0; i < toMails.length; i++) {
            addresses[i] = new InternetAddress(toMails[i]);
            Log.e("WP", toMails[i]);
        }
        message.addRecipients(Message.RecipientType.TO, addresses);// Set
        //设置邮件的标题
        message.setSubject(title);// Set the subject
        MimeMultipart allMultipart = new MimeMultipart("mixed");
        /*
        //添加附件 就放开这段注释
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(attachment);
        attachPart.setDataHandler(new DataHandler(fds));//附件
        attachPart.setFileName(MimeUtility.encodeWord(fds.getName()));*/
        MimeBodyPart textBodyPart = new MimeBodyPart();
        //添加邮件内容
        textBodyPart.setText(body);
        //allMultipart.addBodyPart(attachPart);
        allMultipart.addBodyPart(textBodyPart);
        message.setContent(allMultipart);
        message.saveChanges();
        Transport.send(message);//发送邮件
    }

    private class MyAuthenticator extends javax.mail.Authenticator {
        private String strUser;
        private String strPwd;

        public MyAuthenticator(String user, String password) {
            this.strUser = user;
            this.strPwd = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(strUser, strPwd);
        }
    }

}
