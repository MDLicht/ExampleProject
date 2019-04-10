package com.mdlicht.zb.exampleproject.xmlparsing.model

import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false, name = "note")
class Note : BaseModel {
    @field:Element(name = "to")
    var to: String? = null
    @field:Element(name = "from")
    var from: String? = null
    @field:Element(name = "heading")
    var heading: String? = null
    @field:Element(name = "body")
    var body: String? = null

    override fun getViewType(): Int {
        return -1
    }
}