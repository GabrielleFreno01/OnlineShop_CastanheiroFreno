package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.database.entity.OrderEntity;
import com.android.onlineshop_castanheirofreno.database.pojo.OrderWithItem;
import com.android.onlineshop_castanheirofreno.ui.orders.OrderViewModel;
import com.android.onlineshop_castanheirofreno.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    private Context context;
    private List<OrderWithItem> orders;
    private RecyclerViewItemClickListener myListener;

    public OrdersAdapter(Context context, List<OrderWithItem> orders, RecyclerViewItemClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_order, parent, false);
        OrdersViewHolder holder = new OrdersViewHolder(view);
        view.setOnClickListener(v -> myListener.onItemClick(view, holder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            myListener.onItemLongClick(view, holder.getAdapterPosition());
            return true;
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        OrderWithItem orderWithItem = orders.get(position);

        holder.textView_orderId.setText(Long.toString(orderWithItem.order.getIdOrder()));
        holder.textView_creationDate.setText(orderWithItem.order.getCreationDate());
        holder.textView_productName.setText(orderWithItem.item.getName());
        holder.textView_status.setText(orderWithItem.order.getStatus());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrdersViewHolder extends RecyclerView.ViewHolder{
        TextView textView_orderId;
        TextView textView_creationDate;
        TextView textView_productName;
        TextView textView_status;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_orderId = itemView.findViewById((R.id.textView_orderId));
            textView_creationDate = itemView.findViewById(R.id.textView_creationDate);
            textView_productName = itemView.findViewById(R.id.textView_productName);
            textView_status = itemView.findViewById(R.id.textView_status);


        }
    }

    public void setData(final List<OrderWithItem> data) {
        if (orders == null) {
            orders = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return orders.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(((OrderEntity) orders.get(oldItemPosition).order).getIdOrder()==((OrderEntity) data.get(newItemPosition).order).getIdOrder())
                        return true;
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        OrderWithItem newOrder = (OrderWithItem) data.get(newItemPosition);
                        OrderWithItem oldOrder = (OrderWithItem) orders.get(newItemPosition);
                        return newOrder.order.getIdOrder()== oldOrder.order.getIdOrder()
                                && newOrder.order.getCreationDate() == oldOrder.order.getCreationDate()
                                && newOrder.order.getDeliveryDate() == oldOrder.order.getDeliveryDate()
                                && newOrder.order.getOwner().equals(oldOrder.order.getOwner())
                                && newOrder.order.getIdItem() == oldOrder.order.getIdItem()
                                && newOrder.order.getPrice() == oldOrder.order.getPrice()
                                && newOrder.order.getStatus() == oldOrder.order.getStatus();
                }
            });
            orders = data;
            result.dispatchUpdatesTo(this);
        }
    }

}

