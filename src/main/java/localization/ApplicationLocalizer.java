package localization;

import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;


public class ApplicationLocalizer extends Observable {
    private String currentLanguage = "en";
    private final ResourceBundle resourceBundleEn = ResourceBundle.getBundle("localization", new Locale("en"));

    private final ResourceBundle resourceBundleRu = ResourceBundle.getBundle("localization", new Locale("ru"));

    private ApplicationLocalizer() {
    }

    public static ApplicationLocalizer applicationLocalizer = new ApplicationLocalizer();

    public void changeLanguage(String newLanguage) {
        currentLanguage = newLanguage;
        setChanged();
        notifyObservers("CHANGE_LANGUAGE");
    }

    public String getLocalizedText(String keyText) {
        if (currentLanguage.equals("en")) {
            return resourceBundleEn.getString(keyText);
        } else if (currentLanguage.equals("ru")) {
            return resourceBundleRu.getString(keyText);
        }
        return null;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }
}
