package fr.projet.WebSocket;

import fr.projet.Callback;
import fr.projet.game.Game;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocketClient {

    private final CountDownLatch latch;
    private Session session;
    @Getter
    private String response = null;
    @Getter
    private boolean closed = true;
    @Getter
    @Setter
    private Callback callback;

    public WebSocketClient() {
        this.latch = new CountDownLatch(1);
    }

    private Game game = null;

    public void reConnect(String serverUri) throws DeploymentException, URISyntaxException, IOException, InterruptedException {
        if (isClosed()) {
            connect(serverUri);
        }
    }

    public void connect(String serverUri) throws URISyntaxException, DeploymentException, InterruptedException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, new URI(serverUri));
        latch.await(5, TimeUnit.SECONDS);
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public void setGame(Game game) {
        if (this.game == null)
            this.game = game;
    }

    public void close() throws IOException {
        session.close();
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        closed = false;
        System.out.println("Connected to server");
        latch.countDown();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        response = message;
        System.out.println("Received message: " + message);
        if (message.startsWith("{")) {
            if (callback != null)
                callback.call();
            return;
        }
        game.play1vs1(message);
    }
    @OnClose
    public void onClose() {
        closed = true;
        System.out.println("Connection closed");
    }
}