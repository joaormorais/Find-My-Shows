package com.example.findmyshows

import android.app.Application
import com.example.findmyshows.data.ShowData

class MyApplication : Application() {

    val showData by lazy { ShowData() }

}