package com.example.view

import com.example.core.ShutdownerService
import com.example.core.toHoursAndMinutes
import com.example.core.toSeconds
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*
import java.awt.Toolkit


class MainView : View("PC Shutdowner") {
    private val controller: ShutdownerService by inject()
    private val timeString = SimpleStringProperty()

    override val root = form {
        borderpane {
            paddingTop = 5
            paddingBottom = 5
            paddingHorizontal = 10
        }
        fieldset {
            field("How much time do you want to wait until shutdown? (FORMAT: HHhMM). Example: 2h30 or 2h") {
                textfield(timeString)
            }

            val labelMessage = label()

            hbox {
                button("ABORT shutdown") {
                    action {
                        controller.abortShutdown()
                        labelMessage.setMessage("All shutdowns aborted.")
                    }
                }

                pane {
                    this.hgrow = Priority.ALWAYS
                }

                button("SCHEDULE shutdown") {
                    isDefaultButton = true
                    action {
                        kotlin.runCatching {
                            timeString.value.toSeconds()
                        }.onFailure {
                            handleException(it, labelMessage)
                            return@action
                        }.onSuccess { seconds ->
                            Toolkit.getDefaultToolkit().beep();
                            val result = buildAlert(timeString.value).showAndWait()
                            if (result.isPresent && result.get() == ButtonType.OK) {
                                controller.programShutdown(seconds)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun handleException(it: Throwable, label: Label) {
        Toolkit.getDefaultToolkit().beep();
        if (it is IllegalArgumentException) {
            label.setError(it.message)
        } else {
            label.setMessage("Oops, something went wrong. Aborting shutdown if started.")
            controller.abortShutdown()
        }
    }

    private fun buildAlert(time: String): Alert {
        val (hours, minutes) = time.toHoursAndMinutes().map { it.toInt() }
        return Alert(
            Alert.AlertType.CONFIRMATION,
            "Are you sure you want to turn off your computer in $hours hour(s) and $minutes minute(s)?"
        )
    }


    private fun Label.setError(text: String?) {
        this.textFill = c("red")
        this.text = text
    }

    private fun Label.setMessage(text: String?) {
        this.textFill = c("green")
        this.text = text
    }
}
