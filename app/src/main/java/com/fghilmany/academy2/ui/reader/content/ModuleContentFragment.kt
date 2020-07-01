package com.fghilmany.academy2.ui.reader.content

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.source.local.entity.ModuleEntity
import com.fghilmany.academy2.ui.reader.CourseReaderViewModel
import com.fghilmany.academy2.viewmodel.ViewModelFactory
import com.fghilmany.academy2.vo.Status
import kotlinx.android.synthetic.main.fragment_module_content.*

/**
 * A simple [Fragment] subclass.
 */


class ModuleContentFragment : Fragment() {

    private lateinit var viewModel: CourseReaderViewModel

    companion object{
        val TAG = ModuleContentFragment::class.java.simpleName

        fun newInstance(): ModuleContentFragment {
            return ModuleContentFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            progress_bar.visibility = View.VISIBLE
            viewModel.selectedModule.observe(viewLifecycleOwner, Observer{ moduleEntity ->
                if (moduleEntity != null) {
                    Log.e("CEK_response_status","{$moduleEntity.status}")
                    when (moduleEntity.status) {
                        Status.LOADING -> progress_bar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            progress_bar.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }

                            setButtonNextPrevState(moduleEntity.data)
                            if (!moduleEntity.data.read){
                                viewModel.readContent(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }

                    btn_next.setOnClickListener{ viewModel.setNextPage()}

                    btn_prev.setOnClickListener{ viewModel.setPrevPage()}
                }
            })
        }
    }

    private fun setButtonNextPrevState(module: ModuleEntity) {
        if (activity != null){
            when (module.position){
                0 -> {
                    btn_next.isEnabled = false
                    btn_next.isEnabled = true
                }

                viewModel.getModuleSize() -1 -> {
                    btn_next.isEnabled = true
                    btn_next.isEnabled = false
                }

                else -> {
                    btn_next.isEnabled = true
                    btn_next.isEnabled = true
                }
            }
        }

    }

    private fun populateWebView(module: ModuleEntity) {
        web_view.loadData(module.contentEntity?.content, "text/html", "UTF-8")
    }

}
