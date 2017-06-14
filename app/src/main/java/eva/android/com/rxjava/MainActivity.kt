package eva.android.com.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import eva.android.com.javarx.R
import eva.android.com.rxjava.Adapter.CardAdapter
import eva.android.com.rxjava.Models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val service = Application.service!!

        val mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView

        val mCardAdapter = CardAdapter(realm.where(User::class.java).findAll())
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mCardAdapter
        realm.addChangeListener { mCardAdapter.notifyDataSetChanged() }

        val githubUsers: Array<String> = resources.getStringArray(R.array.github_users)
        findViewById(R.id.button_clear).setOnClickListener {
            realm.executeTransactionAsync { data -> data.delete(User::class.java) }
        }
        findViewById(R.id.button_fetch).setOnClickListener {
            githubUsers.map {
                service.getUsers(it)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { r -> realm.executeTransactionAsync { realm1 -> realm1.insert(r) } })
            }.forEach { compositeDisposable.add(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        compositeDisposable.clear()
    }
}
