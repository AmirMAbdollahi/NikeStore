package com.example.niketest.common

import com.example.niketest.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object{
        fun map(throwable: Throwable):NikeException{
            if (throwable is HttpException){
                try {
                    val errorJSONObject=JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage=errorJSONObject.getString("message")
                    return NikeException(if (throwable.code()==401) NikeException.Type.AUTH else NikeException.Type.SIMPLE, serverMassage = errorMessage)
                }catch (exception:Exception){
                    Timber.e(exception)
                }
            }

            return NikeException(NikeException.Type.SIMPLE, R.string.unkonwn_error)
        }
    }

}