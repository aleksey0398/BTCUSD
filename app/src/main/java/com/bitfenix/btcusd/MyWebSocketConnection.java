package com.bitfenix.btcusd;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * @author Aleksey Mitkin
 * Класс занимается получением и передачей данных с публичного сокет канала
 */

public class MyWebSocketConnection {

    private Observable<Object> observable;
    private WebSocket ws;
    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private WebSocketListener listener;

    private static String btcUSDSubscribe = null;

    static {
        try {
            btcUSDSubscribe = new JSONObject()
                    .put("event", "subscribe")
                    .put("channel", "ticker")
                    .put("pair", "BTCUSD")
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Конструктор, в котором происходит инициализация обектов для работы с сокетом
     * и открывается полнодуплексное соединение
     *
     * @param observer слушатель, которому будут передаваться данные
     */
    public MyWebSocketConnection(Observer<? super Object> observer) {

        observable = Observable.create(e -> {

            request = new Request.Builder().url(Var.urlwss).build();

            listener = new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    super.onOpen(webSocket, response);
                    webSocket.send(btcUSDSubscribe);
                    log("open", response);
                }

                /**
                 * Делаем проверку можем ли мы собрать объект из полученного собщения
                 * Это позволяет отфильтровать любые сообщения,
                 * не имеющие к котировкам никаого отношения
                 * @see ResponseObject */
                @Override
                public void onMessage(WebSocket webSocket, String text) {
                    super.onMessage(webSocket, text);
                    if (ResponseObject.canCreateObject(text))
                        e.onNext(new ResponseObject(text));
                    log("message", text);
                }

                @Override
                public void onClosed(WebSocket webSocket, int code, String reason) {
                    super.onClosed(webSocket, code, reason);
                    log("closed", reason);
                    e.onComplete();
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                    super.onFailure(webSocket, t, response);
                    log("onFailure", t.getMessage());
                    e.onError(t);
                }
            };

            ws = client.newWebSocket(request, listener);

            client.dispatcher().executorService().shutdown();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(observer);
    }

    public void closeConnection() {
        if (ws != null)
            try {
                ws.close(3000, "Because I'm Batman");
            } catch (IllegalArgumentException e) {
                ws.cancel();
            }
    }

    private void log(String method, Object message) {
        Log.i(getClass().getName(), method + "(): " + String.valueOf(message));
    }
}
