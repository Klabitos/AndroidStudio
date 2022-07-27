package com.klabitos.apitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klabitos.apitest.`interface`.API_Service
import com.klabitos.apitest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding

    lateinit var mRecyclerView : RecyclerView
    lateinit var mAdapter : RecyclerAdapter

    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) //Lo modificamos por el viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.svDogs.setOnQueryTextListener(this) //Implementamos el listener que hemos creado
    }
    fun setUpRecyclerView(){
        mAdapter = RecyclerAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this) //linkea con el recycleViews
        binding.rvDogs.adapter = mAdapter
    }
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){ //Este nos avisa al pulsar enter, es el importante
            searchByName(query.lowercase())
        }
        return true
    }

    private fun searchByName(breed:String){
        CoroutineScope(Dispatchers.IO).launch { //dentro de las llaves es hilo asincrono
            val call = getRetrofit().create(API_Service::class.java).getDogsByBreeds("$breed/images")
            val puppies = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    //show Recyclerview
                    val images = puppies?.images ?: emptyList() //Si viene vacio le damos empty list
                    dogImages.clear()
                    dogImages.addAll(images) //Limpiamos y añadimos
                    mAdapter.notifyDataSetChanged() //Se vuelva a cargar el adapter
                }else{
                    showError()
                }
                hideKeyboard()
            }

        }

    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError(){
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true; //Nos avisa con cada cambio, nos da igual
    }

}