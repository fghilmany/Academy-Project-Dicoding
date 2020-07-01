package com.fghilmany.academy2.ui.reader.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.source.local.entity.ModuleEntity
import com.fghilmany.academy2.ui.reader.CourseReaderActivity
import com.fghilmany.academy2.ui.reader.CourseReaderCallback
import com.fghilmany.academy2.ui.reader.CourseReaderViewModel
import com.fghilmany.academy2.viewmodel.ViewModelFactory
import com.fghilmany.academy2.vo.Status
import kotlinx.android.synthetic.main.fragment_module_list.*

/**
 * A simple [Fragment] subclass.
 */
class ModuleListFragment : Fragment(), MyAdapterClickListener {

    private lateinit var viewModel: CourseReaderViewModel

    companion object {
        val TAG = ModuleListFragment::class.java.simpleName

        fun neWInstance() : ModuleListFragment = ModuleListFragment()
    }

    private lateinit var adapterList: ModuleListAdapter
    private lateinit var courseReaderCallback: CourseReaderCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
        adapterList = ModuleListAdapter(this)

        progress_bar.visibility = View.VISIBLE
        viewModel.modules.observe(this, Observer{ moduleEntities ->
            if (moduleEntities != null) {
                when (moduleEntities.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        populateRecyclerView(moduleEntities.data as List<ModuleEntity>)
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderActivity
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        progress_bar.visibility = View.GONE
        adapterList.setModules(modules)
        with(rv_module){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterList
        }
        val dividerItemDecoration = DividerItemDecoration(rv_module.context, DividerItemDecoration.VERTICAL)
        rv_module.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClicked(position: Int, moduleId: String) {
        courseReaderCallback.moveTo(position, moduleId)
        viewModel.setSelectedModule(moduleId)

    }

}
