package com.example.islami.hom.tabs.radio

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class RadioResponse(
    @SerializedName("radios")
    val radios: List<Radio>
)

interface RadioApiService {
    @GET("radios?language=ar")
    suspend fun getRadioList(): RadioResponse
}
