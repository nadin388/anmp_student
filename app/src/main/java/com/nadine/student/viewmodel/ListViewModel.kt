package com.nadine.student.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nadine.student.model.Student

class ListViewModel(app:Application): AndroidViewModel(app) { //androidviewmodel bisa context
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val loadingLD = MutableLiveData<Boolean>() //true = sedang loading, false = sdh tuntas
    val errorLD = MutableLiveData<Boolean>()
    val TAG = "student data fetch"
    private var queue:RequestQueue? = null

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false
//        studentsLD.value = arrayListOf(
//            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100" +
//                    ".jpg/cc0000/ffffff"),
//            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100" +
//                    ".jpg/5fa2dd/ffffff"),
//            Student("11204","Dinny","1994/10/07","6827808747","http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
//        )

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"

        val sr = StringRequest(
            Request.Method.GET, url, {
                //apa yg terjadi kalo sukses
                loadingLD.value = false
                errorLD.value = true
                Log.d("showvoley", it)

                //read json
                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType) //hasilnya pasti bentuk arrayList
                studentsLD.value = result as ArrayList<Student>
            },
            {
                //apa yg terjadi kalo error/failed
                errorLD.value = true
                loadingLD.value = false

            }
        )
        sr.tag = TAG
        queue?.add(sr)


//        loadingLD.value = false
//        errorLD.value = false
    }
}