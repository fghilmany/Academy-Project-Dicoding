package com.fghilmany.academy2.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.CourseEntity
import com.fghilmany.academy2.utils.DataDummy
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
            val course = DataDummy.generateDummyCourses()
            val adapter = BookmarkAdapter(this)
            adapter.setCourse(course)

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
