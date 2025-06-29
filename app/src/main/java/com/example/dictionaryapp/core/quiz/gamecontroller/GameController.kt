package com.example.dictionaryapp.core.quiz.gamecontroller

import android.widget.RadioButton
import com.example.dictionaryapp.core.quiz.ticket.Ticket

class GameController(private var ticket: ArrayList<Ticket>) {

    private var currentIndex = 0
    private var wrongAnswers = 0
    private var trueAnswers = 0


    init {
        ticket.shuffle()
    }

    private fun getCurrentTicket(): Ticket = ticket[currentIndex]

    fun getQuestion(): String = getCurrentTicket().question
    fun getOptions(): List<String> = getCurrentTicket().options.shuffled()
    fun getCurrentLevel(): Int = currentIndex + 1
    fun getTotal(): Int = ticket.size
    fun getAnswer(): String = ticket[currentIndex].answer

    fun checkAnswerAndNext(clickedAnswer: String) {

        if (clickedAnswer.equals(getCurrentTicket().answer, ignoreCase = true)) {
            trueAnswers++

        } else {
            wrongAnswers++

        }
            currentIndex++

    }

    fun checkAnswer(clickedAnswer: String): Boolean {
        return clickedAnswer.equals(getCurrentTicket().answer, ignoreCase = true)
    }

    fun isFinished(): Boolean = ticket.size == currentIndex

    fun totalOptions(): Int = getCurrentTicket().options.size

    fun getTruesCount(): Int = trueAnswers

    fun getWrongCount(): Int = wrongAnswers

    fun clear() {
        trueAnswers = 0
        wrongAnswers = 0
        currentIndex = 0
        ticket.clear()
    }
}



