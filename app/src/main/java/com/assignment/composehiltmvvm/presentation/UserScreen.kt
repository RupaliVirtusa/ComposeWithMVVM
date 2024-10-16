package com.assignment.composehiltmvvm.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.assignment.composehiltmvvm.R
import com.assignment.composehiltmvvm.data.UserState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserScreen(userState: UserState, onListClick: (String) -> Unit) {

    userState.let { userState ->
        when (userState) {
            is UserState.OnError -> {

            }

            is UserState.OnUserLoad -> {
                val response = userState.userResponse
                LazyColumn {
                    itemsIndexed(items = response) { index, item ->
                        Box(modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)) {
                            Column(
                                modifier = Modifier
                                    .background(color = Color.LightGray)
                            ) {
                                val image = loadPicture(
                                    url = item.avatar,
                                    defaultImage = R.drawable.ic_launcher_background
                                ).value
                                image?.let {
                                    Image(
                                        bitmap = image.asImageBitmap(),
                                        contentDescription = "some useful description",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(225.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(
                                    text = item.email ?: "",
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                                )
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                                )
                                if (index < response.size)
                                    Divider(color = Color.Gray, thickness = 1.dp)
                            }
                        }
                    }
                }
            }

            is UserState.Loading -> {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.Center),
                            color = Color.Black
                        )
                    }
                }
            }

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int):
        MutableState<Bitmap?> {
    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // show default image while image loads

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    // get network image

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}

        })

    return bitmapState
}
