package com.worldsnas.starplayer.utils

data class Resource<out T>(val data: T?, val message: String?, val status: Status) {


    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(data, null, Status.SUCCESS)
        }

        fun <T> loading(data:T?): Resource<T> {
            return Resource(null, null, Status.LOADING)
        }

        fun <T> error(message: String?,data:T?): Resource<T> {
            return Resource(null, message, Status.ERROR)
        }
    }


    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}