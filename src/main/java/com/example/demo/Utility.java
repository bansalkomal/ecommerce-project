package com.example.demo;

import com.example.demo.dto.OrderItemResponseDTO;
import com.example.demo.dto.OrderResponseDTO;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class Utility {

<<<<<<< Updated upstream
    public static void sendEmail(String message) throws MessagingException {
=======
//    @Autowired
//    private JavaMailSender mailSender;

//    @Autowired
//    private UserRepository userRepository;

//    public static void sendEmail(String message) throws MessagingException {
//        JavaMailSenderImpl sender = new JavaMailSenderImpl();
////        sender.setHost("smtp.sendgrid.net");
////        sender.setUsername("apikey");
////        sender.setPassword("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");
////        sender.setPort(587);
//
//
//        sender.setHost("smtp.gmail.com");
//        sender.setUsername("bansalkomal93@gmail.com");
//        sender.setPassword("jkcb qkdm khut tfsu");
//        sender.setPort(587);
//        Properties props = new Properties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//        sender.setJavaMailProperties(props);
//        final MimeMessage msg = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        helper.setFrom("bansalkomal93@gmail.com");
//        helper.setTo("bansalkomal93@gmail.com");
//        String body = message;
//        helper.setText(body);
//        helper.setSubject("Order Placed");
//        sender.send(msg);
//    }


    public void sendOrderEmail(String email, OrderResponseDTO orderResponse) throws MessagingException {
>>>>>>> Stashed changes
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setUsername("bansalkomal93@gmail.com");
        sender.setPassword("jkcb qkdm khut tfsu");
        sender.setPort(587);
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        sender.setJavaMailProperties(props);
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //User user = userRepository.findById(userId).orElse(null);
        helper.setTo(email);
        helper.setSubject("Order Confirmation");
        helper.setText(buildEmailBody(orderResponse), true); // HTML content

        sender.send(mimeMessage);
    }

    private String buildEmailBody(OrderResponseDTO orderResponse) {
        StringBuilder sb = new StringBuilder();
        double grandTotal = 0.0;

        sb.append("<html><body>");
        sb.append("<h2>Order Confirmation</h2>");
        sb.append("<p>Thank you for your order! Here are the details:</p>");
        sb.append("<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse;'>");
        sb.append("<tr>")
                .append("<th>Product</th>")
                .append("<th>Quantity</th>")
                .append("<th>Unit Price</th>")
                .append("<th>Total</th>")
                .append("</tr>");

        for (OrderItemResponseDTO item : orderResponse.getOrderItems()) {
            double total = item.getQuantity() * item.getPrice();
            grandTotal += total;
            sb.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td style='text-align: center;'>").append(item.getQuantity()).append("</td>")
                    .append("<td>$").append(String.format("%.2f", item.getPrice())).append("</td>")
                    .append("<td>$").append(String.format("%.2f", total)).append("</td>")
                    .append("</tr>");
        }

        sb.append("<tr>")
                .append("<td colspan='3' style='text-align: right; font-weight: bold;'>Grand Total</td>")
                .append("<td style='font-weight: bold;'>$")
                .append(String.format("%.2f", grandTotal))
                .append("</td>")
                .append("</tr>");

        sb.append("</table>");
        sb.append("<p style='margin-top: 20px;'>We appreciate!</p>");
        sb.append("<p>Regards,<br>Mcart Team</p>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
