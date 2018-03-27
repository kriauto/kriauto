package example.angularspring.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Constant {
	
	private static Map labels = new HashMap();

	public static Map getLabels() {
		return labels;
	}

	public static void setLabels(Map labels) {
		Constant.labels = labels;
	}

	@PostConstruct
	public void initConstants() {
		labels.put("LOGIN_REQUIRED", "Le login est obligatoire");
		labels.put("PASSWORD_REQUIRED", "le mot de passe est obligatoire.");
		labels.put("LOGIN_NOT_FOUND", "le login n'existe pas.");
		labels.put("PASSWORD_FAILED", "le mot de passe est incorrect.");
		labels.put("LOGIN_SUCCES", "Vous etes bien connectes.");
		labels.put("MAIL_REQUIRED", "l'email est bligatoire.");
		labels.put("MAIL_NOT_FOUND", "l'email n'existe pas.");
		labels.put("PASSWORD_SEND", "le nouveau mot de passe vient d'etre envoyer");
		labels.put("LOGOUT_SUCCES", "vous etes déconnectés");
		labels.put("STOP_CAR", "vous seraez notifié par sms une fois la demande d'arret est traitée");
		labels.put("START_CAR", "vous seraez notifié par sms une fois la demande de démarrge est traitée");
		labels.put("GEOFENCE_SUCCES", "Les coordonnées sont enregistrées avec succès");
		labels.put("ACTION_FAILED", "cette action n'est pas autorisée");
	}
}
