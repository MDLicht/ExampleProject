package com.mdlicht.zb.exampleproject.baserecyclerview.etc

import com.mdlicht.zb.exampleproject.baserecyclerview.model.Amodel
import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel
import com.mdlicht.zb.exampleproject.baserecyclerview.model.Bmodel

object SampleUtil {
    fun getSampleDataSet(): MutableList<BaseModel> {
        val dataSet = mutableListOf<BaseModel>()
        for(i in 0..60) {
            if(i % 2 == 0)
                dataSet.add(Amodel("$i - Amodel"))
            else
                dataSet.add(Bmodel("$i - Bmodel"))
        }
        return dataSet
    }
}