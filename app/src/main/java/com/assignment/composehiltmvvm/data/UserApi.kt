package com.assignment.composehiltmvvm.data

import com.assignment.composehiltmvvm.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("/users")
    suspend fun getUsers(): Response<UserResponse>
}