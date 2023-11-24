package com.mtraidi.demo.services;

import com.mtraidi.demo.dao.EnseignantRepository;
import com.mtraidi.demo.dao.IAuthority;
import com.mtraidi.demo.dao.IUser;
import com.mtraidi.demo.dao.ParentRepository;
import com.mtraidi.demo.models.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class UserServices {

    @Autowired
    private IUser repo;
    @Autowired
    private IAuthority iAuthority;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ParentRepository parentRepository ;


    public void registerEns(Enseignant user, String siteURL) throws MessagingException, UnsupportedEncodingException {

        Authority authority = new Authority();
        authority.setName("USER");
        iAuthority.save(authority);
        user.setAuthorities(authority);
        user.setRole(Role.ROLE_ENSEIGNANT);
        user.setPassword(hash(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        repo.save(user);
        sendVerificationEmailEns(user, siteURL);
    }

    public void registerParent(Parent user, String siteURL) throws MessagingException, UnsupportedEncodingException {

        Authority authority = new Authority();
        authority.setName("USER");
        iAuthority.save(authority);
        user.setAuthorities(authority);
        user.setRole(Role.ROLE_USER);
        user.setPassword(hash(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        repo.save(user);
        sendVerificationEmailParent(user, siteURL);
    }

    private void sendVerificationEmailParent(Parent user, String siteURL) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "ihsenbelkhiri@gmail.com";
        String senderName = "Smile Kids Academy";
        String subject = "Merci de vérifier votre Email";
        String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title></title>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "<style type=\"text/css\">\n" +
            "@media screen {\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: normal;\n" +
            "font-weight: 400;\n" +
            "src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: normal;\n" +
            "font-weight: 700;\n" +
            "src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: italic;\n" +
            "font-weight: 400;\n" +
            "src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: italic;\n" +
            "font-weight: 700;\n" +
            "src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
            "}\n" +
            "}\n" +
            "/* CLIENT-SPECIFIC STYLES */\n" +
            "body,\n" +
            "table,\n" +
            "td,\n" +
            "a {\n" +
            "-webkit-text-size-adjust: 100%;\n" +
            "-ms-text-size-adjust: 100%;\n" +
            "}\n" +
            "table,\n" +
            "td {\n" +
            "mso-table-lspace: 0pt;\n" +
            "mso-table-rspace: 0pt;\n" +
            "}\n" +
            "img {\n" +
            "-ms-interpolation-mode: bicubic;\n" +
            "}\n" +
            "/* RESET STYLES */\n" +
            "img {\n" +
            "border: 0;\n" +
            "height: auto;\n" +
            "line-height: 100%;\n" +
            "outline: none;\n" +
            "text-decoration: none;\n" +
            "}\n" +
            "table {\n" +
            "border-collapse: collapse !important;\n" +
            "}\n" +
            "body {\n" +
            "height: 100% !important;\n" +
            "margin: 0 !important;\n" +
            "padding: 0 !important;\n" +
            "width: 100% !important;\n" +
            "}\n" +
            "/* iOS BLUE LINKS */\n" +
            "a[x-apple-data-detectors] {\n" +
            "color: inherit !important;\n" +
            "text-decoration: none !important;\n" +
            "font-size: inherit !important;\n" +
            "font-family: inherit !important;\n" +
            "font-weight: inherit !important;\n" +
            "line-height: inherit !important;\n" +
            "}\n" +
            "/* MOBILE STYLES */\n" +
            "@media screen and (max-width:600px) {\n" +
            "h1 {\n" +
            "font-size: 32px !important;\n" +
            "line-height: 32px !important;\n" +
            "}\n" +
            "}\n" +
            "/* ANDROID CENTER FIX */\n" +
            "div[style*=\"margin: 16px 0;\"] {\n" +
            "margin: 0 !important;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
            "<!-- HIDDEN PREHEADER TEXT -->\n" +
            "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account. </div>\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "<!-- LOGO -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#001D6E\" align=\"center\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#001D6E\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
            "<h1 style=\"font-size: 48px; font-weight: 400; margin: 0px;\">Bienvenu(e)</h1> \n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Bonjour Mr/Mme [[name]]," +
            "Merci de vous être inscrit(e) sur notre application. Veuillez cliquer sur le bouton ci-dessous pour confirmer votre adresse courriel.\n" +
            "\n" +
            "Confirmer l'adresse courriel\n.</p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\">\n" +
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
            "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "<tr>\n" +
            "<td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#7FB5FF\"><a href=\"[[URL]]\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #FFFFFF; display: inline-block;\">Confirmer</a></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr> <!-- COPY -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Si vous n'êtes pas à l'origine de cette inscription, veuillez ignorer cet email.</p>\n" +
            "</td>\n" +
            "</tr> <!-- COPY -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\"><a href=\"/\" target=\"_blank\" style=\"color: #001D6E;\"></a></p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +

            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Cheers,<br>Smile Kids Academy</p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</body>\n" +
            "</html> ";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String fullName = user.getFirstName() + " " + user.getLastName();
        content = content.replace("[[name]]", fullName);
        String verifyURL = siteURL + "/users/verify_parent?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }
    private void sendVerificationEmailEns(Enseignant user, String siteURL) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "ihsenbelkhiri@gmail.com";
        String senderName = "Smile Kids Academy";
        String subject = "Merci de vérifier votre Email";
        String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title></title>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "<style type=\"text/css\">\n" +
            "@media screen {\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: normal;\n" +
            "font-weight: 400;\n" +
            "src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: normal;\n" +
            "font-weight: 700;\n" +
            "src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: italic;\n" +
            "font-weight: 400;\n" +
            "src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
            "}\n" +
            "@font-face {\n" +
            "font-family: 'Lato';\n" +
            "font-style: italic;\n" +
            "font-weight: 700;\n" +
            "src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
            "}\n" +
            "}\n" +
            "/* CLIENT-SPECIFIC STYLES */\n" +
            "body,\n" +
            "table,\n" +
            "td,\n" +
            "a {\n" +
            "-webkit-text-size-adjust: 100%;\n" +
            "-ms-text-size-adjust: 100%;\n" +
            "}\n" +
            "table,\n" +
            "td {\n" +
            "mso-table-lspace: 0pt;\n" +
            "mso-table-rspace: 0pt;\n" +
            "}\n" +
            "img {\n" +
            "-ms-interpolation-mode: bicubic;\n" +
            "}\n" +
            "/* RESET STYLES */\n" +
            "img {\n" +
            "border: 0;\n" +
            "height: auto;\n" +
            "line-height: 100%;\n" +
            "outline: none;\n" +
            "text-decoration: none;\n" +
            "}\n" +
            "table {\n" +
            "border-collapse: collapse !important;\n" +
            "}\n" +
            "body {\n" +
            "height: 100% !important;\n" +
            "margin: 0 !important;\n" +
            "padding: 0 !important;\n" +
            "width: 100% !important;\n" +
            "}\n" +
            "/* iOS BLUE LINKS */\n" +
            "a[x-apple-data-detectors] {\n" +
            "color: inherit !important;\n" +
            "text-decoration: none !important;\n" +
            "font-size: inherit !important;\n" +
            "font-family: inherit !important;\n" +
            "font-weight: inherit !important;\n" +
            "line-height: inherit !important;\n" +
            "}\n" +
            "/* MOBILE STYLES */\n" +
            "@media screen and (max-width:600px) {\n" +
            "h1 {\n" +
            "font-size: 32px !important;\n" +
            "line-height: 32px !important;\n" +
            "}\n" +
            "}\n" +
            "/* ANDROID CENTER FIX */\n" +
            "div[style*=\"margin: 16px 0;\"] {\n" +
            "margin: 0 !important;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
            "<!-- HIDDEN PREHEADER TEXT -->\n" +
            "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account. </div>\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "<!-- LOGO -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#001D6E\" align=\"center\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#001D6E\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
            "<h1 style=\"font-size: 48px; font-weight: 400; margin: 0px;\">Bienvenu(e)</h1> \n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Bonjour Mr/Mme [[name]]," +
            "Merci de vous être inscrit(e) sur notre application. Veuillez cliquer sur le bouton ci-dessous pour confirmer votre adresse courriel.\n" +
            "\n" +
            "Confirmer l'adresse courriel\n.</p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\">\n" +
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
            "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "<tr>\n" +
            "<td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#7FB5FF\"><a href=\"[[URL]]\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #FFFFFF; display: inline-block;\">Confirmer</a></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr> <!-- COPY -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Si vous n'êtes pas à l'origine de cette inscription, veuillez ignorer cet email.</p>\n" +
            "</td>\n" +
            "</tr> <!-- COPY -->\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\"><a href=\"/\" target=\"_blank\" style=\"color: #001D6E;\"></a></p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +

            "</tr>\n" +
            "<tr>\n" +
            "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
            "<p style=\"margin: 0;\">Cheers,<br>Smile Kids Academy</p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</body>\n" +
            "</html> ";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String fullName = user.getFirstName() + " " + user.getLastName();
        content = content.replace("[[name]]", fullName);
        String verifyURL = siteURL + "/users/verify_ens?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public String hash(String password) {


        String hashedPassword = null;
        int i = 0;
        while (i < 5) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashedPassword = passwordEncoder.encode(password);
            i++;
        }

        return hashedPassword;
    }

    public boolean verifyEns(String verificationCode) {
        Enseignant user = enseignantRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repo.save(user);

            return true;
        }

    }
    public boolean verifyParent(String verificationCode) {
        Parent user = parentRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repo.save(user);

            return true;
        }

    }

}
