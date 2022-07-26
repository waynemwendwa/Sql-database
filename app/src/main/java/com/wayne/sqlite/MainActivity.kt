package com.wayne.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputName: EditText = findViewById(R.id.inputName)
        val inputQuantity: EditText = findViewById(R.id.inputQuantity)
        val inputPrice: EditText = findViewById(R.id.inputPrice)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val buttonShow : Button = findViewById(R.id.buttonShow)
        val buttonDelete : Button = findViewById(R.id.buttonDelete)
        val buttonFetchOne : Button = findViewById(R.id.buttonFetchOne)

        //Database
        val db = DBHelper(this,null)

        buttonSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val quantity = inputQuantity.text.toString().trim().toIntOrNull()
            val price = inputPrice.text.toString().trim().toIntOrNull()

            if (quantity != null && price != null && name.isNotEmpty()) {
                //save to our database
                db.addProduct(name, price, quantity)

                //clear inputs
                inputName.text.clear()
                inputQuantity.text.clear()
                inputPrice.text.clear()

                Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show()

            }
        }

        buttonShow.setOnClickListener {
            val products = db.getProducts() //cursor
            while (products!!.moveToNext()){
                val id = products.getInt(0)
                val name = products.getString(1)
                val quantity = products.getInt(2)
                val price = products.getString(3)
                Log.d("PRODUCT","$id : $name : $quantity: $price")
            }
            db.close()
        }

        buttonDelete.setOnClickListener {
            db.deleteProduct(1)
        }

        buttonFetchOne.setOnClickListener {
            val p = db.fetchOneProduct(2)
            p!!.moveToFirst()
            val name = p.getString(1)
            Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
        }
    }
}