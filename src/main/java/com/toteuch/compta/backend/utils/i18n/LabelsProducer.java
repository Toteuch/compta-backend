package com.toteuch.compta.backend.utils.i18n;

import com.toteuch.compta.backend.utils.cdi.qualifier.French;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@ApplicationScoped
public class LabelsProducer {
    private Labels labels;
    private Labels labelsFr;

    @PostConstruct
    private void postConstruct() {
        Locale locale = Locale.ENGLISH;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.toteuch.compta.backend.utils.i18n.messages", locale);
        labels = new LabelsImpl(resourceBundle);

        Locale localeFr = Locale.FRENCH;
        ResourceBundle rbFr = ResourceBundle.getBundle("com.toteuch.compta.backend.utils.i18n.messages", localeFr);
        labelsFr = new LabelsImpl(rbFr);
    }

    @Produces
    @ApplicationScoped
    @Default
    public Labels getLabels() {
        return labels;
    }

    @Produces
    @ApplicationScoped
    @French
    public Labels getLabelsFr() {
        return labelsFr;
    }

    private static class LabelsImpl implements Labels {
        private final ResourceBundle resourceBundle;

        public LabelsImpl(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
        }

        @Override
        public String getString(String key) {
            return resourceBundle.getString(key);
        }

        @Override
        public String getString(String key, Object... args) {
            return MessageFormat.format(resourceBundle.getString(key), args);
        }

    }
}
