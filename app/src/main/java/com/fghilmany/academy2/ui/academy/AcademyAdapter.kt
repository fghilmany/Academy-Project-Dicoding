package com.fghilmany.academy2.ui.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fghilmany.academy2.R
import com.fghilmany.academy2.data.source.local.entity.CourseEntity
import com.fghilmany.academy2.ui.detail.DetailCourseActivity
import kotlinx.android.synthetic.main.items_academy.view.*


class AcademyAdapter internal constructor(): PagedListAdapter<CourseEntity, AcademyAdapter.CourseViewHolder>(
    DIFF_CALLBACK){

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem.courseId == newItem.courseId
            }

            override fun areContentsTheSame(oldItem: CourseEntity, newItem: CourseEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_academy, parent, false)
        return CourseViewHolder(view)
    }


    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        if (course != null){
            holder.bind(course)
        }
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