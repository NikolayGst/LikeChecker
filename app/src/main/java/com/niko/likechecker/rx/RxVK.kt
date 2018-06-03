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

fun  getUser(id: String) : Observable<VKApiUserFull> {
    return Observable.create {
        val request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, id, VKApiConst.FIELDS, "id,first_name,last_name,photo_200"))
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