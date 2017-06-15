package eva.android.com.rxjava

import eva.android.com.rxjava.http.GithubService
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        val restAdapter = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = restAdapter.create(GithubService::class.java)
    }

    companion object {

        var service: GithubService? = null
            set
    }
}
