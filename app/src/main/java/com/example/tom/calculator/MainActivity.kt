package com.example.tom.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

private const val TEXT_CONTENTS = "TextContent"

class MainActivity : AppCompatActivity() {

    // once kotlinx.android.synthetic.main.activity_main.* added, not need to define android components
    /*
    // lateinit = non-null type but do not have to initialize
    // this must be initialized before usage
    private lateinit var result :EditText
    private lateinit var newNumber :EditText

    // lazy = it is a funtion. when the function called first time, it execute and save result.
    //        Once it is executed, you cannot rerun but use the result of the first time.
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE){ findViewById<TextView>(R.id.operation) }
    */

    //variables to hold the operands adn type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add number button listener
        // when user clicks, the value is added on newNumber text box
        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        // operation listener
        var opListener = View.OnClickListener { v ->

            val op = (v as Button).text.toString()
            try{

                // arithmetic operation
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            }
            catch(e: NumberFormatException){
                newNumber.setText("")
            }

            pendingOperation = op
            operation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

        //negation button listner
        buttonNeg.setOnClickListener({view ->
            val value = newNumber.text.toString()
            if( value.isEmpty()){
                newNumber.setText("-")
            }else{
                try{
                    var doubleValue = value.toDouble()
                    doubleValue *=-1
                    newNumber.setText(doubleValue.toString())
                }
                catch(e : NumberFormatException)
                {
                    newNumber.setText("")
                }
            }
        })
    }

    // onSaveInstanceState saves operation symbol
    // onRestoreInstanceState loads saved operstion symbol when it changed view mode
    override fun onRestoreInstanceState(savedInstanceState: Bundle?){
        super.onRestoreInstanceState(savedInstanceState)
        val savedString = savedInstanceState?.getString(TEXT_CONTENTS,"")
        operation?.text = savedString
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(TEXT_CONTENTS, operation?.text.toString())
    }


    // arithmetic operation
    // two inputs and an operation symbol are input by user, arithmetic operation occer
    // the first input is stored in operand1, the second one does not need to be saved, so used as value varialbe
    private fun performOperation(value: Double, operation: String){
        //first num
        if(operand1 == null){
            operand1 = value
        }
        //second num
        else {

            // default operation
            if(pendingOperation == "="){
                pendingOperation = operation
            }

            when(pendingOperation){
                "=" -> operand1 = value
                "/" -> if(value == 0.0){
                            operand1 = Double.NaN
                        }
                        else{
                            operand1 = operand1!! / value
                        }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        //calculating result is saved
        result.setText(operand1.toString())
        //erase input number
        newNumber.setText("")
    }
}
