package com.mdlicht.zb.exampleproject.collapsingtoolbar.model

object DataProvider {
    fun getIntroduction(): List<Introduction> = listOf<Introduction>(
        Introduction("title1", "contents1"),
        Introduction("title2", "contents2"),
        Introduction("title3", "contents3"),
        Introduction("title4", "contents4"),
        Introduction("title5", "contents5"),
        Introduction("title6", "contents6"),
        Introduction("title7", "contents7")
    )

    fun getStroy(): List<Story> = listOf<Story>(
        Story("title1", listOf("contents1-1", "contents1-2", "contents1-3")),
        Story("title2", listOf("contents2-1", "contents2-2")),
        Story("title3", listOf("contents3-1")),
        Story("title4", listOf("contents4-1", "contents4-2", "contents4-3", "contents4-4"))
    )
}