package com.crm.util;

import java.io.File;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtils {

    /**
     * 发送邮件
     * 
     * @param fromAddress
     *            发邮箱地址
     * @param toAddress
     *            收邮箱地址
     * @param title
     *            邮件标题
     * @param content
     *            邮件内容
     * @throws Exception
     */
    public static void send(String smtpServer, String mailAccount, String mailPassword, String toAddress, String title,
            String content) throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpServer);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.debug", "true");

        Session session = Session.getDefaultInstance(props);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailAccount));
        message.setRecipients(RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(title);
        Multipart msgPart = new MimeMultipart();
        message.setContent(msgPart);
        MimeBodyPart body = new MimeBodyPart();
        msgPart.addBodyPart(body);
        body.setContent(content, "text/html;charset=utf-8");
        message.saveChanges();
        Transport trans = session.getTransport();
        trans.connect(smtpServer, mailAccount, mailPassword);
        trans.sendMessage(message, message.getAllRecipients());
    }

    /**
     * 发送邮件包含附件
     * 
     * @param fromAddress
     *            发邮箱地址
     * @param toAddress
     *            收邮箱地址
     * @param title
     *            邮件标题
     * @param content
     *            邮件内容
     * @param sourcePath
     *            附件路径
     * @param filename
     *            文件名
     * @throws Exception
     */
    public static void sendContainFile(String smtpServer, String mailAccount, String mailPassword, String fromAddress,
            String toAddress, String title, String content, File file, String filename) throws Exception {
        // 第一步：创建Session，包含邮件服务器网络连接信息
        Properties props = new Properties();
        // 指定邮件的传输协议，smtp;同时通过验证
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpServer);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        Session session = Session.getDefaultInstance(props);
        // 第二步：编辑邮件内容
        Message message = new MimeMessage(session);
        // 设置邮件消息头
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(title);
        // 设置邮件消息内容、包含附件
        Multipart msgPart = new MimeMultipart();
        message.setContent(msgPart);
        MimeBodyPart body = new MimeBodyPart(); // 正文
        MimeBodyPart attach = new MimeBodyPart(); // 附件
        msgPart.addBodyPart(body);
        msgPart.addBodyPart(attach);
        // 设置正文内容
        body.setContent(content, "text/html;charset=utf-8");
        // 设置附件内容
        attach.setDataHandler(new DataHandler(new FileDataSource(file)));
        attach.setFileName(MimeUtility.encodeText(filename));
        message.saveChanges();
        // 第三步：发送邮件
        Transport trans = session.getTransport();
        trans.connect(smtpServer, mailAccount, mailPassword);
        trans.sendMessage(message, message.getAllRecipients());
    }

    public static void main(String[] args) {
        try {
            send("smtp.mxhichina.com", "postmaster@hsfx365.com", "tuka20100813", "593081574@qq.com", "注册成功通知",
                    "注册成功通知");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
