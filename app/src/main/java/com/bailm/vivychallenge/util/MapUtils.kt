package com.bailm.vivychallenge.util

import android.content.Intent
import android.drm.DrmStore
import android.net.Uri

object MapUtils{
    fun getMapIntentForAddress(address:String):Intent{
        val uri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW,uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }
}