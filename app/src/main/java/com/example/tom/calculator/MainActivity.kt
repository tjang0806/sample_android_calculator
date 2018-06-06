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

        /*
        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)


        // Data input buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        // operation input buttons
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        */

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

        var opListener = View.OnClickListener { v ->

            val op = (v as Button).text.toString()
            try{
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


    private fun performOperation(value: Double, operation: String){
        //first num
        if(operand1 == null){
            operand1 = value
        }
        //second num
        else {

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
        result.setText(operand1.toString())
        newNumber.setText("")
    }
}