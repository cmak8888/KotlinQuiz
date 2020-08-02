package com.csci448.cmak.kotlinquiz2
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"



class QuizViewModel : ViewModel() {

    private val questionBank: MutableList<Question> = mutableListOf()
    private var score = 0

    init {
        questionBank.add(Question(R.string.question1, false))
        questionBank.add(Question(R.string.question2, false))
        questionBank.add(Question(R.string.question3, true))
        questionBank.add(Question(R.string.question2, true))
        Log.d(TAG, "ViewModel instance about to be destroyed")

    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }

    private var currentQuestionIndex = 0


    private val currentQuestion: Question
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId

    val currentQuestionAnswer: Boolean
        get() = currentQuestion.isAnswerTrue

    val currentScore: Int
        get() = score

    fun isAnswerCorrect(answerChoice: Boolean): Boolean {
        if(answerChoice == currentQuestionAnswer) {
            score++
            return true
        }

        return false
    }

    fun moveToNextQuestion() {
        if(currentQuestionIndex == questionBank.size - 1) {
            currentQuestionIndex = 0
        } else {
            currentQuestionIndex++
        }
        if (checkAnswered()) {
            moveToNextQuestion()
        }
    }

    fun moveToPreviousQuestion() {
        if(currentQuestionIndex == 0) {
            currentQuestionIndex = questionBank.size - 1
        } else {
            currentQuestionIndex--
        }
        if (checkAnswered()) {
            moveToPreviousQuestion()
        }
    }

    fun getCurrentQuestionIndex(): Int {
        return currentQuestionIndex
    }

    fun setCurrentQuestionIndex(index: Int) {
        currentQuestionIndex = index
    }

    fun getCurrentAnswer(): Boolean {
        return questionBank[currentQuestionIndex].isAnswerTrue
    }

    fun hasAnswered(){
        currentQuestion.answered = true
    }

    fun checkAnswered(): Boolean {
        return currentQuestion.answered
    }

}