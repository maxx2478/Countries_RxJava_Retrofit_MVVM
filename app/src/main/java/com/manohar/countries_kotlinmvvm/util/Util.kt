package com.manohar.countries_kotlinmvvm.util

import android.content.Context
import android.media.Image
import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.manohar.countries_kotlinmvvm.R

fun getProgressDrawable(context: Context):CircularProgressDrawable
{

    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    return circularProgressDrawable
}

fun ImageView.loadimage(uri:String?, progressDrawable: CircularProgressDrawable)
{



    //For SVG extensions Images
    GlideToVectorYou.init().with(this.context).load(Uri.parse(uri),this);

    /*
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

     */

}