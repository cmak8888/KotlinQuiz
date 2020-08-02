package com.csci448.cmak.kotlinquiz2

class Question(question: Int, isTrue: Boolean) {
    val textResId: Int = question
    val isAnswerTrue: Boolean = isTrue
    var answered : Boolean = false


}