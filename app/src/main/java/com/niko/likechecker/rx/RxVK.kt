package com.niko.likechecker.rx

import com.niko.likechecker.model.Album
import com.niko.likechecker.model.Friend
import com.niko.likechecker.model.Photo
import com.vk.sdk.api.*
import com.vk.sdk.api.model.*
import io.reactivex.Observable

fun getProfileUser(): Observable<VKApiUserFull> {
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
                it.onError(Throwable(error.errorMessage))
            }
        })
    }
}

fun getUser(id: String): Observable<VKApiUserFull> {
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
                it.onError(Throwable(error.errorMessage))
            }
        })
    }
}

fun getAlbums(id: String): Observable<List<Album>> {
    return Observable.create {

        val list = mutableListOf<Album>()

        val photoRequest = VKRequest("photos.getAlbums", VKParameters.from(
                VKApiConst.OWNER_ID, id,
                "need_system", 1))

        photoRequest.executeWithListener(object : VKRequest.VKRequestListener() {

            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val jsonArray = response.json.getJSONObject("response").getJSONArray("items")
                for (i in 0..(jsonArray.length() - 1)) {
                    val item = jsonArray.getJSONObject(i)
                    list.add(Album(item.getString("id"), item.getString("title")))
                }
                it.onNext(list)
                it.onComplete()
            }

            override fun onError(error: VKError) {
                super.onError(error)
                it.onError(Throwable(error.errorMessage))
            }
        })
    }
}

fun getPhotos(id: String, time: Long, albomId: String = "profile"): Observable<List<VKApiPhoto>> {
    return Observable.create {

        val photos = mutableListOf<VKApiPhoto>()

        val photoRequest = VKRequest("photos.get", VKParameters.from(
                VKApiConst.OWNER_ID, id,
                VKApiConst.ALBUM_ID, albomId,
                VKApiConst.REV, 1), VKPhotoArray::class.java)

        photoRequest.executeWithListener(object : VKRequest.VKRequestListener() {

            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val vkPhotoArray = response.parsedModel as VKPhotoArray

                for (vkApiPhoto in vkPhotoArray) {
                    if (time == 0L) {
                        photos.add(vkApiPhoto)
                    } else {

                        if (time < (vkApiPhoto.date * 1000)) {
                            photos.add(vkApiPhoto)
                        }
                    }
                }

                it.onNext(photos)
                it.onComplete()
            }

            override fun onError(error: VKError) {
                super.onError(error)
                it.onError(Throwable(error.errorMessage))
            }
        })
    }
}

fun checkLike(userId: String, peopleId: String, photo: VKApiPhoto): Observable<Photo> {
    return Observable.create {

        val likeRequest = VKRequest("likes.isLiked", VKParameters.from(
                "user_id", userId,
                "type", "photo",
                "owner_id", peopleId,
                "item_id", photo.id))

        likeRequest.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val liked = response.json.getJSONObject("response").getInt("liked")
                if (liked == 1) {
                    it.onNext(Photo(photo.id.toString(), peopleId, photo.photo_604, photo.date))
                }
                it.onComplete()
            }

            override fun onError(error: VKError) {
                super.onError(error)
                it.onError(Throwable(error.errorMessage))
            }
        })
    }

}

fun getFriends(id: String = ""): Observable<List<Friend>> {
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
                it.onError(Throwable(error.errorMessage))
            }
        })
    }
}