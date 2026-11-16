package com.example.skills.model.Repositories.repository

import com.example.skills.R

object VideoCatalog {

    const val DEMO_LINK =
        "https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4"

    private val demoVideo = "android.resource://com.example.skills/" + R.raw.demo_video

    private val videos = mutableMapOf(1 to demoVideo)

    fun getVideo(id: Int): String? {
        return videos[id]
    }

    fun saveVideo(id: Int, link: String) {
        videos[id] = link
    }

    fun deleteVideo(id: Int) {
        videos.remove(id)
    }
}