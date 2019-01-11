package com.nixesea.webimtestapp;

import android.content.Context;
import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Presenter {
    private View view;

    public Presenter(View view){
        this.view = view;
    }

    public void getCurrentUserInfo() {
        final UserModel currentUser = new UserModel();
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"id,first_name, last_name, photo"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
//                Log.i("VKTEST","get current is complete \n " + response.responseString);
                try {
                    JSONObject object = new JSONObject(response.responseString);

                    Log.i("VKTEST","name = " + object);
                    JSONArray jsonArray = object.getJSONArray("response");

                    JSONObject responseObject = jsonArray.getJSONObject(0);

                    currentUser.setId(responseObject.getLong("id"));
                    currentUser.setFirstName(responseObject.getString("first_name"));
                    currentUser.setSecondName(responseObject.getString("last_name"));
                    currentUser.setPhotoURL(responseObject.getString("photo"));

                    view.updateCurrentUserInfo(currentUser.getName(),currentUser.getPhotoURL());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
                Log.i("VKTEST","get current user is error");
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Log.i("VKTEST","get current user is attemptFailed");
            }
        });
    }

    public void getFiveFriend(){
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.COUNT, 5,VKApiConst.FIELDS,"id,first_name, last_name, photo","order","random"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
//                Log.i("VKTEST","get 5 friends is complete \n " + response.responseString);
                try {
                    JSONObject object = new JSONObject(response.responseString);
                    JSONObject responseObject = object.getJSONObject("response");
                    JSONArray array = responseObject.getJSONArray("items");

                    ArrayList<UserModel> friendsList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);

                        UserModel friendUnit = new UserModel();
                        friendUnit.setId(item.getLong("id"));
                        friendUnit.setFirstName(item.getString("first_name"));
                        friendUnit.setSecondName(item.getString("last_name"));
                        friendUnit.setPhotoURL(item.getString("photo"));
                        friendUnit.setOnline(item.getInt("online"));

                        friendsList.add(friendUnit);
                    }

                    view.updateFriendList(friendsList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
                Log.i("VKTEST","get 5 friends is error");
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Log.i("VKTEST","get 5 friends is attemptFailed");
            }
        });
    }

    public interface View{
        void checkLogged();

        void updateFriendList(ArrayList<UserModel> friendList);

        void updateCurrentUserInfo(String name, String photoURL);
    }
}
