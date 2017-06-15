package eva.android.com.rxjava.http

import eva.android.com.rxjava.models.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{user}")
    fun getUsers(@Path("user") user: String): Flowable<User>
}
