package com.niko.likechecker.rx

import com.niko.likechecker.model.Friend
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUserFull
import com.vk.sdk.api.model.VKList
import com.vk.sdk.api.model.VKUsersArray
import io.reactivex.Observable

fun  getProfileUser() : Observable<VKApiUserFull> {
    return Observable.create {
        val request = VKApi.users().get()
        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val vkList = response.parsedModel as VKList<*>
                val vkUser = vkList[0] as VKApiUserFull
                it.onNext(vkUser)
                it.onComplete()
            }

            override fun onError(error: VKError) {
                super.onError(error)
                it.onError(error.httpError)
            }
        })
    }
}

/*
*   private void getFriends(String id) {
        friendsList.clear();
        if (id != null && !id.equals("")) {
            vkRequest = VKApi.friends().get(VKParameters.from(
                    VKApiConst.USER_ID,id,
                    VKApiConst.FIELDS, "id,first_name,last_name,sex,bdate,city",
               /* VKApiConst.COUNT, 100,*/
                    "order", "name"));
        } else {
        vkRequest = VKApi.friends().get(VKParameters.from(
                VKApiConst.FIELDS, "id,first_name,last_name,sex,bdate,city",
               /* VKApiConst.COUNT, 100,*/
                "order", "hints"));
        }
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKUsersArray vkUsersArray = (VKUsersArray) response.parsedModel;

                for (VKApiUserFull user : vkUsersArray) {
                    friendsList.add(new Friends(user.first_name + " " + user.last_name, user.id));
                }

                mFriendsAdapter.setFriendsList(friendsList);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                dismiss();
            }
        });
    }*/

fun getFriends(id: String = "") : Observable<List<Friend>> {
    return Observable.create {

        val friendsList = mutableListOf<Friend>()

        val vkParameters = when {
            id.isEmpty() -> VKParameters.from(
                    VKApiConst.FIELDS, "id,first_name,last_name,sex,bdate,city",
                    "order", "hints")
            else -> VKParameters.from(
                    VKApiConst.USER_ID, id,
                    VKApiConst.FIELDS, "id,first_name,last_name,sex,bdate,city",
                    "order", "name")
        }

        VKApi.friends().get(vkParameters).executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val vkUsersArray = response.parsedModel as VKUsersArray

                for (user in vkUsersArray) {
                    friendsList.add(Friend(user.first_name + " " + user.last_name, user.id))
                }

                it.onNext(friendsList)
                it.onComplete()
            }

            override fun onError(error: VKError) {
                super.onError(error)
                it.onError(error.httpError)
            }
        })
    }
}