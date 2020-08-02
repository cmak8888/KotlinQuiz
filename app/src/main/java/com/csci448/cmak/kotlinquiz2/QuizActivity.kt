package com.csci448.cmak.kotlinquiz2


import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import kotlinx.android.synthetic.main.activity_quiz.*
import androidx.lifecycle.ViewModelProvider

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class QuizActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView

    private var didCheat : Boolean = false

    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_quiz)
        val factory = QuizViewModelFactory()
        quizViewModel = ViewModelProvider(this@QuizActivity, factory).get(QuizViewModel::class.java)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.setCurrentQuestionIndex(currentIndex)
        val trueButton: Button = findViewById(R.id.true_button)
        val falseButton: Button = findViewById(R.id.false_button)
        val prevButton: Button = findViewById(R.id.previous_button)
        val nextButton: Button = findViewById(R.id.next_button)
        val cheatButton: Button = findViewById(R.id.cheat_button)
        scoreTextView = findViewById(R.id.score_text_view)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener{ checkAnswer(true)}
        falseButton.setOnClickListener{checkAnswer(false)}
        prevButton.setOnClickListener{moveToQuestion(-1)}
        nextButton.setOnClickListener{moveToQuestion(1)}
        cheatButton.setOnClickListener{launchCheat()}

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume() called")
    }

    override fun onPause() {
        Log.d(LOG_TAG, "onPause() called")
        super.onPause()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(LOG_TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.getCurrentQuestionIndex())


    }
    override fun onStop() {
        Log.d(LOG_TAG, "onStop() called")
        super.onStop()
    }


    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy() called")
        super.onDestroy()
    }

    override fun onActivityResult(requestCode : Int, statusCode : Int, data: Intent?) {
        Log.d(LOG_TAG, "Inside onActivityResult")
        super.onActivityResult(requestCode, statusCode, data)
        if (statusCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHEAT && data != null) {
                didCheat = CheatActivity.didUserCheat(data)
            }
        }

    }

    private fun launchCheat() {
        Log.d(LOG_TAG, "Inside launchCheat.")
        val cheatIntent = CheatActivity.createIntent(baseContext, quizViewModel.getCurrentAnswer())
        Log.d(LOG_TAG, "Intent Created")
        startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT)

    }


    private fun updateQuestion() {
        setCurrentScoreText()
        questionTextView.text = resources.getString(quizViewModel.currentQuestionTextId)

    }

    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }


    private fun checkAnswer(userAnswer: Boolean) {
        quizViewModel.hasAnswered()
        if(didCheat) {
            Toast.makeText(baseContext, R.string.cheat_toast, Toast.LENGTH_SHORT).show()
        } else {
            var isRight: Boolean = quizViewModel.isAnswerCorrect(userAnswer)

            if (isRight) {
                Toast.makeText(baseContext, R.string.correct_toast, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun moveToQuestion(direction: Int) {
        didCheat = false
        if(direction > 0) {
            quizViewModel.moveToNextQuestion()
        }

        else if(direction < 0) {
            quizViewModel.moveToPreviousQuestion()
        }

        updateQuestion()


    }

    fun getCurrentAnswer() = quizViewModel.getCurrentAnswer()
}
