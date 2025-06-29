package com.example.dictionaryapp.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.core.quiz.gamecontroller.GameController
import com.example.dictionaryapp.core.quiz.storage.TestsStorage
import com.example.dictionaryapp.databinding.FragmentQuizBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class QuizFragment : Fragment() {
    private var gameController: GameController? = null
    private var currentAndTotalIndex: TextView? = null
    private var question: TextView? = null
    private var options: RadioGroup? = null
    private var nextButton: ExtendedFloatingActionButton? = null
    private var changed: Boolean = false
    private var _binding: FragmentQuizBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val quizViewModel =
            ViewModelProvider(this).get(QuizViewModel::class.java)

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        val root: View = binding.root

        TestsStorage.fillData()
        gameController = GameController(TestsStorage.getData())
        startGame()

        loadViews()
        setActionsToView()

        return root
    }
    private fun setActionsToView() {

        lateinit var radioButtonClicked: RadioButton
        var isAnswerTrue: Boolean
        var isCheck = false
        nextButton?.shrink()
        options?.forEach {
            it.setOnClickListener { it ->
                radioButtonClicked = it as RadioButton
                nextButton?.isEnabled = true
                nextButton?.extend()
                isCheck = true
            }
        }


        nextButton?.setOnClickListener {

            if (isCheck) {
                isCheck = false
                options?.forEach {
                    if (it == radioButtonClicked && gameController?.checkAnswer(
                            radioButtonClicked.text.toString()
                        ) == true
                    ) {
                        it.setBackgroundResource(R.drawable.shape_of_correct)
                    }
                    if (it == radioButtonClicked && gameController?.checkAnswer(
                            radioButtonClicked.text.toString()
                        ) == false
                    ) {
                        it.setBackgroundResource(R.drawable.shape_of_wrong)
                        options!!.forEach { findTrue ->

                            if ((findTrue as RadioButton).text.toString() == gameController?.getAnswer()) {
                                findTrue.setBackgroundResource(R.drawable.shape_of_correct)
                            }
                        }
                    }

                }
                options?.forEach { it.isEnabled = false }

                nextButton?.shrink()
                nextButton?.setIconResource(R.drawable.right_24)

                if (gameController?.getCurrentLevel() == gameController!!.getTotal()) {
                    nextButton?.extend()
                    nextButton?.setIconResource(R.drawable.address_card_24)
                    nextButton?.setText("Finish")
                }
            } else {
                gameController?.let {

                    optionClicked(radioButtonClicked)
                    Log.d(
                        "fff",
                        "${gameController!!.getCurrentLevel()} and total ${gameController!!.getTotal()}"
                    )
                    isCheck = true
                    nextButton?.setIconResource(R.drawable.check_circle_24)
                    options?.forEach {
                        it.isEnabled = true
                        it.setBackgroundResource(R.drawable.radiobutton)
                    }

                    if (gameController?.isFinished() == true) {
                        binding.noSavedWord.text =
                            "Siz ${gameController!!.getTruesCount()} ta tug'ri bajardingiz"
                        binding.quizLayout.visibility = View.GONE
                    }

                }
            }

        }
    }

    private fun optionClicked(radioButton: RadioButton?) {
        val clickedOptionText = radioButton?.text.toString()
        gameController?.let {
            it.checkAnswerAndNext(clickedOptionText)

            if (!it.isFinished()) {
                setDataToView()
            }
        }
    }

    private fun setDataToView() {


        nextButton?.isEnabled = false
        gameController?.let { a ->


            currentAndTotalIndex?.text = "${a.getCurrentLevel()} / ${a.getTotal()}"
            question?.text = "${a.getCurrentLevel()}. ${a.getQuestion()}"

            val variants = a.getOptions()

            options?.clearCheck()

            options?.forEachIndexed { index, view ->
                if (index < a.totalOptions()) {
                    (view as RadioButton).text = variants[index]
                    view.visibility = View.VISIBLE
                } else {
                    (view as RadioButton).visibility = View.INVISIBLE
                }

            }
        }
    }


    private fun loadViews() {
        currentAndTotalIndex = binding.textCurrentAndTotal
        question = binding.textQuestion
        options = binding.textOptions
        nextButton = binding.buttonNext
    }

    private fun startGame() {
        if (DictionaryDb.getInstance()!!.getSaved().count >= 5) {

            binding.animationNoSavedWord.visibility = View.INVISIBLE
            binding.startWord.visibility = View.VISIBLE
            binding.startWord.text =
                "Let's play quiz game with words you liked"

            binding.startBtn.visibility = View.VISIBLE
            binding.startBtn.text = "Start"

            binding.animationStart.visibility = View.VISIBLE
        } else {

            binding.animationNoSavedWord.visibility = View.VISIBLE
            binding.noSavedWord.text =
                "You should save five words at least to play quiz game!"

            binding.startWord.visibility = View.GONE
            binding.startBtn.visibility = View.GONE
            binding.animationStart.visibility = View.GONE
        }
        binding.startBtn.setOnClickListener {
            setDataToView()
            binding.quizLayout.visibility = View.VISIBLE
            binding.startBtn.visibility = View.GONE
            binding.startWord.visibility = View.GONE
            binding.animationStart.visibility = View.GONE

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}