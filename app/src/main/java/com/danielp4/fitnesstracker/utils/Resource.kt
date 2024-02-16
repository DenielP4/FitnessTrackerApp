package com.danielp4.fitnesstracker.utils

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

object Resource {

    fun setActionBar(id: Int, activity: AppCompatActivity): ActionBar {
        val ab: ActionBar = activity.supportActionBar!!
        ab.title = activity.getString(id)
        return ab
    }

}