package com.cpa.cpa_word_problem.utils

import android.content.Context
import android.util.Log
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

object KakaoUtils {
    fun share(context: Context) {
        val template = FeedTemplate
            .newBuilder(
                ContentObject.newBuilder(
                    "CPA 말문제", // 공유링크 타이틀
                    "https://user-images.githubusercontent.com/50787869/106095186-ad82d580-6176-11eb-861b-f4475883162c.png", // 공유링크 이미지
                    LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com").build()
                )
                    .setDescrption("#CPA #회계학 #경영학") // 공유링크 설명
                    .setImageHeight(300)
                    .setImageWidth(600)
                    .build()
            )
            .addButton(
                ButtonObject(
                    "앱에서 보기", LinkObject.newBuilder() // 버튼 타이틀
                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()
                )
            )
            .build()

        val serverCallbackArgs: MutableMap<String, String> =
            HashMap()
        serverCallbackArgs["user_id"] = "\${current_user_id}"
        serverCallbackArgs["product_id"] = "\${shared_product_id}"

        KakaoLinkService.getInstance().sendDefault(
            context,
            template,
            serverCallbackArgs,
            object : ResponseCallback<KakaoLinkResponse?>() {
                override fun onFailure(errorResult: ErrorResult) {
                    Log.e("yoonseop", errorResult.toString())
                }

                override fun onSuccess(result: KakaoLinkResponse?) {
                }
            }
        )
    }
}