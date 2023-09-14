package io.davorpatech.apps.musicalsurveyor.domain;

public interface RadioListenerConstants { // NOSONAR
    String DOMAIN_NAME = "musicpoll.RadioListener";

    int NAME_MAXLEN = 255;

    int PHONE_MAXLEN = 15;

    String PHONE_REGEX = "^\\+[0-9]{9,}$"; // Example: +34659881420

    int EMAIL_MAXLEN = 255;

    int ADDRESS_MAXLEN = 500;
}
