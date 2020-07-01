package com.fghilmany.academy2.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_bookmark.*

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]

            val adapter = BookmarkAdapter(this)
            progress_bar.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(viewLifecycleOwner, Observer { courses ->
                progress_bar.visibility = View.GONE
                adapter.setCourse(courses)
                adapter.notifyDataSetChanged()
            })

            with(rv_bookmark){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(activity).apply {
                setType(mimeType)
                setChooserTitle("Bagikan aplikasi ini sekarang")
                setText(resources.getString(R.string.share_text, course.title))
                startChooser()
            }
        }
    }

}
