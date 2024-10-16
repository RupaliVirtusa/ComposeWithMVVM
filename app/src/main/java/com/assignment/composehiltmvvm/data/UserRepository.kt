package com.assignment.composehiltmvvm.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val userApi: UserApi) {
    suspend fun getUser(): Flow<UserState> {
        return flow {
            emit(UserState.Loading)
            val response = userApi.getUsers()

            if (response.isSuccessful) {
                response.body()?.let { UserState.OnUserLoad(it) }?.let { emit(it) }
            } else {
                emit(UserState.OnError("some error"))
            }
        }.flowOn(Dispatchers.IO).catch {
            emit(UserState.OnError(it.message.toString()))
        }
    }
}