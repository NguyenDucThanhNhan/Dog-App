package com.example.a6_dogapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a6_dogapp.R
import com.example.a6_dogapp.model.DogBreed
import com.example.a6_dogapp.viewmodel.DogAdapter
import com.example.a6_dogapp.viewmodel.DogsApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ListFragment : Fragment() {
    private lateinit var apiService: DogsApiService
    private lateinit var rvDogs: RecyclerView
//    private var dogBreeds: ArrayList<DogBreed> = ArrayList()
    private lateinit var dogsAdapter: DogAdapter


    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        rvDogs = view.findViewById(R.id.rv_dog)
        val dogBreeds = mutableListOf<DogBreed>()
        dogsAdapter = DogAdapter(dogBreeds)
        rvDogs.adapter = dogsAdapter
        rvDogs.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )

        apiService = DogsApiService()

        val dogsObserver = object : DisposableSingleObserver<List<DogBreed>>() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(dog: List<DogBreed>) {
                dogBreeds.addAll(dog)
                dogsAdapter.notifyDataSetChanged()
            }

            override fun onError(e: Throwable) {
                Log.d("DEBUG", "Fail")
            }
        }

        disposable.add(
            apiService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(dogsObserver)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.mi_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dogsAdapter.filter.filter(newText)
                return true
            }
        })
    }

}