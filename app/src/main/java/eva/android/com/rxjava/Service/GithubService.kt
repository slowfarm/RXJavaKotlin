package eva.android.com.rxjava.Service

import eva.android.com.rxjava.Models.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{user}")
    fun getUsers(@Path("user") user: String): Flowable<User>
}
