package com.fghilmany.academy2.ui.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.CourseEntity
import com.fghilmany.academy2.ui.detail.DetailCourseActivity
import kotlinx.android.synthetic.main.items_academy.view.*


class AcademyAdapter : RecyclerView.Adapter<AcademyAdapter.CourseViewHolder>(){
    private var listCourse = ArrayList<CourseEntity>()

    fun setCourse(course: List<CourseEntity>?){
        if (course == null) return
        this.listCourse.clear()
        this.listCourse.addAll(course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_academy, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int = listCourse.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = listCourse[position]
        holder.bind(course)
    }

    class CourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(course: CourseEntity){
            with(itemView){
                tv_item_title.text = course.title
                tv_item_description.text = course.description
                tv_item_date.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                itemView.setOnClickListener {
                    val i = Intent(itemView.context, DetailCourseActivity::class.java)
                    i.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(i)
                }
                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(img_poster)
            }
        }

    }

}