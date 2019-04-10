package com.mdlicht.zb.exampleproject.xmlparsing.api

import com.mdlicht.zb.exampleproject.xmlparsing.model.Note
import io.reactivex.Single
import retrofit2.http.GET

interface XmlParsingService {
    @GET("xml/note.xml")
    fun getNote(): Single<Note>
}