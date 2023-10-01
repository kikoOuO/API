package api;

import org.json.JSONException;
import entity.User;
import org.json.JSONObject;
import usecase.CreatUserUseCase;

import java.io.IOException;
import java.util.List;

public interface ChatDB {
        User createUser(String user_id, String nickname, String profileUrl, String profileFile,
                        String issueAccessToken, List<String> discoveryKeys, JSONObject metadata) throws JSONException;


}
