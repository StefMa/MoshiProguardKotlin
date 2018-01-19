package guru.stefma.moshiproguardkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.squareup.moshi.Json
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    data class UserModel(
            @field:Json(name = "userId") val userId: String,
            @Json(name = "id") val id: String,
            @field:Json(name = "title") val title: String,
            @Json(name = "body") val body: String
    )

    interface Service {

        @GET("/posts")
        fun getPosts(): Single<List<UserModel>>

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()
                .create(Service::class.java)
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(MainActivity::class.java.simpleName, "It works: $it")
                    findViewById<TextView>(R.id.debug_text).apply {
                        text = it.toString()
                    }
                }, {
                    Log.d(MainActivity::class.java.simpleName, "It doesn't work: $it")
                    findViewById<TextView>(R.id.debug_text).apply {
                        text = it.toString()
                    }
                })
    }
}
