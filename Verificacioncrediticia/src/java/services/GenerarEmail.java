package services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import jdk.internal.net.http.websocket.Transport;

@Path("notificaciones")
public class GenerarEmail{

    private static final String FROM_EMAIL = "tu_correo@gmail.com"; // Cambiar por tu correo
    private static final String PASSWORD = "tu_contraseña";         // Cambiar por tu contraseña

    @GET
    @Path("/enviar/{idCliente}/{estado}")
    public String enviarNotificacion(
            @PathParam("idCliente") String idCliente,
            @PathParam("estado") String estado) {

        String subject;
        String messageBody;

        // Generar el asunto y el cuerpo del mensaje según el estado
        if (estado.equalsIgnoreCase("rechazado")) {
            subject = "Solicitud Rechazada";
            messageBody = generarMensajeRechazo(idCliente);
        } else if (estado.equalsIgnoreCase("aprobado")) {
            subject = "Solicitud Aprobada";
            messageBody = generarMensajeAprobacion(idCliente);
        } else {
            return "Estado inválido. Usa 'aprobado' o 'rechazado'.";
        }

        // Enviar el correo
        try {
            enviarEmail("destinatario@correo.com", subject, messageBody); // Cambiar destinatario si es necesario
            return "Correo enviado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }

    // Método para generar mensaje por solicitud rechazada
    private String generarMensajeRechazo(String idCliente) {
        return "Estimado cliente (ID: " + idCliente + "),\n\n"
                + "Lamentamos informarle que su solicitud ha sido rechazada debido a nuestros criterios de evaluación crediticia.\n\n"
                + "Para más información, por favor comuníquese con nuestro servicio al cliente.\n\n"
                + "Saludos cordiales,\n"
                + "Equipo de Verificación Crediticia.";
    }

    // Método para generar mensaje por solicitud aceptada
    private String generarMensajeAprobacion(String idCliente) {
        return "Estimado cliente (ID: " + idCliente + "),\n\n"
                + "Nos complace informarle que su solicitud ha sido aprobada exitosamente.\n\n"
                + "Puede proceder con los siguientes pasos para finalizar su proceso.\n\n"
                + "Saludos cordiales,\n"
                + "Equipo de Verificación Crediticia.";
    }

    // Método para enviar el correo electrónico
    private void enviarEmail(String toEmail, String subject, String messageBody) throws Exception {
        // Configuración de propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        // Crear el mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(messageBody);
    }
}

