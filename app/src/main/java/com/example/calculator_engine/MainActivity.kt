package com.example.calculator_engine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator_engine.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import javax.xml.xpath.XPathExpression

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumberic = false
    var stateError = false
    //var lastDot = false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
    }

    fun onClearEntryClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        //lastDot = false
        lastNumberic = false
        binding.resultTv.visibility = View.GONE
    }
    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumberic = false

    }
    fun onBackSpaceClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try{
            val lastChar = binding.dataTv.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }
    fun onOperatorClick(view: View) {
        if(!stateError && lastNumberic){
            binding.dataTv.append((view as Button).text)
            //lastDot = false
            lastNumberic = false
            onEqual()
        }
    }
    fun onDigitClick(view: View) {
        if(stateError){
            binding.dataTv.text = (view as Button).text
            stateError = false
        }else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumberic = true
        onEqual()
    }
    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
    }
    fun onEqual(){
        if(lastNumberic && !stateError){
            var txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            }catch (ex: ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumberic = false
            }
        }

    }
}