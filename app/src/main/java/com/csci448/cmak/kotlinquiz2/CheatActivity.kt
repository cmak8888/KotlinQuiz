package com.csci448.cmak.kotlinquiz2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

private const val LOG_TAG: String = "448.CheatActivity"
private var EXTRA_ANSWER_IS_TRUE: String = "CORRECT ANSWER_KEY:" +
        ""
class CheatActivity : AppCompatActivity() {


    private lateinit var answerTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_cheat)
        Log.d(LOG_TAG, "Changing Content View to Activity Cheat")
        answerTextView = findViewById(R.id.the_answer)
        val cheatButton: Button = findViewById(R.id.cheats)

        val solution = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        cheatButton.setOnClickListener{
            Log.d(LOG_TAG, EXTRA_ANSWER_IS_TRUE)
            val answerText = when {
                solution -> R.string.t
                else -> R.string.f
            }
            Log.d(LOG_TAG, "Intent is :" + answerText )
            answerTextView.visibility = View.VISIBLE
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }


    }

    private fun setAnswerShownResult(isAnswerShown : Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun createIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            Log.d(LOG_TAG, "Creating Intent")
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

        fun didUserCheat(intent: Intent): Boolean {
            return intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        }
    }
}