package eva.android.com.rxjava.Models


import com.google.gson.annotations.SerializedName

import io.realm.RealmObject

open class User: RealmObject() {

    var login: String? = null
    var id: Int = 0
    @SerializedName("avatar_url") var avatarUrl: String? = null

}