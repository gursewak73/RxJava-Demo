package com.rxjavademo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribe()
    }

    private fun subscribe() {
        val subscribe = getObservableData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.wtf("data", it)
            }
        disposable.addAll(subscribe)
    }

    private fun getObservableData() : Observable<String> {
        return Observable.create<String> {
            emitter -> emitter.onNext("Test")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable.clear()
        }
    }

}
