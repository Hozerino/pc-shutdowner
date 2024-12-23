package com.example

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
    }

    init {
        label and  heading {
            padding = box(15.px)
            fontSize = 25.px
            fontWeight = FontWeight.BOLD
        }
    }
}
