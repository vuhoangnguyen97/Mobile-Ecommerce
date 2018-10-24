package com.example.legia.mobileweb.Translate;

import android.database.Observable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;

public class YandexTranslation {
    public static String ERROR_MESSAGE = "Can't translate!";
    private static String mKey;

    public YandexTranslation setKey(String key) {
        mKey = key;
        return this;
    }

    public static Translation getTranslation(String sourceText, String sourceLang, String destinationLang) throws Throwable {

        String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + "trnsl.1.1.20180914T173158Z.519ec9dd90a2e48a.7b09f27fc3840c1c28ffe23f5235b02840f16a32"
                + "&text="+sourceText.replaceAll("\\s+","%20")+"&lang="+sourceLang+"-" +destinationLang;
        URL url = new URL(yandexUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        InputStream inputStream = conn.getInputStream();

        String result = convertInputStreamToString(inputStream);

        inputStream.close();
        conn.disconnect();

        return Translation.fromJson(result);
    }

    public rx.Observable<Translation> getTranslationObservable(final String sourceText, final String sourceLang, final String destinationLang) {
        return rx.Observable.create(new rx.Observable.OnSubscribe<Translation>() {
            @Override
            public void call(Subscriber<? super Translation> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {

                        Translation translation = getTranslation(sourceText, sourceLang, destinationLang);
                        subscriber.onNext(translation);
                        subscriber.onCompleted();
                    }
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public rx.Observable<String> getTextObservable(final String sourceText, final String sourceLang, final String destinationLang) {
        return getTranslationObservable(sourceText, sourceLang, destinationLang)
                .map(new Func1<Translation, String>() {
                    @Override
                    public String call(Translation translation) {
                        if (Translation.hasTranslation(translation)){
                            return translation.translations.get(0);
                        }
                        return ERROR_MESSAGE;
                    }
                });
    }

    public static class Translation {
        public int code;
        public String lang;
        public List<String> translations;

        public static Translation fromJson(String json) throws JSONException {
            Translation translation = new Translation();
            JSONObject jsonObj = new JSONObject(json);
            translation.code = jsonObj.getInt("code");
            translation.lang = jsonObj.getString("lang");
            JSONArray text = jsonObj.getJSONArray("text");
            translation.translations = fromJsonArray(text);
            return translation;
        }

        public static boolean hasTranslation(Translation t){
            return t != null && t.translations != null && t.translations.size() != 0;
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private static List<String> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() != 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                String text = jsonArray.getString(i);
                list.add(text);
            }
        }
        return list;
    }
}
