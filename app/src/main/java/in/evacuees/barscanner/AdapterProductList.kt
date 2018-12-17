package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.databinding.ItemProductListBinding
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class AdapterProductList(val context: Context, val list: MutableList<Product>) : RecyclerView.Adapter<AdapterProductList.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterProductList.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_product_list, p0, false)

        val binding = DataBindingUtil.bind<ItemProductListBinding>(view)

        return ViewHolder(binding!!)

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AdapterProductList.ViewHolder, poistion: Int) {
        val data = list[poistion]
        holder.onBind(data)
    }

    class ViewHolder(val b: ItemProductListBinding) : RecyclerView.ViewHolder(b.root) {
        fun onBind(data: Product) {
            b.product = data
            b.executePendingBindings()
        }

    }
}