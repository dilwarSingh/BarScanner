package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.tables.SoldProduct
import `in`.evacuees.barscanner.databinding.ItemSoldProductsBinding
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class AdapterSoldProductList(val context: Context, val list: MutableList<SoldProduct>) : RecyclerView.Adapter<AdapterSoldProductList.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sold_products, p0, false)
        val b = DataBindingUtil.bind<ItemSoldProductsBinding>(view)

        return ViewHolder(b!!)
    }

    fun listUpdated() {
        println(list.size)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
        val soldProduct = list[position]
        holder.onBind(soldProduct)
        /*   holder.binding.id.text = soldProduct.productId
           holder.binding.name.text = soldProduct.productName

           holder.binding.executePendingBindings()*/

    }

    class ViewHolder(val binding: ItemSoldProductsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(soldProduct: SoldProduct) {
            binding.soldProduct = soldProduct
            binding.executePendingBindings()
        }

    }
}