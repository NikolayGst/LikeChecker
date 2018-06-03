package com.niko.likechecker.ui.like

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.niko.likechecker.R
import com.niko.likechecker.extensions.logs

class LikeFriendActivity : AppCompatActivity() {

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_friend)

        id = intent.extras.getString("id")

        logs(id)
    }
}
