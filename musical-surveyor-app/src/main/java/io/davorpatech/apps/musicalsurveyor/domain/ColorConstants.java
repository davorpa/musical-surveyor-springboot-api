package io.davorpatech.apps.musicalsurveyor.domain;

public interface ColorConstants { // NOSONAR
    String DOMAIN_NAME = "musicpoll.Color";

    int CODE_MAXLEN = 25;

    String CODE_REGEX = "^(\\#[0-9A-F]{6})$|^([a-z]+)$";
}
