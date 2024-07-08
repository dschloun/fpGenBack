package be.unamur.fpgen.application;

import java.util.UUID;

public class Constants {
    public static final UUID domainUnknow = UUID.fromString("103117af-b97d-4401-8091-ae7b5ce94259");
    public static final UUID categoryUnknow = UUID.fromString("5e061b00-343f-4e54-ab82-b32b26dd2f70");
    public static final String spontaneousJobUrl = "https://afelio.be/job/spontaneous-application/";
    public static final UUID spontaneousJobId = UUID.fromString("e72f6f7f-432a-435e-980b-df8032034e91");

    //recruiter email template
    public static final String applicationNotificationHR = "Vous avez reçu une candidature pour le poste de %1$s. " +
            "\n" +
            "\nDe la part de %2$s %3$s." +
            "\n" +
            "\nVoici son email: %4$s" +
            "\n" +
            "\nEt son téléphone: %5$s" +
            "\n" +
            "\n" +
            "\nCeci est un email automatique de AfelioHR";

    //candidate email template
    public static final String applicationNotificationCandidate = "Bonjour %2$s %3$s. " +
            "\n" +
            "\nTu as postulé pour le poste de %1$s." +
            "\n" +
            "\nAfelio concerve tes coordonnées afin de te recontacter"+
            "\n" +
            "\npar email: %4$s" +
            "\n" +
            "\noù téléphone: %5$s" +
            "\n" +
            "\n voici le lien pour consulter les conditions générales du traitement des données personnelles:" +
            "\n %6$s" +
            "\n" +
            "\n" +
            "\nCeci est un email automatique de AfelioHR";
}