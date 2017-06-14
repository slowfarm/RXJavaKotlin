package eva.android.com.rxjava.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import eva.android.com.javarx.R
import eva.android.com.rxjava.Models.User
import io.realm.RealmResults


class CardAdapter(private val mItems: RealmResults<User>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        with(viewHolder) {
            name.text = mItems[i].login
            id.text = mItems[i].id.toString()
            Picasso.with(itemView.context)
                    .load(mItems[i].avatarUrl)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_error)
                    .into(avatar)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id) as TextView
        val name: TextView = itemView.findViewById(R.id.name) as TextView
        val avatar: ImageView = itemView.findViewById(R.id.avatar) as ImageView
    }
}