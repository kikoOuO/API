package usecase;
import entity.User;
import api.ChatDB;
import org.json.JSONObject;

import java.util.List;

public final class CreatUserUseCase {

    private final ChatDB chatDB;

    public CreatUserUseCase(ChatDB chatDB) { this.chatDB = chatDB;}

    public User createUser(String user_id, String nickname, String profileUrl, String profileFile,
                          String issueAccessToken, List<String> discoveryKeys, JSONObject metadata) {
        return chatDB.createUser(user_id, nickname, profileUrl, profileFile,
                issueAccessToken, discoveryKeys, metadata);
    }

}
